package ast;

import environment.Environment;
import java.util.List;
import java.util.*;

/**
 * Class for procedure calls in AST. Uses the procedure name and arguements to 
 * represent a procoedure call.
 *
 * @author Kaden Kapadia
 * @version 4/12/22
 */
public class ProcedureCall extends Expression
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
    public ProcedureCall(String s, List<Expression> l)
    {
        str = s;
        list = l;
    }

    /**
     * Gets the procedure then evaluates the arguements from the procedure.
     * Then, executes procedure call. 
     *
     * @throw IllegalArgumentException if the size of the list is the same as the size
     * of the procedure declaration.
     * @param  envir the environment that evaluates the expression
     * @return the value of the produre at the end
     */
    public int eval(Environment envir)
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
        
        return e.getVariable(str);
    }
}
