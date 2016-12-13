package com.zz.stone.ast;

import java.util.List;

public class PrimaryExpr extends ASTList {

    public PrimaryExpr(List<ASTree> children) {
        super(children);
    }

    public static ASTree create(List<ASTree> children) {
        return children.size() == 1 ? children.get(0) : new PrimaryExpr(children);
    }
}
