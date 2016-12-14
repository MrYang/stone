package com.zz.stone.func;

import com.zz.stone.StoneException;
import com.zz.stone.ast.ASTree;

import java.lang.reflect.Method;

public class NativeFunction {

    protected Method method;
    protected String name;
    protected int numParams;

    public NativeFunction(Method method, String name) {
        this.method = method;
        this.name = name;
        numParams = method.getParameterCount();
    }

    public String toString() {
        return "<native:" + hashCode() + ">";
    }

    public int numOfParameters() {
        return numParams;
    }

    public Object invoke(Object[] args, ASTree tree) {
        try {
            return method.invoke(null, args);
        } catch (Exception e) {
            throw new StoneException("bad native function call:" + name, tree);
        }
    }
}
