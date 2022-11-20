#Program to find running sum of 1d array in Python
def runningSum(array,n):
  rs = array[0]
  # list1 = [rs]
  for i in range(1,n):
    array[i] += array[i-1]
    # print(array)
    # list1.append(array[i])
  return array

array = [8,3,6,2]

# def runningSum(array,n):
#   list1 = [array[0]]
  
#   if n > 0:  
#     m = array[n-1]+runningSum(array,n-1)
#     list1.append(m)
#   return list1


print(runningSum(array,len(array)))
