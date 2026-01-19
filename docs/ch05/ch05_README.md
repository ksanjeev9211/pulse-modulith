
# Chapter 05 — Follow Graph & Fanout Modeling

## Purpose

Chapter 05 introduces the **social graph** into the system.

Until this point, the feed system operated in isolation, implicitly assuming that users only consume their own content. This chapter adds the minimal structure required to reason about **who should see whose posts**, which is a foundational requirement for any social feed system.

The primary goal of this chapter is **modeling**, not optimization.

---

## What Was Added

This chapter introduces a **directed follow graph**:

```
Follower ──▶ Followee
```

### Key additions

- A new `follow` module
- A minimal follow relationship model (`followerId → followeeId`)
- Follow and unfollow operations
- Repository queries to retrieve:
  - followers of a user
  - followees of a user

The graph is intentionally minimal and does not include:
- privacy rules
- blocking or muting
- groups or lists
- ranking or weights

---

## Why This Matters

The follow graph is the structural prerequisite for **feed fanout**.

Once follower relationships exist, every post implicitly creates fanout pressure. The number of followers directly determines the cost of propagating a post through the system.

This chapter enables reasoning about:

- write amplification (fanout on write)
- read amplification (fanout on read)
- the celebrity problem
- push vs pull feed strategies

At this stage, these are **design questions**, not implementation decisions.

---

## What Was Intentionally Not Done

To remain aligned with the book and avoid premature optimization, this chapter does **not** introduce:

- feed fanout logic
- asynchronous processing
- background workers
- caching
- Redis or message brokers
- infrastructure changes

All execution remains:
- synchronous
- single-JVM
- backed by the existing database

---

## Tests

An integration test (`FollowGraphIT`) validates:

- creation of follow edges via HTTP
- persistence of the follow graph
- correct retrieval of followers and followees
- removal of follow edges

These tests ensure the graph exists as a concrete, working model rather than a purely conceptual construct.

---

## How This Chapter Connects Forward

With the follow graph in place, the system now has enough information to model **fanout strategies**.

The next step is to analyze how different fanout approaches behave under scale and to understand where the system breaks before introducing infrastructure to address those limits.

This prepares the groundwork for subsequent chapters focused on scalability tradeoffs and system evolution.

---

**This chapter establishes the social graph required to reason about feed scalability.**
