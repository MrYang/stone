package com.zz.stone.eval;

import com.zz.stone.StoneException;

public abstract class AbstractEnv implements Environment {

    public void putNew(String name, Object value) {
        throw new StoneException("not implement");
    }

    public Environment where(String name) {
        throw new StoneException("not implement");
    }

    public void setOuter(Environment e) {
        throw new StoneException("not implement");
    }

    public void put(int nest, int index, Object value) {
        throw new StoneException("not implement");
    }

    public Object get(int nest, int index) {
        throw new StoneException("not implement");
    }

    public Symbols symbols() {
        throw new StoneException("no symbols");
    }
}
