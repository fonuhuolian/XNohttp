package org.fonuhuolian.xnohttp.callback;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

public class XNohttpDownLoadSimpleCallBack implements DownloadListener {


    @Override
    public void onDownloadError(int what, Exception exception) {

    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {

    }

    @Override
    public void onFinish(int what, String filePath) {

    }

    @Override
    public void onCancel(int what) {

    }
}
