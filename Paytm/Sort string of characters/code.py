# Sort string of characters

# m = sorted('pooja') #python technique
# hello = ''.join(m)
# hello

def sortString(arr):
  max_char = 26
  count_data = [0 for i in range(max_char)] #count upto 26 digit
  for i in range(len(arr)):
    # print(ord(arr[i])-ord('a'))
    count_data[ord(arr[i])-ord('a')] += 1 #enter the data in count_data list 
  
  # print(count_data)
  
  for i in range(max_char): #loop upto 26 digit 
    for j in range(count_data[i]): #loop upto count data value digit
      print(chr(ord('a') + i), end = "") #print the value in char

sortString("pooja")
