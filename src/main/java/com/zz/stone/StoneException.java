package com.zz.stone;

public class StoneException extends RuntimeException {

    public StoneException() {
        super();
    }

    public StoneException(String message) {
        super(message);
    }

    public StoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoneException(Throwable cause) {
        super(cause);
    }

    public StoneException(Token token) {
        this("syntax error around " + token.getText() + " at line" + token.getLineNumber());
    }
}
