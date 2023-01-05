class Solution(object):
    def productExceptSelf(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        n = len(nums)
        product = [0]*n
        # new = [0]*n
        # for i in range(n):
        #   product = 1
        #   for j in range(n):
        #     if i == j:
        #       continue
        #     product = product * nums[j]
        #   new[i] = product
      
        # return new
        prefix = [0]*n
        postfix = [0]*n

        prefix[0] = 1
        for i in range(1,n):
          prefix[i] = prefix[i-1] * nums[i-1]
        
        postfix[n-1] = 1
        for j in range(n-2,-1,-1):
          # print(j,nums[j+1],postfix[j+1],n-2,n-1)
          postfix[j]= postfix[j+1] * nums[j+1]
        
        # print(prefix,postfix)
        for k in range(n):
          product[k] = prefix[k] * postfix[k]
        
        return product
