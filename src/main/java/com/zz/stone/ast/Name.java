package com.zz.stone.ast;

import com.zz.stone.Token;

public class Name extends ASTLeaf {

    public Name(Token token) {
        super(token);
    }

    public String name() {
        return token().getText();
    }
}
