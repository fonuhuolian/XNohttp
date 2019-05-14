package org.fonuhuolian.xnohttp;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import org.fonuhuolian.xnohttp.base.XNoHttpBaseCallBack;
import org.fonuhuolian.xnohttp.params.XBitmapParams;
import org.fonuhuolian.xnohttp.params.XHeaderMapParams;
import org.fonuhuolian.xnohttp.params.XRequestMapParams;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XNoHttpStringRequester {

    private Request<String> mRequest;
    private static final int what = 200;

    private XNoHttpStringRequester(String url) {
        mRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
    }

    private XNoHttpStringRequester(String url, RequestMethod method) {
        mRequest = NoHttp.createStringRequest(url, method);
    }

    public void cancle() {
        if (mRequest != null)
            mRequest.cancel();
    }


    public static class Builder {

        // StringRequest
        private final XNoHttpStringRequester X;


        // 文件参数
        private XBitmapParams mXBitmapParams;
        // 是否是json请求
        private boolean isJsonRequest = false;


        // 监听
        private XNoHttpBaseCallBack hcb;

        public Builder(String httpUrl) {
            X = new XNoHttpStringRequester(httpUrl);
        }

        public Builder(String htttpUrl, RequestMethod method) {
            X = new XNoHttpStringRequester(htttpUrl, method);
        }


        // 添加头信息
        public Builder addHeaderParams(String key, String value) {

            X.mRequest.addHeader(key, value);

            return this;
        }


        public Builder addHeaderParams(XHeaderMapParams params) {

            if (params != null) {

                Map<String, String> map = params.params();

                for (String key : map.keySet()) {
                    X.mRequest.addHeader(key, map.get(key));
                }
            }

            return this;
        }

        // 添加请求信息
        public Builder addRequestParams(String key, Object value) {

            Map<String, Object> params = new HashMap<>();
            params.put(key, value);

            X.mRequest.add(params);

            return this;
        }

        public Builder addRequestParams(String key, Map<String, Object> value) {

            X.mRequest.add(value);

            return this;
        }

        public Builder addRequestParams(String key, List<? extends Object> value) {

            if (value != null && value.size() != 0) {
                for (int i = 0; i < value.size(); i++) {
                    Map<String, Object> params = new HashMap<>();
                    params.put(key, value.get(i));
                    X.mRequest.add(params);
                }
            }

            return this;
        }

        public Builder addRequestParams(XRequestMapParams params) {

            if (params != null) {
                Map<String, Object> map = params.params();
                X.mRequest.add(map);
            }
            return this;
        }


        // 添加文件信息
        public Builder addBinaryParams(String key, File value) {

            if (value != null && value.exists()) {
                X.mRequest.add(key, new FileBinary(value));
            }

            return this;
        }

        public Builder addBinaryParams(String key, String filePathValue) {

            if (!TextUtils.isEmpty(filePathValue)) {

                File file = new File(filePathValue);

                if (file.exists()) {
                    X.mRequest.add(key, new FileBinary(file));
                }
            }

            return this;
        }

        public Builder addBinaryParams(String key, List<String> filePathValues) {


            if (filePathValues != null && filePathValues.size() > 0) {

                for (int i = 0; i < filePathValues.size(); i++) {
                    addBinaryParams(key, filePathValues.get(i));
                }
            }

            return this;
        }

        public Builder addBinaryParams2(String key, List<File> fileValues) {


            if (fileValues != null && fileValues.size() > 0) {

                for (int i = 0; i < fileValues.size(); i++) {
                    addBinaryParams(key, fileValues.get(i));
                }
            }

            return this;
        }

        public Builder addBitmapParams(String key, Bitmap bitmapValue, String fileName) {

            if (mXBitmapParams == null)
                mXBitmapParams = XBitmapParams.create();

            mXBitmapParams.put(key, new BitmapBinary(bitmapValue, fileName));

            return this;
        }

        public Builder addBitmapParams(XBitmapParams params) {

            if (mXBitmapParams == null)
                mXBitmapParams = params;
            else
                mXBitmapParams.putAll(params.params());

            return this;
        }


        /**
         * json请求
         */
        public Builder addJsonParams(String jsonObject) {
            isJsonRequest = true;
            X.mRequest.setDefineRequestBodyForJson(jsonObject); // 传入JSONObject即可。
            return this;
        }

        public Builder addJsonParams(JSONObject jsonObject) {
            isJsonRequest = true;
            X.mRequest.setDefineRequestBodyForJson(jsonObject); // 传入JSONObject即可。
            return this;
        }


        /**
         * 添加请求等级
         */
        public Builder addPriority(Priority priority) {
            X.mRequest.setPriority(priority);
            return this;
        }


        /**
         * 添加取消标记
         */
        public Builder addCancleSign(Object sign) {
            X.mRequest.setCancelSign(sign);
            return this;
        }


        /**
         * 添加回调
         */
        public Builder addResponseListener(XNoHttpBaseCallBack hcb) {
            this.hcb = hcb;
            return this;
        }

        /**
         * 开始请求
         */
        public XNoHttpStringRequester build() {

            if (hcb == null)
                throw new RuntimeException("must be call addResponseListener() and parameter is not null");

            // json直接拦截
            if (isJsonRequest) {
                XNohttpServer.getInstance().getRequestQueue().add(what, X.mRequest, hcb);
                return X;
            }


            if (mXBitmapParams != null && mXBitmapParams.params().size() > 0) {

                List<Map<String, BitmapBinary>> mapList = mXBitmapParams.params();

                for (int i = 0; i < mapList.size(); i++) {

                    Map<String, BitmapBinary> map = mapList.get(i);

                    for (String key : map.keySet()) {
                        X.mRequest.add(key, map.get(key));
                    }
                }
            }

            // 添加到队列
            XNohttpServer.getInstance().getRequestQueue().add(what, X.mRequest, hcb);

            return X;
        }
    }
}
