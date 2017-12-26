package cn.zdxiang.sexygirl.utils;

import android.content.Context;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import java.util.Random;

import cn.zdxiang.mysuites.ui.AdWebActivity;
import cn.zdxiang.sexygirl.R;
import cn.zdxiang.sexygirl.constant.AdCons;

/**
 * Created by JM on 2017/12/26 0026.下午 4:12
 * Description:
 */

public class Random2AdUtil {

    /**
     * 随机5个数0-5，当为1时候进入广告
     *
     * @param context context
     * @param bound   总随机数
     * @return boolean
     */
    public static boolean random2Ad(Context context, int bound) {
        Random rand = new Random();
        int random = rand.nextInt(bound);
        if (random == 1) {
            AdWebActivity.start(context, AdCons.BIAN_XIAN_MAO_CHOU_JIANG, context.getString(R.string.free_choujiang));
            MobclickAgent.onEvent(context, "choujiang");
            return true;
        } else {
            return false;
        }
    }
}
