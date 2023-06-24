//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zy;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.List;

public abstract class Position implements Serializable {
    private static final long serialVersionUID = 8323108727124862243L;

    public Position() {
    }

    public abstract String merge(List<String> var1);

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}