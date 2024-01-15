nums = [2,11,15,7]
target = 9
# output = [0,1]

def twoSum(arr, target):
    n = len(arr)
    # for i in range(n):
    #     for j in range(n):
    #         if arr[i] + arr[j] == target:
    #             return [i,j]
    dict1 = {}

    for i in range(n):
        complement = target - arr[i]
        print(complement)
        if complement in dict1:
            return [i,dict1[complement]]
        dict1[arr[i]] = i


print(twoSum(nums,target))
