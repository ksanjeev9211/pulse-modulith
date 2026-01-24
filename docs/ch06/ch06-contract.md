# ch06-contract.md

## 1. Chapter Goal

- Establish data access as a system-level contract, not a storage detail.
- Teach how simple read/write semantics become complex at scale.
- Frame data systems around guarantees, not implementations.

---

## 2. Primary Pressure Introduced

- Data correctness pressure:
    - concurrent access
    - partial failures
    - inconsistent views
- The system must continue functioning despite unreliable components.
- Perfect consistency conflicts with availability under failure.

---

## 3. What the Reader Must Observe or Feel

- A key-value interface appears simple but hides deep tradeoffs.
- Failure is the default state, not the exception.
- Guarantees must be chosen intentionally.
- There is no universally correct data behavior.
- The reader should feel tension between:
    - correctness
    - availability
    - latency

---

## 4. What Must NOT Be Introduced Yet

- No business-domain modeling.
- No advanced indexing or query languages.
- No analytics workloads.
- No multi-table transactions.
- No premature optimization of access patterns.

If the design resembles a general-purpose database, it exceeds this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “How do we store data?”

To:
- “What behavior does the system promise under failure?”

The reader must internalize:
- interfaces define systems more than internal implementations
- tradeoffs are unavoidable
- data systems are about guarantees, not perfection

---

## 6. Guardrails for Implementation Exercises

- Define read and write expectations explicitly.
- Decide behavior under failure before success paths.
- Treat network unreliability as normal.
- Prefer clarity of guarantees over feature richness.
- Stop once behavioral contracts are clear.

If the exercise expands functionality instead of defining guarantees, it violates this chapter.
