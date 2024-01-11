#maximum subarray
# Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
# Output: 6
# Explanation: The subarray [4,-1,2,1] has the largest sum 6.

def maxmum_subarray(arr):
    n = len(arr)
    maximum_sum = arr[0]
    global_sum_so_far = arr[0]
    for i in range(1,n-1):
        maximum_sum = max(arr[i], arr[i]+maximum_sum)
        print(maximum_sum)
        global_sum_so_far = max(global_sum_so_far,maximum_sum)
    
    return global_sum_so_far

nums = [-1,-3,-4,-5] #[-2,1,-3,4,-1,2,1,-5,4]
print(maxmum_subarray(nums))