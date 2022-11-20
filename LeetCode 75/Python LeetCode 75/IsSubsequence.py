# 392. Is Subsequence
#A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of 
# the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
def isSubsequence(s, t): #solve using two pointer approch
  n = len(s)
  m = len(t)
  i = 0 #for s string
  j = 0 #for t string
  while i<n and j<m:
    if s[i] == t[j]:
      i += 1 #increase i pointer when string is matched
    j+= 1 #j pointer always increases
  
  return i==n

s = "abcz"
t = "ahbgdc"
print(isSubsequence(s, t))
