package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.eval.Environment;
import com.zz.stone.func.Function;
import com.zz.stone.func.NativeFunction;

import java.util.List;

public class Arguments extends Postfix {

    public Arguments(List<ASTree> children) {
        super(children);
    }

    public int size() {
        return numChildren();
    }

    public Object eval(Environment callerEnv, Object value) {
        if (value instanceof Function) {
            return eval(callerEnv, (Function) value);
        }

        if (value instanceof NativeFunction) {
            return evalNative(callerEnv, (NativeFunction) value);
        }

        throw new StoneException("bad function");
    }

    private Object eval(Environment callerEnv, Function func) {
        ParameterList params = func.parameters();
        if (size() != params.size()) {
            throw new StoneException("bad number of arguments", this);
        }

        Environment newEnv = func.makeEnv();
        int num = 0;
        for (ASTree a : this) {
            params.eval(newEnv, num++, a.eval(callerEnv));
        }

        return func.body().eval(newEnv);
    }

    private Object evalNative(Environment callerEnv, NativeFunction func) {
        int nparams = func.numOfParameters();
        if (size() != nparams) {
            throw new StoneException("bad number of arguments", this);
        }

        Object[] args = new Object[nparams];
        int num = 0;
        for (ASTree a : this) {
            args[num++] = a.eval(callerEnv);
        }

        return func.invoke(args, this);
    }
}
