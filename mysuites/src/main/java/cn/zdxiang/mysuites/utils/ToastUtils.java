package cn.zdxiang.mysuites.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author fuckyou
 * @date 16-12-8.上午10:09
 * @description ToastUtils
 */

public class ToastUtils {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//        try {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            View toastView = inflater.inflate(R.layout.activity_login_toast, null);
//            TextView texts = (TextView) toastView.findViewById(R.id.toastTis);
//            texts.setTextColor(Color.WHITE);
//            if (message != null && !message.equals("")) {
//                texts.setText(message);
//            }
//            toast.setView(toastView);
//            toast.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void showLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//        try {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            View toastView = inflater.inflate(R.layout.activity_login_toast, null);
//            TextView texts = (TextView) toastView.findViewById(R.id.toastTis);
//            texts.setTextColor(Color.WHITE);
//            if (message != null && !message.equals("")) {
//                texts.setText(message);
//            }
//            toast.setView(toastView);
//            toast.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void show(Context context, String message, boolean isLong) {
        if (context == null) return;
        Toast.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
//        try {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            Toast toast = Toast.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            View toastView = inflater.inflate(R.layout.activity_login_toast, null);
//            TextView texts = (TextView) toastView.findViewById(R.id.toastTis);
//            texts.setTextColor(Color.WHITE);
//            if (message != null && !message.equals("")) {
//                texts.setText(message);
//            }
//            toast.setView(toastView);
//            toast.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
