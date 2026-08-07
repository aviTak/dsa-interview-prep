// A conveyor belt has packages that must be shipped within D days. 
// The packages must be shipped in the order given.
// Each day you load packages onto the ship without exceeding its weight capacity.
// Return the least weight capacity that will let you ship all packages within D days.

// weights = [1,2,3,4,5,6,7,8,9,10], D = 5. 
// Answer should be 15.

function shipWithinDays(weights, D) {
    let low = Math.max(...weights),
        high = weights.reduce((accum, num) => accum + num, 0);

    while (low < high) {
        let mid = Math.floor((low + high) / 2);

        if (canShip(weights, D, mid)) {
            high = mid;
        } else {
            low = mid + 1;
        }
    }

    return low;
}

function canShip(weights, D, mid) {
    let days = 1, sum = 0;

    const N = weights.length;

    for (let i = 0; i < N; i++) {
        sum += weights[i];

        if (sum > mid) {
            days++;
            sum = weights[i];
        }
    }

    return days <= D;
}

function main() {
    const weights = [1,2,3,4,5,6,7,8,9,10], D = 5;

    console.log(shipWithinDays(weights, D));
}

main();