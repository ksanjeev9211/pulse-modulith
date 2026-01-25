# Feed Track — 07: Async Fanout (In-Process)

## Pressure That Forces This Step
Fanout-on-write makes `POST /v1/posts` latency proportional to follower count.

At high fanout, the request path becomes dominated by feed writes.

## Design Decision
Decouple fanout from the post request path while staying within project constraints:

- Still single JVM
- Still single DB
- Still no brokers
- Still in-process events

The change is *where the work runs*:

- `sync`: fanout happens in the request flow after commit
- `async`: fanout happens after commit, but off-thread

This is controlled by configuration:

- `pulse.feed.fanout.async=false` (default)
- `pulse.feed.fanout.async=true`

## How To Validate (Opt-In)
Async fanout changes the consistency model, so the assertions must be "eventual".

An opt-in integration test exists for this mode:

```bash
mvnw.cmd -Dtest=FanoutAsyncIT -Dpulse.async=true test
```

## What This Introduces
- Eventual consistency for feeds: a follower might not see the post immediately.
- New failure mode: fanout can fail without failing the post write.
- Backpressure becomes necessary later (thread pool/queue saturation).

## Why This Is Acceptable (Now)
The pressure is real and measurable.

This step trades immediate feed visibility for keeping post writes responsive.

## What We Explicitly Still Did NOT Add
- Message brokers
- Persistent job queue / outbox
- Retries and DLQs
- Hybrid push/pull

Those become relevant only when this in-process async approach shows its own limits.
