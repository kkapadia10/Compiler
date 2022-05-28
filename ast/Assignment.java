package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Handles situations when the variable is assigned to an expression.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class Assignment extends Statement
{
    // instance variables - replace the example below with your own
    private String variable;
    private Expression exp;

    /**
     * Constructor for objects of class Assignment.
     * 
     * @param var the variable that is assigned to the expression
     * @param express the expression that the variable is assigned to
     */
    public Assignment(String var, Expression express)
    {
        variable = var;
        exp = express;
    }

    /**
     * Executes by setting a variable to an expression
     * inside an environment.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public void exec (Environment envir)
    {
        envir.setVariable(variable, exp.eval(envir));
    }

    /**
     * Compiles the assignment by loading a variable's value to
     * a temp register. Then saves $v0's value to the variable.
     * 
     * @param e the Emitter being used to write the file
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("la $t1 var" + variable);
        e.emit("sw $v0, ($t1) #moves the variable" + variable + " to $t0");
    }
}
