# Feed Track — 04: Follow Graph

## Problem
Feeds require knowing *who should see whose posts*.

## Solution
- Directed follow graph: `follower → followee`
- Separate follow module
- Query followers of an author

## Key Design Rule
Graph correctness before scale optimization.

## Why This Matters
Fanout modeling depends entirely on graph accuracy.