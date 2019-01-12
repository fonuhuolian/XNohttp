package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;

/**
 * 带关闭按钮的对话框
 */
public class XLoadingCloseImgDialog extends XLoadingBaseDialog {

    private AlertDialog dialog;
    private XCloseDialogClosingListener listener;

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingCloseImgDialog() {

    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingCloseImgDialog(Context context) {
        super.mContext = context;
    }

    /**
     * 设置关闭对话框监听
     *
     * @param listener
     */
    public void setClosingListener(XCloseDialogClosingListener listener) {
        this.listener = listener;
    }

    public void show() {

        if (dialog == null && mContext != null) {

            dialog = new AlertDialog.Builder(mContext).create();
            // 背景透明
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
            dialog.setCancelable(false);
        }

        if (dialog != null && !dialog.isShowing() && mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            dialog.show();

            dialog.setContentView(R.layout.xnohttp_close_img_dialog);

            Window window = dialog.getWindow();

            if (window == null)
                return;

            ImageView imageView = window.findViewById(R.id.x_loading_close);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (listener != null)
                        listener.dismiss();
                }
            });
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
