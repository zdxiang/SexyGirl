package cn.zdxiang.mysuites.model;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by JM on 2017/11/27 0027.下午 4:34
 * Description:
 */

public class SplashModel {

    private boolean enable;

    /**
     * 底部图标resource id
     */
    private int logoResId;

    /**
     * 闪屏显示持续时间
     * default 3000ms
     */
    private int duration = 3000;

    private String title;

    private String content;

    private Class<? extends Activity> mainActivity;

    /**
     * @param logoResId    闪屏中底部的logo resource id
     * @param title        闪屏中底部的title
     * @param content      闪屏中底部的content
     * @param mainActivity 闪屏结束后要启动的第一个activity,不能为空
     */
    public SplashModel(int logoResId, String title, String content, @NonNull Class<? extends Activity> mainActivity) {
        this.logoResId = logoResId;
        this.mainActivity = mainActivity;
        this.title = title;
        this.content = content;
    }

    /**
     * @param enable       是否开启广告
     * @param logoResId    闪屏中底部的logo resource id
     * @param title        闪屏中底部的title
     * @param content      闪屏中底部的content
     * @param duration     闪屏持续的时间，默认3秒
     * @param mainActivity 闪屏结束后要启动的第一个activity,不能为空
     */
    public SplashModel(boolean enable, int logoResId, String title, String content, int duration, @NonNull Class<? extends Activity> mainActivity) {
        this.enable = enable;
        this.logoResId = logoResId;
        this.title = title;
        this.content = content;
        this.duration = duration;
        this.mainActivity = mainActivity;
    }

    public boolean isAdEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getLogoResId() {
        return logoResId;
    }

    public void setLogoResId(int logoResId) {
        this.logoResId = logoResId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Class getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(Class mainActivity) {
        this.mainActivity = mainActivity;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
