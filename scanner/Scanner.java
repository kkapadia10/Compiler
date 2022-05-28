package scanner;

import java.io.*;
import java.io.IOException;

/**
 * Scanner is a simple scanner for Compilers and Interpreters 
 * (2014-2015) lab exercise 1. In this lab, we parsed through a file
 * and outputted the specific token associated with each character in the file.
 * For example, we examined digits, letters, operands, and whitespace. 
 * Additionally, we checked if we reached the end of the line or end of the file
 * and returned EOL and EOF, respectively.
 *
 * @author Kaden Kapadia
 * @author Anu Datar
 * @version January 27, 2022
 *
 * Usage: Provide some input text for the scanner. The scanner will parse 
 * through it (line by line) and will then output the various lexemes 
 * in the input text.
 *
 */

public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(file name);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field 
     * currentChar. Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Gets the next character of the input.
     */
    private void getNextChar()
    {
        try
        {
            int input = in.read();
            if (input == -1)
                eof = true;
            else currentChar = (char) input;
            if (currentChar == '.')
            {
                eof = true;
            }
        }
        catch (IOException e)
        {
            System.out.println("IOException Error");
            e.printStackTrace();
            //System.exit(-1);
        }
    }

    /**
     * Checks whether currentChar and the parameter are equal.
     * @param expected the character currentChar is being compared to
     * @throws ScanErrorException thrown if there is a file-reading error
     */
    private void eat(char expected) throws ScanErrorException
    {
        if(expected != currentChar)
        {
            throw new ScanErrorException("Illegal character. Expected: " 
                + expected + ". Instead, got:  " 
                + currentChar);
        }
        getNextChar();
    }

    /**
     * Checks if there are more characters to be parsed in the file.
     * @return true if there are more characters in the file; otherwise,
     *          false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Checks if the given character is a digit (0-9).
     * @param input the character to be checked
     * @return true if the given character is a digit; otherwise,
     *          false
     */
    public static boolean isDigit(char input)
    {
        return input >= '0' && input <= '9';
    }

    /**
     * Checks if the given character is a letter, without regard to case
     * @param input the character to be checked
     * @return true if the given character is a letter; otherwise,
     *          false
     */
    public static boolean isLetter(char input)
    {
        return (input >= 'a' &&  input <= 'z') || 
        (input >= 'A' && input <= 'Z');
    }

    /**
     * Checks if the given character is a whitespace.
     * @param input the character to be checked
     * @return true if the given character is a white space; otherwise,
     *          false
     */
    public static boolean isWhiteSpace(char input)
    {
        return (input == '\n' || input == '\t' || 
            input == '\r' || input == ' ');
    }

    /**
     * Checks if the given character is an operand.
     *
     * @param input the character to be checked
     * @return true if the given character is an operand; otherwise,
     *          false
     */
    private boolean isOperand(char input)
    {
        return (input == '+' || input == '-' || input == '*' || 
            input == '/' || input == '%' || input == '='
            || input == '<' || input == '>' || input == '(' 
            || input == ')' || input == ':' || input == ',');
    }

    /**
     * Checks if the inputted character is an operand that can be a
     * two character operator (used consecutively, ex: ==).
     *
     * @param input the character to be checked
     * @return true if the given character is an operator 
     *              that can be consecutively used; otherwise,
     *         false
     */
    public static boolean isDoubleOperator(char input)
    {
        return (input == '=' || input == '<' || input == '>' || input == ':');
    }

    /**
     * Checks if we are at the end of the line.
     *
     * @param input the character to be checked
     * @return true if we are at the end of the line; otherwise,
     *          false
     */
    public static boolean endOfLine(char input)
    {
        return (input == ';');
    }

    /**
     * Checks if we are at the end of the file being checked.
     *
     * @param input the character to be checked
     * @return true if we are at the end of the file; otherwise,
     *          false
     */
    public static boolean endOfFile(char input)
    {
        return (input == '.');
    }

    /**
     * Scans a number using its regex definition: digit(digit)*.
     * @return the number being read in string form
     * @throws ScanErrorException thrown if not a number
     */
    private String scanNumber() throws ScanErrorException
    {
        String lex = "";
        if (!isWhiteSpace(currentChar) && isDigit(currentChar))
        {
            lex += currentChar;
            if (hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return lex;
            }
        }
        else
        {
            throw new ScanErrorException("Expected valid number");
        }
        while(hasNext() && !isWhiteSpace(currentChar) && isDigit(currentChar))
        {
            lex += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return lex;
            }
        }
        return lex;
    }

    /**
     * Scans an identifier using its regex definition: letter(letter|digit)*.
     * @return the identifier being read in
     * @throws ScanErrorException thrown if not an identifier
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String lex = "";
        if (isLetter(currentChar))
        {
            lex += currentChar;
            if (hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return lex;
            }
        }
        else
        {
            throw new ScanErrorException("Expected valid identifier");
        }
        while(hasNext() && isDigit(currentChar) && 
        !isWhiteSpace(currentChar) || isLetter(currentChar))
        {
            lex += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return lex;
            }
        }
        return lex;
    }

    /**
     * Scans an operand using its regex definition: [+, - , *, /, %, =, <, >].
     * @return the operand being read in
     * @throws ScanErrorException thrown if not an operand
     */
    private String scanOperand() throws ScanErrorException
    {
        String lex = "";
        if (isOperand(currentChar))
        {
            lex += currentChar;
            if (hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return lex;
            }
        }
        else
        {
            throw new ScanErrorException("Expected valid operand");
        }        
        while(isDoubleOperator(currentChar))
        {
            lex += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return lex;
            }
        }
        return lex;
    }

    /**
     * Scans an in line comment by eating every 
     * subsequent character from the line.
     *
     * @throws ScanErrorException
     */
    private void scanInLineComments() throws ScanErrorException
    {
        while (hasNext() && (currentChar != '\n'))
        {
            eat(currentChar);
        }
        eat(currentChar);
    }

    /**
     * Parses through the given file while figuring out 
     * the type of token presented. Based on that, it returns 
     * the specific token. Removes in line comments and determines
     * where the end of the line and end of the file are.
     *
     * @return the tokens scanned through
     */
    public String nextToken() throws ScanErrorException
    {
        String tok = "";

        if (hasNext()) 
        {
            while (hasNext() && isWhiteSpace(currentChar)) 
            {
                eat(currentChar);
            }
            if (isOperand(currentChar)) 
            {
                if (currentChar != '/' && isOperand(currentChar)) 
                {
                    tok = scanOperand();
                    return tok;
                }
                if (currentChar == '/') 
                {
                    eat(currentChar);
                    if (currentChar == '/') 
                    {
                        scanInLineComments();
                    } 
                    else 
                    {
                        return "/";
                    }
                }
            }            
            if (isDigit(currentChar)) 
            {
                tok = scanNumber();
                return tok;
            } 
            else if (isLetter(currentChar)) 
            {
                tok = scanIdentifier();
                return tok;
            } 
            else if (endOfLine(currentChar)) 
            {
                tok = "EOL";
                eat(currentChar);
            } 
            
            else if (!hasNext()) 
            {
                tok = "EOF";
                eof = true;
            }
        }
        if (tok.compareTo("//") != 0 && !tok.equals(""))
        {
            return tok;
        }
        else
        {
            System.out.println("Character Error: " + currentChar);            
            throw new ScanErrorException(currentChar + " is not valid");
        }
    }
}