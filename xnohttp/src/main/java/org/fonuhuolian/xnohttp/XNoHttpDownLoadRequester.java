package org.fonuhuolian.xnohttp;

import android.os.Environment;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.tools.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

        /**
         * 此次请求 清空全部全局参数
         */
        public XNoHttpDownLoadRequester buildWithOutGlobalParams() {

            if (TextUtils.isEmpty(mDownLoadUrl))
                throw new RuntimeException("must be call addDownLoadUrl() and parameter is not null");

            if (dl == null)
                throw new RuntimeException("must be call addDownLoadListener() and parameter is not null");

            // 添加到队列
            XNoHttpDownLoadRequester downLoadRequester = new XNoHttpDownLoadRequester(mDownLoadUrl, mRequestMethod, mFileFolder, mFileName, isRange, isDeletOld);


            // 请求参数的值(包含全局的以及后传值的)
            MultiValueMap<String, Object> paramKeyValues = downLoadRequester.mRequest.getParamKeyValues();
            // 全局参数的值
            MultiValueMap<String, String> globalParamsKeyValues = NoHttp.getInitializeConfig().getParams();

            if ((globalParamsKeyValues != null && globalParamsKeyValues.size() > 0) && (paramKeyValues != null && paramKeyValues.size() > 0)) {

                Set<String> globalKeys = globalParamsKeyValues.keySet();

                // 循环全局的key
                for (String globalKey : globalKeys) {

                    if (paramKeyValues.containsKey(globalKey)) {
                        // 包含 全局 和 临时的值
                        List<Object> paramsValues = downLoadRequester.mRequest.getParamKeyValues().getValues(globalKey);
                        List<String> globalValues = globalParamsKeyValues.getValues(globalKey);
                        paramsValues.removeAll(globalValues);

                        downLoadRequester.mRequest.getParamKeyValues().remove(globalKey);

                        if (paramsValues.size() > 0) {

                            for (int i = 0; i < paramsValues.size(); i++) {
                                Object o = paramsValues.get(i);
                                Map<String, Object> params = new HashMap<>();
                                params.put(globalKey, o);
                                downLoadRequester.mRequest.add(params);
                            }
                        }
                    }
                }
            }

            NoHttp.getDownloadQueueInstance().add(what,
                    downLoadRequester.mRequest.setPriority(mPriority).setCancelSign(mCancleSign),
                    dl);

            return downLoadRequester;
        }
    }
}
