package ast;
import java.util.List;
import environment.Environment;
import emitter.Emitter;

/**
 * Block class groups statements together so they are
 * evaluated together.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class Block extends Statement
{
    // instance variables - replace the example below with your own
    private List < Statement > state;

    /**
     * Constructor for objects of class Block.
     * 
     * @param list list that contains all the statements 
     */
    public Block(List < Statement > list)
    {
        state = list;
    }

    /**
     * Executes the block by iterating through the list.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public void exec (Environment envir)
    {
        int i = 0;
        while (i < state.size())
        {
            Statement sta = state.get(i);
            sta.exec(envir); 
            i++;
        }
    }

    /**
     * Compiles a block by compiling every statement in the block.
     * 
     * @param e the Emitter being used to write the file
     */
    @Override
    public void compile(Emitter e)
    {
        for (int i = 0; i < state.size(); i++)
        {
            Statement s = state.get(i);
            s.compile(e);
        }
    }
}
