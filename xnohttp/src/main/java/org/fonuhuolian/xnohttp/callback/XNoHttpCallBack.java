package org.fonuhuolian.xnohttp.callback;

import android.content.Context;

import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.base.XNoHttpBaseCallBack;
import org.fonuhuolian.xnohttp.utils.XToastUtils;

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
    public void showErrorMsg(int code, String errMsg) {
        super.showErrorMsg(code, errMsg);
        XToastUtils.getInstance().shortToast(errMsg + " 状态码:" + code);
    }
}
