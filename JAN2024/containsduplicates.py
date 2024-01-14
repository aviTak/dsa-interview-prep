# 217. Contains Duplicate
# Input: nums = [1,2,3,1]
# Output: true
def containduplicates(arr):
    n = len(arr)
    dict = {}
    for index,value in enumerate(arr):
        if value not in dict:
            dict[value] = 1
        else:
            print(dict[value])
            dict[value] += 1
            return True
    
    return False


nums = [1,2,3,4,5,1,2,1]
print(containduplicates(nums))