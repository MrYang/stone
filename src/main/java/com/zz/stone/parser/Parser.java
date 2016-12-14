package com.zz.stone.parser;

import com.zz.stone.Lexer;
import com.zz.stone.StoneException;
import com.zz.stone.Token;
import com.zz.stone.ast.ASTLeaf;
import com.zz.stone.ast.ASTList;
import com.zz.stone.ast.ASTree;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class Parser {

    protected static abstract class Element {

        protected abstract void parse(Lexer lexer, List<ASTree> res);

        protected abstract boolean match(Lexer lexer);
    }

    protected static class Tree extends Element {
        protected Parser parser;

        protected Tree(Parser p) {
            parser = p;
        }

        protected void parse(Lexer lexer, List<ASTree> res) {
            res.add(parser.parse(lexer));
        }

        protected boolean match(Lexer lexer) {
            return parser.match(lexer);
        }
    }

    protected static class OrTree extends Element {
        protected Parser[] parsers;

        protected OrTree(Parser[] p) {
            parsers = p;
        }

        protected void parse(Lexer lexer, List<ASTree> res) {
            Parser p = choose(lexer);
            if (p == null) {
                throw new StoneException(lexer.peek(0));
            } else {
                res.add(p.parse(lexer));
            }
        }

        protected boolean match(Lexer lexer) {
            return choose(lexer) != null;
        }

        protected Parser choose(Lexer lexer) {
            for (Parser p : parsers) {
                if (p.match(lexer)) {
                    return p;
                }
            }

            return null;
        }

        protected void insert(Parser p) {
            Parser[] newParsers = new Parser[parsers.length + 1];
            newParsers[0] = p;
            System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
            parsers = newParsers;
        }
    }

    protected static class Repeat extends Element {
        protected Parser parser;
        protected boolean onlyOnce;

        protected Repeat(Parser p, boolean once) {
            parser = p;
            onlyOnce = once;
        }

        protected void parse(Lexer lexer, List<ASTree> res) {
            while (parser.match(lexer)) {
                ASTree t = parser.parse(lexer);
                if (t.getClass() != ASTList.class || t.numChildren() > 0) {
                    res.add(t);
                }

                if (onlyOnce) {
                    break;
                }
            }
        }

        protected boolean match(Lexer lexer) {
            return parser.match(lexer);
        }
    }

    protected static abstract class AToken extends Element {
        protected Factory factory;

        protected AToken(Class<? extends ASTLeaf> type) {
            if (type == null) {
                type = ASTLeaf.class;
            }

            factory = Factory.get(type, Token.class);
        }

        protected void parse(Lexer lexer, List<ASTree> res) {
            Token t = lexer.read();
            if (test(t)) {
                ASTree leaf = factory.make(t);
                res.add(leaf);
            } else {
                throw new StoneException(t);
            }
        }

        protected boolean match(Lexer lexer) {
            return test(lexer.peek(0));
        }

        protected abstract boolean test(Token t);
    }

    protected static class IdToken extends AToken {
        Set<String> reserved;

        protected IdToken(Class<? extends ASTLeaf> type, Set<String> r) {
            super(type);
            reserved = r != null ? r : new HashSet<>();
        }

        protected boolean test(Token t) {
            return t.isIdentifier() && !reserved.contains(t.getText());
        }
    }

    protected static class NumToken extends AToken {
        protected NumToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        protected boolean test(Token t) {
            return t.isNumber();
        }
    }

    protected static class StrToken extends AToken {
        protected StrToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        protected boolean test(Token t) {
            return t.isString();
        }
    }

    protected static class Leaf extends Element {
        protected String[] tokens;

        protected Leaf(String[] pat) {
            tokens = pat;
        }

        protected void parse(Lexer lexer, List<ASTree> res) {
            Token t = lexer.read();
            if (t.isIdentifier()) {
                for (String token : tokens) {
                    if (token.equals(t.getText())) {
                        find(res, t);
                        return;
                    }
                }
            }

            if (tokens.length > 0) {
                throw new StoneException(tokens[0] + " expected.");
            } else {
                throw new StoneException(t);
            }
        }

        protected void find(List<ASTree> res, Token t) {
            res.add(new ASTLeaf(t));
        }

        protected boolean match(Lexer lexer) {
            Token t = lexer.peek(0);
            if (t.isIdentifier()) {
                for (String token : tokens) {
                    if (token.equals(t.getText())) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    protected static class Skip extends Leaf {
        protected Skip(String[] t) {
            super(t);
        }

        protected void find(List<ASTree> res, Token t) {
        }
    }

    public static class Precedence {
        int value;
        boolean leftAssoc; // left associative

        public Precedence(int v, boolean a) {
            value = v;
            leftAssoc = a;
        }
    }

    public static class Operators extends HashMap<String, Precedence> {
        public static boolean LEFT = true;
        public static boolean RIGHT = false;

        // prec 表示优先级, leftAssoc 表示左结合或者右结合
        public void add(String name, int prec, boolean leftAssoc) {
            put(name, new Precedence(prec, leftAssoc));
        }
    }

    protected static class Expr extends Element {
        protected Factory factory;
        protected Operators ops;
        protected Parser factor;

        protected Expr(Class<? extends ASTree> clazz, Parser exp, Operators map) {
            factory = Factory.getForASTList(clazz);
            ops = map;
            factor = exp;
        }

        public void parse(Lexer lexer, List<ASTree> res) {
            ASTree right = factor.parse(lexer);
            Precedence prec;
            while ((prec = nextOperator(lexer)) != null) {
                right = doShift(lexer, right, prec.value);
            }

            res.add(right);
        }

        private ASTree doShift(Lexer lexer, ASTree left, int prec) {
            ArrayList<ASTree> list = new ArrayList<>();
            list.add(left);
            list.add(new ASTLeaf(lexer.read()));
            ASTree right = factor.parse(lexer);
            Precedence next;
            while ((next = nextOperator(lexer)) != null && rightIsExpr(prec, next)) {
                right = doShift(lexer, right, next.value);
            }

            list.add(right);
            return factory.make(list);
        }

        private Precedence nextOperator(Lexer lexer) {
            Token t = lexer.peek(0);
            if (t.isIdentifier()) {
                return ops.get(t.getText());
            }

            return null;
        }

        private static boolean rightIsExpr(int prec, Precedence nextPrec) {
            if (nextPrec.leftAssoc) {
                return prec < nextPrec.value;
            }

            return prec <= nextPrec.value;
        }

        protected boolean match(Lexer lexer) {
            return factor.match(lexer);
        }
    }

    public static final String factoryName = "create";

    protected static abstract class Factory {
        protected abstract ASTree make0(Object arg) throws Exception;

        protected ASTree make(Object arg) {
            try {
                return make0(arg);
            } catch (IllegalArgumentException e1) {
                throw e1;
            } catch (Exception e2) {
                throw new RuntimeException(e2); // this compiler is broken.
            }
        }

        protected static Factory getForASTList(Class<? extends ASTree> clazz) {
            Factory f = get(clazz, List.class);
            if (f == null)
                f = new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        List<ASTree> results = (List<ASTree>) arg;
                        if (results.size() == 1) {
                            return results.get(0);
                        } else {
                            return new ASTList(results);
                        }
                    }
                };
            return f;
        }

        protected static Factory get(Class<? extends ASTree> clazz, Class<?> argType) {
            if (clazz == null) {
                return null;
            }
            try {
                final Method m = clazz.getMethod(factoryName, new Class<?>[]{argType});
                return new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        return (ASTree) m.invoke(null, arg);
                    }
                };
            } catch (NoSuchMethodException e) {
            }

            try {
                final Constructor<? extends ASTree> c = clazz.getConstructor(argType);
                return new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        return c.newInstance(arg);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected List<Element> elements;
    protected Factory factory;

    public Parser(Class<? extends ASTree> clazz) {
        reset(clazz);
    }

    protected Parser(Parser p) {
        elements = p.elements;
        factory = p.factory;
    }

    public ASTree parse(Lexer lexer) {
        ArrayList<ASTree> results = new ArrayList<>();
        for (Element e : elements) {
            e.parse(lexer, results);
        }

        return factory.make(results);
    }

    protected boolean match(Lexer lexer) {
        if (elements.size() == 0) {
            return true;
        }
        Element e = elements.get(0);
        return e.match(lexer);
    }

    public static Parser rule() {
        return rule(null);
    }

    public static Parser rule(Class<? extends ASTree> clazz) {
        return new Parser(clazz);
    }

    /**
     * 清空语法规则
     *
     * @return parser
     */
    public Parser reset() {
        elements = new ArrayList<>();
        return this;
    }

    public Parser reset(Class<? extends ASTree> clazz) {
        elements = new ArrayList<>();
        factory = Factory.getForASTList(clazz);
        return this;
    }

    /**
     * 向语法规则中添加终结符(整形字面量)
     *
     * @return parser
     */
    public Parser number() {
        return number(null);
    }

    public Parser number(Class<? extends ASTLeaf> clazz) {
        elements.add(new NumToken(clazz));
        return this;
    }

    /**
     * 向语法规则中添加终结符(除保留字之外的标识符)
     *
     * @param reserved 保留字
     * @return parser
     */
    public Parser identifier(Set<String> reserved) {
        return identifier(null, reserved);
    }

    public Parser identifier(Class<? extends ASTLeaf> clazz, Set<String> reserved) {
        elements.add(new IdToken(clazz, reserved));
        return this;
    }

    /**
     * 向语法规则中添加终结符(字符串字面量)
     *
     * @return parser
     */
    public Parser string() {
        return string(null);
    }

    public Parser string(Class<? extends ASTLeaf> clazz) {
        elements.add(new StrToken(clazz));
        return this;
    }

    /**
     * 向语法规则中添加终结符(与pat 匹配的标识符)
     *
     * @param pat pattern
     * @return parser
     */
    public Parser token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

    /**
     * 向语法规则中添加未包含于抽象语法树的终结符(与pat 匹配的标识符)
     *
     * @param pat pattern
     * @return parser
     */
    public Parser sep(String... pat) {
        elements.add(new Skip(pat));
        return this;
    }

    /**
     * 向语法规则中添加非终结符p
     *
     * @param p 非终结符
     * @return parser
     */
    public Parser ast(Parser p) {
        elements.add(new Tree(p));
        return this;
    }

    public Parser or(Parser... p) {
        elements.add(new OrTree(p));
        return this;
    }

    public Parser maybe(Parser p) {
        Parser p2 = new Parser(p);
        p2.reset();
        elements.add(new OrTree(new Parser[]{p, p2}));
        return this;
    }

    /**
     * 向语法规则中添加可省略的非终结符p
     *
     * @param p 非终结符
     * @return parser
     */
    public Parser option(Parser p) {
        elements.add(new Repeat(p, true));
        return this;
    }

    public Parser repeat(Parser p) {
        elements.add(new Repeat(p, false));
        return this;
    }

    /**
     * 向语法规则中添加双目运算表达式
     *
     * @param subexp    parser
     * @param operators 运算符
     * @return parser
     */
    public Parser expression(Parser subexp, Operators operators) {
        elements.add(new Expr(null, subexp, operators));
        return this;
    }

    public Parser expression(Class<? extends ASTree> clazz, Parser subexp, Operators operators) {
        elements.add(new Expr(clazz, subexp, operators));
        return this;
    }

    public Parser insertChoice(Parser p) {
        Element e = elements.get(0);
        if (e instanceof OrTree) {
            ((OrTree) e).insert(p);
        } else {
            Parser otherwise = new Parser(this);
            reset(null);
            or(p, otherwise);
        }

        return this;
    }
}
