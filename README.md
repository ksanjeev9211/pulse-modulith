# pulse-modulith (ch00-baseline)

Pulse is a chapter-by-chapter system design learning project built with **Spring Boot 4 + Java 25** using **feature-based packaging** (modulith-ready).

This branch (`ch00-baseline`) provides a minimal, working backend with:
- User creation + lookup
- Post creation
- List posts for a user
- DTO-based controllers
- JPA + H2 in-memory DB
- Actuator endpoints
- A Spring Modulith architecture verification test

---

## Tech stack
- Java 25
- Spring Boot 4.x
- Spring Web (REST)
- Spring Data JPA
- Validation (Bean Validation)
- Actuator
- Spring Modulith (verification test)
- H2 (in-memory DB)

---

## Package structure (feature-first)

```mermaid
flowchart LR
  subgraph USER["user"]
    USER_WEB["user.web"]
    USER_SVC["user.service"]
  end
  subgraph POST["post"]
    POST_WEB["post.web"]
    POST_SVC["post.service"]
  end
  POST_SVC --> USER_SVC
