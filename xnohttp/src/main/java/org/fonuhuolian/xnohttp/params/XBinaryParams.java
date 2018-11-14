package org.fonuhuolian.xnohttp.params;


import com.yanzhenjie.nohttp.FileBinary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XBinaryParams {

    private List<Map<String, FileBinary>> binarys = new ArrayList<>();
    private long binarysLength = 0;

    private XBinaryParams() {
    }

    public static XBinaryParams create() {
        XBinaryParams hp = new XBinaryParams();
        return hp;
    }

    public static XBinaryParams create(String key, FileBinary val) {
        XBinaryParams hp = new XBinaryParams();
        Map<String, FileBinary> map = new HashMap<>();
        map.put(key, val);
        hp.binarys.add(map);
        hp.binarysLength += val.getBinaryLength();
        return hp;
    }


    public XBinaryParams put(String key, FileBinary val) {
        Map<String, FileBinary> map = new HashMap<>();
        map.put(key, val);
        this.binarys.add(map);
        this.binarysLength += val.getBinaryLength();
        return this;
    }

    public List<Map<String, FileBinary>> params() {
        return binarys;
    }

    public List<Map<String, FileBinary>> putAll(List<Map<String, FileBinary>> list) {

        if (list != null && list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {

                Map<String, FileBinary> map = list.get(i);

                for (String key : map.keySet()) {
                    put(key,map.get(key));
                }
            }
        }

        return binarys;
    }

    public long getBinarysLength() {
        return binarysLength;
    }
}
