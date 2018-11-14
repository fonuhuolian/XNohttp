package org.fonuhuolian.xnohttp;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class XNohttpServer {

    private static XNohttpServer instance;

    /**
     * 请求队列。
     */
    private RequestQueue requestQueue;

    private XNohttpServer() {
        requestQueue = NoHttp.newRequestQueue(3);
    }

    /**
     * 请求队列。
     */
    public static XNohttpServer getInstance() {
        if (instance == null)
            synchronized (XNohttpServer.class) {
                if (instance == null)
                    instance = new XNohttpServer();
            }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    /**
     * 取消这个sign标记的所有请求。
     *
     * @param sign 请求的取消标志。
     */
    public void cancelBySign(Object sign) {
        requestQueue.cancelBySign(sign);
    }


    public void cancleDownLoadBySign(Object sign) {
        NoHttp.getDownloadQueueInstance().cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求。
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    public void cancleDownLoadAll() {
        NoHttp.getDownloadQueueInstance().cancelAll();
    }
}