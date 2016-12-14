package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

public class ArrayLiteral extends ASTList {

    public ArrayLiteral(List<ASTree> children) {
        super(children);
    }

    public int size() {
        return numChildren();
    }

    @Override
    public Object eval(Environment env) {
        int s = size();
        Object[] res = new Object[s];
        int i = 0;
        for (ASTree t : this) {
            res[i++] = t.eval(env);
        }

        return res;
    }
}
