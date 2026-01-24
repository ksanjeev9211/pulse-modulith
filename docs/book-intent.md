# book-intent.md

## 1. Core Goals of the Book

- Teach system design thinking, not system memorization.
- Train reasoning under ambiguity.
- Emphasize tradeoff-driven design over perfect architectures.
- Build intuition for scaling constraints before introducing solutions.
- Ensure design decisions can be clearly explained and defended.

---

## 2. How the Author Expects the Reader to Think

- Think in constraints first, components second.
- Always ask “why now?” before “how?”.
- Treat system design as an iterative conversation, not a final answer.
- Prefer simple, explainable designs over impressive ones.
- Continuously evaluate:
    - bottlenecks
    - failure modes
    - cost vs benefit
- Optimize for clarity of reasoning, not cleverness.

---

## 3. Intended Learning Progression

- Begin with single-machine mental models.
- Introduce scale only when bottlenecks appear.
- Progress intentionally through:
    1. Core functionality
    2. Traffic growth
    3. Availability risks
    4. Performance bottlenecks
    5. Data growth
    6. Decoupling and distribution
- Each new concept must be forced by observable pain.
- Scaling is iterative, never upfront.

---

## 4. Rules About When NOT to Introduce Complexity

- Do NOT introduce:
    - caching without read pressure
    - sharding without data growth
    - async systems without throughput or latency pain
    - multi-region systems without availability requirements
- Never design for massive scale unless explicitly required.
- Avoid premature optimization.
- Complexity must be justified by a concrete failure or limit.

---

## 5. Common Reader Mistakes / Anti-Patterns

- Jumping directly to distributed systems.
- Designing real-world hyperscale systems prematurely.
- Over-focusing on algorithms instead of architecture.
- Failing to state assumptions.
- Treating design as static rather than evolving.
- Optimizing for elegance instead of business or system needs.
- Presenting one solution without discussing alternatives.

---

## 6. Reusable Design Contract

This contract governs all designs derived from this book.

### Design Entry Rules
- Clarify scope and requirements first.
- Explicitly state assumptions.
- Define scale before architecture.

### Design Evolution Rules
- Start simple.
- Add components only when existing ones break.
- Let bottlenecks dictate architecture changes.

### Complexity Rules
- No component exists without a failure scenario it solves.
- Every added layer must justify:
    - latency impact
    - operational cost
    - cognitive load

### Communication Rules
- Explain tradeoffs explicitly.
- Compare alternatives.
- Acknowledge limitations openly.

### Success Criteria
- Design is understandable.
- Design is defensible.
- Design can evolve.

Violation of these rules indicates design immaturity,
regardless of technical correctness.
