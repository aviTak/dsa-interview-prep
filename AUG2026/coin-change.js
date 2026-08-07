// Coin Change, the closest cousin to what you just did,
// then we move to API design.

// coins = [1,2,5], amount = 11 → answer 3 (5+5+1).
// Return the fewest coins making up the amount, or -1 if impossible.

// function coinChange(coins, amount) {
//     const dp = new Array(amount + 1).fill(Infinity);

//     dp[0] = 0;

//     for(let i = 1; i <= amount; i++) {
//         for (let j = 0; j < coins.length; j++) {
//             if (i - coins[j] >= 0) {
//                 dp[i] = Math.min(dp[i], 1 + dp[i - coins[j]]);
//             }
//         }
//     }

//     return dp[amount] === Infinity ? - 1 : dp[amount];
// }

function coinChange(coins, amount, dp) {
    if (amount === 0) {
        return 0;
    }

    if (dp[amount] !== -1) {
        return dp[amount];
    }

    let count = Infinity;
    const N = coins.length;

    for (let i = 0; i < N; i++) {
        if (amount - coins[i] >= 0) {
            const res = coinChange(coins, amount - coins[i], dp);

            if (res !== - 1) {
                count = Math.min(count, res + 1);
            }            
        }
    }

    dp[amount] = count === Infinity ? -1 : count;
    return dp[amount];
}

function coinChange1(coins, amount) {
    const dp = new Array(amount + 1).fill(-1);

    return coinChange(coins, amount, dp);
}

function main() {
    const coins = [1,2,5], amount = 11;

    console.log(coinChange1(coins, amount));
}

main();