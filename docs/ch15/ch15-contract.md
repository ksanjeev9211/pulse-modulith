# ch15-contract.md

## 1. Chapter Goal

- Introduce stateful collaboration over shared data.
- Teach how user actions interact through the same objects.
- Establish storage systems as active participants in user experience.

---

## 2. Primary Pressure Introduced

- Concurrent modification pressure:
    - multiple users editing the same data
    - overlapping operations
    - conflicting intents
- The system must preserve usability without strict serialization.

---

## 3. What the Reader Must Observe or Feel

- Simultaneous access is the default, not the edge case.
- Conflicts emerge naturally from collaboration.
- Strong consistency degrades availability and responsiveness.
- User experience depends on conflict-handling strategy.
- The reader should feel tension between:
    - correctness
    - usability
    - latency

---

## 4. What Must NOT Be Introduced Yet

- No advanced collaboration intelligence.
- No semantic merge logic.
- No recommendation or automation features.
- No enterprise governance workflows.
- No deep offline-first optimization.

If the design attempts perfect conflict resolution, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Store user files.”

To:
- “Coordinate shared state across many actors.”

The reader must internalize:
- concurrency is unavoidable
- conflicts are design choices
- storage behavior directly shapes collaboration

---

## 6. Guardrails for Implementation Exercises

- Model concurrent edits explicitly.
- Accept conflict as normal.
- Prefer predictable behavior over perfect outcomes.
- Separate user intent from storage state.
- Stop once conflict-handling boundaries are clear.

If the exercise aims for flawless synchronization, it exceeds this chapter’s intent.
