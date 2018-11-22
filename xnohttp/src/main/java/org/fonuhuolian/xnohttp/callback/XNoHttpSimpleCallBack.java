package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;

import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XNoHttpSimpleCallBack extends XNoHttpCallBack {


    public XNoHttpSimpleCallBack(Activity context) {
        super(context);
    }

    public XNoHttpSimpleCallBack(Activity context, XLoadingBaseDialog XLoadingBaseDialog) {
        super(context, XLoadingBaseDialog);
    }

    @Override
    public void onFailed(int code, String discrbe) {

    }
}
