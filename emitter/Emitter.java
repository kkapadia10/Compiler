package emitter;
import java.io.*;

/**
 * Emitter class converts the code into assembly/MIPS code. 
 *
 * @author Kaden Kapadia
 * @version 5/9/22
 */
public class Emitter
{
    private PrintWriter prnt;
    private int varCount;

    /**
     * Constructor for objects of the Emitter class. 
     * @param outputFileName the output file where the code is written
     */
    public Emitter(String outputFileName)
    {	
        try
        {
            prnt = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        varCount = 0;
    }

    /**
     * Prints a line of code to the new code file.
     */
    public void emit(String cg)
    {
        if (!cg.endsWith(":"))
        {
            cg = "\t" + cg;
        }
        prnt.println(cg);
    }    

    /**
     * Creates a new line. 
     */
    public void newline()
    {
        emit("la $a0 newline");
        emit("li $v0 4");
        emit("syscall #print newline");
    }

    /**
     * Closes the file.
     */
    public void close()
    {
        prnt.close();
    }

    /**
     * Pushes the reg value onto the stack.
     */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw " + reg +  " ($sp) #push on stack");
    }

    /**
     * Pops the top value from the stack to reg. 
     */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4 #pop off stack");
    }

    /**
     * Returns a unique label ID for a conditional.
     * This prevents conditionals from having the same call. 
     * 
     * @return a unique label ID for each conditional
     */
    public int nextLabelID()
    {
        varCount++;
        return varCount;
    }
}

