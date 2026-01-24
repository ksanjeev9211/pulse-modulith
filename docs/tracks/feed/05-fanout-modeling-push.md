# Feed Track — 05: Fanout Modeling (Push)

## Problem
How do posts reach followers at read time efficiently?

## Naive Solution
- Fanout-on-write
- Write one feed entry per follower

## Key Decisions
- Fanout after commit
- Synchronous execution
- Composite key `(userId, postId)` for idempotency

## Pressure Created
- Write amplification
- Celebrity problem

## Why This Step Exists
To *experience* fanout cost before optimizing it away.