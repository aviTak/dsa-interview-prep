#reverse a group of link list
list1 = [1,2,3,4,5,6,7,8,9]
rlist = [3,2,1,6,5,4,9,8,7]
def reverseArrayGroup(arr,k):
    n = len(arr)
    i = 0
    for i in range(0,n-1,i+k):
        start = i
        end = min(i+k-1,n-1)
        print(start,end)
        while start<end:
            arr[start],arr[end] = arr[end],arr[start]
            start+=1
            end -=1
    return arr

print(reverseArrayGroup(list1,4))

