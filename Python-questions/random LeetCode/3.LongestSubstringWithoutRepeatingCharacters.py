class Solution(object):
    def lengthOfLongestSubstring(self, s):
        """
        :type s: str
        :rtype: int
        """
        last_seen = {} #storage for all string with index value 

        start_index = 0
        max_length = 0

        for current in range(len(s)):
            if s[current] in last_seen:
                start_index = max(start_index,last_seen[s[current]]+1) #if char is repeated end the string and make it as start index


            max_length = max(max_length,current-start_index+1) #findout max length between current index to start index
            last_seen[s[current]] = current  #step 1 store the value of string with index

        return max_length
