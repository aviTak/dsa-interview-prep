// You have days you must travel (given as a sorted array),
// and three passes: 1-day, 7-day, 30-day, with costs [c1, c7, c30].
// A pass bought on day i covers day i through i+duration-1.
// Find the minimum total cost.

// days = [1,4,6,7,8,20], costs = [2,7,15] → answer 11.
// 1, 2, 3, 4

// function minCost(days, costs) {
//     const N = days.length,
//         lastDay = days[N - 1],
//         dp = new Array(lastDay + 1).fill(0);

//     let j = 0;
    
//     for (let i = 1; i <= lastDay; i++) {
//         if (i !== days[j])  {
//             dp[i] = dp[i - 1];
//             continue;
//         }

//         const one = dp[i - 1] + costs[0],
//             seven = dp[Math.max(0, i - 7)] + costs[1],
//             thirty = dp[Math.max(0, i - 30)] + costs[2];

//         dp[i] = Math.min(one, seven, thirty);

//         j++;
//     }

//     return dp[lastDay];
// }

function minCost(days, costs, dp, i) {
    if (i >= days.length) {
        return 0;
    }

    if (dp[i] !== -1) {
        return dp[i];
    }

    const one = costs[0] + minCost(days, costs, dp, i + 1);

    let j = i;

    while (j < days.length && days[j] < days[i] + 7) {
        j++;
    }

    const seven = costs[1] + minCost(days, costs, dp, j);

    j = i;

    while (j < days.length && days[j] < days[i] + 30) {
        j++;
    }

    const thirty = costs[2] + minCost(days, costs, dp, j);

    dp[i] = Math.min(one, seven, thirty);
    return dp[i];
}

function minCost1(days, costs) {
    const dp = new Array(days.length).fill(-1);

    return minCost(days, costs, dp, 0);
}

function main() {
    const days = [1,4,6,7,8,20], costs = [2,7,15];

    console.log(minCost1(days, costs));
}

main();

