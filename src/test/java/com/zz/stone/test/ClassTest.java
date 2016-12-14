package com.zz.stone.test;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import com.zz.stone.ast.ASTree;
import com.zz.stone.ast.NullStmnt;
import com.zz.stone.eval.Environment;
import com.zz.stone.eval.NestedEnv;
import com.zz.stone.func.Natives;
import com.zz.stone.parser.ClassParser;
import org.junit.Test;

import java.io.StringReader;

public class ClassTest extends BasicTest {

    @Test
    public void test_class() {
        StringReader stringReader = new StringReader(class_info);
        Lexer lexer = new Lexer(stringReader);
        Environment env = new Natives().environment(new NestedEnv());

        ClassParser classParser = new ClassParser();
        while (lexer.peek(0) != Token.EOF) {
            ASTree asTree = classParser.parse(lexer);
            if (!(asTree instanceof NullStmnt)) {
                Object value = asTree.eval(env);
                System.out.println(" => " + value);
            }
        }
    }
}
