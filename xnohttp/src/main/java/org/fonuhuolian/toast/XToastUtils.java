package org.fonuhuolian.toast;

import android.app.Application;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class XToastUtils {

    private static XToastUtils instance;

    private Toast mToast = null;

    private static Application mContext;

    private XToastUtils() {

        if (mContext == null)
            throw new RuntimeException("Please invoke XToastUtils.init(Application) on Application#onCreate()");
    }

    public static XToastUtils getInstance() {
        if (instance == null)
            synchronized (XToastUtils.class) {
                if (instance == null)
                    instance = new XToastUtils();
            }
        return instance;
    }

    // 需要在Application进行全局初始化
    public static void init(Application context) {
        mContext = context;
    }

    public void longToast(String msg) {
        cancle();
        if (TextUtils.isEmpty(msg))
            return;
        mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        mToast.show();
    }

    public void longToast(int stringResMsg) {
        cancle();
        try {
            mContext.getResources().getString(stringResMsg);
        } catch (Resources.NotFoundException e) {
            return;
        }
        mToast = Toast.makeText(mContext, stringResMsg, Toast.LENGTH_LONG);
        mToast.show();
    }

    public void shortToast(String msg) {
        cancle();
        if (TextUtils.isEmpty(msg))
            return;
        mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void shortToast(int stringResMsg) {
        cancle();
        try {
            mContext.getResources().getString(stringResMsg);
        } catch (Resources.NotFoundException e) {
            return;
        }
        mToast = Toast.makeText(mContext, stringResMsg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void cancle() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}


