// Given an array of integers numbers where each element in the array
// represents the maximum number of positions that can be moved forward from that index;
// it is acceptable to move by fewer positions.

// Determine whether it is possible to reach the last index of the array by starting from the first index.
// Return true if it can be reached and false otherwise.

// Input
// numbers: number[]: An array of integers, each index's value
// is the maximum number of positions reachable from that index

// Input: numbers = [4,1,0,0,2,3]
// Output: true
// Explanation: Move from index 0 to 4, then move 1 position to the last index.
// Input: numbers = [1,0,0,0]
// Output: false
// Explanation: Can only move from index 0 to index 1 and no further movements thereafter, so it impossible to reach the last index.
// Input: numbers = [2,3,1,1,4]
// Output: true
// Explanation: Move 1 position forward from index 0 to index 1 (it is allowed to move up to 2 position

function isReachable(numbers) {
    const N = numbers.length;

    let maxReach = numbers[0];

    for (let i = 0; i < N; i++) {
        if (i > maxReach) {
            return false;
        }

        maxReach = Math.max(maxReach, i + numbers[i]);

        if (maxReach >= N - 1) {
            return true;
        }
    }

    return true;
}

function main() {
    const numbers = [1,0,1,1,4];

    console.log(isReachable(numbers));
}

main();
