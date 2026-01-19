
# Chapter 04 — Feed Hydration

## Context

In the previous chapters:

- **Chapter 02** introduced feed publishing using in-process domain events.
- **Chapter 03** introduced feed retrieval using a dedicated feed read model.

At this point, the system can return feed items, but those items only contain identifiers and metadata.

However, a real news feed must display **fully rendered content**, not just IDs.

This chapter introduces **feed hydration**, following the same flow described in  
*System Design Interview – An Insider’s Guide* (News Feed system design).

---

## Problem Statement

The feed store contains entries such as:

- postId  
- authorId  
- authorName  
- createdAt  

But the client requires:

- post text
- full post content
- UI-ready feed items

The feed service must therefore **hydrate feed entries** by fetching post data.

---

## Why This Is Non-Trivial

A naïve solution would be to perform a database join:

```sql
SELECT *
FROM feed_item f
JOIN post p ON f.post_id = p.id;
```

This approach is intentionally avoided because:

- it tightly couples feed storage to post storage
- it prevents independent scaling of read and write models
- it makes caching and fanout strategies difficult later
- it violates modular boundaries

The book explicitly avoids joins for this reason.

---

## Architectural Goal

Implement feed hydration while preserving:

- strict module boundaries
- independent read and write models
- no entity sharing across modules
- no database joins
- no caching or async infrastructure yet

This chapter focuses purely on **read composition**.

---

## Design Approach

The hydration flow mirrors the book’s conceptual design:

```
Client
  ↓
Feed Service
  ↓
Feed Store (post IDs)
  ↓
Post Service (read API)
  ↓
Compose response
```

Key principle:

> The feed module never accesses post entities or repositories directly.

---

## Public Read Contract

To enable hydration without violating boundaries, a read-only API is introduced.

### post.api.PostQueryService

This interface exposes a **read-safe contract** from the post module:

```java
List<PostView> findPostsByIds(Collection<Long> postIds);
```

Characteristics:

- returns DTOs only
- never exposes entities
- owned entirely by the post module
- accessible to feed via `post :: api`

This mirrors the “post service lookup” described in the book.

---

## Feed Hydration Flow

1. Feed module retrieves feed items using cursor pagination.
2. Extract post IDs from feed entries.
3. Invoke PostQueryService to fetch post data.
4. Build an in-memory lookup map.
5. Compose hydrated feed responses in application logic.
6. Return UI-ready feed items.

No joins.  
No entity sharing.  
No cross-module persistence access.

---

## Resulting API Response

```json
{
  "items": [
    {
      "postId": 5,
      "text": "Event Test E",
      "authorId": 1,
      "authorName": "sanjeev",
      "createdAt": "2026-01-19T01:50:58.325113Z"
    }
  ],
  "nextCursor": "1768787439921_1"
}
```

The response is now fully renderable by the client.

---

## Key Learnings

### 1. Feed stores IDs intentionally

Feed tables are not incomplete by accident — they are incomplete by design.

Storing full post content would significantly increase write amplification.

---

### 2. Hydration is unavoidable

Once a feed must display content, the system must compose data from multiple sources.

This complexity is fundamental, not optional.

---

### 3. Database joins are the wrong abstraction

Joins tightly couple read models and prevent independent evolution.

Hydration occurs in the application layer instead.

---

### 4. Boundaries force better design

Because the feed module cannot access post internals, a clean read contract emerges naturally.

This is exactly how real systems evolve.

---

### 5. CQRS emerges organically

Without explicitly introducing the pattern:

- write path → domain events
- read model → feed store
- read composition → hydration

The system now behaves as CQRS-lite.

---

## Constraints Preserved

This chapter intentionally keeps the system simple:

- single JVM
- synchronous execution
- in-process calls only
- no Redis
- no message queues
- no async workers
- H2 database retained

These constraints ensure focus remains on architecture, not infrastructure.

---

## Why This Chapter Matters

Feed hydration is the pivot point in the book.

After this step, the following pressures become unavoidable:

- repeated database reads
- high read amplification
- lack of caching
- hot users and celebrity problems
- fanout scalability issues

These pressures justify the next architectural evolution.

---

## What Comes Next

The next chapter explores:

- fanout on write vs fanout on read
- scaling tradeoffs
- why caching becomes necessary
- how large systems handle high-fanout users

This is where infrastructure decisions finally become justified.

---

**Chapter 04 establishes the last fully synchronous, single-node version of the news feed system before scale forces architectural change.**
