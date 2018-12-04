package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.widget.CircleTextProgressbar;

public class XLoadingDownLoadDialog extends XLoadingBaseDialog implements DialogInterface.OnKeyListener {

    private AlertDialog dialog;
    private CircleTextProgressbar progressBar;
    private XDownLoadDialogListener mListener;
    private boolean isShowText = false;

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingDownLoadDialog() {

    }

    public XLoadingDownLoadDialog(XDownLoadDialogListener listener) {
        this.mListener = listener;
    }

    public XLoadingDownLoadDialog(XDownLoadDialogListener listener, boolean isShowText) {
        this(listener);
        this.isShowText = isShowText;
    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingDownLoadDialog(Context context) {
        super.mContext = context;
    }

    public XLoadingDownLoadDialog(Context context, XDownLoadDialogListener listener) {
        super.mContext = context;
        this.mListener = listener;
    }

    public XLoadingDownLoadDialog(Context context, XDownLoadDialogListener listener, boolean isShowText) {
        this(context, listener);
        this.isShowText = isShowText;
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
            dialog.setContentView(R.layout.xnohttp_download_dialog);
            Window window = dialog.getWindow();
            if (window == null)
                return;
            progressBar = window.findViewById(R.id.x_download_progress);
            TextView cancle = window.findViewById(R.id.x_download_cancle);
            progressBar.setProgressColor(Color.WHITE);
            progressBar.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            // 和系统普通进度条一样，0-100。
            progressBar.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
            progressBar.setOutLineColor(Color.parseColor("#646464"));

            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                    if (mListener != null)
                        mListener.cancleClick();
                }
            });
        }
    }


    public void setProgress(int progress) {
        // 如果想自己设置进度，比如100。
        if (progressBar != null) {
            progressBar.setProgress(progress);

            if (isShowText)
                progressBar.setText(progress + "%");
        }
    }

    @Override
    public void dismiss() {
        if (dialog != null && dialog.isShowing() && mContext != null)
            dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (mListener != null)
            mListener.backClick(dialogInterface);
        return false;
    }

    public interface XDownLoadDialogListener {

        void cancleClick();

        void backClick(DialogInterface dialogInterface);
    }
}
