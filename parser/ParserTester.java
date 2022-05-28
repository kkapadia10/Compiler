package parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import scanner.*;
import environment.Environment;
import ast.Statement;
import ast.Program;

/**
 * Tests the Parser class. 
 *
 * @author Kaden Kapadia
 * @version March 21, 2022
 */
public class ParserTester
{
    /**
     * Tests the Parser class after AST.
     * 
     * @throws FilenotFoundException if file name is invalid
     */
    public static void main(String[] args) throws FileNotFoundException, 
                                                    ScanErrorException
    {
        Scanner scan = new Scanner(new FileInputStream(
                                    new File("parserTest9.txt")));
                                    
        Parser par = new Parser(scan);
        Environment envir = new Environment(null); 
        //par.parseProgram().exec(envir);
        Program pro = par.parseProgram();
        pro.compile("output.asm");
    }
}
