package playground;

import playground.playdb.Database;
import playground.playdb.Interpreter;
import playground.playdb.Operation;
import playground.playdb.SyntaxException;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Database instance = new Database();
        Interpreter interpreter = new Interpreter();

        System.out.print("Playground DB started\nplaydb > ");
        while(instance.isRunning()) {
            try {
                Operation op = interpreter.parse(sc.nextLine());
                String output = instance.evaluate(op);
                if(output != null) System.out.println(output);
            } catch (SyntaxException e) {
                System.out.println(e.getMessage());
            }
            //checking that we didn't close the session during this operation TODO TEST
            if(instance.isRunning())
                System.out.print("playdb > ");
        }
    }
}
