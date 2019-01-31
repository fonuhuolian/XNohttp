package org.fonuhuolian.xnohttp.base;

import android.content.Context;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import org.fonuhuolian.xnohttp.R;

import java.net.URISyntaxException;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public abstract class XNoHttpBaseCallBack implements OnResponseListener<String> {

    private Context mContext;
    private XLoadingBaseDialog mXLoadingBaseDialog;

    // 无Loading
    public XNoHttpBaseCallBack(Context context) {
        this.mContext = context;
    }

    // 有Loading
    public XNoHttpBaseCallBack(Context context, XLoadingBaseDialog XLoadingBaseDialog) {
        this.mContext = context;
        this.mXLoadingBaseDialog = XLoadingBaseDialog;
        if (mXLoadingBaseDialog != null && context != null)
            this.mXLoadingBaseDialog.setContext(context);
    }

    @Override
    public void onStart(int what) {

        if (mXLoadingBaseDialog != null && !mXLoadingBaseDialog.isShowing())
            mXLoadingBaseDialog.show();

    }

    @Override
    public void onSucceed(int what, Response<String> response) {

        // 状态码
        int code = response.responseCode();

        if (code == 200) {

            String s = response.get();

            if (TextUtils.isEmpty(s)) {
                onFailed(code, "获取服务器数据发生异常");
                showXErrorMsg(code, "获取服务器数据发生异常");
            } else {
                onSucceed(s);
            }

        } else {
            onFailed(code, "连接服务器失败");
            showXErrorMsg(code, "连接服务器失败");
        }

        if (mXLoadingBaseDialog != null && mXLoadingBaseDialog.isShowing())
            mXLoadingBaseDialog.dismiss();

    }

    @Override
    public void onFailed(int what, Response<String> response) {

        Exception exception = response.getException();
        String errMsg = "";
        int code = response.responseCode();

        if (exception instanceof NetworkError) {// 网络不好
            errMsg = mContext.getResources().getString(R.string.x_error_please_check_network);
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
            errMsg = mContext.getResources().getString(R.string.x_error_no_cache);
        } else if (exception instanceof ServerError) {
            errMsg = mContext.getResources().getString(R.string.x_error_sever_error);
        } else if (exception instanceof StorageReadWriteError) {
            errMsg = mContext.getResources().getString(R.string.x_error_storage_read_write_error);
        } else if (exception instanceof StorageSpaceNotEnoughError) {
            errMsg = mContext.getResources().getString(R.string.x_error_storage_not_enough_error);
        } else if (exception instanceof TimeoutError) {// 请求超时
            errMsg = mContext.getResources().getString(R.string.x_error_timeout);
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            errMsg = mContext.getResources().getString(R.string.x_error_not_found_server);
        } else if (exception instanceof URLError) {// URL是错的
            errMsg = mContext.getResources().getString(R.string.x_error_url_error);
        } else if (exception instanceof URISyntaxException) {
            errMsg = mContext.getResources().getString(R.string.x_error_uri_syntax);
        } else {
            errMsg = mContext.getResources().getString(R.string.x_error_unknow);
        }

        onFailed(code, errMsg);

        if (mXLoadingBaseDialog != null && mXLoadingBaseDialog.isShowing())
            mXLoadingBaseDialog.dismiss();

        showXErrorMsg(code, errMsg);
    }

    @Override
    public void onFinish(int what) {

    }


    public abstract void onSucceed(String json);

    public abstract void onFailed(int code, String discrbe);

    public void showXErrorMsg(int code, String errMsg) {

    }
}
