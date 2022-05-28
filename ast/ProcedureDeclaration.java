package ast;
import java.util.List;
import environment.Environment;

/**
 * Class for procedure declarations in AST. Uses the procedure name,
 * statement call, and arguements to represetn a procedure declaration.
 * represent a procoedure call.
 *
 * @author Kaden Kapadia
 * @version 4/12/22
 */
public class ProcedureDeclaration
{
    // instance variables - replace the example below with your own
    private String str;
    private List<String> list;
    private Statement stmt;

    /**
     * Constructor for objects of class ProcedureDeclaration.
     * 
     * @param str the procedure name
     * @param list the procedure's arguements
     * @param stmt the procedure's statement call
     */
    public ProcedureDeclaration(String name, List<String> l, Statement s)
    {
       str = name;
       list = l;
       stmt = s;
    }

    /**
     * Returns the list used in the procedure.
     * 
     * @return list list used in the procedure
     */
    public List<String> getList()
    {
        return list;
    }
    
    /**
     * Returns the statement used in the procedure.
     * 
     * @return stmt the statement used in the procedure
     */    
    public Statement getStatement()
    {
        return stmt;
    }
    
    /**
     * Executes the procedure.
     * 
     * @param envir the environment that evaluates the expression
     */
    public void exec(Environment envir)
    {
        envir.setProcedure(str, this);
    }
}
