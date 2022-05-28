package ast;
import java.util.List;
import environment.*;
import emitter.Emitter;

/**
 * Program class for AST. This class outlines an object for AST.
 *
 * @author Kaden Kapadia
 * @version April 12, 2022
 */
public class Program
{
    // instance variables - replace the example below with your own
    private List<ProcedureDeclaration> proced;
    private Statement stmt;
    private List<String> variables;

    /**
     * Constructor for objects of class Program
     */
    public Program(List<ProcedureDeclaration> procs, Statement s, List<String> vars)
    {
        // initialise instance variables
        proced = procs;
        stmt = s;        
        variables = vars;
    }

    /**
     * Executes all the objects from the environment.
     * 
     * @param envir the environment being evaluated
     */
    public void exec(Environment envir)
    {
        for (int i = 0; i < proced.size(); i++)
        {
            ProcedureDeclaration pd = proced.get(i);
            pd.exec(envir);
        }
        stmt.exec(envir);
    }

    /**
     * Sends the program to the mips file. 
     * 
     * @param filename the filename being used for the output file
     */
    public void compile (String filename)
    {
        {
            Emitter e = new Emitter(filename);
            e.emit(".data");
            e.emit("newline: .asciiz \"\\n\" #newline variable");
            for (int i = 0; i < variables.size(); i++)
            {
                String var = variables.get(i);
                e.emit("var" + var + ": .word " + 0 + " #initialized as 0");
            }
            e.emit(".text");
            e.emit(".globl main");
            e.emit("main: #QTSPIM automatically looks for main");
            stmt.compile(e);
            e.emit("li $v0 10");
            e.emit("syscall #exits");
        }
    }
}
