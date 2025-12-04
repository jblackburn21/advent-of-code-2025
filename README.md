# AoC 2025 wiith Clojure

This repo uses the [aoc-clj-elf](https://github.com/jjcomer/aoc-clj-elf) helper library.

## Pre-requisites

```bash
brew install --cask temurin@21
brew install clojure/tools/clojure
brew install borkdude/brew/babashka
```

## Commands

```bash
# Init a new day
bb new --year 2025 --day <day>

# Get the iput for a day
bb get --year 2025 --day <day>

# Runs tests for a day
bb test --year 2025 --day <day>

# Solve puzzle for a day
bb solve --year 2025 --day <day>
```