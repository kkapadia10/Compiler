package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Handles binary operations. Evaluates expressions that 
 * contain binary operations.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class BinOp extends Expression
{
    // instance variables - replace the example below with your own
    private String operator;
    private Expression first;
    private Expression second;

    /**
     * Constructor for objects of class BinOp.
     * 
     * @param op the operator in the expression
     * @param f the first part of the expression
     * @param s the second part of the expression
     */
    public BinOp(String op, Expression f, Expression s)
    {
        // initialise instance variables
        operator = op;
        first = f;
        second = s;
    }

    /**
     * Evaluates the binary operations in the expression 
     * and produces another value.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public int eval (Environment envir)
    {        
        int a = first.eval(envir);
        int b = second.eval(envir);

        if (operator.equals("*"))
        {
            return a * b;
        }
        if (operator.equals("/"))
        {
            return a / b;
        }
        if (operator.equals("+"))
        {
            return a + b;
        }
        if (operator.equals("-"))
        {
            return a - b;
        }

        else
        {
            return a % b;
        }
    }

    /**
     * Compiles a binary operation by compiling from left to right.
     * As the code goes from left to right, puts the operation on the stack. 
     * 
     * @param e the Emitter being used to write the file
     */
    @Override
    public void compile(Emitter e)
    {
        first.compile(e);
        e.emitPush("$v0");
        second.compile(e);
        e.emitPop("$t0");
        if(operator.equals("+"))
        {
            e.emit("addu $v0, $v0, $t0");
        }
        else if(operator.equals("-"))
        {
            e.emit("subu $v0, $v0, $t0");
        }
        else
        {
            if(operator.equals("*"))
            {
                e.emit("mult $v0, $t0");
            }
            else
            {
                e.emit("div $t0, $v0");
            }
            e.emit("mflo $v0");
        }
    }
}
