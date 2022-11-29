# 62. Unique Paths
class Solution(object):
    def uniquePaths(self, m, n):
        """
        :type m: int
        :type n: int
        :rtype: int
        """
        rows, cols = (m, n)
        dp = [[0]*cols]*rows #make a grid and store the value for each move 

        for i in range(m):
            for j in range(n):
                if i == 0 or j == 0: #for intial base condition
                    dp[i][j] = 1
                else:
                    dp[i][j] = dp[i][j-1]  + dp[i-1][j] #we follow approch as top to down approch
    
        return dp[m-1][n-1] #return end of point of grid
    
#Input: m = 3, n = 2
# Output: 3
# Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
# 1. Right -> Down -> Down
# 2. Down -> Down -> Right
# 3. Down -> Right -> Down
# [[1,2]
# [4,5]
# [6,7]]
