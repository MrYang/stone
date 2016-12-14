package com.zz.stone.parser;

import com.zz.stone.ast.Fun;

public class ClosureParser extends FuncParser {

    public ClosureParser() {
        primary.insertChoice(Parser.rule(Fun.class).sep("fun").ast(paramList).ast(block));
    }
}
