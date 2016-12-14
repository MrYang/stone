package com.zz.stone.parser;


import com.zz.stone.Token;
import com.zz.stone.ast.ClassBody;
import com.zz.stone.ast.ClassStmnt;
import com.zz.stone.ast.Dot;

import static com.zz.stone.parser.Parser.rule;

/**
 * 语法规则
 * <p>
 * member   : def | simple
 * class_body: "{" [ member ] { (";" | EOL) [ member ]} "}"
 * defclass : "class" IDENTIFIER [ "extends" IDENTIFIER ] class_body
 * postfix  : "." IDENTIFIER | "(" [ args ] ")"
 * program  : [ defclass | def | statement ] (";" | EOL)
 */
public class ClassParser extends ClosureParser {

    Parser member = rule().or(def, simple);
    Parser class_body = rule(ClassBody.class).sep("{").option(member)
            .repeat(rule().sep(";", Token.EOL).option(member))
            .sep("}");
    Parser defclass = rule(ClassStmnt.class).sep("class").identifier(reserved)
            .option(rule().sep("extends").identifier(reserved))
            .ast(class_body);

    public ClassParser() {
        postfix.insertChoice(rule(Dot.class).sep(".").identifier(reserved));
        program.insertChoice(defclass);
    }
}
