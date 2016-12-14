package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import com.zz.stone.ast.ASTree;
import com.zz.stone.ast.NullStmnt;
import com.zz.stone.eval.BasicEnv;
import com.zz.stone.eval.Environment;
import com.zz.stone.parser.BasicParser;
import org.junit.Test;

import java.io.StringReader;

public class EvaluatorTest extends BasicTest {

    @Test
    public void test_eval() {
        StringReader stringReader = new StringReader(lexer);
        Lexer lexer = new Lexer(stringReader);
        Environment env = new BasicEnv();

        BasicParser basicParser = new BasicParser();
        while (lexer.peek(0) != Token.EOF) {
            ASTree asTree = basicParser.parse(lexer);
            if (! (asTree instanceof NullStmnt)) {
                Object value = asTree.eval(env);
                System.out.println(asTree + " => " + value);
            }
        }
    }
}
