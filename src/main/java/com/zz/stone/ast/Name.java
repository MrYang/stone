package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.Token;
import com.zz.stone.eval.Environment;
import com.zz.stone.eval.Symbols;

/**
 * 表示一个Identifier 节点
 */
public class Name extends ASTLeaf {

    protected static final int UNKNOWN = -1;
    protected int nest, index;

    public Name(Token token) {
        super(token);
        index = UNKNOWN;
    }

    public void lookup(Symbols syms) {
        Symbols.Location loc = syms.get(name());
        if (loc == null)
            throw new StoneException("undefined name: " + name(), this);
        else {
            nest = loc.nest;
            index = loc.index;
        }
    }

    public void lookupForAssign(Symbols syms) {
        Symbols.Location loc = syms.put(name());
        nest = loc.nest;
        index = loc.index;
    }

    public Object evalOpt(Environment env) {
        if (index == UNKNOWN) {
            return env.get(name());
        } else {
            return env.get(nest, index);
        }
    }

    public void evalForAssign(Environment env, Object value) {
        if (index == UNKNOWN) {
            env.put(name(), value);
        } else {
            env.put(nest, index, value);
        }
    }

    public String name() {
        return token().getText();
    }

    public Object eval(Environment env) {
        Object value = env.get(name());
        if (value == null) {
            throw new StoneException("undefined name:" + name());
        }

        return value;
    }
}
