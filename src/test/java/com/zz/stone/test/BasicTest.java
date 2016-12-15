package com.zz.stone.test;

public class BasicTest {

    protected String lexer =
            "str = \"123\"" +
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

    protected String native_func =
            "def fib(n){\n" +
            "        if n < 2 {\n" +
            "                n\n" +
            "        } else {\n" +
            "                fib(n - 1) + fib(n - 2)\n" +
            "        }\n" +
            "}\n" +
            "\n" +
            "t = currentTime()\n" +
            "fib 15\n" +
            "print currentTime() - t + \"ms\"";

    protected String class_info =
            "class Position {\n" +
            "        x = y = 0\n" +
            "\n" +
            "        def move(nx, ny) {\n" +
            "                x = nx; y = ny\n" +
            "        }\n" +
            "}\n" +
            "\n" +
            "p = Position.new\n" +
            "\n" +
            "p.move(3, 4)\n" +
            "p.x = 10\n" +
            "print p.x + p.y";


    protected String array =
            "a = [2, 3, 4]\n" +
            "print a[1]\n" +
            "\n" +
            "a[1] = \"three\"\n" +
            "print \"a[1]: \" + a[1]\n" +
            "\n" +
            "b = [[\"one\", 1], [\"two\", 2]]\n" +
            "print b[1][0] + \": \" + b[1][1]";
}
