package com.zz.stone.ast;

import com.zz.stone.Token;
import com.zz.stone.eval.Environment;

public class StringLiteral extends ASTLeaf {

    public StringLiteral(Token token) {
        super(token);
    }

    public String value() {
        return token().getText();
    }

    public Object eval(Environment env) {
        return value();
    }
}
