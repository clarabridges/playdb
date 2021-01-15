package playground.playdb;

import lombok.Getter;

public class LogEntry {
    //TODO add transaction #
    @Getter private Integer transactionId;
    @Getter private String key;
    @Getter private String prev;
    @Getter private String current;

    public LogEntry(int id, String k, String c, String p) {
        this.transactionId = id;
        this.key = k;
        this.current = c;
        this.prev = p;
    }

    public String toString() {
        return "[" + this.transactionId + ", " + this.key + "," + this.prev + "," + this.current + "]";
    }
}
