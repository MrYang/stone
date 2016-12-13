package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.Token;
import com.zz.stone.eval.Environment;

public class Name extends ASTLeaf {

    public Name(Token token) {
        super(token);
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
