package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.eval.Environment;
import com.zz.stone.func.Function;

import java.util.List;

public class Arguments extends Postfix {

    public Arguments(List<ASTree> children) {
        super(children);
    }

    public int size() {
        return numChildren();
    }

    public Object eval(Environment callerEnv, Object value) {
        if (!(value instanceof Function)) {
            throw new StoneException("bad function");
        }

        Function func = (Function) value;
        ParameterList params = func.parameters();
        if (size() != params.size()) {
            throw new StoneException("bad number of arguments");
        }

        Environment newEnv = func.makeEnv();
        int num = 0;
        for (ASTree a : this){
            params.eval(newEnv, num++, a.eval(callerEnv));
        }

        return func.body().eval(newEnv);
    }
}
