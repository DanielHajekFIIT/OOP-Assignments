package org.jikvict.tasks.exposed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class ExperimentRecord implements Serializable {
    private String recordId;
    private String author;
    private List<Integer> measurements;
    private long createdAtEpochMilli;

    private transient double cachedAverage;

    public ExperimentRecord(String recordId, String author, List<Integer> measurements, long createdAtEpochMilli)
            throws InvalidRecordException {
        // isBlank() is used instead of isEmpty() to also get strings full of spaces, such as: " "
        if (recordId == null || recordId.isBlank() ||
            author == null || author.isBlank() ||
            measurements == null || measurements.stream().allMatch(m -> m < 0) ||
            createdAtEpochMilli <= 0) {
            throw new InvalidRecordException("Invalid record id or author or measurements or epoch!");
        }
        this.recordId = recordId;
        this.author = author;
        // Defensive copy - instead of directly assigning to measurements (reference), store a copy in the instance
        this.measurements = new ArrayList<>(measurements);
        this.createdAtEpochMilli = createdAtEpochMilli;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getAuthor() {
        return author;
    }

    public java.util.List<Integer> getMeasurements() {
        // Return immutable copy
        return Collections.unmodifiableList(measurements);
    }

    public long getCreatedAtEpochMilli() {
        return createdAtEpochMilli;
    }

    public double average() {
        double sum = 0;
        for (Integer measurement : measurements) {
            sum += measurement;
        }

        // measurements can never be of size 0
        return sum / measurements.size();
    }

    public void addMeasurement(int value) throws InvalidRecordException {
        if (value < 0) {
            throw new InvalidRecordException("Invalid value for new measurement!");
        }
            measurements.add(value);
    }

    // Here is where some hookey pokey happens and all the non-transient fields are restored and the transient
    // one, in this case cachedAverage, is recomputed.
    // A function like this should be private for some reason
    // and "Java finds it automatically during deserialization via reflection" (whatever that means)
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // restores all non-transient fields normally
        this.cachedAverage = average(); // recompute the cache
    }
}
