# ch07-contract.md

## 1. Chapter Goal

- Establish identity generation as a distributed systems problem.
- Separate identifier creation from business meaning.
- Treat IDs as infrastructure-level primitives.

---

## 2. Primary Pressure Introduced

- Coordination pressure:
    - multiple writers
    - parallel system components
    - absence of a single authority
- Centralized generation limits scalability and availability.

---

## 3. What the Reader Must Observe or Feel

- Identity must exist before persistence.
- Uniqueness is harder than it appears.
- Ordering, scale, and availability compete with one another.
- ID choices shape downstream system behavior.
- The reader should feel tension between:
    - simplicity
    - global coordination
    - decentralization

---

## 4. What Must NOT Be Introduced Yet

- No business semantics embedded in identifiers.
- No cross-entity relationships.
- No workflow orchestration.
- No consistency mechanisms beyond identity creation.
- No assumption of global clocks.

If identifiers encode meaning or behavior, the design violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “An ID is just a number.”

To:
- “An ID is a distributed contract.”

The reader must internalize:
- ID strategy constrains architecture
- uniqueness requires tradeoffs
- ID generation must survive partial failure

---

## 6. Guardrails for Implementation Exercises

- Treat ID generation as a standalone concern.
- Define required properties explicitly.
- Avoid implicit coordination.
- Prefer availability over perfect ordering.
- Stop once identity guarantees are clear.

If the exercise evolves into workflow or data modeling, it exceeds this chapter’s intent.
