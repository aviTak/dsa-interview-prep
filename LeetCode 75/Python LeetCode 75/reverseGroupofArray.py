#reverse a group of array
def reverseGroup(arr,k):
  i = 0
  n = len(array)
  while i < n:
    left = i
    right = min(i+k-1,n-1)
    print(left,right)
    while left < right:
      array[left],array[right] = array[right],array[left]
      left +=1
      right-=1
    i += k
  return array

array = [1,2,3,4,5,6,7,8]
print(reverseGroup(array,3))
