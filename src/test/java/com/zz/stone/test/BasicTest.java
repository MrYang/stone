package com.zz.stone.test;

public class BasicTest {

    protected String lexer =
            "even = 0\n" +
            "\n" +
            "odd = 0\n" +
            "\n" +
            "i = 1\n" +
            "\n" +
            "while i < 10 {\n" +
            "        if i % 2 == 0 {\n" +
            "                even = even + 1\n" +
            "        } else {\n" +
            "                odd = odd + 1\n" +
            "        }\n" +
            "        i = i + 1\n" +
            "}\n" +
            "\n" +
            "even\n" +
            "\n" +
            "odd\n" +
            "\n" +
            "even + odd";

    protected String func =
            "def fib(n) {\n" +
            "        if n < 2 {\n" +
            "                n\n" +
            "        } else {\n" +
            "                fib(n - 1) + fib(n - 2)\n" +
            "        }\n" +
            "}\n" +
            "\n" +
            "fib(10)";

    protected String closure_func =
            "inc = fun (x) { x + 1 }\n" +
            "inc(3)";
}
