package playground.playdb;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Database {

    private Map<String, String> data;
    private Multiset<String> counts;
    //keeps a ledger of operations, modified value, and new value
    private Deque<LogEntry> opLog;
    //used to keep track of active transactions and identify which ops in the opLog belong to which transaction
    private int transactionDepth;

    private boolean running = true;

    public Database() {
        data = new HashMap<>();
        opLog = new LinkedList<>();
        counts = HashMultiset.create();
        transactionDepth = 0;
    }

    private String get(String key) {
        String ret = null;
        String tmp = data.get(key);
        if (tmp == null)
            ret = "NULL";
        else
            ret = tmp;
        return ret;
    }

    private void set(String key, String value) {
        String prev = data.get(key);
        if (this.transactionDepth > 0)
            opLog.push(new LogEntry(transactionDepth, key, value, prev));
        data.put(key, value);
        //decrement from counts the previous value if it exists
        counts.remove(prev);
        //increment in counts the new value
        counts.add(value);
    }

    private void delete(String key) {
        String val = data.get(key);
        //enter into log if a transaction is active
        if (this.transactionDepth > 0)
            opLog.push(new LogEntry(transactionDepth, key, null, data.get(key)));
        data.remove(key);
        counts.remove(val);
    }

    private String count(String val) {
        return String.valueOf(counts.count(val));
    }

    private void begin() {
        transactionDepth++;
    }

    private void rollback(boolean debug) {
        if (transactionDepth < 1) {
            System.out.println("TRANSACTION NOT FOUND");
        } else {

            while (opLog.peek() != null && opLog.peek().getTransactionId() == transactionDepth) {
                LogEntry tmp = opLog.pop();
                //visible rollback for debug and fun purposes
                if(debug)
                    System.out.println("Replacing " + tmp.getCurrent() + " with " + tmp.getPrev() + " for " + tmp.getKey());

                if(tmp.getPrev() == null)
                    data.remove(tmp.getKey());
                else
                    data.put(tmp.getKey(), tmp.getPrev());

                counts.remove(tmp.getCurrent());
                counts.add(tmp.getPrev());
            }

            transactionDepth--;
        }
    }

    private void commit() {
        while ((opLog.peek() != null) && (opLog.peek().getTransactionId() == transactionDepth)) {
            opLog.pop();
        }
        transactionDepth--;
    }

    public boolean isRunning() {
        return this.running;
    }

    public String evaluate(Operation op) {
        //ignore empty returns
        if(op == null) return "";
        String out = null;

        switch (op.getOp()) {
            case "BEGIN":
                begin();
                break;
            case "ROLLBACK":
                boolean debug = (op.getVar() != null);
                rollback(debug);
                break;
            case "COMMIT":
                commit();
                break;
            case "SET":
                set(op.getVar(), op.getVal());
                break;
            case "GET":
                out = get(op.getVar());
                break;
            case "DELETE":
                delete(op.getVar());
                break;
            case "COUNT":
                out = count(op.getVar());
                break;
            case "END":
                this.running = false;
                break;
            case "SHOW":
                printState();
                break;
            default:
                out = "Syntax error. Please verify your input.";
        }
        return out;
    }

    private void printState() {
        StringBuilder opLogString = new StringBuilder();
        for (Object anOpLog : opLog) {
            opLogString.append(anOpLog.toString());
        }

        System.out.println("DATA: " + data.toString() +
                "\nOP LOG: " + opLogString.toString() +
                "\nTransactions: " + this.transactionDepth +
                "\nCounts: " + "[" + String.join(", ", counts) + "]" + "\n");
    }

}
