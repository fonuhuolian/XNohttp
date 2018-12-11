package org.fonuhuolian.xnohttp.callback;

import android.content.Context;

import org.fonuhuolian.toast.XToastUtils;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.base.XNoHttpBaseCallBack;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XNoHttpCallBack extends XNoHttpBaseCallBack {


    public XNoHttpCallBack(Context context) {
        super(context);
    }

    public XNoHttpCallBack(Context context, XLoadingBaseDialog XLoadingBaseDialog) {
        super(context, XLoadingBaseDialog);
    }

    @Override
    public void showXErrorMsg(int code, String errMsg) {
        super.showXErrorMsg(code, errMsg);

        if (code == 0)
            XToastUtils.getInstance().shortToast(errMsg);
        else
            XToastUtils.getInstance().shortToast(errMsg + " 状态码:" + code);
    }
}
