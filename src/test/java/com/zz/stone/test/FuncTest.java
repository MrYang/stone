package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import com.zz.stone.ast.ASTree;
import com.zz.stone.ast.NullStmnt;
import com.zz.stone.eval.Environment;
import com.zz.stone.eval.NestedEnv;
import com.zz.stone.parser.FuncParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;

public class FuncTest extends BasicTest {

    @Test
    public void test_eval() throws FileNotFoundException {
        StringReader stringReader = new StringReader(func);
        Lexer lexer = new Lexer(stringReader);
        Environment env = new NestedEnv();

        FuncParser funcParser = new FuncParser();
        while (lexer.peek(0) != Token.EOF) {
            ASTree asTree = funcParser.parse(lexer);
            if (! (asTree instanceof NullStmnt)) {
                Object value = asTree.eval(env);
                System.out.println(asTree + " => " + value);
            }
        }
    }
}
