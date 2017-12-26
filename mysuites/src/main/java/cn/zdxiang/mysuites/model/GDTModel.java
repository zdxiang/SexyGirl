package cn.zdxiang.mysuites.model;

/**
 * Created by JM on 2017/11/27 0027.下午 4:29
 * Description:
 */

public class GDTModel {

    private String appId;


    private String bannerPosID;

    /**
     *
     */
    private String interteristalPosID;

    /**
     * 闪屏广告id
     */
    private String splashPosID;

    /**
     * 原生广告id
     */
    private String nativePosID;

    /**
     * 原生视频广告id
     */
    private String nativeVideoPosID;

    /**
     *原生模板广告id
     */
    private String nativeExpressPosID;

    /**
     * 内容广告id
     */
    private String contentAdPosID;

    public GDTModel() {
    }

    public GDTModel(String appId, String bannerPosID, String interteristalPosID, String splashPosID, String nativePosID, String nativeVideoPosID, String nativeExpressPosID, String contentAdPosID) {
        this.appId = appId;
        this.bannerPosID = bannerPosID;
        this.interteristalPosID = interteristalPosID;
        this.splashPosID = splashPosID;
        this.nativePosID = nativePosID;
        this.nativeVideoPosID = nativeVideoPosID;
        this.nativeExpressPosID = nativeExpressPosID;
        this.contentAdPosID = contentAdPosID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBannerPosID() {
        return bannerPosID;
    }

    public void setBannerPosID(String bannerPosID) {
        this.bannerPosID = bannerPosID;
    }

    public String getInterteristalPosID() {
        return interteristalPosID;
    }

    public void setInterteristalPosID(String interteristalPosID) {
        this.interteristalPosID = interteristalPosID;
    }

    public String getSplashPosID() {
        return splashPosID;
    }

    public void setSplashPosID(String splashPosID) {
        this.splashPosID = splashPosID;
    }

    public String getNativePosID() {
        return nativePosID;
    }

    public void setNativePosID(String nativePosID) {
        this.nativePosID = nativePosID;
    }

    public String getNativeVideoPosID() {
        return nativeVideoPosID;
    }

    public void setNativeVideoPosID(String nativeVideoPosID) {
        this.nativeVideoPosID = nativeVideoPosID;
    }

    public String getNativeExpressPosID() {
        return nativeExpressPosID;
    }

    public void setNativeExpressPosID(String nativeExpressPosID) {
        this.nativeExpressPosID = nativeExpressPosID;
    }

    public String getContentAdPosID() {
        return contentAdPosID;
    }

    public void setContentAdPosID(String contentAdPosID) {
        this.contentAdPosID = contentAdPosID;
    }
}
