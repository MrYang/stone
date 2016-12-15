package com.zz.stone.ast;

import com.zz.stone.StoneException;
import com.zz.stone.StoneObject;
import com.zz.stone.eval.Environment;

import java.util.List;

/**
 * 双目操作符
 */
public class BinaryExpr extends ASTList {

    public BinaryExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree left() {
        return child(0);
    }

    public String operator() {
        return ((ASTLeaf) child(1)).token().getText();
    }

    public ASTree right() {
        return child(2);
    }

    public Object eval(Environment env) {
        String op = operator();
        if ("=".equals(op)) {
            Object right = right().eval(env);
            return computeAssign(env, right);
        }

        Object left = left().eval(env);
        Object right = right().eval(env);
        return computeOp(left, op, right);
    }

    protected Object computeAssign(Environment env, Object rvalue) {
        ASTree left = left();
        if (left instanceof Name) {
            env.put(((Name) left).name(), rvalue);
            return rvalue;
        }

        if (left instanceof PrimaryExpr) {
            PrimaryExpr p = (PrimaryExpr) left;
            if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                Object t = ((PrimaryExpr) left).evalSubExpr(env, 1);
                if (t instanceof StoneObject) {
                    return setField((StoneObject) t, (Dot) p.postfix(0), rvalue);
                }
            }

            if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                Object a = ((PrimaryExpr) left).evalSubExpr(env, 1);
                if (a instanceof Object[]) {
                    ArrayRef ref = (ArrayRef) p.postfix(0);
                    Object index = ref.index().eval(env);
                    if (index instanceof Integer) {
                        Integer i = (Integer) index;
                        ((Object[]) a)[i] = rvalue;
                        return rvalue;
                    }
                }

                throw new StoneException("bad array access", this);
            }
        }


        throw new StoneException("bad assignment");
    }

    protected Object computeOp(Object left, String op, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return computeNumber((Integer) left, op, (Integer) right);
        }

        if (op.equals("+")) {
            return String.valueOf(left) + String.valueOf(right);
        }

        if (op.equals("==")) {
            if (left == null) {
                return right == null ? TRUE : FALSE;
            }
            return left.equals(right) ? TRUE : FALSE;
        }

        throw new StoneException("bad type");
    }

    protected Object computeNumber(Integer left, String op, Integer right) {
        if (op.equals("+")) {
            return left + right;
        }

        if (op.equals("-")) {
            return left - right;
        }

        if (op.equals("*")) {
            return left * right;
        }

        if (op.equals("/")) {
            return left / right;
        }

        if (op.equals("%")) {
            return left % right;
        }

        if (op.equals("==")) {
            return left.equals(right) ? TRUE : FALSE;
        }

        if (op.equals(">")) {
            return left.compareTo(right) > 0 ? TRUE : FALSE;
        }

        if (op.equals("<")) {
            return left.compareTo(right) < 0 ? TRUE : FALSE;
        }

        throw new StoneException("bad operator");
    }

    private Object setField(StoneObject obj, Dot expr, Object rvalue) {
        String name = expr.name();
        obj.write(name, rvalue);
        return rvalue;
    }
}
