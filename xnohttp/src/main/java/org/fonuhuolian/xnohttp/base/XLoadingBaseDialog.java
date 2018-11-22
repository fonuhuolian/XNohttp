package org.fonuhuolian.xnohttp.base;

import android.app.Activity;


/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XLoadingBaseDialog {

    protected Activity mContext;

    public void setActivityContext(Activity mContext) {
        this.mContext = mContext;
    }

    // 子类去实现显示dialog
    public abstract void show();

    // 子类去实现dialog关闭
    public abstract void dismiss();

    // 子类去实现dialog是否正在显示
    public abstract boolean isShowing();
}
