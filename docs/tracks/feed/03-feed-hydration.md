# Feed Track — 03: Feed Hydration

## Problem
Precomputing full feed entries causes excessive write amplification.

## Solution
- Feed stores lightweight references
- Post and user data hydrated at read time

## Tradeoff Introduced
- Read amplification
- Simpler writes

## Why This Exists
To feel the cost of composition before introducing caching.