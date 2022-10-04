#longest plaidrome substring

# A utility function to print a
# substring str[low..high]
import sys

def printSubStr(st, low, high) :
    sys.stdout.write(st[low : high + 1])
    sys.stdout.flush()
    return ''

def LongestPlaidromeSubstring(arr,n):
  rows, cols = (n, n)
  pal =[[0 for x in range(cols)] for y in range(rows)] #row to col from matrix size, intially all are 0
  # print(pal)
  pal[0][0] = 0 #
  x = -1
  y=-1
  max = 1

  for i in range(1,n): #this loop work upto 2 length
    pal[i][i] = 1; #For length = 1, it will be 1
    # print(arr[i],arr[i-1])
    if arr[i] == arr[i-1]: #for 
      pal[i-1][i] = 1
      if max < 2: #just check upto 2 size
        max = 2
        x = i-1
        y = i
    else:
      pal[i-1][i] = 0
  # print(pal)
  for size in range(3,n): #for 3 length to n -- row
    for start in range(n-size+1): #for column 
      end = start+size-1
      if arr[start] == arr[end] and pal[start+1][end-1]==1: 
        pal[start][end]=1
        if max < size:
          max = size
          x = start
          y = end
    # print(x,y)
  
  print(printSubStr(arr,x, y+1))




a = "aaaabbaaa";
LongestPlaidromeSubstring(a,len(a))
