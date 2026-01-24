# Feed Track — 00: Baseline

## Purpose
Establish a correct, simple baseline before any scaling concerns.

## What Exists
- Single JVM
- Single database
- Synchronous REST APIs
- User creation
- Post creation
- DTO-based controllers
- Spring Modulith boundaries enforced

## Constraints
- No async processing
- No caching
- No events
- No premature abstractions

## Why This Matters
Every later decision is measured against this baseline.
If this step is skipped, scaling decisions lose meaning.