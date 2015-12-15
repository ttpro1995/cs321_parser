package a1351040;



import java.util.*;

abstract class AST {
    
}

class Program extends AST {
    public Program(Expr expr ) {
        this.expr = expr;
    }

    private Expr expr;
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
    
}

class Factor extends Term{
    protected int num;

    public Factor(int num) {
        this.num = num;
    }
    
}