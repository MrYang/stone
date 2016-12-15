package com.zz.stone;

/**
 * 代表一个单词对象, token 的种类只用三种, 分别是number, string, identifier
 */
public abstract class Token {

    public static final Token EOF = new Token(-1) {

        public String name() {
            return "EOF";
        }
    };

    // 换行
    public static final String EOL = "\\n";

    private int lineNumber;

    protected Token(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    // 行号
    public int getLineNumber() {
        return lineNumber;
    }

    public boolean isIdentifier() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    // 数字字面量
    public int getNumber() {
        throw new StoneException("not number token");
    }

    // 节点文本
    public String getText() {
        return "";
    }

    public abstract String name();

}
