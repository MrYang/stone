package com.zz.stone.eval;

import java.util.HashMap;
import java.util.Map;

public class NestedEnv extends AbstractEnv {

    protected Map<String, Object> values;
    protected Environment outer;

    public NestedEnv() {
        this(null);
    }

    public NestedEnv(Environment outer) {
        values = new HashMap<>();
        this.outer = outer;
    }

    @Override
    public void put(String name, Object value) {
        Environment e = where(name);
        if (e == null) {
            e = this;
        }

        e.putNew(name, value);
    }

    @Override
    public Object get(String name) {
        Object value = values.get(name);
        if (value == null && outer != null) {
            return outer.get(name);
        }

        return value;
    }

    @Override
    public void putNew(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public Environment where(String name) {
        if (values.get(name) != null) {
            return this;
        } else if (outer == null) {
            return null;
        } else {
            return outer.where(name);
        }
    }

    @Override
    public void setOuter(Environment e) {
        outer = e;
    }
}
