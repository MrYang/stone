package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

public abstract class Postfix extends ASTList {

    public Postfix(List<ASTree> children) {
        super(children);
    }

    public abstract Object eval(Environment env, Object value);
}
