package com.zz.stone.parser;

import com.zz.stone.Lexer;
import com.zz.stone.Token;
import com.zz.stone.ast.*;

import java.util.HashSet;
import java.util.Set;

import static com.zz.stone.parser.Parser.Operators;
import static com.zz.stone.parser.Parser.rule;


/**
 * 语法规则
 * <p>
 * primary  : "(" expr ")" | NUMBER | IDENTIFIER | STRING
 * factor   : "-" primary | primary
 * expr     : factor { OP factor}
 * block    : "{" [ statement ] { (";" | EOL) [ statement ] } "}"
 * simple   : expr
 * statement: "if" expr block [ "else" block ] | "while" expr block | simple
 * program  : [ statement ] (";" | EOL)
 *
 *
 * factor:因子,term:项,expression:表达式
*/
public class BasicParser {

    // 结束符
    Set<String> reserved = new HashSet<>();
    // 操作符
    Operators operators = new Operators();

    Parser expr0 = rule();
    Parser primary = rule(PrimaryExpr.class)
            .or(rule().sep("(").ast(expr0).sep(")"),
                    rule().number(NumberLiteral.class),
                    rule().identifier(Name.class, reserved),
                    rule().string(StringLiteral.class));
    Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), primary);
    Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

    Parser statement0 = rule();
    Parser block = rule(BlockStmnt.class)
            .sep("{").option(statement0)
            .repeat(rule().sep(";", Token.EOL).option(statement0))
            .sep("}");
    Parser simple = rule(PrimaryExpr.class).ast(expr);
    Parser statement = statement0.or(
            rule(IfStmnt.class).sep("if").ast(expr).ast(block)
                    .option(rule().sep("else").ast(block)),
            rule(WhileStmnt.class).sep("while").ast(expr).ast(block),
            simple);

    Parser program = rule().or(statement, rule(NullStmnt.class))
            .sep(";", Token.EOL);

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        operators.add("=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 5, Operators.LEFT);
    }

    public ASTree parse(Lexer lexer) {
        return program.parse(lexer);
    }
}
