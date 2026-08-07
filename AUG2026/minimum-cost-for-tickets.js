// You have days you must travel (given as a sorted array),
// and three passes: 1-day, 7-day, 30-day, with costs [c1, c7, c30].
// A pass bought on day i covers day i through i+duration-1.
// Find the minimum total cost.

// days = [1,4,6,7,8,20], costs = [2,7,15] → answer 11.
// 1, 2, 3, 4

function minCost(days, costs) {
    const N = days.length,
        lastDay = days[N - 1],
        dp = new Array(lastDay + 1).fill(0);

    let j = 0;
    
    for (let i = 1; i <= lastDay; i++) {
        if (i !== days[j])  {
            dp[i] = dp[i - 1];
            continue;
        }

        const one = dp[i - 1] + costs[0],
            seven = dp[Math.max(0, i - 7)] + costs[1],
            thirty = dp[Math.max(0, i - 30)] + costs[2];

        dp[i] = Math.min(one, seven, thirty);

        j++;
    }

    return dp[lastDay];
}

function main() {
    const days = [1,4,6,7,8,20], costs = [2,7,15];

    console.log(minCost(days, costs));
}

main();

