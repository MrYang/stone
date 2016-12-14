package com.zz.stone.parser;

import com.zz.stone.ast.Arguments;
import com.zz.stone.ast.DefStmnt;
import com.zz.stone.ast.ParameterList;

import static com.zz.stone.parser.Parser.rule;

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
