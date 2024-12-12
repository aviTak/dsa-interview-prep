Job         |  J1  |  J2  |  J3  |  J4  |  J5  |  J6  |  J7
Profit      |  35  |  30  |  25  |  20  |  15  |  12  |  5
Deadline    |  3   |  4   |  4   |  2   |  3   |  1   |  2


Think of it as time slots:-
1. 12:00 - 13:00
2. 13:00 - 14:00
3. 14:00 - 15:00
4. 15:00 - 16:00

Each task takes 1 hour of time.

1 means that the task needs to be performed within the 1st hour.
Similarly, 4 means that the task can be done anywhere between the 1st four hours.

1. Firstly, sort the jobs based on decreasing order of profits.
2. Iterate in this order.
3. Try to fit the current slot in the last possible slot, as in one with deadline 4 in the 4th slot else 4--.

Output:-

12 - J4 - 13 - J3 - 14 - J1 - 15 - J2 - 16
 