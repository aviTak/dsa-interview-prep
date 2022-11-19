list1 = ['c','e','a','l','z','m','c','a','a']
def sortedList(list1):
  max_char = 26
  count = [0 for i in range(max_char)] #char length 26 hoti h
  for i in range(len(list1)):
    count[ord(list1[i])-ord('a')]+= 1 #count use krna hai ki list m every character kitni baar aa rha hai
  
  for i in range(0,max_char,1): #hme 26 char length tk for loop chlna hai and than than jo count digit pr loop chlna tha
    for j in range(0,count[i]):
      print((chr(ord('a')+i)),end=" ")
  # return mst

sortedList(list1)
