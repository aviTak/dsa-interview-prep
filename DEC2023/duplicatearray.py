# def duplicatearray(arr):
#     dict1 = {}
#     for i in range(len(arr)):
#         if dict1.get(arr[i],None):
#             dict1[arr[i]] += 1
#         else:
#             dict1[arr[i]] = 1
#     return len(dict1)
#if slow == fast than ignore it and increase the fast one else increase slow by 1 and assign arr[slow] = arr[fast]
def duplicatearray(arr):
    slow = 0
    fast = 1
    n = len(arr)-1
    while fast < n:
        if arr[slow] != arr[fast]:
            slow += 1
            arr[slow] = arr[fast]
            print(arr)
        
        fast += 1
    
    return slow+1


arr = [2,2,2,3,3,3,4,4,4,5,5,5] #[2,3,2,3,5,6,2,3,1,3,5,2,2,2]
print(duplicatearray(arr))
