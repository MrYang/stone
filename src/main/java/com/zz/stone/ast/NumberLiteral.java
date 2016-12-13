package com.zz.stone.ast;

import com.zz.stone.Token;

public class NumberLiteral extends ASTLeaf {

    public NumberLiteral(Token token) {
        super(token);
    }

    public int value() {
        return token().getNumber();
    }
}
