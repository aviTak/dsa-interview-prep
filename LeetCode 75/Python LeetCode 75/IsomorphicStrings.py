# 205. Isomorphic Strings
# Hash Table

def isIsomorphic(s,t):
  n = len(s)
  m = len(t)
  if m != n: #base condition
      return False
  dict1 = {}
  c = 'a'
  for i in range(n):
    print(i)
    if t[i] not in dict1.values():
      dict1[s[i]] = t[i]
    if s[i] in dict1:
      c = dict1[s[i]]
      print(c,t[i])
      if c != t[i]:
        return False
    else:
        return False
  return True

s = "foo"
t = "bar"
print(isomorphic(s,t))
