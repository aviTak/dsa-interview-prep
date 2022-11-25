
# Python implementation of simple method to find count of
# pairs with given sum.
 
# Returns number of pairs in arr[0..n-1] with sum equal to 'sum'
def getPairsCount(arr,n,sum):
  storage = {}
  count = 0
  for i in range(len(arr)):

    if (sum - arr[i]) in storage:
      count += storage[sum-arr[i]]
    if arr[i]  in storage:
      storage[arr[i]] += 1
    else:
      storage[arr[i]] = 1
  print(storage)
  return count


# Driver code
arr = [1, 5, 7, -1,5]
n = len(arr)
sum = 6
print('Count of pairs is', getPairsCount(arr, n, sum))
