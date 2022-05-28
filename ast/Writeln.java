package ast;
import environment.Environment;
import emitter.Emitter;

/**
 * Writeln class handles the printing of the values.
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of class WriteLn.
     * 
     * @param expression the expression being evaluated then printed
     */
    public Writeln(Expression expression)
    {
        exp = expression;
    }

    /**
     * Executes the writeln statement and prints it.
     * 
     * @param envir the environment that evaluates the expression
     */
    @Override
    public void exec (Environment envir)
    {
        System.out.println(exp.eval(envir));
    }

    /**
     * Compiles a writeln by writing the MIPS/assembly version.
     * 
     * @param e the Emitter being used to write the file.
     */
    @Override
    public void compile(Emitter e)
    {
        exp.compile(e); 
        e.emit("move $a0, $v0 #moves $v0 to $a0");
        e.emit("li $v0, 1 #print");
        e.emit("syscall");
        e.newline();
    }
}
