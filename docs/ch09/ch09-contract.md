# ch09-contract.md

## 1. Chapter Goal

- Introduce external dependency management as a core system concern.
- Teach that large systems often operate on data they do not own.
- Establish controlled ingestion as a first-class architectural responsibility.

---

## 2. Primary Pressure Introduced

- Uncontrolled input pressure:
    - unknown data quality
    - unpredictable source behavior
    - variable response times
- The system cannot dictate the pace or reliability of external entities.

---

## 3. What the Reader Must Observe or Feel

- External systems define the system’s weakest points.
- Throughput is constrained by the slowest dependency.
- Failure often originates outside system boundaries.
- Progress requires discipline, not speed or completeness.
- The reader should feel tension between:
    - completeness
    - safety
    - resource limits

---

## 4. What Must NOT Be Introduced Yet

- No ranking or relevance logic.
- No semantic understanding of data.
- No advanced scheduling strategies.
- No real-time guarantees.
- No assumption of cooperative external systems.

If the design assumes friendly or predictable sources, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “We fetch data.”

To:
- “We carefully control how data enters the system.”

The reader must internalize:
- ingestion is adversarial by default
- boundaries matter more than throughput
- safety precedes completeness

---

## 6. Guardrails for Implementation Exercises

- Treat every external call as unreliable.
- Control concurrency explicitly.
- Isolate ingestion from consumption.
- Accept partial progress as success.
- Stop once ingestion boundaries are enforceable.

If the exercise optimizes speed instead of containment, it exceeds this chapter’s intent.
