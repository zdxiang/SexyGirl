package cn.zdxiang.mysuites.model;

/**
 * Created by JM on 2017/11/27 0027.下午 4:23
 * Description:
 */

public class MiPushModel {

    private String appId;

    private String appKey;

    public MiPushModel(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
