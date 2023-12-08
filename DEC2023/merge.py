#merge_sort
def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr)//2 #give the mid point
        left = arr[:mid]
        right = arr[mid:]
        merge_sort(left) #merge sort for left part
        merge_sort(right) #merge sort for right part
        print(f"{left=} -- {right=}")

        i = j = k = 0
        while i < len(left) and j < len(right):
            if left[i] <= right[j]:
                arr[k] = left[i]
                i+= 1
            else:
                arr[k] = right[j]
                j+= 1
            k+= 1
        
        while i < len(left): #if data have just left side
            arr[k] = left[i]
            i+= 1
            k+= 1
        
        while j < len(right): #if data have just right side
            arr[k] = right[j]
            j+= 1
            k+=1

data =[1] #[1, 2, 3, 6, 9, 12, 11, 10, 7, 4, 5, 8,0]
merge_sort(data)
print(data)
# print(list1)
    