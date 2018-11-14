package org.fonuhuolian.xnohttp.callback;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import org.fonuhuolian.xnohttp.R;


public class LoadingNoCancleDialog {

    private AlertDialog dialog;
    private Context mContext;

    public LoadingNoCancleDialog(Context context) {
        this.mContext = context;
    }

    public void show() {

        if (dialog == null && mContext != null) {
            dialog = new AlertDialog.Builder(mContext, R.style.XDialogNoBackgroundDimStyle).create();
            dialog.setCancelable(false);
        }

        if (dialog != null && !dialog.isShowing()) {
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
