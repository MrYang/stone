package com.zz.stone;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 词法分析器
 */
public class Lexer {

    // 使用正则表达式区分三种不同的token
    public static String regexPattern = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";

    public Pattern pattern = Pattern.compile(regexPattern);

    // 存放每行读取的token
    private List<Token> queue = new ArrayList<>();

    private boolean hasMore;

    private LineNumberReader reader;

    public Lexer(Reader r) {
        hasMore = true;
        reader = new LineNumberReader(r);
    }

    /**
     * 每次调用读取一个单词,直到源文件末尾
     *
     * @return 返回一个单词
     */
    public Token read() {
        if (fillQueue(0)) {
            return queue.remove(0);
        }

        return Token.EOF;
    }

    /**
     * 预读, 返回read 方法即将返回的单词之后的第i 个单词
     *
     * @param i 单词位置
     * @return 返回一个单词
     */
    public Token peek(int i) {
        if (fillQueue(i)) {
            return queue.get(i);
        }

        return Token.EOF;
    }

    private boolean fillQueue(int i) {
        while (i >= queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }

        return true;
    }

    protected void readLine() {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new StoneException(e);
        }

        if (line == null) {
            hasMore = false;
            return;
        }

        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);

        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNo, matcher);
                pos = matcher.end();
            } else {
                throw new StoneException("bad token at line " + lineNo);
            }
        }
        queue.add(new IdToken(lineNo, Token.EOL));
    }

    protected void addToken(int lineNo, Matcher matcher) {
        String m = matcher.group(1);
        if (m != null) {    // if not a space
            if (matcher.group(2) == null) {     // if not a comment
                Token token;
                if (matcher.group(3) != null) {
                    token = new NumToken(lineNo, Integer.parseInt(m));
                } else if (matcher.group(4) != null) {
                    token = new StringToken(lineNo, toStringLiteral(m));
                } else {
                    token = new IdToken(lineNo, m);
                }

                queue.add(token);
            }
        }
    }

    protected String toStringLiteral(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\') {
                    c = s.charAt(++i);
                } else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    protected static class NumToken extends Token {
        private int value;

        protected NumToken(int line, int value) {
            super(line);
            this.value = value;
        }

        public boolean isNumber() {
            return true;
        }

        public String getText() {
            return Integer.toString(value);
        }

        public int getNumber() {
            return value;
        }

        public String name() {
            return "NumToken";
        }
    }

    protected static class IdToken extends Token {
        private String text;

        protected IdToken(int line, String text) {
            super(line);
            this.text = text;
        }

        public boolean isIdentifier() {
            return true;
        }

        public String getText() {
            return text;
        }

        public String name() {
            return "IdToken";
        }
    }

    protected static class StringToken extends Token {
        private String literal;

        protected StringToken(int line, String literal) {
            super(line);
            this.literal = literal;
        }

        public boolean isString() {
            return true;
        }

        public String getText() {
            return literal;
        }

        public String name() {
            return "StringToken";
        }
    }

}
