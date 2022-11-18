#Program for multiplication of array elements
def multiplicationArray(array,n):
  if n == 0:
    return  array[n]
  else:
    return array[n] * multiplicationArray(array,n-1)


array = [1,2,3,4,5]
# print(multiplicationArray(array,len(array)-1))


#brute force technique
def multiplicationArray(array,n):
  m = 1
  for i in range(n):
    m = m * array[i]
  return m

print(multiplicationArray(array,len(array)))
