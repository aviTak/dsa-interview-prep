class Solution(object):
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        store = {}
        
        for key,value in enumerate(nums):
            temp = target - nums[key]
            if temp not in store:
                store[value] = key
            
            else:
                return [store[temp],key]
        return []
                
            
