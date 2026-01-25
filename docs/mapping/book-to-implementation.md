
# Book ↔ Implementation Mapping

This file keeps the project honest:

- **Book chapters** (TOC) remain the source of truth for concepts and terminology.
- **Implementation milestones** live under `docs/tracks/*` and may span multiple book chapters.
- This mapping links *what we built* to *what the book teaches* without forcing chapter-number alignment.

---

## Track: Feed (Pulse Modulith)

### Milestone 00 — Baseline (single JVM)
- **Docs:** `docs/tracks/feed/00-baseline.md`
- **Branch:** `feed-00-baseline` (or `main` if baseline lives on main)
- **What we built:**
  - User creation + lookup
  - Post creation
  - DTO-based controllers
  - Spring Modulith verification
- **Book concepts (high-level):**
  - Start simple, evolve under pressure
  - Establish clear APIs and boundaries

---

### Milestone 01 — Cursor Pagination (read scalability)
- **Docs:** `docs/tracks/feed/01-cursor-pagination.md`
- **Branch (existing):** `ch01-scale-basics`
- **Suggested branch name:** `feed-01-cursor-pagination`
- **What we built:**
  - Cursor pagination (`<epochMillis>_<postId>`)
  - Deterministic ordering (`createdAt DESC, id DESC`)
  - Composite index and page size limits
- **Book concepts:**
  - Read scalability pitfalls (offset pagination issues)
  - Deterministic reads under concurrent writes

---

### Milestone 02 — Domain Events (in-process decoupling)
- **Docs:** `docs/tracks/feed/02-domain-events.md`
- **Branch (existing):** `ch02-domain-events`
- **Suggested branch name:** `feed-02-domain-events`
- **What we built:**
  - `post.api.PostCreatedEvent` under `@NamedInterface("api")`
  - In-process event publication from `PostService`
  - `@TransactionalEventListener(AFTER_COMMIT)` consumers
- **Book concepts:**
  - Decoupling write-side side effects
  - Event-driven thinking before distributed messaging

---

### Milestone 03 — Feed Hydration (read composition + amplification)
- **Docs:** `docs/tracks/feed/03-feed-hydration.md`
- **Branch (existing):** `ch04-feed-hydration`
- **Suggested branch name:** `feed-03-feed-hydration`
- **What we built:**
  - Feed as a projection (materialized list of post references)
  - Hydration by joining post/user details at read time
  - Cursor pagination on feed reads
- **Book concepts:**
  - Read amplification
  - Composition tradeoffs (precompute vs compute-on-read)

---

### Milestone 04 — Follow Graph (directed social graph)
- **Docs:** `docs/tracks/feed/04-follow-graph.md`
- **Branch (existing):** `ch05-follow-graph`
- **Suggested branch name:** `feed-04-follow-graph`
- **What we built:**
  - `follow` module with directed edges (`follower → followee`)
  - Minimal follow/unfollow operations
  - `FollowGraphIT`
- **Book concepts:**
  - Social graph modeling for feeds
  - Fanout prerequisites

---

### Milestone 05 — Fanout Modeling (push / fanout-on-write)
- **Docs:** `docs/tracks/feed/05-fanout-modeling-push.md`
- **Branch (existing):** `ch05-fanout-modeling`
- **Suggested branch name:** `feed-05-fanout-modeling-push`
- **What we built / in progress:**
  - Feed entries owned by viewer (`userId`)
  - Composite key `(userId, postId)` via `@EmbeddedId`
  - Fanout writes in feed listener after commit
  - Debugging IT scenario: A posts, B/C follow A ⇒ B/C should see post
- **Book concepts:**
  - Fanout on write vs fanout on read
  - Write amplification and celebrity problem (pressure creation)

---

### Milestone 06 — Fanout Pressure (make the pain visible)
- **Docs:** `docs/tracks/feed/06-fanout-pressure.md`
- **Branch:** `main`
- **What we built:**
  - Stabilized fanout integration assertions (avoid global DB counts)
  - Disabled pressure harness test that simulates 1k followers and times `POST /v1/posts`
- **Book concepts:**
  - Throughput coupling to graph shape
  - Celebrity problem as a forced design pressure
  - "Why now?" before introducing async/batching/hybrids

---

## Track: Infrastructure Concepts (book TOC-driven)

These are **book chapter topics** that will be introduced only when the implementation hits real pressure.

### Consistent Hashing
- **Docs:** `docs/tracks/infra/consistent-hashing.md`
- **Book chapter:** (TOC) “Consistent Hashing”
- **When it becomes relevant:**
  - Sharding feed storage
  - Distributing caches / partitions
  - Routing requests to nodes

### Caching
- **Docs:** `docs/tracks/infra/caching.md`
- **Book chapter:** (TOC) “Caching”
- **When it becomes relevant:**
  - Demonstrated read pressure / hotspots
  - Measured latency issues
  - Expensive hydration costs

---

## Notes
- If the book’s TOC chapter numbers differ from our implementation sequence, **we do not renumber milestones**.
- We map concepts explicitly here instead.
