#Rotate array anticlockwise
# Example:
# 'arr '= [1,2,3,4,5]
# 'k' = 1  rotated array = [2,3,4,5,1] #clockwise
# 'k' = 2  rotated array = [3,4,5,1,2]
# 'k' = 3  rotated array = [4,5,1,2,3] and so on.

def rotateArray(arr: list, k: int) -> list:
    # Write your code here.
    # list1 = arr[:k]
    # list2 = arr[k:]
    # list2.extend(list1)
    # return list2
    def reverse(start,end):
        while start<end:
            arr[start],arr[end] = arr[end],arr[start]
            start += 1
            end -=1
    
    n = len(arr)
    d = k % n #if we have let's say 101 to rotate, then we only rotate it 1 time not 101 times
    reverse(0,n-1)  #whole list reversed
    print('first',arr,d,k)
    reverse(0,n-d-1) #Just reverse at same position upto k element
    print('2nd',arr)
    reverse(n-d,n-1) #now rotate the last pending element
    
    return arr

k = 3
rotated_a = [1,2,3,4,5]
print(rotateArray(rotated_a,k))