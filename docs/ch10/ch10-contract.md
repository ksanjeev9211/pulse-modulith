# ch10-contract.md

## 1. Chapter Goal

- Establish event propagation as a system-wide concern.
- Teach how one action fans out into many independent effects.
- Frame delivery as a coordination problem, not a messaging problem.

---

## 2. Primary Pressure Introduced

- Fanout pressure:
    - one producer
    - many consumers
    - heterogeneous delivery paths
- A single event must reach multiple targets without tight coupling.

---

## 3. What the Reader Must Observe or Feel

- Direct communication does not scale with the number of recipients.
- Reliability becomes harder as audience size grows.
- Delivery guarantees vary by channel and consumer.
- Timing is inconsistent by nature.
- The reader should feel tension between:
    - immediacy
    - reliability
    - isolation

---

## 4. What Must NOT Be Introduced Yet

- No personalization logic.
- No ranking or prioritization.
- No real-time ordering guarantees.
- No synchronous dependencies.
- No assumption of uniform delivery success.

If the design requires all consumers to succeed together, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Send a message.”

To:
- “Coordinate many independent deliveries.”

The reader must internalize:
- events may outlive their producers
- delivery is best-effort unless explicitly strengthened
- decoupling defines resilience

---

## 6. Guardrails for Implementation Exercises

- Model producers and consumers as isolated.
- Assume partial delivery is normal.
- Avoid shared failure domains.
- Favor independence over immediacy.
- Stop once fanout paths are clearly separated.

If the exercise enforces strict synchronization, it exceeds this chapter’s intent.
