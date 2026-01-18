# PROJECT_CONTEXT.md

## Project Overview

**Pulse Modulith** is a chapter-by-chapter system design learning project inspired by  
*System Design Interview – An Insider’s Guide*.

The objective is to **learn system design by building**, allowing architecture to evolve
only when real design pain appears — not through premature optimization.

This project intentionally mirrors how real systems grow.

---

## Core Goal

- Build a realistic backend system
- Introduce architectural concepts incrementally
- Preserve reasoning behind every decision
- Maintain strong modular boundaries
- Prepare the system for future evolution into distributed systems

---

## Technology Stack

- Java 25
- Spring Boot 4.x
- Maven
- Spring Web (REST)
- Spring Data JPA
- Spring Modulith
- H2 (current database)

Planned later:
- Testcontainers
- LocalStack (AWS simulation)
- Redis
- Messaging systems

---

## Architectural Principles

- Feature-based packaging
- Modular monolith (Spring Modulith)
- Single JVM execution
- No microservices initially
- No asynchronous processing initially
- Architecture evolves only when justified

---

## Package Structure

```
com.sanjeev.pulse
├── user
├── post
├── platform
```

Each package represents a bounded context / feature module.

---

## Modulith Rules

Spring Modulith is used to enforce architectural boundaries.

### Declared dependencies

- post → allowed to depend on user
- user → depends on no other modules

Dependencies are declared using `package-info.java`.

Module verification is enforced via:

```
ApplicationModules.of("com.sanjeev.pulse").verify();
```

Important:
- Modulith validates actual bytecode dependencies
- Merely adding imports does not create dependencies
- Only real usage (fields, params, method calls) is checked

---

## Chapter 00 — Baseline (Completed)

### Objective
Establish a working baseline system with clean boundaries.

### Implemented

- User creation and lookup
- Post creation
- DTO-based controllers
- Synchronous request/response model
- Feature-based package structure
- Modulith verification test

### Characteristics

- Everything runs synchronously
- No async processing
- No caching
- No events
- Single database
- Single JVM

This is intentional.

---

## Chapter 01 — Scale Basics (Completed)

### Objective
Understand how read paths break at scale and fix them correctly.

### Focus Area
Read scalability — not infrastructure.

### Implemented

- Cursor-based pagination
- Deterministic ordering:
  ORDER BY createdAt DESC, id DESC
- Composite index:
  (authorId, createdAt, id)
- Stable cursor model:
  <epochMillis>_<postId>
- Enforced page size limits
- Clean API contract

### API

GET /v1/users/{userId}/posts

Parameters:
- limit (default 20, max 100)
- cursor (opaque)

Response:
```
{
  "items": [...],
  "nextCursor": "epochMillis_postId"
}
```

### Key Learning

Offset-based pagination breaks due to:
- inconsistent results
- poor performance
- unstable ordering

Cursor pagination solves these issues.

Documentation:
docs/ch01/README.md

---

## Intentional Omissions

- Redis caching
- Load balancers
- CDN
- Message brokers
- Async processing
- Microservices

These will be introduced only when the system forces them.

---

## Current System State

- Stateless REST APIs
- Deterministic reads
- Synchronous writes
- Modular boundaries enforced
- Single database
- Single JVM

---

## Why Chapter 02 Is Needed

When a post is created, multiple independent concerns must react:

- feed updates
- notifications
- counters
- search indexing

Handling this synchronously increases latency and coupling.

This problem cannot be solved by caching or load balancers.

It requires domain events.

---

## Chapter 02 — Planned

### Objective
Introduce domain events for decoupling write-side side effects.

### Constraints

- Single JVM
- In-memory events only
- No Kafka, SQS, SNS yet
- No distributed guarantees

### Planned Steps

1. Create PostCreatedEvent
2. Publish event from PostService
3. Introduce feed module
4. Feed listens to post events
5. Post remains unaware of feed existence

---

## Learning Philosophy

Architecture must emerge from pressure, not preference.

No abstraction is added unless a real problem demands it.

---

## Branch Strategy

- main → stable reference
- ch01-scale-basics → merged
- ch02-domain-events → next

---

## Continuation Prompt

To continue work in a new session:

"""
Continue Pulse Modulith from Chapter 02.
Chapter 01 is complete with cursor pagination and modulith boundaries.
Start with introducing domain events in-process.
"""

---

End of context.
