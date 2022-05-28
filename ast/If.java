package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Handles if comments in AST. 
 *
 * @author Kaden Kapadia
 * @version March 23, 2022
 */
public class If extends Statement
{
    // instance variables - replace the example below with your own
    private Conditional con;
    private Statement first;
    private Statement second;

    /**
     * Constructor for objects of class If.
     * This is used when the if method call has tree parameters.
     * 
     * @param condit the conditional statement
     * @param f the statement being executed if the conditional is true
     * @param s the statement being executed if the conditional is false
     */
    public If(Conditional condit, Statement f, Statement s)
    {
        con = condit;
        first = f;
        second = s;
    }

    /**
     * Constructor for objects of class If.
     * This is used when the if method call has two parameters.
     * 
     * @param condit the statement being executed if the conditional is true
     * @param f the statement being executed if the conditional is false 
     */
    public If(Conditional condit, Statement f)
    {
        con = condit;
        first = f;
    }

    /**
     * Executes if statements in the environment and decides if the 
     * true or false statement should be executed.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public void exec(Environment envir)
    {        
        if (con.eval(envir) != 1)
        {
            if (second != null)
            {
                second.exec(envir);
            }
        }
        else
        {
            if (first != null)
            {
                first.exec(envir);
            }
        }
    }

    /**
     * Compiles the if by compiling the condition statement. 
     * If the condition is met, the program jumps to the if. 
     * Otherwise, the program jumps to the else if.
     * 
     * @param e the Emitter being used to write the file
     */
    public void compile(Emitter e)
    {
        int n = e.nextLabelID();
        
        con.compile(e, "elseif" + n);
        first.compile(e);
        e.emit("j endif" + n);
        e.emit("elseif" + n + ":  #jump for the else");
        
        if (second != null)
        {
            second.compile(e);
        }
        
        e.emit("endif" + n + ":	  #jump for the if");
    }
}
