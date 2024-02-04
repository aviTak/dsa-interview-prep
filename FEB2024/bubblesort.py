#bubble sort

def bubbleSort(arr):
    n = len(arr)
    for i in range(n):
        elment_swap = False
        print(i,"N",n,"next",n-i-1)
        for j in range(n-i-1): #this loop alway be less than total length of n and we are also decrese 1 more becaue next step have j+1

            if arr[j] > arr[j+1]:
                arr[j],arr[j+1] = arr[j+1],arr[j]
                elment_swap = True
        if elment_swap != True:
            break


    return arr

# arr = [7,2,5,9,8,3,1]
arr = [1,2,3,4,5,6]
print(bubbleSort(arr))