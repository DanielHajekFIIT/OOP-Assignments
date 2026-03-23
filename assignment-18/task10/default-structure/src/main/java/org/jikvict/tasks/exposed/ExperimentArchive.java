package org.jikvict.tasks.exposed;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// the class doing the saving and loading should also be serializable, not just the record itself
public class ExperimentArchive implements Serializable {
    private List<ExperimentRecord> records = new ArrayList<>();

    public ExperimentArchive() {}

    public void addRecord(ExperimentRecord record) throws DuplicateRecordException {
        // r is a member of records list, returns true if any recordId matches
        if (records.stream()
                   .anyMatch(r -> r.getRecordId().equals(record.getRecordId()))) {
            throw new DuplicateRecordException("Duplicate record");
        }
        records.add(record);
    }

    public List<ExperimentRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public Optional<ExperimentRecord> findById(String recordId) {
        // filter is used to remove any elements that don't satisfy the predicate, then findFirst() just grabs
        // the first element in the stream without thinking (IDK why this isn't a single method grr)
        return records.stream()
                      .filter(r -> r.getRecordId().equals(recordId))
                      .findFirst();
    }

    // -------------Serialization and deserialization--------------------
    // (still a bit of black magic to me)
    public void saveToFile(String filePath) throws IOException {
        // Important to close all the streams, just like in C.
        // Try-with-resources ensures the streams are automatically closed. Denoted by declarations in brackets
        // after try.
        // Could also be done in a try catch block with a "finally" block at the end to handle the exception, as well
        // as closing the stream manually, here it's combined.
        // If done manually, streams have to be closed in the reverse order of their declaration.
        try (
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(file)) {
            // this keyword used to refer to instance
            out.writeObject(this);
        }
        // No catch blocks required, the caller should handle exceptions
        // Try block remains in case of exception to safely close the streams
    }

    public static ExperimentArchive loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        // Try-with-resources to ensure streams are closed
        try (
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file)) {
            return (ExperimentArchive) in.readObject();
        }
        // Again, as with saveToFile, caller is responsible for handling exceptions
    }
}
