package org.fonuhuolian.xnohttp.params;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class XHeaderParams {

    private Map<String, String> map = new HashMap<String, String>();

    private XHeaderParams() {
    }

    public static XHeaderParams create() {
        return new XHeaderParams();
    }

    public static XHeaderParams create(String key, String val) {
        XHeaderParams hp = new XHeaderParams();
        hp.map.put(key, val);
        return hp;
    }


    public XHeaderParams put(String key, String val) {
        this.map.put(key, val);
        return this;
    }

    public Map<String, String> params() {
        return map;
    }

}
