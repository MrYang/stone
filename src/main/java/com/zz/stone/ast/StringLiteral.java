package com.zz.stone.ast;

import com.zz.stone.Token;

public class StringLiteral extends ASTLeaf {

    public StringLiteral(Token token) {
        super(token);
    }

    public String value() {
        return token().getText();
    }
}
