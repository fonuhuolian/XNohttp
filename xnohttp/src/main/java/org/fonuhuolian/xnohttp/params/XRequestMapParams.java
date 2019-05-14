package org.fonuhuolian.xnohttp.params;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class XRequestMapParams {

    private Map<String, Object> map = new HashMap<String, Object>();

    private XRequestMapParams() {
    }

    public static XRequestMapParams create() {
        return new XRequestMapParams();
    }

    public static XRequestMapParams create(String key, Object val) {
        XRequestMapParams hp = new XRequestMapParams();
        hp.map.put(key, val);
        return hp;
    }

    public static XRequestMapParams create(Map<String, Object> map) {
        XRequestMapParams hp = new XRequestMapParams();
        hp.map = map;
        return hp;
    }

    public XRequestMapParams put(String key, Object val) {
        this.map.put(key, val);
        return this;
    }

    public Map<String, Object> params() {
        return map;
    }

}
