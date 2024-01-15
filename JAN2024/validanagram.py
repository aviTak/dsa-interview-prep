def anagram(s,t):
    if len(s) != len(t):
        return False
    dict1 = defaultdict(int)
    print(dict1)
    for i in s:
        dict1[i] += 1

    for j in t:
        dict1[j] -=1
    
    for value in dict1.values():
        if value != 0:
            return False
    
    return True
    
    
    



s = "anagram" 
t = "nagaram"
print(anagram(s,t))