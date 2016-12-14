package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

public class ClassBody extends ASTList {

    public ClassBody(List<ASTree> children) {
        super(children);
    }

    @Override
    public Object eval(Environment env) {
        for (ASTree t : this) {
            t.eval(env);
        }

        return null;
    }
}
