def removeduplicate(arr):
    dict1 = {}

    for i,v in enumerate(arr):
        if v in dict1:
            dict1[v] += 1
        else:
            dict1[v] = 1
    
    print(dict1)
    arrs= []
    for i,v in dict1.items():
        arrs.append(i)
    
    return arrs
        



arr = [2, 3, 3, 3, 6, 9, 9]   
print(removeduplicate(arr)) 