package cn.zdxiang.sexygirl.widget.imagetrans;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.blankj.utilcode.util.ScreenUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import cn.zdxiang.sexygirl.R;
import cn.zdxiang.sexygirl.widget.MyHolderBitmap;
import cn.zdxiang.sexygirl.widget.imagetrans.listener.ImageLoad;


/**
 * Created by fuckyou on 17/6/1.
 */

public class MyImageLoad implements ImageLoad {
    private static final Pattern webPattern = Pattern.compile("http[s]*://[[[^/:]&&[a-zA-Z_0-9]]\\.]+(:\\d+)?(/[a-zA-Z_0-9]+)*(/[a-zA-Z_0-9]*([a-zA-Z_0-9]+\\.[a-zA-Z_0-9]+)*)?(\\?(&?[a-zA-Z_0-9]+=[%[a-zA-Z_0-9]-]*)*)*(#[[a-zA-Z_0-9]|-]+)?(.jpg|.png|.gif|.jpeg)?");
    private static final String ASSET_PATH_SEGMENT = "android_asset";

    private InputStream is;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public MyImageLoad(Context context, String content) {
        MyHolderBitmap myHolderBitmap = new MyHolderBitmap(context, content);
        Bitmap viewBitmap = getViewBitmap(myHolderBitmap, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
        baos = new ByteArrayOutputStream();
        if (viewBitmap == null) return;
        viewBitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
    }

    @Override
    public void loadImage(final String url, final LoadCallback callback, final ImageView imageView) {
        Uri uri = Uri.parse(url);
        if (isLocalUri(uri.getScheme())) {
            if (isAssetUri(uri)) {
                //是asset资源文件
                return;
            } else {
                //是本地文件
                loadImageFromLocal(uri.getPath(), callback, imageView);
                return;
            }
        } else {
            if (isNetUri(url)) {
                loadImageFromNet(url, callback, imageView);
                return;
            } else {
                is = new ByteArrayInputStream(baos.toByteArray());
                TileBitmapDrawable.attachTileBitmapDrawable(imageView, is, new TileBitmapDrawable.OnLoadListener() {
                    @Override
                    public void onLoadFinish(TileBitmapDrawable drawable) {
                        callback.loadFinish(drawable);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }
                });

            }
            Log.e("MyImageLoad", "未知的图片URL的类型");
        }
    }

    /**
     * 从网络加载图片
     */
    private void loadImageFromNet(String url, final LoadCallback callback, final ImageView imageView) {

        OkHttpImageLoad.get(url).url(url).listener(new OkHttpImageLoad.ImageDownLoadListener() {
            @Override
            public void inProgress(float progress, long total) {
                callback.progress(progress);
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(String path) {
                loadImageFromLocal(path, callback, imageView);
            }

            @Override
            public void onCancel() {

            }

        }).execute();
    }

    /**
     * 从本地加载图片
     */
    private void loadImageFromLocal(String url, final LoadCallback callback, final ImageView imageView) {
        TileBitmapDrawable.attachTileBitmapDrawable(imageView, url, new TileBitmapDrawable.OnLoadListener() {
            @Override
            public void onLoadFinish(TileBitmapDrawable drawable) {
                callback.loadFinish(drawable);
            }

            @Override
            public void onError(Exception ex) {

            }
        });
    }


    @Override
    public boolean isCache(String url) {
        if (isLocalUri(Uri.parse(url).getScheme())) {
            //是本地图片不用预览图
            return true;
        }
        return OkHttpImageLoad.isCached(url);
    }

    @Override
    public void destroy() {
        TileBitmapDrawable.clearCache();
    }

    @Override
    public void cancel(String url) {
        if (!isCache(url))
            OkHttpImageLoad.cancel(url);
    }

    private static boolean isNetUri(String url) {
        return webPattern.matcher(url).find();
    }

    private static boolean isLocalUri(String scheme) {
        return ContentResolver.SCHEME_FILE.equals(scheme)
                || ContentResolver.SCHEME_CONTENT.equals(scheme)
                || ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme);
    }

    public static boolean isAssetUri(Uri uri) {
        return ContentResolver.SCHEME_FILE.equals(uri.getScheme()) && !uri.getPathSegments().isEmpty()
                && ASSET_PATH_SEGMENT.equals(uri.getPathSegments().get(0));
    }

    /**
     * 把View绘制到Bitmap上
     *
     * @param width  该View的宽度
     * @param height 该View的高度
     * @return 返回Bitmap对象
     * add by csj 13-11-6
     */
    public static Bitmap getViewBitmap(View comBitmap, int width, int height) {
        Bitmap bitmap = null;
        if (comBitmap != null) {
            comBitmap.clearFocus();
            comBitmap.setPressed(false);
            boolean willNotCache = comBitmap.willNotCacheDrawing();
            comBitmap.setWillNotCacheDrawing(false);

            // Reset the drawing cache background color to fully transparent
            // for the duration of this operation
            int color = comBitmap.getDrawingCacheBackgroundColor();
            comBitmap.setDrawingCacheBackgroundColor(0);
            float alpha = comBitmap.getAlpha();
            comBitmap.setAlpha(1.0f);

            if (color != 0) {
                comBitmap.destroyDrawingCache();
            }

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            comBitmap.measure(widthSpec, heightSpec);
            comBitmap.layout(0, 0, width, height);

            comBitmap.buildDrawingCache();
            Bitmap cacheBitmap = comBitmap.getDrawingCache();
            if (cacheBitmap == null) {
                Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + comBitmap + ")", new RuntimeException());
                return null;
            }
            bitmap = Bitmap.createBitmap(cacheBitmap);
            // Restore the view
            comBitmap.setAlpha(alpha);
            comBitmap.destroyDrawingCache();
            comBitmap.setWillNotCacheDrawing(willNotCache);
            comBitmap.setDrawingCacheBackgroundColor(color);
        }
        return bitmap;
    }

}
