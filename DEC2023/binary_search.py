# def binary_search(X,l,r,key):
#     if l>r:
#         return -1
#     mid = l + (r-l)//2
#     # print(mid,X[mid])
#     if X[mid] == key:
#         return mid
#     if X[mid]>key:
#         return binary_search(X,l,mid-1,key)
#     else:
#         return binary_search(X,mid+1,r,key)
def binary_search(arr,l,r,target):
    
    while l<=r:
        mid = l + (r-l)//2
        if arr[mid] == target:
            return mid
        elif arr[mid]>target:
            r = mid-1
        else:
            l = mid+1
    return -1


arr = [1,2,3,4,5]
n = len(arr)-1
print(binary_search(arr,0,n,4)) 