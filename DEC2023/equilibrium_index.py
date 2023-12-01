# Write a program to find the equilibrium index of an array. 
# The equilibrium index of an array is an index such that the sum of elements at lower indexes is
# equal to the sum of elements at higher indexes.
# Input: A[] = [-7, 1, 5, 2, -4, 3, 0], Output: 3
# Explanation: 1 is an equilibrium index, i.e., A[0] = A[2] + A[3] + A[4] = 0.
def equiindex(arr):
    n = len(arr)
    for i in range(n):
        # print(i,'i')
        left_sum = 0
        for j in range(i):
            # print(j,'j')
            left_sum += arr[j]
        
        right_sum = 0
        for k in range(i+1,n):
            right_sum += arr[k]
        
        if left_sum == right_sum:
            return i
    return -1

def eqindexvalue(arr):
    n = len(arr)
    prefix = [0] * n
    prefix[0] = arr[0]

    for i in range(1,n):
        prefix[i] = prefix[i-1] + arr[i]
    
    total_sum = prefix[n-1]
    print(prefix,total_sum)
    for j in range(n):
        left_sum = prefix[j] - arr[j]
        right_sum = total_sum-prefix[j]
        print(f'left_sum - {left_sum}, right_sum - {right_sum}')
        if left_sum == right_sum:
            return j
    return -1


arr = [-7, 1, 5, 2, -4, 3, 0]
# arr = [1, 2, -2, -1]
# arr = [0, 1, 3, -2, -1]
# print(equiindex(arr))
print(eqindexvalue(arr))