# Feed Track — 02: Domain Events

## Problem
Synchronous write-side coupling causes latency growth and rigidity.

## Solution
- In-process domain events
- `PostCreatedEvent` published after commit
- Consumers decoupled from PostService

## Constraints
- Single JVM
- No message brokers
- No async infrastructure

## Key Outcome
New behavior can be added without modifying PostService.