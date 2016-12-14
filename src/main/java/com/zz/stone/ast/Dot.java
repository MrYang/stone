package com.zz.stone.ast;

import com.zz.stone.ClassInfo;
import com.zz.stone.StoneException;
import com.zz.stone.StoneObject;
import com.zz.stone.eval.Environment;
import com.zz.stone.eval.NestedEnv;

import java.util.List;

public class Dot extends Postfix {

    public Dot(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public String toString() {
        return "." + name();
    }

    @Override
    public Object eval(Environment env, Object value) {
        String member = name();
        if (value instanceof ClassInfo) {
            if ("new".equals(member)) {
                ClassInfo ci = (ClassInfo) value;
                NestedEnv nestedEnv = new NestedEnv(ci.environment());
                StoneObject so = new StoneObject(nestedEnv);
                nestedEnv.putNew("this", so);
                initObject(ci, nestedEnv);
                return so;
            }
        } else if (value instanceof StoneObject) {
            return ((StoneObject) value).read(member);
        }

        throw new StoneException("bad member access: " + member, this);
    }

    protected void initObject(ClassInfo ci, Environment env) {
        if (ci.superClass() != null) {
            initObject(ci.superClass(), env);
        }

        ci.body().eval(env);
    }
}
