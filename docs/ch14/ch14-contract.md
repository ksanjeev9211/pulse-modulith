# ch14-contract.md

## 1. Chapter Goal

- Introduce large-scale media delivery as a bandwidth-dominated system.
- Teach how storage, compute, and network constraints diverge at scale.
- Establish content distribution as the primary architectural concern at scale.

---

## 2. Primary Pressure Introduced

- Bandwidth and scale pressure:
    - massive content size
    - global consumption
    - highly skewed access patterns
- Network cost and delivery speed dominate all other constraints.

---

## 3. What the Reader Must Observe or Feel

- Media systems are constrained more by movement than computation.
- Most cost occurs after data is created.
- Popularity is uneven and unpredictable.
- Global users experience the system differently.
- The reader should feel tension between:
    - quality
    - cost
    - reach

---

## 4. What Must NOT Be Introduced Yet

- No recommendation engines.
- No monetization logic.
- No content moderation workflows.
- No creator analytics systems.
- No personalization pipelines.

If the design focuses on user intelligence instead of delivery mechanics, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Store and serve content.”

To:
- “Move large content efficiently at global scale.”

The reader must internalize:
- distribution dominates design
- locality matters
- infrastructure cost shapes architecture

---

## 6. Guardrails for Implementation Exercises

- Prioritize clarity of the delivery path.
- Treat network as the primary bottleneck.
- Design for uneven access patterns.
- Separate upload concerns from playback concerns.
- Stop once global delivery behavior is understood.

If the exercise optimizes engagement instead of distribution, it exceeds this chapter’s intent.
