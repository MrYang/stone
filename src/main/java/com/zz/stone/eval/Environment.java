package com.zz.stone.eval;

public interface Environment {

    void put(String name, Object value);

    Object get(String name);
}
