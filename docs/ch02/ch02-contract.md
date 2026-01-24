# ch02-contract.md

## 1. Chapter Goal

- Train the reader to quantify scale before designing systems.
- Replace intuition-based thinking with order-of-magnitude reasoning.
- Establish estimation as a design prerequisite, not a validation step.

---

## 2. Primary Pressure Introduced

- Uncertainty pressure:
    - unknown traffic
    - unknown data volume
    - unknown growth rate
- Design decisions become risky when numbers are missing.
- Architecture without scale awareness is speculative.

---

## 3. What the Reader Must Observe or Feel

- Numbers expose hidden constraints.
- Rough estimates are sufficient to:
    - eliminate impossible designs
    - narrow the solution space
- Precision is unnecessary; directional correctness is mandatory.
- Scale thinking must feel:
    - approximate
    - fast
    - imperfect — yet useful
- Discomfort should arise from realizing:
    - “I was designing blindly before.”

---

## 4. What Must NOT Be Introduced Yet

- No detailed architecture diagrams.
- No database selection debates.
- No technology benchmarking.
- No performance tuning.
- No optimization strategies.

If a solution is proposed before scale is estimated, the exercise is invalid.

---

## 5. Expected Mental Shift at Chapter End

From:
- “What architecture should we use?”

To:
- “What scale are we designing for?”

The reader must internalize:
- estimation precedes design
- orders of magnitude matter more than accuracy
- numbers define architectural boundaries

---

## 6. Guardrails for Implementation Exercises

- Always write assumptions explicitly.
- Use round numbers only.
- Favor mental math over calculators.
- Focus on:
    - QPS
    - storage growth
    - peak vs average load
- Stop once constraints become clear.

The exercise is complete when:
- infeasible designs are ruled out
- feasible directions emerge

Any further refinement violates this chapter’s intent.
