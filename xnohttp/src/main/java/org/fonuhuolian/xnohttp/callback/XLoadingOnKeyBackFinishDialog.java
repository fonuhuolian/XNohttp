package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.base.XLoadingStyle;

public class XLoadingOnKeyBackFinishDialog extends XLoadingBaseDialog implements DialogInterface.OnKeyListener {

    private AlertDialog dialog;
    private XLoadingStyle style = XLoadingStyle.NORMAL;

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingOnKeyBackFinishDialog() {

    }

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingOnKeyBackFinishDialog(XLoadingStyle style) {
        this.style = style;
    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingOnKeyBackFinishDialog(Context context) {
        super.mContext = context;
    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingOnKeyBackFinishDialog(Context context, XLoadingStyle style) {
        super.mContext = context;
        this.style = style;
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
            switch (style) {
                case NORMAL:
                    dialog.setContentView(R.layout.xnohttp_waiting_dialog);
                    break;
                case YOCYCLE:
                    dialog.setContentView(R.layout.xnohttp_waiting_dialog_yo_cicle);
                    Window window = dialog.getWindow();
                    if (window != null) {
                        ImageView img = window.findViewById(R.id.img);
                        Glide.with(mContext).load(R.drawable.xnohttp_loading_yo_cicle).asGif().into(img);
                    }
                    break;
            }
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
