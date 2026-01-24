# Feed Track — 01: Cursor Pagination

## Problem
Offset-based pagination breaks under concurrent writes and large offsets.

## Solution
- Cursor-based pagination using `(createdAt, id)`
- Deterministic ordering
- Stable cursors

## Key Decisions
- Cursor format: `<epochMillis>_<postId>`
- Enforced page size limits
- No caching

## What Pressure Was Addressed
- Inconsistent reads
- Poor performance at high offsets

## What Was Deferred
- Index sharding
- Caching