package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

public class BlockStmnt extends ASTList {

    public BlockStmnt(List<ASTree> children) {
        super(children);
    }

    public Object eval(Environment env) {
        Object result = 0;
        for (ASTree t : this) {
            if (!(t instanceof NullStmnt)) {
                result = t.eval(env);
            }
        }
        return result;
    }
}
