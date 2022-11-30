class Solution(object):
    def characterReplacement(self, s, k):
        """
        :type s: str
        :type k: int
        :rtype: int
        """
        count = {}
        len_s = len(s)
        left = 0
        maxf = 0 #maxium frequency of char
        replace = 0

        
        for right,value in enumerate(s):
          # print(count.get(value,0))
            count[value] = 1 + count.get(value,0) #store the value into count 
            maxf = max(maxf,count[value]) #choose the maxium freq char
          # print(right-left+1)

            while (right-left+1 )- maxf > k: #if k is less than getting value 
                count[s[left]] -=1 #decease the window size
                left +=1  #increase the left pointer for next index

          #if (right-left+1)-maxf is less than or equal to k than choose max value into replacement
            replace = max(replace,right-left+1)
        return replace
