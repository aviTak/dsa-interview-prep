// An experienced robber aims to steal the money in the houses on a street. Each house has a certain amount of money hidden, but there is a catch: the security systems in adjacent houses are interconnected. If the robber breaks into two neighboring houses, the police will be alerted.

// Given an array of integers numbers where each element represents the amount of money in a house, determine the maximum amount of money the robber can steal without triggering the alarm.

// Input
// numbers: number[]: An array of integers
// Notes
// The robber cannot steal from two adjacent houses
// Examples
// Input: numbers = [1,2,3,1]
// Output: 4
// Explanation: The robber can steal a maximum of 4 by robbing house at index 0 (value 1) and house at index 2 (value 3).
// Input: numbers = [2,7,9,3,1]
// Output: 12
// Explanation: The robber can steal a maximum of 12 by robbing house at index 0 (value 2), house at index 2 (value 9), and house at index 4 (value 1).
// Input: numbers = [3,6,1,0,6,0,0,9]
// [3, 6, 6, 6, 12, 12, 21]
// Output: 21
// Explanation: The robber can steal a maximum of 21 by robbing house at index 1 (value 6), house at index 4 (value 6), house at index 7 (value 9).

function maxTheft(numbers) {
    
    const N = numbers.length;

    if (N === 0) return 0;
    if (N === 1) return numbers[0];

    const dp = new Array(N);

    let prev = numbers[0];
    let curr = Math.max(numbers[0], numbers[1]);

    for (let i = 2; i <= N; i++) {
        const temp = Math.max(numbers[i] + prev, curr);

        prev = curr;
        curr = temp;
    }

    return prev;
}

function main() {
    const numbers = [1,2,3,1];

    console.log(maxTheft(numbers));
}

main();
