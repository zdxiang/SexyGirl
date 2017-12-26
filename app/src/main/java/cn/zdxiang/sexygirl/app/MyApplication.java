package cn.zdxiang.sexygirl.app;

import android.graphics.BitmapFactory;

import org.litepal.LitePal;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.zdxiang.mysuites.app.SuitesApplication;
import cn.zdxiang.mysuites.model.BuglyModel;
import cn.zdxiang.mysuites.model.GDTModel;
import cn.zdxiang.mysuites.model.MiPushModel;
import cn.zdxiang.mysuites.model.SplashModel;
import cn.zdxiang.mysuites.model.UmengModel;
import cn.zdxiang.sexygirl.MainActivity;
import cn.zdxiang.sexygirl.R;
import cn.zdxiang.sexygirl.constant.APPKeys;
import cn.zdxiang.sexygirl.service.TraceServiceImpl;

/**
 * Created by JM on 2017/11/22 0022.下午 3:20
 * Description:
 */

public class MyApplication extends SuitesApplication {
    public static ExecutorService cThreadPool;
    @Override
    public void onCreate() {
        super.onCreate();
        initParams();
        install(BUGLY_MODEL, UMENG_MODEL, MIPUSH_MODEL, TraceServiceImpl.class);
        cThreadPool = Executors.newFixedThreadPool(5);
        IMAGE_CACHE_PATH = getExternalCacheDir().getPath();
        LitePal.initialize(this);
    }

    private void initParams() {
        BUGLY_MODEL = new BuglyModel(APPKeys.BUGLY_APP_ID, false);
        UMENG_MODEL = new UmengModel(APPKeys.UMENG_KEYS, "default", false, false);
        MIPUSH_MODEL = new MiPushModel(APPKeys.MIPUSH_APP_ID, APPKeys.MIPUSH_APP_KEY);
        GDT_MODEL = new GDTModel(APPKeys.GDT_APP_ID, APPKeys.GDT_BANNER_POS_ID, APPKeys.GDT_INTERTERISTAL_POS_ID, APPKeys.GDT_SPLASH_POS_ID, APPKeys.GDT_NATIVE_POS_ID, APPKeys.GDT_NATIVE_VIDEO_POS_ID, APPKeys.GDT_NATIVE_EXPRESS_POS_ID, APPKeys.GDT_CONTENT_POS_ID);
        SPLASH_MODEL = new SplashModel(true, R.drawable.ic_launcher, "性感诱惑", "哈哈哈", 500, MainActivity.class);
    }

    private static String IMAGE_CACHE_PATH;

    private static final String HASH_ALGORITHM = "MD5";

    private static final int RADIX = 10 + 26; // 10 digits + 26 letters

    public static String getImageCachePath() {
        return IMAGE_CACHE_PATH;
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return hash;
    }

    public static String generate(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        if (imageUri.endsWith(".gif") || imageUri.endsWith(".GIF")) {
            return bi.toString(RADIX) + ".weicogif";
        }
        return bi.toString(RADIX) + ".weico";
    }

    public static int getMaxSizeOfBitMap(String path) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        return Math.max(op.outWidth, op.outHeight);
    }

    public static void deleteFiles(String path) {
        deleteFiles(new File(path));
    }

    public static void deleteFiles(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        //文件夹递归删除
        File[] files = file.listFiles();
        if (null == files) {
            return;
        }
        for (File subFile : files) {
            deleteFiles(subFile);
        }
        file.delete();
    }

}
