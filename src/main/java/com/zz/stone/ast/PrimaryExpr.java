package com.zz.stone.ast;

import com.zz.stone.eval.Environment;

import java.util.List;

/**
 * 表达式
 */
public class PrimaryExpr extends ASTList {

    public PrimaryExpr(List<ASTree> children) {
        super(children);
    }

    public static ASTree create(List<ASTree> children) {
        return children.size() == 1 ? children.get(0) : new PrimaryExpr(children);
    }

    public ASTree operator() {
        return child(0);
    }

    public Postfix postfix(int nest) {
        return (Postfix) child(numChildren() - nest - 1);
    }

    public boolean hasPostfix(int nest) {
        return numChildren() - nest > 1;
    }

    public Object eval(Environment env) {
        return evalSubExpr(env, 0);
    }

    public Object evalSubExpr(Environment env, int nest) {
        if (hasPostfix(nest)) {
            Object target = evalSubExpr(env, nest + 1);
            return postfix(nest).eval(env, target);
        } else {
            return operator().eval(env);
        }

    }
}
