package com.placement.codeedge.model.enums;

public enum Topic {
    ARRAYS("Arrays"),
    STRINGS("Strings"),
    LINKED_LIST("Linked List"),
    TREES("Trees"),
    GRAPHS("Graphs"),
    DYNAMIC_PROGRAMMING("Dynamic Programming"),
    RECURSION("Recursion & Backtracking"),
    BINARY_SEARCH("Binary Search"),
    HEAPS("Heaps & Priority Queue"),
    SLIDING_WINDOW("Sliding Window"),
    TWO_POINTERS("Two Pointers"),
    GREEDY("Greedy"),
    STACK_QUEUE("Stack & Queue"),
    HASHING("Hashing"),
    SORTING("Sorting"),
    MATH("Math & Bit Manipulation");

    private final String displayName;

    Topic(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
