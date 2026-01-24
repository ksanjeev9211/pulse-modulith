# AGENTS.md

This repository is a Spring Boot modulith-style backend (single Maven module) written in Java 25.
It uses feature-first packaging and Spring Modulith to enforce module boundaries.

Cursor rules: none found in `.cursor/rules/` or `.cursorrules`.
Copilot rules: none found in `.github/copilot-instructions.md`.

## Quick Orientation
- Build tool: Maven (via wrapper)
- Runtime: Spring Boot 4.x
- DB: H2 in-memory (`jdbc:h2:mem:pulse`) in PostgreSQL compatibility mode
- Modules (top-level packages under `com.sanjeev.pulse`): `user`, `post`, `follow`, `feed`, `platform`
- Module boundaries: `src/main/java/.../<module>/package-info.java` using `@ApplicationModule`

## Commands
Use the wrapper:
- Windows: `mvnw.cmd <goals...>`
- macOS/Linux/WSL: `./mvnw <goals...>`

### Build
- Compile (skip tests): `mvnw.cmd -DskipTests compile`
- Full build (tests): `mvnw.cmd verify`
- Package jar (skip tests): `mvnw.cmd -DskipTests package`

### Run
- Run locally: `mvnw.cmd spring-boot:run`
- H2 console (when running): `/h2-console`

### Tests
- Run all tests: `mvnw.cmd test`
- Run all tests (quieter): `mvnw.cmd -q test`

Run a single test class:
- `mvnw.cmd -Dtest=PulseModulithApplicationTests test`
- `mvnw.cmd -Dtest=FeedHydrationIT test`

Run a single test method:
- `mvnw.cmd -Dtest=FeedHydrationIT#hydratedFeedContainsText test`

Useful flags:
- More error detail: `mvnw.cmd -e test`
- Maven debug: `mvnw.cmd -X test`

### Lint / Formatting
No dedicated formatter/linter configured (no Spotless/Checkstyle/etc. detected). Use IDE defaults; follow conventions below.

## Code Style and Conventions

### Package / module structure
- Feature-first packages only under `com.sanjeev.pulse`.
- Common subpackages (use when helpful, not mandatory):
  - `web`: REST controllers + web-only DTOs
  - `dto`: request/response DTOs shared beyond web
  - `api`: stable module API used cross-module
- Prefer `otherModule :: api` dependencies over depending on `otherModule` directly.
- Update `allowedDependencies` in `package-info.java` whenever introducing a new cross-module call.

### Visibility (encapsulation)
- Default to package-private for implementation classes.
- Keep entities and repositories package-private unless referenced cross-module.
- Expose cross-module entrypoints via small interfaces/records in `<module>.api`.

### Imports
- Use explicit imports (no `*`).
- Import order: `java.*`, `jakarta.*`, `org.*`, then `com.*`.

### Formatting
- 4-space indentation.
- Prefer small cohesive methods; avoid long parameter lists.
- Prefer early returns for validation.

### Types and DTOs
- Prefer Java `record` for immutable request/response types.
- Nullable by contract: wrapper types (`Long`). Non-null by contract: primitives (`long`).
- Apply Jakarta Validation annotations on request records (`@NotBlank`, `@Size`, `@NotNull`).
- Controllers should use `@Valid` on `@RequestBody`.

### Naming
- Services: `<Thing>Service`; query-only services: `<Thing>QueryService`.
- Controllers: `<Thing>Controller` or `<Context><Thing>Controller`.
- Repository interfaces: `<Thing>Repository`.
- Tests: unit-ish `*Test`, integration `*IT`.

### Error handling (HTTP)
- Throw domain exceptions for business conditions:
  - `ResourceNotFoundException` -> 404
  - `ResourceConflictException` -> 409
- For invalid inputs, throw `IllegalArgumentException` (mapped to 400).
- Centralize HTTP mapping in `src/main/java/com/sanjeev/pulse/platform/ApiExceptionHandler.java`.
- Use `ProblemDetail` for consistent error responses.

### Transactions + events
- Put transactional boundaries on service methods.
- Use `@Transactional(readOnly = true)` for query paths.
- Event listeners that write should usually run AFTER_COMMIT and in a new tx
  (`@TransactionalEventListener(phase = AFTER_COMMIT)` + `REQUIRES_NEW`).

## Modulith Rules
- Keep module dependencies minimal and explicit.
- When adding a new cross-module call:
  1) Add an interface/record under provider `api`
  2) Implement it internally
  3) Update consumer `allowedDependencies` to `provider :: api`
  4) Verify with the architecture test

Run architecture verification:
- `mvnw.cmd -Dtest=ModulithArchitectureTest test`

## Cursor Pagination Rules
Stable cursor pagination is used in read paths.
- Cursor format: `<epochMillis>_<id>` (example: `1737138123456_101`)
- Deterministic ordering: `ORDER BY createdAt DESC, id DESC` (feed may use `postId`)
- Cursor filter matches ordering:
  `createdAt < :createdAt OR (createdAt = :createdAt AND id < :id)`
- Clamp `limit` (current convention): min 1, max 100
- `nextCursor` comes from the last returned row

## Testing Guidelines
- Prefer integration tests for module interactions (`@SpringBootTest` + real DB).
- Keep tests deterministic:
  - Dont rely on implicit ordering; always `ORDER BY`.
  - Avoid sleeps; prefer transactional/event semantics.
- If you change module deps or cross-module APIs, extend tests accordingly.

## Before Submitting
- Ensure `mvnw.cmd test` passes.
- If you changed module dependencies, ensure `ModulithArchitectureTest` passes.
- Keep public APIs small and stable; prefer `api` surfaces over direct module coupling.
