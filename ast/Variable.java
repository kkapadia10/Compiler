package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * The variable classes handles variables.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class Variable extends Expression
{
    // instance variables - replace the example below with your own
    private String str;

    /**
     * Constructor for objects of class Variable.
     * 
     * @param eval the string being evaluated
     */
    public Variable(String eval)
    {
        // initialise instance variables
        str = eval;
    }

    /**
     * Evaluates the variable in the environment.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public int eval (Environment envir)
    {
        return envir.getVariable(str);
    }

    /**
     * Compiles a variable by loading the variable's value into $v0.
     * 
     * @param e the Emitter being used to write the file
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("la $t1 var" + str);
        e.emit("lw $v0 ($t1)");
    }
}
