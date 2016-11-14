package com.enid.rxjavacomm.util;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.enid.rxjavacomm.comm.MyApplication;


/**
 * toast显示类，可以在子线程直接调用
 * Created by biglove-work on 2016/1/13.
 */
public class ToastUtil {


    private static Toast toast;

    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void showToast(final String text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(text, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, duration);
                }
            });
        }
    }

    private static void show(String text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (toast != null) {
//            toast.cancel();
        }
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }


}
