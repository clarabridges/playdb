package playground.playdb;

public class SyntaxException extends Exception {

    public SyntaxException() {
        super("Syntax error. Allowed commands: " + String.join(",", Interpreter.keywords));
    }

    public SyntaxException(String message) {
        super(message);
    }
}
