package playground.playdb;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {

    public static final Set<String> keywords = Sets.newHashSet("BEGIN", "ROLLBACK", "COMMIT", "SET", "GET", "DELETE", "COUNT", "END", "SHOW");
    private final String operation = "^\\s*(" + String.join("|", keywords) + ")(\\s+[a-zA-Z0-9]*)?(\\s+[a-zA-Z0-9]*)?\\s*$";

    private HashMap<String, String> ops = new HashMap<>();
    private Pattern opsPattern = null;

    public Interpreter() {
        opsPattern = Pattern.compile(operation);

        ops.put("BEGIN", "^\\s*(BEGIN)\\s*$");
        ops.put("ROLLBACK", "^\\s*(ROLLBACK)(\\s+VIEW)?\\s*$");
        ops.put("COMMIT", "^\\s*(COMMIT)\\s*$");
        ops.put("SET", "^\\s*(SET)\\s+([a-zA-Z0-9]*)\\s+([a-zA-Z0-9]*)\\s*$");
        ops.put("GET", "^\\s*GET\\s+[a-zA-Z0-9]*\\s*$");
        ops.put("DELETE", "^\\s*DELETE\\s+[a-zA-Z0-9]*\\s*$");
        ops.put("COUNT", "^\\s*COUNT\\s+[a-zA-Z0-9]+\\s*$");
        ops.put("END", "^\\s*END\\s*$");
        ops.put("SHOW", "^\\s*SHOW\\s*$");
    }


    public Operation parse(String command) throws SyntaxException {
        //ignore an empty return
        if (command.replaceAll("\\s", "").length() == 0)
            return null;

        Operation operation = null;
        Matcher m = opsPattern.matcher(command);

        //validate that the operation sent matches the expected syntax
        if (m.find() && command.matches(ops.get(m.group(1)))) {
            operation = new Operation(m.group(1), stringOrNull(m.group(2)), stringOrNull(m.group(3)));
        } else {
            throw new SyntaxException();
        }

        return operation;
    }

    private String stringOrNull(String param) {
        String ret = null;
        if(param != null) {
            String str = param.replaceAll("\\s", "");
            ret = (str.length() == 0) ? null : str;
        }
        return ret;
    }
}
