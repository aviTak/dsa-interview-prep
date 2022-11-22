#409. Longest Palindrome
class Solution(object):
    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: int
        palidrome formula = sum(all of even count)+(sum(allof odd-1))+1)
        """
        dict1 = {}
        for i in range(len(s)):
            if s[i] not in dict1:
                dict1[s[i]] = 1
            else:
                dict1[s[i]] += 1
      # print(dict1)
        result = 0
        oddFlag = False
        for key,value in dict1.items():
            if value % 2 == 0:
                result += value
                # print(result,'r')
            else:
                oddFlag = True
                result += (value-1)
                # print(result,'oo')

        if oddFlag == True:
            result += 1

        return result
        
