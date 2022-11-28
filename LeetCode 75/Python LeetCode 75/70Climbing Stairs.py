#70. Climbing Stairs
class Solution(object):
    def climbStairs(self, n):
        """
        :type n: int
        :rtype: int
        """
        if n == 1 or n == 2:
            return n
        dp  = [0 for i in range(n)]
        dp[0] = 1
        dp[1] = 2
        for i in range(2,n):
            dp[i] = dp[i-1] + dp[i-2]
        
        return dp[n-1]
#         if n == 1:
#             return 1
#         if n == 2:
#             return 2
        
#         return self.climbStairs(n-1) + self.climbStairs(n-2)
        
        
