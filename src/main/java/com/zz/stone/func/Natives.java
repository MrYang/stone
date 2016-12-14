package com.zz.stone.func;

import com.zz.stone.StoneException;
import com.zz.stone.eval.Environment;

import javax.swing.*;
import java.lang.reflect.Method;

public class Natives {

    public Environment environment(Environment env) {
        appendNatives(env);
        return env;
    }

    protected void appendNatives(Environment env) {
        append(env, "print", Natives.class, "print", Object.class);
        append(env, "read", Natives.class, "read");
        append(env, "length", Natives.class, "length", String.class);
        append(env, "toInt", Natives.class, "toInt", Object.class);
        append(env, "currentTime", Natives.class, "currentTime");
    }

    protected void append(Environment env, String name, Class<?> clazz, String methodName, Class<?>... params) {
        Method m;
        try {
            m = clazz.getMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            throw new StoneException("cannot find a native function:" + methodName);
        }

        env.put(name, new NativeFunction(m, methodName));
    }

    // native methods
    public static int print(Object object) {
        System.out.println(object.toString());
        return 0;
    }

    public static String read() {
        return JOptionPane.showInputDialog(null);
    }

    public static int length(String s) {
        return s.length();
    }

    public static int toInt(Object object) {
        if (object instanceof String) {
            return Integer.parseInt((String) object);
        } else if (object instanceof Integer) {
            return (Integer) object;
        }

        throw new NumberFormatException(object.toString());
    }

    private static long startTime = System.currentTimeMillis();

    public static int currentTime() {
        return (int) (System.currentTimeMillis() - startTime);
    }
}
