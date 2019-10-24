package org.fonuhuolian.xnohttp;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.Priority;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.tools.MultiValueMap;

import org.fonuhuolian.xnohttp.base.XNoHttpBaseCallBack;
import org.fonuhuolian.xnohttp.params.XHeaderMapParams;
import org.fonuhuolian.xnohttp.params.XRequestMapParams;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

            if (bitmapValue != null) {
                BitmapBinary bitmapBinary = new BitmapBinary(bitmapValue, fileName);
                X.mRequest.add(key, bitmapBinary);
            }

            return this;
        }


        /**
         * json请求
         */
        public Builder addJsonParams(String jsonObject) {
            X.mRequest.setDefineRequestBodyForJson(jsonObject); // 传入JSONObject即可。
            return this;
        }

        public Builder addJsonParams(JSONObject jsonObject) {
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

            // 添加到队列
            XNohttpServer.getInstance().getRequestQueue().add(what, X.mRequest, hcb);

            return X;
        }

        /**
         * 临时替换全局参数
         * 即 全局传参的key与用户传参的key一致 会抹掉全局传参key所对应的value
         */
        public XNoHttpStringRequester buildTemporaryReplaceGlobal() {

            if (hcb == null)
                throw new RuntimeException("must be call addResponseListener() and parameter is not null");

            // 请求参数的值(包含全局的以及后传值的)
            MultiValueMap<String, Object> paramKeyValues = X.mRequest.getParamKeyValues();
            // 全局参数的值
            MultiValueMap<String, String> globalParamsKeyValues = NoHttp.getInitializeConfig().getParams();
            // 存储重复的key
            Set<String> repeat = new HashSet<String>();

            if ((globalParamsKeyValues != null && globalParamsKeyValues.size() > 0) && (paramKeyValues != null && paramKeyValues.size() > 0)) {

                Set<String> globalKeys = globalParamsKeyValues.keySet();
                Set<String> paramKeys = paramKeyValues.keySet();

                for (String globalKey : globalKeys) {

                    for (String paramKey : paramKeys) {
                        if (paramKey.equals(globalKey)) {
                            repeat.add(paramKey);
                        }
                    }

                }
            }

            // 清空重复
            for (String s : repeat) {
                // 此key下的全部value并且包含全局的
                List<Object> paramValues = paramKeyValues.getValues(s);
                // 此key下的全部全局value
                List<String> globalValues = globalParamsKeyValues.getValues(s);
                // 长度不相等，代表除了全局的还有其他的元素
                if (paramValues.size() != globalValues.size()) {
                    paramValues.removeAll(globalValues);
                }
                X.mRequest.getParamKeyValues().remove(s);
                X.mRequest.getParamKeyValues().add(s, paramValues);
            }

            // 添加到队列
            XNohttpServer.getInstance().getRequestQueue().add(what, X.mRequest, hcb);

            return X;
        }
    }
}
