package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;

import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.base.XNoHttpBaseCallBack;
import org.fonuhuolian.xnohttp.utils.XToastUtils;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XNoHttpCallBack extends XNoHttpBaseCallBack {


    public XNoHttpCallBack(Activity context) {
        super(context);
    }

    public XNoHttpCallBack(Activity context, XLoadingBaseDialog XLoadingBaseDialog) {
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
