package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;

/**
 * 不可点击返回键关闭的对话框
 */
public class XLoadingNoCancleDialog extends XLoadingBaseDialog {

    private AlertDialog dialog;

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingNoCancleDialog() {

    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingNoCancleDialog(Context context) {
        super.mContext = context;
    }

    public void show() {

        if (dialog == null && mContext != null) {
            dialog = new AlertDialog.Builder(mContext, R.style.XDialogNoBackgroundDimStyle).create();
            dialog.setCancelable(false);
        }

        if (dialog != null && !dialog.isShowing() && mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            dialog.show();
            dialog.setContentView(R.layout.xnohttp_waiting_dialog);
        }
    }


    public void dismiss() {
        if (dialog != null && dialog.isShowing() && mContext != null)
            dialog.dismiss();
    }


    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
