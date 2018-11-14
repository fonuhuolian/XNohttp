package org.fonuhuolian.xnohttp.callback;

import android.content.Context;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XNoHttpSimpleCallBack extends XNoHttpCallBack {


    public XNoHttpSimpleCallBack(Context context) {
        super(context);
    }

    public XNoHttpSimpleCallBack(Context context, XLoadingBaseDialog XLoadingBaseDialog) {
        super(context, XLoadingBaseDialog);
    }

    @Override
    public void onFailed() {

    }
}
