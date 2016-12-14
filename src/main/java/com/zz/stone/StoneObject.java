package com.zz.stone;

import com.zz.stone.eval.Environment;

public class StoneObject {

    protected Environment env;

    public StoneObject(Environment env) {
        this.env = env;
    }

    public Object read(String member) {
        return getEnv(member).get(member);
    }

    public void write(String member, Object value) {
        getEnv(member).putNew(member, value);
    }

    protected Environment getEnv(String member){
        Environment e = env.where(member);
        if (e != null && e == env) {
            return e;
        }

        throw new StoneException("access fail");
    }
}
