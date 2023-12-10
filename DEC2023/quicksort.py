#quick sort
def swap(a,b):
    a,b = b, a
def partion(arr,low,high):
    pi = arr[high]
    i = low-1
    print(f"{pi}=={i}")
    for j in range(low,high):
        if arr[j] < pi: 
            i += 1
            print(arr[j],arr[i])
            arr[j],arr[i] = arr[i],arr[j]
            print(arr,'swap')

        arr[high],arr[i+1] = arr[i+1],arr[high]
    print(arr,pi)
    return i+1

def quicksort(arr,low,high):
    if low < high:
        partion_index = partion(arr,low,high)
        print(partion_index,'partion_index')
        quicksort(arr,low,partion_index-1)
        quicksort(arr,partion_index+1,high)
    return arr
    

arr = [10, 7, 8, 9, 1, 5]
n = len(arr)-1
print(n)
quicksort(arr,0,n)
print(arr,"final")