package cn.zdxiang.mysuites.model;

/**
 * Created by JM on 2017/11/27 0027.下午 4:15
 * Description:
 */

public class BuglyModel {

    private String appId;

    private boolean debug;

    public BuglyModel(String appId, boolean debug) {
        this.appId = appId;
        this.debug = debug;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
