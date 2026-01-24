# ch12-contract.md

## 1. Chapter Goal

- Introduce real-time interaction as a distributed coordination problem.
- Teach how user presence and immediacy reshape system assumptions.
- Establish communication systems as inherently latency-sensitive.

---

## 2. Primary Pressure Introduced

- Temporal pressure:
    - near-instant delivery expectations
    - bidirectional communication
    - rapidly changing client state
- Delays are user-visible and system-defining.

---

## 3. What the Reader Must Observe or Feel

- Real-time systems magnify small delays.
- Connection management becomes a core responsibility.
- State changes are continuous, not transactional.
- Availability conflicts directly with immediacy.
- The reader should feel tension between:
    - speed
    - reliability
    - consistency

---

## 4. What Must NOT Be Introduced Yet

- No advanced presence intelligence.
- No content analysis or moderation logic.
- No cross-channel orchestration.
- No delivery ranking or prioritization.
- No assumptions of permanent connectivity.

If the design assumes stable connections, it violates this chapter.

---

## 5. Expected Mental Shift at Chapter End

From:
- “Send messages.”

To:
- “Maintain live system state under constant change.”

The reader must internalize:
- connections are resources
- time is a constraint
- failure is continuous, not exceptional in real-time systems

---

## 6. Guardrails for Implementation Exercises

- Treat disconnects as normal behavior.
- Separate message transport from message meaning.
- Accept temporary inconsistency.
- Favor responsiveness over completeness.
- Stop once live-state boundaries are clear.

If the exercise attempts to perfect delivery guarantees, it exceeds this chapter’s intent.
