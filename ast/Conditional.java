package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Conditional class evaluates conditional expressions.
 *
 * @author Kaden Kapadia
 * @version March 23, 2022
 */
public class Conditional extends Expression
{
    // instance variables - replace the example below with your own
    private String oper;
    private Expression first;
    private Expression second;

    /**
     * Constructor for objects of class Conditional.
     * 
     * @param op the conditional operator
     * @param s1 the expression to the left of the conditional
     * @param s2 the expression to the right of the conditional
     */
    public Conditional(String op, Expression s1, Expression s2)
    {
        oper = op;
        first = s1;
        second = s2;
    }

    /**
     * Evaluates a conditional statement.
     * 
     * @param envir the environment that evaluates the expression
     * @return 1 if there is a conditional; 0, otherwise.
     */
    @Override
    public int eval(Environment envir)
    {
        if (oper.equals("=="))
        {
            if (first.eval(envir) == second.eval(envir))
            {
                return 1;
            }		
        }
        else if (oper.equals("<"))
        {
            if (first.eval(envir) < second.eval(envir))
            {
                return 1;
            }
        }
        else if (oper.equals("<="))
        {
            if (first.eval(envir) <= second.eval(envir))
            {
                return 1;
            }
        }
        else if (oper.equals(">"))
        {
            if (first.eval(envir) > second.eval(envir))
            {
                return 1;
            }
        }        
        else if (oper.equals(">="))
        {
            if (first.eval(envir) >= second.eval(envir))
            {
                return 1;
            }
        }

        else if (oper.equals("<>"))
        {
            if (first.eval(envir) != second.eval(envir))
            {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Compiles a conditional by evaluating the first expression.
     * Then pushes that first expression onto the stack.
     * Evaluates the second expression and stores the first into a temp variable.
     * 
     * If the condition if false, the program jumps to vLab. 
     * 
     * conditional statement
     * @param vLab the label to jump to if the condition is false
     */
    public void compile(Emitter e, String vLab)
    {
        first.compile(e);
        e.emitPush("$v0");
        second.compile(e);
        e.emitPop("$t1");

        if (oper.equals("="))
        {
            e.emit("bne $t1, $v0, " + vLab + "#condition statement");
        }
        else if (oper.equals("<>"))
        {
            e.emit("beq $t1, $v0, " + vLab + "#condition statement");
        }
        else if (oper.equals("<"))
        {
            e.emit("bge $t1, $v0, " + vLab + "#condition statement");
        }
        else if (oper.equals(">"))
        {
            e.emit("ble $t1, $v0, " + vLab + "#condition statement");
        }
        else if (oper.equals("<="))
        {
            e.emit("bgt $t1, $v0, " + vLab + "#condition statement");
        }
        else if (oper.equals(">="))
        {
            e.emit("blt $t1, $v0, " + vLab + "#condition statement");
        }
        
        else
        {
            throw new IllegalArgumentException("condition not found/recognized");
        }
        
        e.emit("#Conditional statement");
    }
}