package com.zz.stone.parser;

import com.zz.stone.ast.ArrayLiteral;
import com.zz.stone.ast.ArrayRef;

import static com.zz.stone.parser.Parser.rule;

/**
 * 语法规则
 * <p>
 * elements : expr { "," expr }
 * primary  : ( "[" [ elements ] "]" | "(" expr ")" | NUMBER | IDENTIFIER | STRING ) { postfix }
 * postfix  : "(" [ args ] ")" | "[" expr "]"
 */
public class ArrayParser extends FuncParser {

    Parser elements = rule(ArrayLiteral.class).ast(expr).repeat(rule().sep(",").ast(expr));

    public ArrayParser() {
        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
}

