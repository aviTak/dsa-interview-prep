class Solution(object):
    def findAnagrams(self, s, p):
        """
        :type s: str
        :type p: str
        :rtype: List[int]
        """
        ans = []
        max_char = 256
        p_size = len(p)
        s_size = len(s)
        if s_size < p_size:
          return ans
        
        count_P = [0 for i in range(max_char)]
        count_Window = [0 for i in range(max_char)]

        for i in range(p_size):
          count_P[ord(p[i])] += 1
          count_Window[ord(s[i])]  +=1
        # print("count_P--",count_P)
        
        # Traverse through remaining# characters of pattern
        for i in range(p_size,s_size):
          print(i,p_size)
          		# Compare counts of current# window of text with# counts of pattern[]
          if self.is_compare(count_P,count_Window,max_char):
            ans.append(i-p_size)
            print(ans)
          # Add current character to current window
          count_Window[ord(s[i])] +=1
          # print("count_Window2--",count_Window)
          # Remove the first character of previous window
          count_Window[ord(s[i-p_size])]-=1
          # print("count_Window--",count_Window)
        
        if self.is_compare(count_P,count_Window,max_char):
          ans.append(s_size-p_size)
        return ans


    def is_compare(self,arr1,arr2,max_char):
        for i in range(max_char):
          if arr1[i] != arr2[i]:
            return False
        return True





        # ans = []
        # max_char = 26
        # lists = [0 for i in range(max_char)]
        # listp = [0 for i in range(max_char)]
        # window_size = len(p)
        # s_size = len(s)
        # if s_size < window_size:
        #   return ans
        # left = 0 
        # right = 0
        # while right < window_size:
        #   listp[ord(p[right])-ord('a')] += 1
        #   lists[ord(s[right]) - ord('a')] +=1
        #   right += 1
        
        # right -=1
        # while(right < s_size):
        #   if listp == lists:
        #     ans.append(left)
        #   right += 1
        #   if right != s_size:
        #     lists[ord(s[right]) - ord('a')] += 1
        #   lists[ord(s[left])-ord('a')] -=1
        #   left += 1
        #   print(ans)
          
        # return ans

# s,p = "abab","ab"
s,p = "cbaebabacd", "abc"
sol = Solution()
sol.findAnagrams(s,p)
