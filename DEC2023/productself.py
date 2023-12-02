# Product of array except self
# Input: X[] = [2, 1, 3, 4], Output: product[] = [12, 24, 8, 6]

def productself(arr):
    n = len(arr)
    new_list = []

    for i in range(n):
        left_sum = 1
        right_sum = 1

        for j in range(i):
            left_sum *= arr[j]
        
        for k in range(i+1,n):
            right_sum *= arr[k]
        
        data = left_sum * right_sum
        new_list.append(data)
    
    return new_list


# arr = [2, 1, 3, 4]
arr = [5,2,8,4,5]
print(productself(arr))