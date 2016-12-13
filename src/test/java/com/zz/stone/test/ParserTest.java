package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import com.zz.stone.ast.ASTree;
import com.zz.stone.parser.BasicParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ParserTest {

    @Test
    public void test_parser() throws FileNotFoundException {
        Lexer lexer = new Lexer(new FileReader("/tmp/stone.zz"));

        BasicParser basicParser = new BasicParser();
        while (lexer.peek(0) != Token.EOF) {
            ASTree asTree = basicParser.parse(lexer);
            System.out.println("=> " + asTree.toString());
        }
    }
}
