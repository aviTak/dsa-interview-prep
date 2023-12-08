list1 = [1, 2, 3, 6, 9, 12, 11, 10, 7, 4, 5, 8,0]

#Brute force approach using a single loop: Time complexity = O(n), Space complexity = O(1), Total comparison count in the worst case = 2(n-1).
# def minmax(arr):
#     min = arr[0]
#     max = arr[0]
#     for i in range(1,len(arr)):
#         if arr[i] < min:
#             min = arr[i]
#         elif arr[i] > max:
#             max = arr[i]
#     dict = {'min':min,'max':max}
#     return dict
# Using divide and conquer: Time complexity = O(n), Space complexity = O(logn), Total comparison count = 3n/2 - 2 (If n is a power of 2).
# def findminmax(arr,l,r):
#     min = 0
#     max = 0
#     print(arr,l,r)

#     if l==r:
#         max = arr[l]
#         min = arr[r]
#     elif l+1 == r:
#         if arr[l] < arr[r]:
#             max = arr[r]
#             min = arr[l]
#         else:
#             max = arr[l]
#             min = arr[r]
#     else:
#         mid = l+(r-l)//2
#         leftminmax = findminmax(arr,l,mid)
#         rightminmax = findminmax(arr,mid+1,r)
#         print(arr)

#         if leftminmax[0] < rightminmax[0]:
#             max = rightminmax[0]
#         else:
#             max = leftminmax[0]
        
#         if leftminmax[1] < rightminmax[1]:
#             min = leftminmax[1]
#         else:
#             min = rightminmax[1]
        

#     maxmin = [max,min]
#     return maxmin
def findminmax(arr,n):
    max = 0
    min = 0
    i = 0
    if n%2 !=0:
        print('n',n)
        max = arr[0]
        min = arr[0]
        i = 1
        print(f"{max=},{min=}")
    else:
        print('iii',i)
        if arr[0]<arr[1]:
            max = arr[1]
            min = arr[0]
        else:
            max =arr[0]
            min = arr[1]

        i = 2
        print(f"data-{max=},{min=},{i}")
    
    while i < n:

        if arr[i] < arr[i+1]:
            if arr[i] < min:
                min = arr[i]
            if arr[i+1]<max:
                max = arr[i+1]
        
        else:
            if arr[i]>max:
                max = arr[i]
            if arr[i+1] < min:
                min = arr[i+1]
        
        i+=2
        print('i',i)
        print(f"while -- {max=},{min=}")
    
    min_max = [max,min]
    return min_max





data = [6,7,1,2,0,5,2]
# l = 0
# r = len(data)-1
# print(findminmax(data,l,r))
# data = [1,2,0,4,-1,6]
print(findminmax(data,len(data)))
