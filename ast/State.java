package ast;

import environment.Environment;
import java.util.List;
import java.util.*;
import environment.*;

/**
 * Same as the code for ProcedureCall, execpt using the execute method instead
 * of the evaluate method.
 *
 * @author Kaden Kapadia
 * @version 4/12/22
 */
public class State extends Statement
{
    // instance variables - replace the example below with your own
    private String str;
    private List<Expression> list;

    /**
     * Constructor for objects of class ProcedureCall.
     * 
     * @param str the procedure name
     * @param list the procedure's arguements
     */
    public State(String s, List<Expression> l)
    {
        str = s;
        list = l;
    }

    /**
     * Gets the procedure then evaluates the arguements from the procedure.
     * Then, executes procedure call. 
     * There is no return statement in this method, unlike in ProcedureCall.
     *
     * @throw IllegalArguementException if the size of the list is not the same as the size
     * of the procedure declaration.
     * @param  envir the environment that evaluates the expression
     */
    @Override
    public void exec(Environment envir)
    {
        Environment env = envir.getEnvir();
        Environment e = new Environment(env);
        ProcedureDeclaration pd = envir.getProcedure(str);
        List<String> pl = pd.getList();
        
        if (list.size() == pl.size())
        {
            
            for (int i = 0; i < pl.size(); i++)
            {
                e.declareVariable(pl.get(i), list.get(i).eval(envir));
            }
            
            e.declareVariable(str, 0);
            pd.getStatement().exec(envir);
        }
        else
        {
            throw new IllegalArgumentException("Incorrect number of arguements in call");
        }
    }
}
