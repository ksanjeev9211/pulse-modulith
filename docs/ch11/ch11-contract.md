# ch11-contract.md

## 1. Chapter Goal

- Integrate multiple prior concepts into a cohesive large-scale system.
- Teach how read and write paths evolve under different scaling pressures.
- Establish feeds as a coordination problem across storage, fanout, and retrieval paths.

---

## 2. Primary Pressure Introduced

- Read–write asymmetry pressure:
    - heavy reads
    - uneven fanout
    - bursty writes
- Optimizing one path degrades the other.
- A single design cannot serve all access patterns equally.

---

## 3. What the Reader Must Observe or Feel

- Scale forces explicit tradeoffs between:
    - latency
    - freshness
    - compute cost
- User experience depends on invisible background work.
- Precomputation shifts cost in time, not total cost.
- The reader should feel tension between:
    - simplicity
    - efficiency
    - correctness

---

## 4. What Must NOT Be Introduced Yet

- No ranking algorithms.
- No machine learning.
- No personalization heuristics.
- No content moderation systems.
- No recommendation intelligence.

If the design optimizes relevance instead of delivery mechanics, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Build the feed when requested.”

To:
- “Decide when and where work should happen.”

The reader must internalize:
- timing of computation defines scalability
- tradeoffs are unavoidable
- architecture reflects dominant access patterns

---

## 6. Guardrails for Implementation Exercises

- Separate write path from read path explicitly.
- Identify where computation occurs in time.
- Accept eventual consistency deliberately.
- Measure success by predictable and explainable behavior.
- Stop once tradeoffs are visible and reasoned.

If the exercise attempts to optimize content quality, it exceeds this chapter’s intent.
