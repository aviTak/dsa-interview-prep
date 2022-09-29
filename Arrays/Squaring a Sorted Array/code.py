# Input: [-2, -1, 0, 2, 3]
# Output: [0, 1, 4, 4, 9]
def squareArray(arr):
  i = 0
  j = len(arr)-1
  s = [0 for x in range(j+1)]
  k = len(arr)-1
  
  while i <= j:
    f = arr[i]
    l = arr[j]
    if f**2 < l**2:
      s[k] = l**2
      j -= 1
      # k += 1
      # print(square)
    if f**2 >= l**2:
      s[k] = f**2
      i += 1
    k -= 1
  return s

arr = [-2, -1, 0, 2, 3]
squareArray(arr)

# def squareArray(arr):
#   i = 0
#   j = len(arr)-1
#   k = 0
#   square = [0 for x in range(j+1)]
#   # print(square)

#   while i <= j:
#     f = arr[i]
#     l = arr[j]
#     if f**2 < l**2:
#       square[k] = l**2
#       j -= 1
#       # k += 1
#       # print(square)
#     if f**2 >= l**2:
#       square[k] = f**2
#       i += 1
#     k += 1
#   print(square)
#   return reverseArray(square)


# def reverseArray(arr):
#   i = 0
#   j = len(arr)-1

#   while i<j:
#     arr[i],arr[j] = arr[j],arr[i]
#     i+=1
#     j -= 1
#   # print(arr)
#   return arr

# arr = [-2, -1, 0, 2, 3]
# squareArray(arr)
