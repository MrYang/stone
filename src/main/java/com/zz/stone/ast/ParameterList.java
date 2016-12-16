package com.zz.stone.ast;

import com.zz.stone.eval.Environment;
import com.zz.stone.eval.Symbols;

import java.util.List;

public class ParameterList extends ASTList {

    protected int[] offsets = null;

    public ParameterList(List<ASTree> children) {
        super(children);
    }

    public String name(int i) {
        return ((ASTLeaf) child(i)).token().getText();
    }

    public int size() {
        return numChildren();
    }

    public void eval(Environment env, int index, Object value) {
        env.putNew(name(index), value);
    }

    public void evalOpt(Environment env, int index, Object value) {
        env.put(0, offsets[index], value);
    }

    public void lookup(Symbols syms) {
        int s = size();
        offsets = new int[s];
        for (int i = 0; i < s; i++) {
            offsets[i] = syms.putNew(name(i));
        }
    }
}
