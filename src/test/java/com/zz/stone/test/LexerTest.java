package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import org.junit.Test;

import java.io.StringReader;

public class LexerTest extends BasicTest {

    @Test
    public void test_lexer() {
        StringReader stringReader = new StringReader(lexer);
        Lexer lexer = new Lexer(stringReader);

        for (Token token; (token = lexer.read()) != Token.EOF;) {
            System.out.println(token.name() + " => " + token.getText());
        }
    }
}
