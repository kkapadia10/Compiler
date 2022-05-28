package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * The number class handles the numbers.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class Number extends Expression
{
    // instance variables - replace the example below with your own
    private int num;

    /**
     * Constructor for objects of class Number.
     * 
     * @param val the value being evaluated
     */
    public Number(int val)
    {
        num = val;
    }

    /**
     * Evaluates the numbers in the environment.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public int eval(Environment envir)
    {
        return num;
    }

    /**
     * Compiles the number by storing the value in $v0.
     * 
     * @param e the Emitter being used to write the file.
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + num);
    }
}
