package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;

public class XLoadingOnKeyBackFinishDialog extends XLoadingBaseDialog implements DialogInterface.OnKeyListener {

    private AlertDialog dialog;

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingOnKeyBackFinishDialog() {

    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingOnKeyBackFinishDialog(Context context) {
        super.mContext = context;
    }

    @Override
    public void show() {


        if (dialog == null && mContext != null) {
            dialog = new AlertDialog.Builder(mContext, R.style.XDialogNoBackgroundDimStyle).create();
            dialog.setCancelable(false);
            dialog.setOnKeyListener(this);
        }

        if (dialog != null && !dialog.isShowing() && mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            dialog.show();
            dialog.setContentView(R.layout.xnohttp_waiting_dialog);
        }
    }

    @Override
    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        dialogInterface.dismiss();
        if (mContext != null && mContext instanceof Activity)
            ((Activity) mContext).finish();
        return false;
    }
}
