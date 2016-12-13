package com.zz.stone.ast;

import com.zz.stone.Token;
import com.zz.stone.eval.Environment;

/**
 * 整形数字
 */
public class NumberLiteral extends ASTLeaf {

    public NumberLiteral(Token token) {
        super(token);
    }

    public int value() {
        return token().getNumber();
    }

    public Object eval(Environment env) {
        return value();
    }
}
