
import java_cup.runtime.*;


%%
%public
%class Scanner
%implements sym

%line
%column

%cup
%cupdebug

%state COMMENT

%{

    int _yyline, _yycolumn;
     
    private Symbol symbol(int type) {
       return new MySymbol(type, yyline+1, yycolumn+1, yytext());
    }

    private Symbol symbol(int type, Object value) {
      return new MySymbol(type, yyline+1, yycolumn+1, value);
    }
    
    public String getTokName(int token) {
      return getTokenName(token);
    }
    
%}

/* main character classes */

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator} | [ \t\f]

Identifier = [a-zA-Z_][a-zA-Z0-9_]*

IntegerLiteral = (0 | [1-9][0-9]*)

DoubleLiteral  = ([0-9]+\.?[0-9]*|[0-9]*\.?[0-9]+)(E[+-]?[0-9]+)?

StringLiteral = L?\"([^\\\"\n]|\\.)*\"

%%

<YYINITIAL> {

	
	"+" { return symbol(PLUS); }
	
	"-" { return symbol(MINUS); }
	
	"*" { return symbol(TIMES); }
	 
	"/" { return symbol(DIVIDE); }
	


    {IntegerLiteral} {
        int val;
        try {
            val = (new Integer(yytext())).intValue();
        } catch (NumberFormatException e) {
            Errors.warn(yyline+1, yycolumn+1, "LEXICAL WARNING: integer literal too large; using max value");
            val = Integer.MAX_VALUE;
        } 
        return symbol(INTLITERAL, new Integer(val)); 
    }
  
  
    /* whitespace */
    {WhiteSpace}                   { /* ignore */ }


  
    /* comment */
    "//" [^\r\n]*                  { /* ignore */ }
    
    "/*" \**                       { _yyline = yyline; _yycolumn = yycolumn; yybegin(COMMENT); }

    \"([^\\\"\n]|\\.)*             { Errors.fatal(yyline+1, yycolumn+1, "LEXICAL ERROR: Unterminated string literal") ; System.exit(-1);}

    .   { Errors.fatal(yyline+1, yycolumn+1, "LEXICAL ERROR: Illegal character \"" + yytext()+ "\""); 
        System.exit(-1); }           

    <<EOF>>                          { return symbol(EOF); }
}

<COMMENT>{
    "*/"      { yybegin(YYINITIAL); }
    
    ([^*]|"*"[^/])*      { /* ignore */  }
    
    <<EOF>> { Errors.fatal(_yyline+1, _yycolumn+1, "LEXICAL ERROR: Missing end comment */"); 
       System.exit(-1); }
}
