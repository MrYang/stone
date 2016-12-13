package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.eval.Environment;

import java.util.Iterator;
import java.util.List;

/**
 * 非叶子节点
 */
public class ASTList extends ASTree {

    protected List<ASTree> children;

    public ASTList(List<ASTree> children) {
        this.children = children;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        String seq = "";
        for (ASTree t : children) {
            sb.append(seq);
            seq = " ";
            sb.append(t.toString());
        }

        return sb.append(')').toString();
    }

    public String location() {
        for (ASTree t : children) {
            String s = t.location();
            if (s != null) {
                return s;
            }
        }

        return null;
    }

    public Object eval(Environment env) {
        throw new StoneException("cannot eval: " + toString());
    }
}
