package cn.zdxiang.mysuites.model;

/**
 * Created by JM on 2017/11/27 0027.下午 4:17
 * Description:
 */

public class UmengModel {

    private String appKey;

    private String channel;

    /**
     * 是否为测试设备
     */
    private boolean checkDevice;

    private boolean debugMode;

    public UmengModel(String appKey, String channel, boolean checkDevice, boolean debugMode) {
        this.appKey = appKey;
        this.channel = channel;
        this.checkDevice = checkDevice;
        this.debugMode = debugMode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isCheckDevice() {
        return checkDevice;
    }

    public void setCheckDevice(boolean checkDevice) {
        this.checkDevice = checkDevice;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
