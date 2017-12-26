package cn.zdxiang.sexygirl.model;

/**
 * Created by JM on 2017/12/26 0026.上午 11:48
 * Description:
 * "isEnableSexygirlAd":false,
 * "alipayPackageCode":"N0Jf6Y31j9"
 */

public class SettingEntity {

    private boolean isEnableSexygirlAd;

    private String alipayPackageCode;

    public boolean isEnableSexygirlAd() {
        return isEnableSexygirlAd;
    }

    public void setEnableSexygirlAd(boolean enableSexygirlAd) {
        isEnableSexygirlAd = enableSexygirlAd;
    }

    public String getAlipayPackageCode() {
        return alipayPackageCode == null ? "" : alipayPackageCode;
    }

    public void setAlipayPackageCode(String alipayPackageCode) {
        this.alipayPackageCode = alipayPackageCode;
    }
}
