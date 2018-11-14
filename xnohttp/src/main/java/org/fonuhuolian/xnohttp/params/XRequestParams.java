package org.fonuhuolian.xnohttp.params;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class XRequestParams {

    private Map<String, Object> map = new HashMap<String, Object>();

    private XRequestParams() {
    }

    public static XRequestParams create() {
        return new XRequestParams();
    }

    public static XRequestParams create(String key, Object val) {
        XRequestParams hp = new XRequestParams();
        hp.map.put(key, val);
        return hp;
    }

    public static XRequestParams create(Map<String, Object> map) {
        XRequestParams hp = new XRequestParams();
        hp.map = map;
        return hp;
    }

    public XRequestParams put(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    public Map<String, Object> params() {
        return map;
    }

}
