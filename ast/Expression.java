package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Abstract Expression Class which goes through expressions.
 * Goes through logic, arithmetic, and operations.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public abstract class Expression
{
    /**
     * Evalues expressions for AST.
     * 
     * @param envir the environment that evaluates the expression
     */
    public abstract int eval (Environment envir);

    /**
     * Returns an error message if the subclass does not compile.
     * 
     * @param e the Emitter being used to write the file.
     * @throw RuntimeException error if the subclass does not compile. 
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}