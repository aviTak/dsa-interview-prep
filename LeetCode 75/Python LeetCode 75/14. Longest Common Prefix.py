class Solution(object):
    def longestCommonPrefix(self, strs):
        """
        :type strs: List[str]
        :rtype: str
        """
        res = ""
        for i in range(len(strs[0])):
            for s in strs:
                # print(i,len(s),s[i],strs[0][i],s)
                if  i == len(s) or s[i]!=strs[0][i]:
                    return res
            res += strs[0][i]
        return res
   
  
  
#   def longestCommonPrefix(strs):
#     if not strs:
#         return ""
#     print(list(zip(*strs)))   
#     for i, letter_group in enumerate(zip(*strs)):
#       print(i,letter_group,len(set(letter_group)),set(letter_group),strs[0][:i])
#       if len(set(letter_group)) > 1:
#         print(len(set(letter_group)),set(letter_group),strs[0][:i],59599)
#         return strs[0][:i]
#     else:
#       print(min(strs),5554)
#       return min(strs)


# longestCommonPrefix(strs)
