#binary search

# def binarysearch(arr,X): 
#     low = 0
#     high = len(arr)-1
#     while low <= high:
#         mid = low + (high-low)//2
        
#         print(mid,arr[mid])
        
#         if arr[mid] == X:
#             return mid
        
#         if arr[mid] < X:
#             low = mid + 1
#         else:
#             high = mid -1
    
#     return -1
def binarysearch(arr,X,low,high):
    while low <= high:
        mid = low + (high-low)//2
        if arr[mid] == X:
            return mid
        if arr[mid] < X:
            return binarysearch(arr,X,mid+1,high)
        else:
            return binarysearch(arr,X,low,mid-1)
    return -1

arr = [1,2,3,4,5,6]
low,high = 0,len(arr)-1
print(binarysearch(arr,5,low,high))