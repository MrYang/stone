package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.eval.Environment;

import java.util.List;

/**
 * "-" 符号
 */
public class NegativeExpr extends ASTList {

    public NegativeExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree operand() {
        return child(0);
    }

    public Object eval(Environment env) {
        Object value = operand().eval(env);
        if (value instanceof Integer) {
            return -(Integer) value;
        }

        throw new StoneException("bad type for -");
    }

    public String toString() {
        return "-" + operand();
    }
}
