package com.zz.stone.eval;

public interface Environment {

    void put(String name, Object value);

    Object get(String name);

    void putNew(String name, Object value);

    Environment where(String name);

    void setOuter(Environment e);

    void put(int nest, int index, Object value);

    Object get(int nest, int index);

    Symbols symbols();
}
