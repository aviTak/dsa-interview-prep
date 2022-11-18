#Program to find running sum of 1d array in Python
def runningSum(array,n):
  for i in range(1,n):
    array[i] += array[i-1] 
  return array
