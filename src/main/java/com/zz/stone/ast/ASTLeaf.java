package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.Token;
import com.zz.stone.eval.Environment;
import com.zz.stone.eval.Symbols;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 叶子节点
 */
public class ASTLeaf extends ASTree {

    private static List<ASTree> empty = new ArrayList<>();

    protected Token token;

    public ASTLeaf(Token token) {
        this.token = token;
    }

    @Override
    public ASTree child(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public Iterator<ASTree> children() {
        return empty.iterator();
    }

    public String toString() {
        return token.getText();
    }

    public String location() {
        return "at line " + token.getLineNumber();
    }

    public Token token() {
        return token;
    }

    public Object eval(Environment env) {
        throw new StoneException("cannot eval: " + toString());
    }

    public void lookup(Symbols sym) {
        throw new StoneException("not implements");
    }
}
