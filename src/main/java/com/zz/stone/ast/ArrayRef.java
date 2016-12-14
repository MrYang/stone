package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.eval.Environment;

import java.util.List;

public class ArrayRef extends Postfix {

    public ArrayRef(List<ASTree> children) {
        super(children);
    }

    public ASTree index() {
        return child(0);
    }

    public String toString() {
        return "[" + index() + "]";
    }

    @Override
    public Object eval(Environment env, Object value) {
        if (value instanceof Object[]) {
            Object index = index().eval(env);
            if (index instanceof Integer) {
                return ((Object[]) value)[(Integer) index];
            }
        }

        throw new StoneException("bad array access", this);
    }
}
