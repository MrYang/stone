package com.zz.stone.ast;

import com.zz.stone.eval.Environment;
import com.zz.stone.func.Function;

import java.util.List;

public class DefStmnt extends ASTList {

    public DefStmnt(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public ParameterList parameters() {
        return (ParameterList) child(1);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(2);
    }

    public Object eval(Environment env) {
        String name = name();
        env.putNew(name, new Function(parameters(), body(), env));
        return name;
    }

    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }
}
