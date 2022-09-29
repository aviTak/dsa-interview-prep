#Segregate 0s and 1s in an array
def segregate0and1(arr,n):
  i= 0
  j = n-1
  mid = 0
  while(i<=j):
    if arr[i] == 0:
      arr[i],arr[mid] = arr[mid],arr[i]
      mid += 1
    i+=1
  return arr      




# Driver function
arr = [0, 1, 0, 1, 1, 1,0,1,0]
n = len(arr)
     
segregate0and1(arr, n)
