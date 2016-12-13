package com.zz.stone.ast;

import java.util.List;

/**
 * 操作符
 */
public class BinaryExpr extends ASTList {

    public BinaryExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token().getText();
    }

    public ASTree right() {
        return child(2);
    }
}
