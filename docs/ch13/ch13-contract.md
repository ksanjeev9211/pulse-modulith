# ch13-contract.md

## 1. Chapter Goal

- Introduce predictive systems that respond before the user finishes acting.
- Teach how partial input reshapes system design assumptions.
- Establish anticipation as a distinct architectural responsibility.

---

## 2. Primary Pressure Introduced

- Incremental input pressure:
    - incomplete user intent
    - rapidly changing queries
    - extremely tight latency budgets
- The system must act without certainty.

---

## 3. What the Reader Must Observe or Feel

- Most work happens before intent is finalized.
- Latency tolerance approaches zero.
- Incorrect results are acceptable; slow results are not.
- Data structures must favor read speed over completeness.
- The reader should feel tension between:
    - accuracy
    - responsiveness
    - resource cost

---

## 4. What Must NOT Be Introduced Yet

- No personalization models.
- No machine learning pipelines.
- No semantic understanding of language.
- No global ranking strategies.
- No offline analytics coupling.

If the design prioritizes correctness over speed, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Respond to a request.”

To:
- “Continuously assist an evolving action.”

The reader must internalize:
- partial information is sufficient
- speed defines usability
- systems may act on probabilistic intent

---

## 6. Guardrails for Implementation Exercises

- Optimize for sub-interaction latency over result quality.
- Treat every keystroke as a new independent request.
- Allow approximate results.
- Precompute aggressively.
- Stop once responsiveness constraints dominate design.

If the exercise focuses on perfect matching or semantics, it exceeds this chapter’s intent.
