class Solution(object):
    def search(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: int
        """
        low = 0
        high = len(nums)-1

        while low <= high:
            mid = low + (high-low)//2# round down (eliminates integer overflow)
            if nums[mid] == target:
                return mid

            elif nums[mid]<target:
                low = mid+1
            elif nums[mid] > target:
                high = mid - 1

        return -1
        
#         def binarySearch(nums,target,low,high):

#   mid = low + (high-low)//2

#   if nums[mid] == target:
#     return mid
  
#   if nums[mid] < target:
#     return binarySearch(nums,target,mid+1,high)
#   else:
#     return binarySearch(nums,target,low,mid-1)

# nums = [-1,0,3,5,9,12]
# target = 9
# low = 0
# high = len(nums)-1
# binarySearch(nums,target,low,high)
