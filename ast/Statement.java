package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Abstract Statement Class. This class lays out the framework for AST.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public abstract class Statement
{
    /**
     * Creates framework for executing different statements in AST.
     * 
     * @param envir the environment that evaluates the expression
     */
    public abstract void exec (Environment envir);

    /**
     * Returns an error message if the subclass does not call.
     * 
     * @param e the Emitter being used to write the file.
     * @throw RuntimeException error if error is found 
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
