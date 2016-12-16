package com.zz.stone.ast;

import com.zz.stone.eval.Environment;
import com.zz.stone.eval.Symbols;
import com.zz.stone.func.Function;
import com.zz.stone.func.OptFunction;

import java.util.List;

public class Fun extends ASTList {

    protected int size = -1;

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

    public Object evalOpt(Environment env) {
        return new OptFunction(parameters(), body(), env, size);
    }

    public static int lookup(Symbols sym, ParameterList params, BlockStmnt body) {
        Symbols newSym = new Symbols(sym);
        params.lookup(newSym);
        body.lookup(newSym);
        return newSym.size();
    }

    public String toString() {
        return "(fun" + parameters() + " " + body() + ")";
    }
}
