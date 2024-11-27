start[] = [1, 3, 0, 5, 8, 5], end[] =  [2, 4, 6, 7, 9, 9]

These are the starting and ending times for the meetings.
We need to have maximum number of meetings without any interlap.

1. To solve this problem, we use Greedy Approach.
2. We have to pick smaller meetings to accomadate more meetings.
3. For doing so, the meetings that end soon, will provide the most space to the other meetings.
4. Sort the meeting timings in increasing order of end time.
5. Use any sorting technique with NlogN time complexity.
6. Have used HeapSort using PriorityQueue for this problem.
7. Make sure that the start time of the current meeting is greater than equal to the last selected meeting's end time.
8. Update the last selected meeting's end time and add the result.
9. Print/return the result after the end of the loop.
