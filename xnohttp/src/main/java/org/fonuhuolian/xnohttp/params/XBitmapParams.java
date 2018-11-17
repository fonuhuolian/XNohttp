package org.fonuhuolian.xnohttp.params;


import com.yanzhenjie.nohttp.BitmapBinary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XBitmapParams {

    private List<Map<String, BitmapBinary>> binarys = new ArrayList<>();
    private long binarysLength = 0;

    private XBitmapParams() {
    }

    public static XBitmapParams create() {
        XBitmapParams hp = new XBitmapParams();
        return hp;
    }

    public static XBitmapParams create(String key, BitmapBinary val) {
        XBitmapParams hp = new XBitmapParams();
        Map<String, BitmapBinary> map = new HashMap<>();
        map.put(key, val);
        hp.binarys.add(map);
        hp.binarysLength += val.getBinaryLength();
        return hp;
    }


    public XBitmapParams put(String key, BitmapBinary val) {
        Map<String, BitmapBinary> map = new HashMap<>();
        map.put(key, val);
        this.binarys.add(map);
        this.binarysLength += val.getBinaryLength();
        return this;
    }

    public List<Map<String, BitmapBinary>> params() {
        return binarys;
    }

    public List<Map<String, BitmapBinary>> putAll(List<Map<String, BitmapBinary>> list) {

        if (list != null && list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {

                Map<String, BitmapBinary> map = list.get(i);

                for (String key : map.keySet()) {
                    put(key, map.get(key));
                }
            }
        }

        return binarys;
    }

    public long getBinarysLength() {
        return binarysLength;
    }
}
