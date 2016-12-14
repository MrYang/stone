package com.zz.stone.parser;

import com.zz.stone.ast.Arguments;
import com.zz.stone.ast.DefStmnt;
import com.zz.stone.ast.ParameterList;

import static com.zz.stone.parser.Parser.rule;

/**
 * 语法规则
 * <p>
 * param    : IDENTIFIER
 * params   : param { "," param}
 * param_list: "(" [ params ] ")"
 * def      : "def" IDENTIFIER param_list block
 * args     : expr { "," expr }
 * postfix  : "(" [ args ] ")"
 * primary  : ( "(" expr ")" | NUMBER | IDENTIFIER | STRING ) { postfix }
 * simple   : expr [ args ]
 * program  : [ def | statement ] (";" | EOL)
 */
public class FuncParser extends BasicParser {

    Parser param = rule().identifier(reserved);
    Parser params = rule(ParameterList.class)
            .ast(param).repeat(rule().sep(",").ast(param));
    Parser paramList = rule().sep("(").maybe(params).sep(")");
    Parser def = rule(DefStmnt.class)
            .sep("def").identifier(reserved).ast(paramList).ast(block);
    Parser args = rule(Arguments.class)
            .ast(expr).repeat(rule().sep(",").ast(expr));
    Parser postfix = rule().sep("(").maybe(args).sep(")");

    public FuncParser() {
        reserved.add(")");
        primary.repeat(postfix);
        simple.option(args);
        program.insertChoice(def);
    }
}
