package com.zz.stone.ast;

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

    public String toString() {
        return "-" +operand();
    }
}
