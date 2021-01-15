# PlayDB

In-memory key value store with transactional support. Uses a queue to maintain a ledger of operations in the scope of any number of transactions. Available commands: 
- SET {name} {value}                                                                                                                                                                      
- GET {name}
- DELETE {name}
- COUNT {value}
- END
- BEGIN
- ROLLBACK [**VIEW**] - optional parameter for seeing the operations being rolled back]
- COMMIT
- **SHOW** - additional command for introspection into the state of the database (state, transactions, transactioned operations, counts)
                                                                                                                                                                      
#### Performance considerations

All data structures selected for storage of data, ledger of transactional operations, and totals per value have an 
average operational performance of O(1)* for insertions, deletions, and access.

Data structures used:
- HashMap
- Multiset (HashMap implementation from Google's Guava library)
- Queue   

*https://www.bigocheatsheet.com/


#### Testing
Four existing test cases are included in src/test/resources and ran via jUnit.


#### Edge cases covered

Although not an exhaustive list, the following are some of the edge cases accounted for:
- extra spaces in commands are ignored
- carriage returns are ignored
- commands are validated for correct syntax depending on the number of parameters they take and the user is alerted if malformed
- command verbs are treated as reserved keywords

#### How to use

1. Build docker image
    ```
    cd playdb
    docker build -t playdb .
    ```
2. Run
    ```
    docker run -it playdb
    ```
3. Play
    ```
    root@container:/opt/playdb# ./playdb.sh run-tests
    root@container:/opt/playdb# ./playdb.sh
    ```
