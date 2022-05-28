package scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Tests the scanner class.
 *
 * @author Kaden Kapadia
 * @version January 27, 2022
 *
 */

public class ScannerTester
{
    /**
     * Tests the scanner class.
     */
    public static void main(String[] args) throws 
        ScanErrorException, IOException
    {
        Scanner scan = new Scanner(new FileInputStream
                            (new File("ScannerTestAdvanced.txt")));
        
        while (scan.hasNext())
        {
            System.out.println(scan.nextToken());
        }
    }
}