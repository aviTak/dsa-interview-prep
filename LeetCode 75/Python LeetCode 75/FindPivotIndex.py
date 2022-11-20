#724. Find Pivot Index
#The pivot index is the index where the sum of all the numbers strictly to the left of the index is equal to the sum of all the numbers strictly to the index's right.

def pivotIndex(nums):  #usingForLoop
  n = len(nums)
  for i in range(len(nums)):
      lefts = 0
      rights =0
      # Check for indexes one by one
      for j in range(i):
        lefts += nums[j]
      
      for j in range(i+1,n):
        rights += nums[j]
      
      # print(lefts,rights)
      if lefts == rights:
        return i
  
  return -1
      
# nums = [1,7,3,6,5,6]
# print(pivotIndex(nums))
# Explanation:
# The pivot index is 3.
# Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
# Right sum = nums[4] + nums[5] = 5 + 6 = 11

def pivotIndexUsingarray_sum(nums):
  n =len(nums)
  leftsum = 0
  rightsum = sum(nums)  #array of sum of all digits
  for i in range(n):
    rightsum -= nums[i] 
    # print(leftsum,rightsum)
    if leftsum == rightsum:
      return i
    leftsum += nums[i]
  
  return -1

nums = [1,7,3,6,5,6]
print(pivotIndexUsingarray_sum(nums))
