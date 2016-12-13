package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LexerTest {

    @Test
    public void test_lexer() throws FileNotFoundException {
        Lexer lexer = new Lexer(new FileReader("/tmp/stone.zz"));
        for (Token token; (token = lexer.read()) != Token.EOF;) {
            System.out.println(token.name() + " => " + token.getText());
        }
    }
}
