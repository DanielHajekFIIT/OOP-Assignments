# Task 10 - Research Archive: Serialization and Parallel Processing

### Data persistence, checked exceptions, and thread-based task execution

In this task you will build a small research archive system for a university lab.
The lab records experiment measurements, stores them on disk, loads them back later,
and runs analyses in parallel to speed up processing.

---

## Learning goals

By completing this task, you will practice:

- Designing classes with clear responsibilities
- Implementing custom checked exceptions
- Using Java serialization to persist and restore object graphs
- Using defensive copying and immutable views for safer APIs
- Executing independent tasks in parallel using threads
- Handling partial completion and timeout-like behavior robustly

---

## Domain story

A university research lab collects measurement sets for many small experiments.
Each experiment has metadata and measurement values. The lab wants to:

1. Store experiment records in memory
2. Save all records to disk and restore them later
3. Run computational tasks in parallel over records

Your implementation should be reusable and testable - not a one-off script.

---

## What to implement

All classes must be in package `org.jikvict.tasks.exposed`.

### 1) `InvalidRecordException`

Create a checked exception:

- Class name: `InvalidRecordException`
- Extends: `Exception`
- Constructor: `InvalidRecordException(String message)`

Use this when record data is invalid.

### 2) `DuplicateRecordException`

Create a checked exception:

- Class name: `DuplicateRecordException`
- Extends: `Exception`
- Constructor: `DuplicateRecordException(String message)`

Use this when adding a record with an already existing id.

### 3) `ExperimentRecord`

Represents one lab record and must support serialization.

Required class properties:

- Class name: `ExperimentRecord`
- Must implement `java.io.Serializable`
- Constructor:
	- `ExperimentRecord(String recordId, String author, java.util.List<Integer> measurements, long createdAtEpochMilli)`
	- Throws `InvalidRecordException` on invalid input

Required behavior:

- `recordId` and `author` must be non-null and not blank
- `measurements` must be non-null, non-empty, and contain only non-negative values
- `createdAtEpochMilli` must be positive
- Store measurements defensively (avoid exposing internal mutable state)
- Methods:
	- `String getRecordId()`
	- `String getAuthor()`
	- `java.util.List<Integer> getMeasurements()` (unmodifiable view)
	- `long getCreatedAtEpochMilli()`
	- `double average()`
	- `void addMeasurement(int value)` throws `InvalidRecordException`

Advanced serialization requirement:

- Add a transient cache field named `cachedAverage` of type `double`
- Recompute cache after deserialization (for example in `readObject`)

### 4) `ExperimentArchive`

Container for many records with save/load support.

Required class properties:

- Class name: `ExperimentArchive`
- Constructor: no-arg public constructor

Required behavior:

- `void addRecord(ExperimentRecord record)` throws `DuplicateRecordException`
	- duplicate means same `recordId`
- `java.util.List<ExperimentRecord> getRecords()`
	- return an unmodifiable list
- `java.util.Optional<ExperimentRecord> findById(String recordId)`
- `void saveToFile(String filePath)` throws `java.io.IOException`
	- serialize entire archive to file
- `static ExperimentArchive loadFromFile(String filePath)`
	- throws `java.io.IOException`, `ClassNotFoundException`

### 5) `ParallelTaskRunner`

Utility that executes independent tasks using a fixed-size thread pool.

Required class properties:

- Class name: `ParallelTaskRunner`
- Constructor: `ParallelTaskRunner(int threadCount)`
	- threadCount must be positive

Required behavior:

- `java.util.List<?> runAll(java.util.List<? extends java.util.concurrent.Callable<?>> tasks)`
	- execute tasks in parallel
	- return results in the same order as input task list
	- if task list is null, throw `IllegalArgumentException`
- `java.util.List<?> runAllWithTimeout(java.util.List<? extends java.util.concurrent.Callable<?>> tasks, long timeoutMillis)`
	- execute tasks in parallel
	- return only results that completed before timeout
	- preserve input order among returned results
	- timeoutMillis must be positive
- `void shutdown()`
	- release resources of underlying executor

Design note: handling interruption, cancellation, and checked exceptions cleanly is part of the challenge.

---

## Constraints and expectations

- Do not use external libraries for serialization or concurrency utilities beyond standard Java SDK.
- Avoid leaking mutable internal collections.
- Avoid race conditions and resource leaks.

---

## Suggested implementation strategy

1. Implement both exceptions first.
2. Implement `ExperimentRecord` with full validation and defensive copying.
3. Implement `ExperimentArchive`, then test save/load manually.
4. Implement `ParallelTaskRunner` and verify execution ordering.
5. Add timeout handling and proper shutdown behavior.
