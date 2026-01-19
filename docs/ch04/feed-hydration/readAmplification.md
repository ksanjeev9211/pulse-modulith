
# Chapter 04 — Read Amplification & Fanout Tradeoffs

## Context

In the previous section of Chapter 04, we implemented **feed hydration**.

The system now performs the following steps for every feed request:

1. Retrieve feed items (post IDs) from the feed store
2. Fetch post objects using the post read API
3. Compose a UI-ready feed response

Functionally, the system works correctly.

However, this design introduces a new and unavoidable pressure: **read amplification**.

This section explains why hydration alone does not scale and prepares the ground for the next architectural evolution described in *System Design Interview – An Insider’s Guide*.

---

## What Is Read Amplification

Read amplification occurs when a single client request results in **multiple backend reads**.

In the current design:

- One request to `/feed`
- Triggers a feed store query
- Triggers one or more post lookups
- May later require user, media, or count lookups

As traffic grows, backend read operations grow much faster than user traffic.

---

## Example

Assume:

- Feed page size = 20
- Each feed item requires hydration
- Each request performs:
  - 1 feed query
  - 1 post query (with multiple IDs)

Now multiply this by:

- millions of active users
- frequent feed refreshes
- infinite scrolling behavior

The system becomes read-heavy very quickly.

This is known as **read amplification**.

---

## Why This Happens

Hydration shifts work from write time to read time.

Earlier chapters optimized writes:

- posting was cheap
- feed publishing was decoupled
- minimal data was stored

But hydration increases read cost:

- multiple lookups
- in-memory composition
- repeated data access

This tradeoff is fundamental and unavoidable.

---

## The Core Insight

There is no free solution.

System design is about deciding **where to pay the cost**.

You can move work between:

- write time
- read time
- storage time

But you cannot eliminate it.

---

## Three Ways to Reduce Read Amplification

At a high level, systems can reduce read amplification in only three ways.

### 1. Cache hydrated results

- Serve feed responses from cache
- Reduce repeated reads

Tradeoff:
- cache invalidation complexity
- consistency challenges

---

### 2. Denormalize at write time

- Store post content directly in feed items
- Reads become simple

Tradeoff:
- increased write amplification
- large fanout costs
- celebrity problem

---

### 3. Push computation earlier (fanout)

- Precompute feeds when posts are created
- Optimize read latency

Tradeoff:
- heavy write load
- uneven fanout distribution

---

## Fanout Models

The book introduces two primary feed strategies.

### Fanout on Write (Push Model)

When a user creates a post:

- the system pushes that post to followers’ feeds

**Pros**
- fast feed reads
- simple retrieval

**Cons**
- expensive writes
- problematic for users with millions of followers

This is known as the **celebrity problem**.

---

### Fanout on Read (Pull Model)

When a user loads the feed:

- the system pulls recent posts from followed users

**Pros**
- cheap writes
- no fanout cost

**Cons**
- slow reads
- expensive queries
- complex sorting and merging

---

### Hybrid Model

Most real systems use a hybrid approach:

- push for normal users
- pull for high-follower (celebrity) users

This balances write and read costs.

---

## Why We Do Not Implement This Yet

At this stage:

- there is no follower graph
- there are no traffic metrics
- there are no hot users
- there is no caching layer

Introducing infrastructure now would be premature.

The book emphasizes understanding the problem before choosing the solution.

---

## Why This Section Matters

This section connects previous chapters:

- domain events enabled decoupling
- feed retrieval enabled read paths
- hydration exposed read amplification

Now the motivation for caching, fanout strategies, and asynchronous processing becomes clear.

Infrastructure is no longer theoretical — it is justified by pressure.

---

## What Comes Next

The next chapter introduces:

- follower relationships
- fanout modeling
- push vs pull decisions in practice
- when and why asynchronous systems become necessary

This marks the transition from purely synchronous designs to systems built for scale.

---

**This concludes Chapter 04 and prepares the system for scale-driven architectural change.**
