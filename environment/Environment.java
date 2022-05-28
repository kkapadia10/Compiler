package environment;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import ast.ProcedureDeclaration;

/**
 * Environment class which includes the variables and values which AST uses.
 *
 * @author Kaden Kapadia 
 * @version March 21, 2022
 */
public class Environment
{
    // instance variables - replace the example below with your own
    private Map < String, Integer > vars;
    private Map < String, ProcedureDeclaration > procs;
    private Environment envir;

    /**
     * Constructor for objects of class Environment.
     */
    public Environment(Environment e)
    {
        vars = new HashMap < String, Integer > ();
        procs = new HashMap < String, ProcedureDeclaration > ();
        envir = e;
    }

    /**
     * Sets the variables for the environment to use. 
     * The variables are put into a map.
     * 
     * @var the variable being used
     * @val the value of the variable
     */
    public void setVariable (String var, Integer val)
    {
        if (!vars.containsKey(var) && envir != null && envir.containsVariable(var))
        {
            envir.setVariable(var, val);
            return;
        }

        vars.put(var, val);
    }

    /**
     * Checks to see if the the environment contains the given variable.
     * 
     * @return true if the environment contains the given variable; otherwise,
     *         false
     */
    private boolean containsVariable(String var)
    {        
        if (envir == null)
        {
            return false;
        }
        else
        {
            return (envir.containsVariable(var) || vars.containsKey(var));
        }
    }

    /**
     * Retrieves the variable, if it exists.
     * 
     * @var the variable to be retrieved.
     */
    public int getVariable (String var)
    {
        if (envir != null)
        {
            return envir.getVariable(var);
        }

        else if (vars.containsKey(var))
        {
            return vars.get(var);
        }

        else
        {
            vars.put(var, 0);
            return 0;
        }
    }

    /**
     * Declares the variable of the procedure to be the given variable and value.
     * 
     * @param var the variable name
     * @param val the value of the variable
     */
    public void declareVariable(String var, int val)
    {
        vars.put(var, val);
    }

    /**
     * Gets the procedure with the name we are looking for.
     * 
     * @param str the name of the procedure
     * @return the statement associated with the requested procedure
     */
    public ProcedureDeclaration getProcedure(String str) 
    {
        ProcedureDeclaration pd;

        if (envir != null) 
        {
            return envir.getProcedure(str);
        }

        if (!procs.containsKey(str))
        {
            throw new RuntimeException("Procedure " + str + " not found");
        }
        else
        {
            pd = procs.get(str);                        
        }

        return pd;
    }

    /**
     * Sets the procedure to the given name and procedure declaration.
     * 
     * @param str the procedure name
     * @param pd the procedure declaration
     */
    public void setProcedure(String str, ProcedureDeclaration pd) 
    {
        if (envir == null)
        {
            procs.put(str, pd);
        }
        else 
        {

            envir.setProcedure(str, pd);
            return;
        }
    }

    /**
     * Gets the environment if it is not null.
     * 
     * @return the environment if it is there; otherwise,
     *         return null
     */
    public Environment getEnvir()
    {
        if (envir != null)
        {
            return envir.getEnvir();
        }

        return this;
    }
}