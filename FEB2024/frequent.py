#find most frequent element in array
arr = [2, 12, 1, 2, 8, 1, 1, 1, 8, 2]
def FrequentArray(arr):
    dict1 = {}
    element = 0
    maxi = 0
    for ele in arr:
        if ele in dict1:
            dict1[ele] += 1
            if maxi < dict1[ele]:
                maxi = dict1[ele]
                element = ele
            else:
                element = min(element,ele)
        else:
            dict1[ele] = 1
            element = ele
    
    print(dict1)

    
    return element

        
FrequentArray(arr)
    # for key,value in dict1.items():
    #     count_freq = value
    #     if count_freq > maxi:
    #         element = key
    #         maxi = count_freq
    #     elif count_freq == maxi:
    #         element = min(element,key)