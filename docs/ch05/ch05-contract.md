# ch05-contract.md

## 1. Chapter Goal

- Introduce data distribution as an architectural inevitability.
- Teach that partitioning is not about performance first, but about ownership and placement.
- Establish stable data distribution as a prerequisite for scalable systems.

---

## 2. Primary Pressure Introduced

- Growth and movement pressure:
    - increasing nodes
    - changing capacity
    - uneven load
- Naive partitioning causes excessive disruption during change.
- System evolution becomes expensive without controlled redistribution of data.

---

## 3. What the Reader Must Observe or Feel

- Data placement choices have long-term consequences.
- Change, not steady state, is the dominant cost.
- Rebalancing can be more dangerous than serving traffic.
- Stability during growth matters more than perfect distribution.
- The reader should feel discomfort around system churn.

---

## 4. What Must NOT Be Introduced Yet

- No business-specific data modeling.
- No query optimization discussions.
- No replication strategies.
- No caching layers.
- No multi-region concerns.

If the design focuses on serving reads faster instead of surviving change, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Where should data go?”

To:
- “How does data move when the system changes?”

The reader must internalize:
- distribution is dynamic
- stability is a design goal
- minimizing movement is as important as balancing load

---

## 6. Guardrails for Implementation Exercises

- Treat node addition and removal as routine events.
- Measure success by minimal redistribution.
- Do not optimize for perfect balance.
- Avoid tying data placement to infrastructure identity.
- Stop once redistribution behavior is predictable.

If the exercise prioritizes throughput or latency, it exceeds this chapter’s intent.
