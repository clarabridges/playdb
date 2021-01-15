package playground.playdb;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

public class Operation {
    @Getter @Setter private String op;
    @Getter @Setter private String var;
    @Getter @Setter private String val;

    public Operation(String op, String var, String val) throws SyntaxException {
        if(Interpreter.keywords.contains(var)) {
            throw new SyntaxException("You cannot use a keyword as an entry name.\nReserved keywords: "
                    + String.join(",", Interpreter.keywords));
        }
        this.op = op;
        this.var = var;
        this.val = val;
    }

}
