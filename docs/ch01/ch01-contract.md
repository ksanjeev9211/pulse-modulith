# ch01-contract.md

## 1. Chapter Goal

- Establish foundational intuition for how systems break as usage grows.
- Train recognition of scaling as a sequence of forced decisions, not architectural preference.
- Anchor system design thinking in progressive evolution, not upfront architecture.

---

## 2. Primary Pressure Introduced

- Growth pressure:
    - increasing users
    - increasing traffic
    - increasing data volume
- Pressure is external and unavoidable, not caused by poor design.
- Systems fail because they outgrow their original assumptions.

---

## 3. What the Reader Must Observe or Feel

- A single-machine system is:
    - correct
    - understandable
    - sufficient — until it is not
- Each scaling step reveals:
    - a new bottleneck
    - a new failure mode
- Scaling feels reactive, not visionary.
- Discomfort should arise from:
    - overload
    - single points of failure
    - slow responses
- Complexity must feel forced, not chosen.

---

## 4. What Must NOT Be Introduced Yet

- No microservices.
- No domain decomposition.
- No event-driven architecture.
- No premature abstractions.
- No optimization without visible pain.
- No future-proofing arguments.

Any component added without a demonstrated bottleneck violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Design the right architecture.”

To:
- “Let the system tell me what it needs next.”

The reader must internalize:
- scaling is incremental
- architecture is evolutionary
- most systems should start simple

---

## 6. Guardrails for Implementation Exercises

- Begin with the simplest possible system.
- Introduce change only when:
    - a failure occurs
    - a limit is reached
    - availability is lost
- Each modification must answer exactly one question:

  **What broke?**

- Do not refactor for cleanliness.
- Do not optimize for elegance.
- Do not simulate scale artificially.

If an implementation feels impressive, it is likely incorrect for this chapter.
