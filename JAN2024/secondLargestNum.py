# Second largest element in the array
def secondLargestElement(arr):
    n = len(arr)
    # arr.sort(reverse = True)
    if n < 2:
        return -1
    flargest = slargest = float('-inf')
    #First approach with 2 for loop
    # for i in range(n):
    #     flargest = max(flargest,arr[i])
    
    # for j in range(n):
    #     if arr[j] != flargest:
    #         slargest = max(slargest,arr[j])
    
    # if slargest == float('-inf'):
    #     return f"no second largest exist"
    # else:
    #     return f"second largest element {slargest}"
    
    # return -1
    #2nd approach with one for loop
    for i in range(n):

        if arr[i] > flargest:
            slargest = flargest
            flargest = arr[i]
        
        elif arr[i]> slargest and arr[i] != flargest:
            slargest = arr[i]
        
    return [slargest,flargest]
        
arr =[8,6,5] #[2, 4, 5, 6, 8]
print(secondLargestElement(arr))