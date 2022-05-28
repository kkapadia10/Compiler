package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * The while class handles while statements. 
 *
 * @author Kaden Kapadia
 * @version March 23, 2022
 */
public class While extends Statement
{
    // instance variables - replace the example below with your own
    private Conditional con;
    private Statement first;

    /**
     * Constructor for objects of class While.
     * 
     * @param conditional the conditional statement
     * @param start the statement in the while
     */
    public While(Conditional conditional, Statement start)
    {
        con = conditional;
        first = start;
    }

    /**
     * Executes a while statement by making sure the conditional is met.
     * If the conditional is met, the while statement is executed.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public void exec (Environment envir)
    {
        int reset = con.eval(envir);
        while (reset == 1)
        {
            if (first != null)
            {
                first.exec(envir);
            }
            reset = con.eval(envir);
        }
    }

    /**
     * Compiles while loop by creating a unique label ID. 
     * Then compiles the conditional and statement. Then jumps
     * to the top of the while and reevaluates the condition. 
     * If the condition is true, the loop will continue; if the loop is false,
     * the program willj jump to the endwhile. 
     * 
     * @param e the Emitter being used to write the file
     */
    public void compile(Emitter e)
    {
        int i = e.nextLabelID();
        
        e.emit("while" + i + ":   #jumps for while" + i);
        con.compile(e, "endwhile" + i);
        first.compile(e);
        e.emit("j while" + i + " #loops back to while");
        e.emit("endwhile" + i + ": #end of while");
    }
}
