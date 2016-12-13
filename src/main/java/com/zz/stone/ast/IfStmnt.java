package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

public class IfStmnt extends ASTList {

    public IfStmnt(List<ASTree> children) {
        super(children);
    }

    public ASTree condition() {
        return child(0);
    }

    public ASTree thenBlock() {
        return child(1);
    }

    public ASTree elseBlock() {
        return numChildren() > 2 ? child(2) : null;
    }

    public Object eval(Environment env) {
        Object c = condition().eval(env);
        if (c instanceof Integer && (Integer) c != FALSE) {
            return thenBlock().eval(env);
        }

        ASTree b = elseBlock();
        if (b == null) {
            return 0;
        }

        return b.eval(env);
    }

    public String toString() {
        return "(if " + condition() + " " + thenBlock() + " else " + elseBlock() + ")";
    }
}
