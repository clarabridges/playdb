package playground;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import playground.playdb.Database;
import playground.playdb.Interpreter;
import playground.playdb.Operation;
import playground.playdb.SyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RunnerTest {

    private Database instance;
    private Interpreter interpreter;
    ClassLoader classLoader;

    @Before
    public void setUp() {
        instance = new Database();
        interpreter = new Interpreter();
        classLoader = getClass().getClassLoader();
    }

    @Test
    public void testCase1() throws FileNotFoundException, SyntaxException {
        System.out.println("Running test case #1\n-----------------------");
        runForFile("test_case_1.txt");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
    }

    @Test
    public void testCase2() throws FileNotFoundException, SyntaxException {
        System.out.println("Running test case #2\n-----------------------");
        runForFile("test_case_2.txt");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
    }

    @Test
    public void testCase3() throws FileNotFoundException, SyntaxException {
        System.out.println("Running test case #3\n-----------------------");
        runForFile("test_case_3.txt");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
    }

    @Test
    public void testCase4() throws FileNotFoundException, SyntaxException {
        System.out.println("Running test case #4\n-----------------------");
        runForFile("test_case_4.txt");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
    }

    private void runForFile(String filename) throws FileNotFoundException, SyntaxException {
        Scanner scanner = new Scanner(new File(classLoader.getResource(filename).getFile()));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] testEntry = line.split(",");
            String in = testEntry[0].replaceAll("\"", "");
            String outPre = testEntry[1].replaceAll("\"", "");
            //in the code we return null for no output and the Runner handles it by not outputting anything
            //replicating here for validation
            String out = (outPre.length() == 0) ? null : outPre;

            Operation op = interpreter.parse(in);
            String output = instance.evaluate(op);
            System.out.println("expected: " + out + " || actual: " + output + "\n");
            assertEquals(out, output);
        }
    }
}
