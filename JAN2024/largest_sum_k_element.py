#sliding window 
#maximum possible sum with at least k element 

def max_sum_subarray(arr,window_size):
    n = len(arr)
    slow_pointer = 0
    fast_pointer = 0
    
    maximum_sum = float('-inf')
    current_sum  = 0

    while fast_pointer < n:
        if fast_pointer - slow_pointer + 1 <= window_size:
            current_sum += arr[fast_pointer]
        else: 
            current_sum += arr[fast_pointer]
            current_sum -= arr[slow_pointer]
            slow_pointer += 1
        
        maximum_sum = max(maximum_sum,current_sum)
        fast_pointer += 1
    
    return maximum_sum
    

nums = [-1,-3,4,5,9,-2,-1]
print(max_sum_subarray(nums,3))