// Split Array Largest Sum — given nums and an integer k,
// split the array into k non-empty contiguous subarrays
// and minimise the largest subarray sum.

// nums = [7,2,5,10,8], k = 2 → answer 18.

function largestSum(nums, k) {
    let low = Math.max(...nums),
        high = nums.reduce((accum, num) => accum + num, 0);

    while (low < high) {
        let mid = Math.floor((low + high) / 2);

        if (canPartition(nums, k, mid)) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }

    return low;
}

function canPartition(nums, k, maxW) {
    let parts = 1, sum = 0;

    const N = nums.length;

    for (let i = 0; i < N; i++) {
        sum += nums[i];

        if (sum > maxW) {
            parts++;
            sum = nums[i];
        }
    }

    return parts <= k;
}

function main () {
    const nums = [7,2,5,10,8], k = 20;

    console.log(largestSum(nums, k));
}

main();