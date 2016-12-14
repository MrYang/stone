package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import com.zz.stone.ast.ASTree;
import com.zz.stone.parser.FuncParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;

public class ParserTest extends BasicTest {

    @Test
    public void test_parser() throws FileNotFoundException {
        StringReader stringReader = new StringReader(lexer);
        Lexer lexer = new Lexer(stringReader);

        FuncParser funcParser = new FuncParser();
        while (lexer.peek(0) != Token.EOF) {
            ASTree asTree = funcParser.parse(lexer);
            System.out.println(asTree + " => " + asTree.toString());
        }
    }
}
