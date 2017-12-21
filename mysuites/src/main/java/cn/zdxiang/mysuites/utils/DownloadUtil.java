package cn.zdxiang.mysuites.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by fuckyou on 8/1/16.
 */
public class DownloadUtil {

    public void enqueue(String url, Context context) {
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "美女骚货.apk");
        r.allowScanningByMediaScanner();
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        if (dm != null) {
            dm.enqueue(r);
        }
    }


    public void enqueue(String url, String fileName, Context context) {
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        r.allowScanningByMediaScanner();
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        if (dm != null) {
            dm.enqueue(r);
        }
    }


    public static void download(final Context context, final String url) {
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "美女骚货.apk") {
            @Override
            public void onBefore(Request request, int id) {

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                Log.d("fuckyou", "download==>" + progress);
            }

            @Override
            public void onResponse(File response, int id) {
                install(context, response.getAbsolutePath());
            }
        });
    }


    /**
     * install the apk
     *
     * @param context  context
     * @param filePath filePath
     * @return boolean
     */
    public static boolean install(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file.length() > 0 && file.exists() && file.isFile()) {
            intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
