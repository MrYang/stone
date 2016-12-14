package com.zz.stone;

import com.zz.stone.ast.ClassBody;
import com.zz.stone.ast.ClassStmnt;
import com.zz.stone.eval.Environment;

public class ClassInfo {

    protected ClassStmnt definition;
    protected Environment environment;
    protected ClassInfo superClass;

    public ClassInfo(ClassStmnt cs, Environment environment) {
        definition = cs;
        this.environment = environment;

        Object object = environment.get(cs.superClass());
        if (object == null) {
            superClass = null;
        } else if (object instanceof ClassInfo) {
            superClass = (ClassInfo) object;
        } else {
            throw new StoneException("unknown super class " + cs.superClass(), cs);
        }

    }

    public String name() {
        return definition.name();
    }

    public ClassInfo superClass() {
        return superClass;
    }

    public ClassBody body() {
        return definition.body();
    }

    public Environment environment() {
        return environment;
    }

    @Override
    public String toString() {
        return "<class " + name() + ">";
    }
}
