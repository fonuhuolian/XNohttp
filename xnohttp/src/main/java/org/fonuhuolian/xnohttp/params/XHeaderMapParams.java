package org.fonuhuolian.xnohttp.params;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者： macpro  on 2018/6/17.
 * 邮箱： xxx.com
 */
public class XHeaderMapParams {

    private Map<String, String> map = new HashMap<String, String>();

    private XHeaderMapParams() {
    }

    public static XHeaderMapParams create() {
        return new XHeaderMapParams();
    }

    public static XHeaderMapParams create(String key, String val) {
        XHeaderMapParams hp = new XHeaderMapParams();
        hp.map.put(key, val);
        return hp;
    }


    public XHeaderMapParams put(String key, String val) {
        this.map.put(key, val);
        return this;
    }

    public Map<String, String> params() {
        return map;
    }

}
