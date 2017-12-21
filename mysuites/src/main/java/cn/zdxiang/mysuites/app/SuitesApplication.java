package cn.zdxiang.mysuites.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import cn.zdxiang.mysuites.deamon.AbsWorkService;
import cn.zdxiang.mysuites.deamon.DaemonEnv;
import cn.zdxiang.mysuites.model.BuglyModel;
import cn.zdxiang.mysuites.model.GDTModel;
import cn.zdxiang.mysuites.model.MiPushModel;
import cn.zdxiang.mysuites.model.SplashModel;
import cn.zdxiang.mysuites.model.UmengModel;

/**
 * Created by JM on 2017/11/23 0023.下午 3:18
 * Description:
 */

public abstract class SuitesApplication extends Application {

    public static final String TAG = "suitesApplication";

    public static BuglyModel BUGLY_MODEL = null;

    public static UmengModel UMENG_MODEL = null;

    public static MiPushModel MIPUSH_MODEL = null;

    public static GDTModel GDT_MODEL = null;

    public static SplashModel SPLASH_MODEL = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 安装MySuites套件
     *
     * @param buglyModel        不适用传null， bugly的配置实体详情见{@link BuglyModel}
     * @param umengModel        不适用传null， 友盟统计的参数配置实体，详情见{@link UmengModel}
     * @param miPushModel       不适用传null，小米推送的参数配置实体，详情见{@link MiPushModel}
     * @param daemonWorkService 不适用，可传入null。保活进程工作的服务类.它将运行在名为system的进程中
     */
    public void install(BuglyModel buglyModel, UmengModel umengModel, MiPushModel miPushModel, Class<? extends AbsWorkService> daemonWorkService) {
        Utils.init(this);
        if (!shouldInit()) return;
        if (buglyModel != null) {
            Bugly.init(this, buglyModel.getAppId(), buglyModel.isDebug());
        }

        if (umengModel != null) {
            MobclickAgent.UMAnalyticsConfig umAnalyticsConfig = new MobclickAgent.UMAnalyticsConfig(this, umengModel.getAppKey(), umengModel.getChannel(), MobclickAgent.EScenarioType.E_UM_NORMAL);
            MobclickAgent.startWithConfigure(umAnalyticsConfig);
            MobclickAgent.setCheckDevice(umengModel.isCheckDevice());
            MobclickAgent.setDebugMode(umengModel.isDebugMode());
        }

        if (miPushModel != null) {
            // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
            // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
            MiPushClient.registerPush(this, miPushModel.getAppId(), miPushModel.getAppKey());
            LoggerInterface newLogger = new LoggerInterface() {

                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(TAG, content, t);
                }

                @Override
                public void log(String content) {
                    Log.d(TAG, content);
                }
            };
            Logger.setLogger(this, newLogger);
        }

        if (daemonWorkService != null) {
            DaemonEnv.initialize(this, daemonWorkService, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
            DaemonEnv.startServiceMayBind(daemonWorkService);
        } else {
            Log.d(TAG, "保活进程没有启动");
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        assert am != null;
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
