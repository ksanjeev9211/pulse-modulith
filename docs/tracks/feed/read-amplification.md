# Read Amplification

## What Is Read Amplification?

Read amplification occurs when a single user request triggers **multiple backend reads**
to assemble the response.

In feed systems, this typically means:
- reading feed entries
- then reading post data
- then reading author data

One logical read becomes *many physical reads*.

---

## Where It Appears in This Project

Read amplification was introduced intentionally during:

**Feed Track — 03: Feed Hydration**

At this stage:
- Feed entries store lightweight references (postId, authorId)
- Full post and user details are fetched at read time

This keeps writes cheap but shifts cost to reads.

---

## Why We Allow It (For Now)

Read amplification is acceptable when:
- read volume is moderate
- latency requirements are forgiving
- simplicity is preferred

At this stage of evolution:
- correctness > optimization
- clarity > performance
- visibility > efficiency

Avoiding read amplification *now* would hide important tradeoffs.

---

## What Pain It Introduces

As traffic grows, read amplification causes:
- increased latency
- higher database load
- poor cache locality
- uneven performance under bursty traffic

These symptoms **force** architectural change later.

---

## What We Explicitly Did NOT Add

To address read amplification, one might consider:
- caching
- denormalization
- pre-hydrated feeds
- read replicas

None of these were introduced yet because:
- no measured read pressure exists
- complexity would be premature
- tradeoffs would be theoretical

---

## Relationship to Fanout

Read amplification and fanout are duals:

| Approach | Cost Location |
|--------|---------------|
| Fanout-on-read | Read amplification |
| Fanout-on-write | Write amplification |

This project intentionally experiences **both** before choosing a hybrid.

---

## Why This File Exists

This document exists to:
- name the pain clearly
- separate *concept* from *implementation*
- preserve reasoning for future chapters

Optimization should be a response to pain,
not a reflex.

---

**If read amplification does not hurt yet, do not fix it.**