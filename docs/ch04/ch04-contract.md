# ch04-contract.md

## 1. Chapter Goal

- Introduce control as a first-class system responsibility.
- Teach that systems must actively protect themselves from misuse and overload.
- Establish defensive design as an architectural concern, not an afterthought.

---

## 2. Primary Pressure Introduced

- Abuse and overload pressure:
    - unbounded client behavior
    - uneven request patterns
    - external traffic beyond system intent
- Failure is caused not by incorrect behavior, but by lack of enforcement.

---

## 3. What the Reader Must Observe or Feel

- A correct system can still collapse under excess demand.
- Fairness and protection require explicit mechanisms.
- System stability depends on limits, not trust.
- Control must exist even when functionality works perfectly.
- The reader should feel the tension between:
    - openness
    - safety

---

## 4. What Must NOT Be Introduced Yet

- No business-specific optimizations.
- No performance tuning beyond basic protection.
- No distributed coordination strategies.
- No reliability engineering beyond basic containment.
- No assumption that clients behave correctly.

If the design assumes well-behaved consumers, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “If it works, it’s fine.”

To:
- “If it’s unbounded, it’s unsafe.”

The reader must internalize:
- limits define system shape
- protection precedes optimization
- stability is an explicit design responsibility

---

## 6. Guardrails for Implementation Exercises

- Define what must be limited before defining how.
- Enforce boundaries before scaling throughput.
- Treat overload as a normal operating condition.
- Prefer rejection over degradation.
- Stop once control boundaries are clear.

If the exercise optimizes throughput instead of enforcing limits, it exceeds this chapter’s scope.
