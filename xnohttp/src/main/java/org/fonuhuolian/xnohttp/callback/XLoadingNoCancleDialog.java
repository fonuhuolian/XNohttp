package org.fonuhuolian.xnohttp.callback;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.XNohttpServer;
import org.fonuhuolian.xnohttp.base.XLoadingBaseDialog;
import org.fonuhuolian.xnohttp.base.XLoadingStyle;

import static org.fonuhuolian.xnohttp.base.XLoadingStyle.NORMAL;
import static org.fonuhuolian.xnohttp.base.XLoadingStyle.NORMAL2;

/**
 * 不可点击返回键关闭的对话框
 */
public class XLoadingNoCancleDialog extends XLoadingBaseDialog {

    private AlertDialog dialog;
    private XLoadingStyle style = NORMAL;

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

            if (style == NORMAL) {
                dialog.setContentView(R.layout.xnohttp_waiting_dialog);
                return;
            }

            if (style == NORMAL2) {
                dialog.setContentView(R.layout.xnohttp_waiting_dialog2);
                return;
            }

            dialog.setContentView(R.layout.xnohttp_waiting_dialog_gif);

            Window window = dialog.getWindow();

            if (window == null)
                return;

            ImageView imageView = window.findViewById(R.id.img);
            RelativeLayout xGifLayout = window.findViewById(R.id.x_gif_layout);
            ViewGroup.LayoutParams params = xGifLayout.getLayoutParams();

            if (XNohttpServer.getmImageLoader() == null) {
                throw new RuntimeException("Please call XNohttpServer.setImageLoader(new XNohttpServer.ImageLoader() {}) in Application onCreate()");
            }

            switch (style) {

                case DOUBLE_BALL:
                    params.height = dip2px(38);
                    params.width = dip2px(38);
                    XNohttpServer.getmImageLoader().onLoadGifImage(mContext, imageView, R.drawable.xnohttp_double_ball);
                    break;
                case THREE_BALL:
                    params.height = dip2px(40);
                    params.width = dip2px(40);
                    XNohttpServer.getmImageLoader().onLoadGifImage(mContext, imageView, R.drawable.xnohttp_three_dot);
                    break;
            }

            xGifLayout.setLayoutParams(params);
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
