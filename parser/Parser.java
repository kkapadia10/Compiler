package parser;
import scanner.*;
import scanner.Scanner;
import java.util.HashMap;
import java.util.Map;
import ast.*;
import ast.Number;
import java.util.List;
import java.util.ArrayList;

/**
 * The Parser class is for the syntax analysis phase of a compiler. It 
 * parses through the tokens read in by the Scanner and evaluates the 
 * statements. It is able to parse numbers, terms, expressions, statements, 
 * and factors.
 *
 * @author Kaden Kapadia
 * @version 4/12/2022
 */
public class Parser
{
    private Scanner myScanner;
    private String current;

    /**
     * Constructor for objects of class Parser.
     * 
     * @param scanner the Scanner object used
     * @throws ScanErrorException if parsing through an unexpected token
     */
    public Parser(Scanner scanner) throws ScanErrorException
    {
        myScanner = scanner;
        current = myScanner.nextToken();
    }

    /**
     * Determines whether or not the current string matches the expected string.
     * 
     * @param expec the expected string
     * @throws ScanErrorException if parsing through an unexpected token
     */
    private void eat(String expec) throws ScanErrorException
    {
        if (current.equals(expec))
        {
            current = myScanner.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Illegal Character Found: Expected: "
                + expec + ". Instead found " + current);
        }
    }

    /**
     * Parses a number by turning the number (token) into an integer.
     * 
     * @return the number as an integer
     * @throws ScanErrorException if parsing through an unexpected token
     */
    private Number parseNumber() throws ScanErrorException
    {   
        int n = Integer.parseInt(current);
        eat(current);
        return new Number(n);
    }   

    /**
     * Parses through a group of procedures and then returns the 
     * new program object. Uses the program object to execute 
     * to store and run code. 
     * 
     * @return the program object from the group of procedures
     *          which will be evaluated
     * 
     * @throw ScanErrorException if error is found
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<ProcedureDeclaration> proc = new ArrayList<ProcedureDeclaration>();
        while (current.equals("PROCEDURE"))
        {
            eat(current);
            String tok = current;
            eat(current);
            eat("(");

            List<String> lstr = new ArrayList<String>();

            while(!current.equals(")"))
            {
                lstr.add(current);
                eat(current);
                if(current.equals(","))
                {
                    eat(current);
                }
            }

            eat(")");
            eat("EOL");
            Statement stat = parseStatement();

            proc.add(new ProcedureDeclaration(tok, lstr, stat));
        }
        
        List<String> variableList = new ArrayList<String>();
        while(current.equals("VAR"))
        {
            eat(current);
            variableList.add(current);
            eat(current);
            
            while (!current.equals("EOL"))
            {
                eat(",");
                variableList.add(current);
                eat(current);
            }
            
            eat("EOL");
        }
        
        Statement stat = parseStatement();
        return new Program(proc, stat, variableList);
    }

    /**
     * Parses through an expression and determines if the current 
     * token is a + or -.
     * 
     * @return the parsed term after performing mathematical operations on it
     * @throws ScanErrorException if parsing through an unexpected token
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression numb = parseTerm();
        while (current.equals("+") || current.equals("-"))
        {
            if(current.equals("+"))
            {
                eat(current);
                numb = new BinOp ("+", numb, parseTerm());
            }
            else if (current.equals("-"))
            {
                eat(current);
                numb = new BinOp ("-", numb, parseTerm());
            }
        }
        return numb;
    }

    /**
     * Parses through a term and determines if the current token is a * or /. 
     * 
     * @return the parsed term after performing mathematical operations on it
     * @throws ScanErrorException if parsing through an unexpected token
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression numb = parseFactor();
        while (current.equals("*") || current.equals("/"))
        {
            if(current.equals("*"))
            {
                eat(current);
                numb = new BinOp ("*", numb, parseFactor());
            }
            else if (current.equals("/"))
            {
                eat(current);
                numb = new BinOp ("/", numb, parseFactor());              
            }
        }
        return numb;
    }    

    /**
     * Parses through a statement and determines if it is a single 
     * writeln statement, a begin/end block, or a variable statement.
     * 
     * @throws ScanErrorException if parsing through an unexpected token
     */
    public Statement parseStatement() throws ScanErrorException
    {
        while(current.equals("WRITELN"))
        {
            eat(current);
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat("EOL");
            return new Writeln(exp);
        }        
        if (current.equals("IF"))
        {
            eat(current);
            Expression ex = parseExpression();
            String pl = current; 
            eat(current);

            Conditional c = new Conditional(pl, ex, parseExpression());

            eat("THEN");

            Statement state = parseStatement();

            if(current.equals("ELSE"))
            {
                eat(current);
                return new If(c, state, parseStatement());
            }
            else
            {
                return new If(c, state);
            }
        }
        if (current.equals("WHILE"))
        {
            eat(current);
            Expression ex = parseExpression();
            String ace = current; 
            eat(current);

            Conditional c = new Conditional(ace, ex, parseExpression());

            eat("DO");
            return new While(c, parseStatement());
        }
        if(current.equals("BEGIN"))
        {
            eat(current);
            List<Statement> state = new ArrayList <Statement>();

            while (!current.equals("END"))
            {
                state.add(parseStatement());
            }

            eat("END");
            eat("EOL");
            return new Block(state);
        }
        else if (!current.equals("EOF") && !current.equals("EOL")
        && !current.equals("END"))
        {
            Statement ment;
            String str = current;
            eat(current);

            if (current.equals(":="))
            {
                eat(":=");
                Assignment assign = new Assignment(str, parseExpression());
                ment = assign;
            }
            else
            {
                eat("(");
                List < Expression > exp = new ArrayList<>();

                if (!current.equals(")"))
                {
                    exp.add(parseExpression());
                }

                while (!current.equals(")"))
                {
                    eat(",");
                    exp.add(parseExpression());
                }

                eat(")");
                ment =  new State(str, exp);
            }
            eat("EOL");
            return ment;
        }
        else
        {
            return null;
        }
    }

    /**
     * Parses through a factor and determines if it is a 
     * negative number, a parenthesis, a value, or a variable. 
     * 
     * @return the value of the factor as an integer
     * @throws ScanErrorException if parsing through an unexpected token
     */
    public Expression parseFactor() throws ScanErrorException
    {
        Expression numb;

        if (current.equals("("))
        {
            eat(current);
            numb = parseExpression();
            eat(")");
        }
        else if (current.equals("-"))
        {
            eat(current);
            numb = new BinOp("-", new Number(0), parseFactor());
        }
        else 
        {
            try
            {
                numb = parseNumber();
            }
            catch (NumberFormatException n)
            {
                String curr = current;
                eat(current);
                if (current.equals("("))
                {
                    eat(current);
                    List < Expression > lst = new ArrayList<>();

                    while (!current.equals(")"))
                    {
                        lst.add(parseExpression());
                        if (current.equals(","))
                        {
                            eat(current);
                        }
                    }

                    eat(")");
                    numb = new ProcedureCall(curr, lst);
                }
                else
                {
                    numb = new Variable(curr);
                }
            }
        }

        return numb;
    }
}