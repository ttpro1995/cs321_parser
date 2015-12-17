package a1351040;



import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

abstract class AST {
     public abstract void printTree(PrintWriter p, int indent);
        protected int printName(PrintWriter p, int indent) {
        this.doIndent(p, indent);
        p.println(this.getClass().getSimpleName().toString());
        return indent + 4;
    }

    protected void doIndent(PrintWriter p, int indent) {
        int k = 0;
        while (k < indent) {
            p.print(" ");
            ++k;
        }
    }

    protected void printTerm(PrintWriter p, int indent, int token) {
        this.doIndent(p, indent);
        p.println(this.getTokenName(token));
    }

    protected void printTerm(PrintWriter p, int indent, int token, String val) {
        this.doIndent(p, indent);
        p.println(String.valueOf(this.getTokenName(token)) + " (" + val + ")");
    }

    protected String getTokenName(int token) {
        try {
            Field[] classFields = sym.class.getFields();
            int i = 0;
            while (i < classFields.length) {
                if (classFields[i].getInt(null) == token) {
                    return classFields[i].getName();
                }
                ++i;
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return "UNKNOWN TOKEN";
    }
}

class Program extends AST {

   
    public Program(Expr expr ) {
        this.expr = expr;
    }

    private Expr expr;

    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        expr.printTree(p, indent);
    }
    
}


abstract class Expr extends AST{
    protected Expr e1;
    protected Expr e2;
   
}

class PlusExpr extends Expr{

    public PlusExpr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        e1.printTree(p, indent);
        this.printTerm(p, indent, sym.PLUS);
        e2.printTree(p, indent);
    }
}

class MinusExpr extends Expr{

    public MinusExpr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        e1.printTree(p, indent);
        this.printTerm(p, indent, sym.MINUS);
        e2.printTree(p, indent);
    }
}


class TimesExpr extends Expr{

    public TimesExpr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        e1.printTree(p, indent);
        this.printTerm(p, indent, sym.TIMES);
        e2.printTree(p, indent);
    }
}

class DivideExpr extends Expr{

    public DivideExpr(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        e1.printTree(p, indent);
        this.printTerm(p, indent, sym.DIVIDE);
        e2.printTree(p, indent);
    }
}

abstract class Factor extends Expr{
    
}


class NumFactor extends Factor{
   protected int num;

    public NumFactor(int num) {
        this.num = num;
    }

    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = printName(p,indent);
        this.printTerm(p, indent, sym.INTLITERAL,Integer.toString(num));
    }
}

class BracketFactor extends Factor{
    protected Expr expr;

    public BracketFactor(Expr expr) {
        this.expr = expr;
    }
    
    
    @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        this.printTerm(p, indent, sym.LBRACKET);
        expr.printTree(p, indent);
        this.printTerm(p, indent, sym.RBRACKET);
    }
    
}