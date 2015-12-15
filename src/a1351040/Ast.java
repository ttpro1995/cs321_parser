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
    protected Expr expr;
    protected Term term;
}

class PlusExpr extends Expr{

    public PlusExpr(Expr expr, Term term) {
        this.expr = expr;
        this.term =term;
    }
     @Override
    public void printTree(PrintWriter p, int indent) {
        indent = this.printName(p, indent);
        expr.printTree(p, indent);
        printTerm(p,indent,sym.PLUS);
        term.printTree(p, indent);
    }
}

abstract class Term extends Expr{
    protected Term term;
    protected Factor factor;
}

class MulTerm extends Term{

    public MulTerm(Term term, Factor factor) {
        this.factor = factor;
        this.term = term;
    }
    @Override
    public void printTree(PrintWriter p, int indent) {
        
    }
}

class Factor extends Term{
    protected int num;

    public Factor(int num) {
        this.num = num;
    }
    @Override
    public void printTree(PrintWriter p, int indent) {
         indent = this.printName(p, indent);
          printTerm(p,indent,sym.INTLITERAL,Integer.toString(num));
    }
}