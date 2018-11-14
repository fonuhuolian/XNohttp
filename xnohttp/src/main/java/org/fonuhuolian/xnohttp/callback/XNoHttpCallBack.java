package org.fonuhuolian.xnohttp.callback;

import android.content.Context;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.fonuhuolian.xnohttp.R;
import org.fonuhuolian.xnohttp.utils.XToastUtils;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XNoHttpCallBack implements OnResponseListener<String> {

    private Context mContext;
    private XLoadingDialog mXLoadingDialog;

    // 无Loading
    public XNoHttpCallBack(Context context) {
        this.mContext = context;
    }

    // 有Loading
    public XNoHttpCallBack(Context context, XLoadingDialog XLoadingDialog) {
        this.mContext = context;
        this.mXLoadingDialog = XLoadingDialog;
        if (mXLoadingDialog != null)
            this.mXLoadingDialog.setContext(context);
    }

    @Override
    public void onStart(int what) {

        if (mXLoadingDialog != null && !mXLoadingDialog.isShowing())
            mXLoadingDialog.show();

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        if (response.responseCode() == 200) {
            String json = response.get();


            if (TextUtils.isEmpty(json)) {
                XToastUtils.getInstance().shortToast("获取服务器数据发生异常");
                onFailed();
            } else {

                if (json.startsWith("{") && json.endsWith("}") || json.startsWith("[") && json.endsWith("]")) {
                    onSucceed(json);
                } else {
                    XToastUtils.getInstance().shortToast("获取服务器数据发生异常");
                    onFailed();
                }

            }

        } else {

            XToastUtils.getInstance().shortToast("与服务器连接发生异常-状态码:" + response.responseCode());
            onFailed();
        }

    }

    @Override
    public void onFailed(int what, Response<String> response) {

        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
            XToastUtils.getInstance().shortToast(R.string.x_error_please_check_network);
        } else if (exception instanceof TimeoutError) {// 请求超时
            XToastUtils.getInstance().shortToast(R.string.x_error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            XToastUtils.getInstance().shortToast(R.string.x_error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
            XToastUtils.getInstance().shortToast(R.string.x_error_url_error);
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
        } else {
            XToastUtils.getInstance().shortToast(R.string.x_error_unknow);
        }

        onFailed();
    }

    @Override
    public void onFinish(int what) {

        if (mXLoadingDialog != null && mXLoadingDialog.isShowing())
            mXLoadingDialog.dismiss();
    }


    public abstract void onSucceed(String json);

    public abstract void onFailed();
}
