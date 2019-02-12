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
import org.fonuhuolian.xnohttp.params.XBinaryParams;
import org.fonuhuolian.xnohttp.params.XBitmapParams;
import org.fonuhuolian.xnohttp.params.XHeaderParams;
import org.fonuhuolian.xnohttp.params.XRequestParams;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;
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

        // 头布局参数
        private XHeaderParams mXHeaderParams;
        // 请求参数
        private XRequestParams mXRequestParams;
        // 文件参数
        private XBinaryParams mXBinaryParams;
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

            if (mXHeaderParams == null)
                mXHeaderParams = XHeaderParams.create();

            mXHeaderParams.put(key, value);

            return this;
        }


        public Builder addHeaderParams(XHeaderParams params) {

            if (mXHeaderParams == null) {
                mXHeaderParams = params;
            } else {

                Map<String, String> map = params.params();

                for (String key : map.keySet()) {
                    mXHeaderParams.put(key, map.get(key));
                }
            }

            return this;
        }

        // 添加请求信息
        public Builder addRequestParams(String key, Object value) {

            if (mXRequestParams == null)
                mXRequestParams = XRequestParams.create();

            mXRequestParams.put(key, value);

            return this;
        }

        public Builder addRequestParamsList(String key, List<Object> value) {

            if (mXRequestParams == null)
                mXRequestParams = XRequestParams.create();

            if (value != null && value.size() != 0) {
                for (int i = 0; i < value.size(); i++) {
                    mXRequestParams.put(key, value.get(i));
                }
            }

            return this;
        }

        public Builder addRequestParams(XRequestParams params) {

            if (mXRequestParams == null) {
                mXRequestParams = params;
            } else {

                Map<String, Object> map = params.params();

                for (String key : map.keySet()) {
                    mXRequestParams.put(key, map.get(key));
                }
            }
            return this;
        }


        // 添加文件信息
        public Builder addBinaryParams(String key, File value) {

            if (mXBinaryParams == null)
                mXBinaryParams = XBinaryParams.create();

            if (value == null)
                return this;

            mXBinaryParams.put(key, new FileBinary(value));

            return this;
        }

        public Builder addBinaryParams(String key, String filePathValue) {

            if (mXBinaryParams == null)
                mXBinaryParams = XBinaryParams.create();

            if (TextUtils.isEmpty(filePathValue))
                return this;

            mXBinaryParams.put(key, new FileBinary(new File(filePathValue)));

            return this;
        }

        public Builder addBinaryParams(XBinaryParams params) {

            if (mXBinaryParams == null)
                mXBinaryParams = params;
            else
                mXBinaryParams.putAll(params.params());

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

            // 添加header
            if (mXHeaderParams != null && mXHeaderParams.params().size() > 0) {

                Map<String, String> map = mXHeaderParams.params();

                Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    X.mRequest.addHeader(next.getKey(), next.getValue());
                }
            }

            // json直接拦截
            if (isJsonRequest) {
                XNohttpServer.getInstance().getRequestQueue().add(what, X.mRequest, hcb);
                return X;
            }

            if (mXRequestParams == null)
                mXRequestParams = XRequestParams.create();

            // 添加请求参数
            X.mRequest.add(mXRequestParams.params());

            // 添加文件参数
            if (mXBinaryParams != null && mXBinaryParams.params().size() > 0) {

                List<Map<String, FileBinary>> mapList = mXBinaryParams.params();

                for (int i = 0; i < mapList.size(); i++) {

                    Map<String, FileBinary> map = mapList.get(i);

                    for (String key : map.keySet()) {
                        X.mRequest.add(key, map.get(key));
                    }
                }
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
