# Feed Track — 06: Fanout Pressure

## Problem
Fanout-on-write makes the cost of a post proportional to the author's follower count.

This couples write throughput (and post latency) to the shape of the follow graph.

## What We Observe
- Post write latency increases as follower count increases.
- Database work increases linearly (one feed row per follower).
- A single "celebrity" user can dominate write capacity.

## How We Measure It (No Fixes)
This project is intentionally single JVM + single DB + synchronous.

Two signals make the pressure visible:

1) App log from fanout listener (already in place)
- `fanout-on-write ... targets=... inserted=... tookMs=...`
- This measures the fanout work itself.

2) Disabled pressure harness integration test
- `src/test/java/com/sanjeev/pulse/feed/FanoutPressureIT.java`
- Creates 1 author + 1k followers, then times `POST /v1/posts`
- Enable only when you want to feel the pressure:

```bash
mvnw.cmd -Dtest=FanoutPressureIT -Dpulse.pressure=true test
```

## Why This Step Exists
To make the "celebrity problem" tangible before introducing any complexity.

If the system does not hurt, we do not evolve it.

## What We Explicitly Did NOT Add
- Async fanout
- Queues or brokers
- Batching
- Hybrid push/pull
- Caching

Those are solutions. This step is only the pressure.
