package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.ImageView;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.XNohttpServer;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.base.XLoadingStyle;

/**
 * 不可点击返回键关闭的对话框
 */
public class XLoadingNoCancleDialog extends XLoadingBaseDialog {

    private AlertDialog dialog;
    private XLoadingStyle style = XLoadingStyle.NORMAL;

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingNoCancleDialog() {

    }

    /**
     * 此构造需要配合XNohttp使用
     */
    public XLoadingNoCancleDialog(XLoadingStyle style) {
        this.style = style;
    }

    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingNoCancleDialog(Context context) {
        super.mContext = context;
    }


    /**
     * 此构造无需要配合XNohttp使用
     */
    public XLoadingNoCancleDialog(Context context, XLoadingStyle style) {
        super.mContext = context;
        this.style = style;
    }

    public void show() {

        if (dialog == null && mContext != null) {
            dialog = new AlertDialog.Builder(mContext, R.style.XDialogNoBackgroundDimStyle).create();
            dialog.setCancelable(false);
        }

        if (dialog != null && !dialog.isShowing() && mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            dialog.show();

            switch (style) {
                case NORMAL:
                    dialog.setContentView(R.layout.xnohttp_waiting_dialog);
                    break;
                case YOCYCLE:
                    dialog.setContentView(R.layout.xnohttp_waiting_dialog_yo_cicle);
                    Window yoWindow = dialog.getWindow();
                    if (yoWindow != null) {
                        ImageView img = yoWindow.findViewById(R.id.img);
                        if (XNohttpServer.getmImageLoader() != null)
                            XNohttpServer.getmImageLoader().onLoadGifImage(mContext, img, R.drawable.xnohttp_loading_yo_cicle);
                        else
                            throw new RuntimeException("Please call XNohttpServer.setImageLoader(new XNohttpServer.ImageLoader() {}) in Application onCreate()");
                    }
                    break;
                case YPDOUBLEBALL:
                    dialog.setContentView(R.layout.xnohttp_waiting_dialog_yp_ball);
                    Window ypWindow = dialog.getWindow();
                    if (ypWindow != null) {
                        ImageView img = ypWindow.findViewById(R.id.img);
                        if (XNohttpServer.getmImageLoader() != null)
                            XNohttpServer.getmImageLoader().onLoadGifImage(mContext, img, R.drawable.xnohttp_loading_yp_ball);
                        else
                            throw new RuntimeException("Please call XNohttpServer.setImageLoader(new XNohttpServer.ImageLoader() {}) in Application onCreate()");
                    }
                    break;
            }
        }
    }


    public void dismiss() {
        if (dialog != null && dialog.isShowing() && mContext != null) {

            Window window = dialog.getWindow();

            switch (style) {
                case YOCYCLE:
                    if (window != null) {
                        ImageView img = window.findViewById(R.id.img);
                        img.setImageResource(R.drawable.xnohttp_loading_yo_cicle);
                    }
                    break;
                case YPDOUBLEBALL:
                    if (window != null) {
                        ImageView img = window.findViewById(R.id.img);
                        img.setImageResource(R.drawable.xnohttp_loading_yp_ball);
                    }
                    break;
            }
            dialog.dismiss();
        }
    }


    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }
}
