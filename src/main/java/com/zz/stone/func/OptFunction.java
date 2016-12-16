package com.zz.stone.func;


import com.zz.stone.ast.BlockStmnt;
import com.zz.stone.ast.ParameterList;
import com.zz.stone.eval.ArrayEnv;
import com.zz.stone.eval.Environment;

public class OptFunction extends Function {

    protected int size;

    public OptFunction(ParameterList parameters, BlockStmnt body, Environment env, int memorySize) {
        super(parameters, body, env);
        size = memorySize;
    }

    @Override
    public Environment makeEnv() {
        return new ArrayEnv(size, env);
    }
}
