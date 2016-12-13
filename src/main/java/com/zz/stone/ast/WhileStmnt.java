package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

public class WhileStmnt extends ASTList {

    public WhileStmnt(List<ASTree> children) {
        super(children);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree body() {
        return child(1);
    }

    public Object eval(Environment env) {
        Object result = 0;
        for (; ; ) {
            Object c = condition().eval(env);
            if (c instanceof Integer && (Integer) c == FALSE) {
                return result;
            } else {
                result = body().eval(env);
            }
        }
    }

    public String toString() {
        return "(while" + condition() + " " + body() + ")";
    }
}
