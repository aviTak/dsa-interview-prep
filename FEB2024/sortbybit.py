# 1356. Sort Integers by The Number of 1 Bits

# ou are given an integer array arr. Sort the integers in the array in
# ascending order by the number of 1's in their binary representation and 
# in case of two or more integers have the same number of 1's you have to sort them in ascending order.

# Return the array after sorting it.

 

# Example 1:

# Input: arr = [0,1,2,3,4,5,6,7,8]
# Output: [0,1,2,4,8,3,5,6,7]
# Explantion: [0] is the only integer with 0 bits.
# [1,2,4,8] all have 1 bit.
# [3,5,6] have 2 bits.
# [7] has 3 bits.
# The sorted array by bits is [0,1,2,4,8,3,5,6,7]

def decimaltobinary(num):
    if num < 1:
        return ''
    else:
        return decimaltobinary(num // 2) + str(num % 2)
    # if num >= 1:
    #     # print(num,'num')
    #     return decimaltobinary(num // 2) + str(num % 2)
        

def count1s(data):
    count = 0
    for i in data:
        if i == "1":
            count += 1
    return count

def sortByBits(arr):
    dict1 = {}
    for i in arr:
        data = decimaltobinary(i)
        m = count1s(data)
        dict1[i] = m
    return dict1

def sort_dict(m):
    for key,value in dict1.items():
        print(key)
    


# print(decimaltobinary(4))
# print(count1s("110"))

arr = [0,1,2,3,4,5]
dict1 = sortByBits(arr)
# print(sort_dict({0: 0, 1: 1, 2: 1, 3: 2, 4: 1, 5: 2}))
print(dict1)
m = {k: v for k, v in sorted(dict1.items(), key=lambda item: item[1])}
print(m)
sort_dict(m)


