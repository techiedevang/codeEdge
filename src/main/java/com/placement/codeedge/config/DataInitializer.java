package com.placement.codeedge.config;

import com.placement.codeedge.model.Problem;
import com.placement.codeedge.model.enums.Difficulty;
import com.placement.codeedge.model.enums.Topic;
import com.placement.codeedge.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProblemRepository repo;

    @Override
    public void run(String... args) {
        if (repo.count() > 0) { log.info("DB already seeded."); return; }
        List<Problem> all = new ArrayList<>();
        all.addAll(arrays());
        all.addAll(strings());
        all.addAll(linkedList());
        all.addAll(trees());
        all.addAll(graphs());
        all.addAll(dp());
        all.addAll(binarySearch());
        all.addAll(stackQueue());
        all.addAll(heaps());
        all.addAll(misc());
        repo.saveAll(all);
        log.info("Seeded {} problems.", all.size());
    }

    private Problem p(String title, Topic t, Difficulty d, String hint, String link, double rate, String... cos) {
        return Problem.builder().title(title).topic(t).difficulty(d)
                .hint(hint).leetcodeLink(link).acceptanceRate(rate)
                .companies(List.of(cos)).build();
    }

    private List<Problem> arrays() {
        return List.of(
            p("Two Sum", Topic.ARRAYS, Difficulty.EASY, "Use a HashMap to store complement → index", "https://leetcode.com/problems/two-sum/", 49.5, "Amazon","Google","Microsoft","Flipkart"),
            p("Best Time to Buy and Sell Stock", Topic.ARRAYS, Difficulty.EASY, "Track minimum price seen so far", "https://leetcode.com/problems/best-time-to-buy-and-sell-stock/", 54.2, "Amazon","Flipkart","Adobe"),
            p("Maximum Subarray (Kadane's Algorithm)", Topic.ARRAYS, Difficulty.MEDIUM, "Keep running sum; reset when negative", "https://leetcode.com/problems/maximum-subarray/", 50.1, "Amazon","Microsoft","Goldman Sachs"),
            p("Container With Most Water", Topic.ARRAYS, Difficulty.MEDIUM, "Two pointers from both ends", "https://leetcode.com/problems/container-with-most-water/", 54.4, "Amazon","Google"),
            p("3Sum", Topic.ARRAYS, Difficulty.MEDIUM, "Sort + two pointers for each element", "https://leetcode.com/problems/3sum/", 32.5, "Amazon","Google","Uber"),
            p("Product of Array Except Self", Topic.ARRAYS, Difficulty.MEDIUM, "Prefix and suffix products", "https://leetcode.com/problems/product-of-array-except-self/", 64.8, "Amazon","Google","Microsoft"),
            p("Maximum Product Subarray", Topic.ARRAYS, Difficulty.MEDIUM, "Track both max and min products", "https://leetcode.com/problems/maximum-product-subarray/", 34.6, "Amazon","Flipkart"),
            p("Find Minimum in Rotated Sorted Array", Topic.ARRAYS, Difficulty.MEDIUM, "Binary search on the pivot", "https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/", 48.6, "Microsoft","Amazon"),
            p("Search in Rotated Sorted Array", Topic.ARRAYS, Difficulty.MEDIUM, "Modified binary search", "https://leetcode.com/problems/search-in-rotated-sorted-array/", 39.2, "Amazon","Google","Uber"),
            p("Merge Intervals", Topic.ARRAYS, Difficulty.MEDIUM, "Sort by start, merge overlapping", "https://leetcode.com/problems/merge-intervals/", 46.3, "Google","Amazon","Microsoft"),
            p("Missing Number", Topic.ARRAYS, Difficulty.EASY, "Use sum formula: n*(n+1)/2 − sum", "https://leetcode.com/problems/missing-number/", 63.0, "Microsoft","Amazon"),
            p("Trapping Rain Water", Topic.ARRAYS, Difficulty.HARD, "Two pointers: track left/right max", "https://leetcode.com/problems/trapping-rain-water/", 60.7, "Amazon","Google","Microsoft"),
            p("Jump Game", Topic.ARRAYS, Difficulty.MEDIUM, "Track the farthest reachable index", "https://leetcode.com/problems/jump-game/", 38.6, "Amazon","Google"),
            p("Rotate Array", Topic.ARRAYS, Difficulty.MEDIUM, "Reverse whole array, then halves", "https://leetcode.com/problems/rotate-array/", 39.3, "Microsoft","Amazon"),
            p("Spiral Matrix", Topic.ARRAYS, Difficulty.MEDIUM, "Shrink boundaries after each traversal", "https://leetcode.com/problems/spiral-matrix/", 47.1, "Amazon","Microsoft"),
            p("Set Matrix Zeroes", Topic.ARRAYS, Difficulty.MEDIUM, "Use first row/col as markers", "https://leetcode.com/problems/set-matrix-zeroes/", 51.3, "Amazon","Google"),
            p("Longest Consecutive Sequence", Topic.ARRAYS, Difficulty.MEDIUM, "Use a HashSet; start only from n-1 absent", "https://leetcode.com/problems/longest-consecutive-sequence/", 46.9, "Amazon","Google"),
            p("Find the Duplicate Number", Topic.ARRAYS, Difficulty.MEDIUM, "Floyd's cycle detection (tortoise & hare)", "https://leetcode.com/problems/find-the-duplicate-number/", 59.2, "Amazon"),
            p("Sort Colors (Dutch National Flag)", Topic.SORTING, Difficulty.MEDIUM, "Three-way partition with low/mid/high pointers", "https://leetcode.com/problems/sort-colors/", 60.5, "Amazon","Google"),
            p("Subarray Sum Equals K", Topic.ARRAYS, Difficulty.MEDIUM, "HashMap of prefix sums", "https://leetcode.com/problems/subarray-sum-equals-k/", 44.3, "Amazon","Google","Uber")
        );
    }

    private List<Problem> strings() {
        return List.of(
            p("Valid Anagram", Topic.STRINGS, Difficulty.EASY, "Count character frequencies", "https://leetcode.com/problems/valid-anagram/", 63.2, "Amazon","Google"),
            p("Group Anagrams", Topic.STRINGS, Difficulty.MEDIUM, "Sort each word as key in HashMap", "https://leetcode.com/problems/group-anagrams/", 67.1, "Amazon","Google","Microsoft"),
            p("Longest Substring Without Repeating Characters", Topic.STRINGS, Difficulty.MEDIUM, "Sliding window with a HashSet", "https://leetcode.com/problems/longest-substring-without-repeating-characters/", 33.8, "Amazon","Google","Microsoft"),
            p("Longest Palindromic Substring", Topic.STRINGS, Difficulty.MEDIUM, "Expand around center", "https://leetcode.com/problems/longest-palindromic-substring/", 32.5, "Amazon","Microsoft"),
            p("Valid Parentheses", Topic.STRINGS, Difficulty.EASY, "Stack: push open, pop for close", "https://leetcode.com/problems/valid-parentheses/", 40.6, "Amazon","Google"),
            p("Generate Parentheses", Topic.STRINGS, Difficulty.MEDIUM, "Backtrack with open/close counts", "https://leetcode.com/problems/generate-parentheses/", 73.5, "Google","Amazon"),
            p("Minimum Window Substring", Topic.STRINGS, Difficulty.HARD, "Sliding window with two frequency maps", "https://leetcode.com/problems/minimum-window-substring/", 41.9, "Amazon","Google","Facebook"),
            p("Palindrome Partitioning", Topic.STRINGS, Difficulty.MEDIUM, "Backtrack, check palindrome at each split", "https://leetcode.com/problems/palindrome-partitioning/", 67.7, "Amazon"),
            p("Word Break", Topic.STRINGS, Difficulty.MEDIUM, "DP: dp[i] = can break s[0..i]", "https://leetcode.com/problems/word-break/", 45.7, "Amazon","Google"),
            p("Reverse Words in a String", Topic.STRINGS, Difficulty.MEDIUM, "Split, trim, reverse list", "https://leetcode.com/problems/reverse-words-in-a-string/", 36.2, "Microsoft","Amazon"),
            p("String Compression", Topic.STRINGS, Difficulty.MEDIUM, "Two pointers: read/write", "https://leetcode.com/problems/string-compression/", 48.2, "Amazon"),
            p("Implement strStr()", Topic.STRINGS, Difficulty.EASY, "KMP or sliding window match", "https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/", 39.8, "Amazon","Google"),
            p("Longest Common Prefix", Topic.STRINGS, Difficulty.EASY, "Vertical scan or trie", "https://leetcode.com/problems/longest-common-prefix/", 41.8, "Amazon")
        );
    }

    private List<Problem> linkedList() {
        return List.of(
            p("Reverse Linked List", Topic.LINKED_LIST, Difficulty.EASY, "Iterative: prev/curr/next pointers", "https://leetcode.com/problems/reverse-linked-list/", 73.6, "Amazon","Microsoft","Adobe"),
            p("Detect Cycle in Linked List", Topic.LINKED_LIST, Difficulty.EASY, "Floyd's slow/fast pointer", "https://leetcode.com/problems/linked-list-cycle/", 49.3, "Amazon","Google"),
            p("Merge Two Sorted Lists", Topic.LINKED_LIST, Difficulty.EASY, "Compare heads and link smaller", "https://leetcode.com/problems/merge-two-sorted-lists/", 62.9, "Amazon","Google","Microsoft"),
            p("Merge K Sorted Lists", Topic.LINKED_LIST, Difficulty.HARD, "Min-heap of (val, list-index)", "https://leetcode.com/problems/merge-k-sorted-lists/", 52.2, "Amazon","Google"),
            p("Remove Nth Node From End", Topic.LINKED_LIST, Difficulty.MEDIUM, "Two pointers n apart", "https://leetcode.com/problems/remove-nth-node-from-end-of-list/", 42.6, "Amazon","Google"),
            p("Copy List with Random Pointer", Topic.LINKED_LIST, Difficulty.MEDIUM, "HashMap: old node → new node", "https://leetcode.com/problems/copy-list-with-random-pointer/", 54.6, "Amazon","Microsoft"),
            p("Add Two Numbers", Topic.LINKED_LIST, Difficulty.MEDIUM, "Simulate digit-by-digit with carry", "https://leetcode.com/problems/add-two-numbers/", 42.0, "Amazon","Google"),
            p("LRU Cache", Topic.LINKED_LIST, Difficulty.MEDIUM, "Doubly linked list + HashMap", "https://leetcode.com/problems/lru-cache/", 42.5, "Amazon","Google","Microsoft"),
            p("Reorder List", Topic.LINKED_LIST, Difficulty.MEDIUM, "Find mid, reverse second half, merge", "https://leetcode.com/problems/reorder-list/", 56.0, "Amazon","Google"),
            p("Palindrome Linked List", Topic.LINKED_LIST, Difficulty.EASY, "Find mid, reverse second half, compare", "https://leetcode.com/problems/palindrome-linked-list/", 51.3, "Amazon","Microsoft")
        );
    }

    private List<Problem> trees() {
        return List.of(
            p("Maximum Depth of Binary Tree", Topic.TREES, Difficulty.EASY, "Recursive: 1 + max(left, right)", "https://leetcode.com/problems/maximum-depth-of-binary-tree/", 74.4, "Amazon","Google"),
            p("Validate Binary Search Tree", Topic.TREES, Difficulty.MEDIUM, "Pass min/max bounds recursively", "https://leetcode.com/problems/validate-binary-search-tree/", 32.5, "Amazon","Google","Microsoft"),
            p("Binary Tree Level Order Traversal", Topic.TREES, Difficulty.MEDIUM, "BFS with a queue, track level size", "https://leetcode.com/problems/binary-tree-level-order-traversal/", 67.0, "Amazon","Google","Microsoft"),
            p("Symmetric Tree", Topic.TREES, Difficulty.EASY, "Mirror check: left.left==right.right", "https://leetcode.com/problems/symmetric-tree/", 53.3, "Amazon","Microsoft"),
            p("Binary Tree Maximum Path Sum", Topic.TREES, Difficulty.HARD, "At each node: gain = max(left,0)+max(right,0)+val", "https://leetcode.com/problems/binary-tree-maximum-path-sum/", 39.7, "Amazon","Google"),
            p("Lowest Common Ancestor of BST", Topic.TREES, Difficulty.MEDIUM, "If both < root, go left; both > root, go right", "https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/", 62.3, "Amazon","Google","Microsoft"),
            p("Diameter of Binary Tree", Topic.TREES, Difficulty.EASY, "Max path through each node = left_h + right_h", "https://leetcode.com/problems/diameter-of-binary-tree/", 57.7, "Amazon","Google"),
            p("Kth Smallest Element in BST", Topic.TREES, Difficulty.MEDIUM, "In-order traversal gives sorted order", "https://leetcode.com/problems/kth-smallest-element-in-a-bst/", 70.7, "Amazon","Google"),
            p("Right Side View of Binary Tree", Topic.TREES, Difficulty.MEDIUM, "BFS: take last element of each level", "https://leetcode.com/problems/binary-tree-right-side-view/", 61.7, "Amazon","Microsoft"),
            p("Construct Binary Tree from Inorder and Preorder", Topic.TREES, Difficulty.MEDIUM, "Preorder root splits inorder into halves", "https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/", 61.0, "Amazon"),
            p("Flatten Binary Tree to Linked List", Topic.TREES, Difficulty.MEDIUM, "Morris traversal or stack-based preorder", "https://leetcode.com/problems/flatten-binary-tree-to-linked-list/", 64.4, "Amazon","Microsoft"),
            p("Path Sum II", Topic.TREES, Difficulty.MEDIUM, "Backtracking DFS, collect paths summing to target", "https://leetcode.com/problems/path-sum-ii/", 57.8, "Amazon","Microsoft")
        );
    }

    private List<Problem> graphs() {
        return List.of(
            p("Number of Islands", Topic.GRAPHS, Difficulty.MEDIUM, "BFS/DFS flood fill from each '1'", "https://leetcode.com/problems/number-of-islands/", 57.4, "Amazon","Google","Microsoft"),
            p("Clone Graph", Topic.GRAPHS, Difficulty.MEDIUM, "BFS + HashMap<oldNode, newNode>", "https://leetcode.com/problems/clone-graph/", 56.9, "Amazon","Google"),
            p("Course Schedule (Cycle Detection)", Topic.GRAPHS, Difficulty.MEDIUM, "Topological sort / DFS with visited states", "https://leetcode.com/problems/course-schedule/", 45.9, "Google","Amazon","Microsoft"),
            p("Course Schedule II (Topo Sort)", Topic.GRAPHS, Difficulty.MEDIUM, "Kahn's BFS algorithm", "https://leetcode.com/problems/course-schedule-ii/", 49.0, "Google","Amazon"),
            p("Number of Connected Components", Topic.GRAPHS, Difficulty.MEDIUM, "Union-Find or DFS count components", "https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/", 63.0, "Amazon","Google"),
            p("Pacific Atlantic Water Flow", Topic.GRAPHS, Difficulty.MEDIUM, "BFS from ocean edges inward", "https://leetcode.com/problems/pacific-atlantic-water-flow/", 53.4, "Google"),
            p("Word Ladder", Topic.GRAPHS, Difficulty.HARD, "BFS on word graph with single-char changes", "https://leetcode.com/problems/word-ladder/", 37.1, "Amazon","Google"),
            p("Walls and Gates", Topic.GRAPHS, Difficulty.MEDIUM, "Multi-source BFS from all gates", "https://leetcode.com/problems/walls-and-gates/", 60.5, "Amazon","Google"),
            p("Rotting Oranges", Topic.GRAPHS, Difficulty.MEDIUM, "Multi-source BFS from all rotten oranges", "https://leetcode.com/problems/rotting-oranges/", 52.8, "Amazon","Google")
        );
    }

    private List<Problem> dp() {
        return List.of(
            p("Climbing Stairs", Topic.DYNAMIC_PROGRAMMING, Difficulty.EASY, "dp[i] = dp[i-1] + dp[i-2] (Fibonacci)", "https://leetcode.com/problems/climbing-stairs/", 51.8, "Amazon","Google","Adobe"),
            p("Coin Change", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i] = min coins to make amount i", "https://leetcode.com/problems/coin-change/", 42.3, "Amazon","Google","Flipkart"),
            p("Longest Increasing Subsequence", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i] = max LIS ending at i; O(n log n) with patience sort", "https://leetcode.com/problems/longest-increasing-subsequence/", 54.2, "Amazon","Google","Microsoft"),
            p("Longest Common Subsequence", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i][j]: match or max of skip-left/skip-up", "https://leetcode.com/problems/longest-common-subsequence/", 57.6, "Amazon","Google"),
            p("0/1 Knapsack Problem", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i][w] = max value with i items and capacity w", "https://leetcode.com/problems/", 55.0, "Amazon","Flipkart","Goldman Sachs"),
            p("Unique Paths", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i][j] = dp[i-1][j] + dp[i][j-1]", "https://leetcode.com/problems/unique-paths/", 63.1, "Amazon","Google"),
            p("Edit Distance", Topic.DYNAMIC_PROGRAMMING, Difficulty.HARD, "dp[i][j]: min ops to convert s1[0..i] to s2[0..j]", "https://leetcode.com/problems/edit-distance/", 54.3, "Amazon","Google"),
            p("House Robber", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i] = max(dp[i-1], dp[i-2]+nums[i])", "https://leetcode.com/problems/house-robber/", 50.4, "Amazon","Google"),
            p("House Robber II (Circular)", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "Run House Robber on [0..n-2] and [1..n-1]", "https://leetcode.com/problems/house-robber-ii/", 41.0, "Amazon"),
            p("Decode Ways", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "dp[i]: ways to decode s[0..i]", "https://leetcode.com/problems/decode-ways/", 34.2, "Amazon","Google"),
            p("Partition Equal Subset Sum", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "Subset-sum DP for target = total/2", "https://leetcode.com/problems/partition-equal-subset-sum/", 46.7, "Amazon","Google"),
            p("Matrix Chain Multiplication", Topic.DYNAMIC_PROGRAMMING, Difficulty.HARD, "dp[i][j] = min multiplications for chain i..j", "https://leetcode.com/problems/", 55.0, "Amazon","Goldman Sachs"),
            p("Target Sum", Topic.DYNAMIC_PROGRAMMING, Difficulty.MEDIUM, "DFS + memo or subset-sum with DP", "https://leetcode.com/problems/target-sum/", 45.0, "Amazon")
        );
    }

    private List<Problem> binarySearch() {
        return List.of(
            p("Binary Search", Topic.BINARY_SEARCH, Difficulty.EASY, "Classic lo/hi/mid — return mid when found", "https://leetcode.com/problems/binary-search/", 55.4, "Amazon","Google"),
            p("Search a 2D Matrix", Topic.BINARY_SEARCH, Difficulty.MEDIUM, "Treat matrix as flat sorted array", "https://leetcode.com/problems/search-a-2d-matrix/", 49.0, "Amazon","Microsoft"),
            p("Find Peak Element", Topic.BINARY_SEARCH, Difficulty.MEDIUM, "Move towards higher neighbor", "https://leetcode.com/problems/find-peak-element/", 46.2, "Google","Amazon"),
            p("Median of Two Sorted Arrays", Topic.BINARY_SEARCH, Difficulty.HARD, "Binary search on shorter array partition", "https://leetcode.com/problems/median-of-two-sorted-arrays/", 37.0, "Amazon","Google"),
            p("Kth Smallest in Sorted Matrix", Topic.BINARY_SEARCH, Difficulty.MEDIUM, "Binary search on value range + count", "https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/", 60.4, "Amazon","Google"),
            p("Capacity to Ship Packages in D Days", Topic.BINARY_SEARCH, Difficulty.MEDIUM, "Binary search on capacity range", "https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/", 67.5, "Amazon","Google")
        );
    }

    private List<Problem> stackQueue() {
        return List.of(
            p("Min Stack", Topic.STACK_QUEUE, Difficulty.EASY, "Two stacks: one for values, one for minimums", "https://leetcode.com/problems/min-stack/", 52.9, "Amazon","Google","Microsoft"),
            p("Evaluate Reverse Polish Notation", Topic.STACK_QUEUE, Difficulty.MEDIUM, "Push numbers; on operator pop two, compute, push result", "https://leetcode.com/problems/evaluate-reverse-polish-notation/", 47.0, "Amazon","Google"),
            p("Daily Temperatures", Topic.STACK_QUEUE, Difficulty.MEDIUM, "Monotonic decreasing stack of indices", "https://leetcode.com/problems/daily-temperatures/", 66.6, "Amazon","Google"),
            p("Largest Rectangle in Histogram", Topic.STACK_QUEUE, Difficulty.HARD, "Monotonic stack of indices", "https://leetcode.com/problems/largest-rectangle-in-histogram/", 44.3, "Amazon","Google"),
            p("Sliding Window Maximum", Topic.STACK_QUEUE, Difficulty.HARD, "Monotonic deque of indices", "https://leetcode.com/problems/sliding-window-maximum/", 46.7, "Amazon","Google")
        );
    }

    private List<Problem> heaps() {
        return List.of(
            p("Kth Largest Element in Array", Topic.HEAPS, Difficulty.MEDIUM, "Min-heap of size k; or QuickSelect", "https://leetcode.com/problems/kth-largest-element-in-an-array/", 65.1, "Amazon","Google","Microsoft"),
            p("Top K Frequent Elements", Topic.HEAPS, Difficulty.MEDIUM, "HashMap + min-heap of size k", "https://leetcode.com/problems/top-k-frequent-elements/", 64.6, "Amazon","Google"),
            p("Find Median from Data Stream", Topic.HEAPS, Difficulty.HARD, "Two heaps: max-heap for lower, min-heap for upper half", "https://leetcode.com/problems/find-median-from-data-stream/", 51.3, "Amazon","Google"),
            p("Task Scheduler", Topic.HEAPS, Difficulty.MEDIUM, "Max-heap by frequency; fill idle slots", "https://leetcode.com/problems/task-scheduler/", 57.9, "Amazon","Google"),
            p("Reorganize String", Topic.HEAPS, Difficulty.MEDIUM, "Greedy with max-heap; alternate most frequent chars", "https://leetcode.com/problems/reorganize-string/", 53.2, "Google","Amazon")
        );
    }

    private List<Problem> misc() {
        return List.of(
            p("Two Sum II (Sorted Array)", Topic.TWO_POINTERS, Difficulty.MEDIUM, "Two pointers from ends", "https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/", 60.9, "Amazon","Google"),
            p("Move Zeroes", Topic.TWO_POINTERS, Difficulty.EASY, "Snowball: count zeros behind, shift", "https://leetcode.com/problems/move-zeroes/", 60.5, "Amazon","Microsoft"),
            p("Longest Repeating Character Replacement", Topic.SLIDING_WINDOW, Difficulty.MEDIUM, "Sliding window: window_size - max_freq <= k", "https://leetcode.com/problems/longest-repeating-character-replacement/", 51.9, "Google","Amazon"),
            p("Minimum Size Subarray Sum", Topic.SLIDING_WINDOW, Difficulty.MEDIUM, "Expand right; shrink left when sum >= target", "https://leetcode.com/problems/minimum-size-subarray-sum/", 44.0, "Amazon","Google"),
            p("Pow(x, n) — Fast Exponentiation", Topic.MATH, Difficulty.MEDIUM, "Recursive halving; handle negative n", "https://leetcode.com/problems/powx-n/", 34.2, "Amazon","Google","Microsoft"),
            p("Number of 1 Bits (Hamming Weight)", Topic.MATH, Difficulty.EASY, "n & (n-1) clears lowest set bit", "https://leetcode.com/problems/number-of-1-bits/", 67.5, "Amazon","Microsoft"),
            p("Counting Bits", Topic.MATH, Difficulty.EASY, "dp[i] = dp[i>>1] + (i & 1)", "https://leetcode.com/problems/counting-bits/", 77.4, "Amazon","Google"),
            p("Single Number", Topic.MATH, Difficulty.EASY, "XOR all elements — duplicates cancel", "https://leetcode.com/problems/single-number/", 70.9, "Amazon","Google"),
            p("Fibonacci Number", Topic.RECURSION, Difficulty.EASY, "Memoized recursion or bottom-up DP", "https://leetcode.com/problems/fibonacci-number/", 68.9, "Amazon","Google"),
            p("Combination Sum", Topic.RECURSION, Difficulty.MEDIUM, "Backtrack with remaining target", "https://leetcode.com/problems/combination-sum/", 70.4, "Amazon","Google"),
            p("Permutations", Topic.RECURSION, Difficulty.MEDIUM, "Backtrack: swap and recurse", "https://leetcode.com/problems/permutations/", 76.3, "Amazon","Google","Microsoft"),
            p("Subsets", Topic.RECURSION, Difficulty.MEDIUM, "Backtrack: include or exclude each element", "https://leetcode.com/problems/subsets/", 76.5, "Amazon","Google"),
            p("N-Queens Problem", Topic.RECURSION, Difficulty.HARD, "Backtrack with column, diagonal sets", "https://leetcode.com/problems/n-queens/", 66.7, "Amazon","Google"),
            p("Letter Combinations of Phone Number", Topic.RECURSION, Difficulty.MEDIUM, "Backtrack over digit→chars map", "https://leetcode.com/problems/letter-combinations-of-a-phone-number/", 57.5, "Amazon","Google"),
            p("Word Search", Topic.RECURSION, Difficulty.MEDIUM, "DFS + backtrack on grid", "https://leetcode.com/problems/word-search/", 40.2, "Amazon","Microsoft"),
            p("Merge Sort Implementation", Topic.SORTING, Difficulty.MEDIUM, "Divide: split. Conquer: sort halves. Combine: merge.", "https://leetcode.com/problems/sort-an-array/", 62.0, "Amazon","Google"),
            p("Top K Frequent Words", Topic.HASHING, Difficulty.MEDIUM, "HashMap + min-heap; sort by freq then lex", "https://leetcode.com/problems/top-k-frequent-words/", 57.3, "Amazon","Google"),
            p("Two Sum III - Data Structure Design", Topic.HASHING, Difficulty.EASY, "HashMap: store counts; check complement", "https://leetcode.com/problems/two-sum-iii-data-structure-design/", 35.4, "Amazon"),
            p("4Sum", Topic.TWO_POINTERS, Difficulty.MEDIUM, "Fix two indices + two pointers", "https://leetcode.com/problems/4sum/", 36.2, "Amazon","Google"),
            p("Longest Mountain in Array", Topic.TWO_POINTERS, Difficulty.MEDIUM, "Two passes: expand left then right from peak", "https://leetcode.com/problems/longest-mountain-in-array/", 39.6, "Amazon")
        );
    }
}
