package org.fonuhuolian.xnohttp;

import android.os.Environment;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;


public class XNoHttpDownLoadRequester {

    private DownloadRequest mRequest;
    private static final int what = 0;

    private XNoHttpDownLoadRequester(String url, RequestMethod method, String fileFolder, String fileName, boolean isRange, boolean isDeleteOld) {
        mRequest = new DownloadRequest(url, method, fileFolder, fileName, isRange, isDeleteOld);
    }


    public void cancle() {
        if (mRequest != null)
            mRequest.cancel();
    }


    public static class Builder {

        // 下载地址
        private String mDownLoadUrl;
        // 请求方式
        private RequestMethod mRequestMethod = RequestMethod.GET;
        // 文件存储路径(文件夹)
        private String mFileFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
        // 是否短点续传
        private boolean isRange = false;
        // 是否删除旧文件
        private boolean isDeletOld = true;
        // 文件名
        private String mFileName;
        // 优先级
        private Priority mPriority = Priority.DEFAULT;
        // 取消标记
        private Object mCancleSign;


        // 监听
        private DownloadListener dl;

        public Builder() {

        }


        public Builder addDownLoadUrl(String downLoadUrl) {
            this.mDownLoadUrl = downLoadUrl;
            return this;
        }

        public Builder addRequestMethod(RequestMethod requestMethod) {
            this.mRequestMethod = mRequestMethod;
            return this;
        }

        public Builder addFileFolder(String fileFolder) {
            this.mFileFolder = fileFolder;
            return this;
        }

        public Builder addFileName(String fileName) {
            this.mFileName = fileName;
            return this;
        }

        public Builder isRange(boolean isRange) {
            this.isRange = isRange;
            return this;
        }

        public Builder isDeletOld(boolean isDeletOld) {
            this.isDeletOld = isDeletOld;
            return this;
        }

        /**
         * 添加请求等级
         */
        public Builder addPriority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        /**
         * 添加取消标记
         */
        public Builder addCancleSign(Object sign) {
            this.mCancleSign = sign;
            return this;
        }


        /**
         * 添加回调
         */
        public Builder addDownLoadListener(DownloadListener dl) {
            this.dl = dl;
            return this;
        }

        /**
         * 开始请求
         */
        public XNoHttpDownLoadRequester build() {

            if (TextUtils.isEmpty(mDownLoadUrl))
                throw new RuntimeException("must be call addDownLoadUrl() and parameter is not null");

            if (dl == null)
                throw new RuntimeException("must be call addDownLoadListener() and parameter is not null");

            // 添加到队列
            XNoHttpDownLoadRequester downLoadRequester = new XNoHttpDownLoadRequester(mDownLoadUrl, mRequestMethod, mFileFolder, mFileName, isRange, isDeletOld);
            NoHttp.getDownloadQueueInstance().add(what,
                    downLoadRequester.mRequest.setPriority(mPriority).setCancelSign(mCancleSign),
                    dl);

            return downLoadRequester;
        }
    }
}
