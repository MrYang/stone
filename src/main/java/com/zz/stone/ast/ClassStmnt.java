package com.zz.stone.ast;

import com.zz.stone.ClassInfo;
import com.zz.stone.eval.Environment;

import java.util.List;

public class ClassStmnt extends ASTList {

    public ClassStmnt(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf) child(0)).token().getText();
    }

    public String superClass() {
        if (numChildren() < 3) {
            return null;
        }

        return ((ASTLeaf) child(1)).token().getText();
    }

    public ClassBody body() {
        return (ClassBody) child(numChildren() - 1);
    }

    @Override
    public Object eval(Environment env) {
        ClassInfo classInfo = new ClassInfo(this, env);
        env.put(name(), classInfo);
        return name();
    }

    @Override
    public String toString() {
        String parent = superClass();
        if (parent == null) {
            parent = "*";
        }

        return "(class " + name() + " " + parent + " " + body() + ")";
    }
}
