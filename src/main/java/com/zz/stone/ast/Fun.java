package com.zz.stone.ast;

import com.zz.stone.eval.Environment;
import com.zz.stone.func.Function;

import java.util.List;

public class Fun extends ASTList {

    public Fun(List<ASTree> children) {
        super(children);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    @Override
    public Object eval(Environment env) {
        return new Function(parameters(), body(), env);
    }

    public String toString() {
        return "(fun" + parameters() + " " + body() + ")";
    }
}
