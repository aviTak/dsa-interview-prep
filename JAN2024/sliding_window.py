def slidingwindow(arr,window_size):
    n = len(arr)

    slow_pointer = 0
    fast_pointer = 0
    total_sum    = 0

    while fast_pointer < n:
        if fast_pointer - slow_pointer + 1 <= window_size:
            total_sum += arr[fast_pointer]
            print(fast_pointer - slow_pointer + 1,window_size,total_sum,'total sum1')     
        else:
             total_sum += arr[fast_pointer]
             total_sum -= arr[slow_pointer]
             slow_pointer += 1
             print(fast_pointer - slow_pointer + 1,window_size,total_sum,'total sum2')
        fast_pointer += 1
    
    return total_sum

arr = [1,3,4,0,2,5,9]
k = 3
print(slidingwindow(arr,k))

