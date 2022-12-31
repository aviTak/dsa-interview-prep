import sys

def MaxProducttriplet(arr):

  n = len(arr)

  maxA = -sys.maxsize-1
  maxB = -sys.maxsize-1
  maxC = -sys.maxsize-1

  minA = sys.maxsize
  minB = sys.maxsize

  for key,value in enumerate(arr):

    if value > maxA:
      maxC = maxB
      maxB = maxA
      maxA = value
    
    elif value > maxB:
      maxC = maxB
      maxB = value
    
    elif value > maxC:
      maxC = value
    
    if value< minA:
      minB = minA
      minA = value
    elif value < minB:
      minB = value
  # print(minA,minB,maxA)
  product = max(minA*minB*maxA,maxA*maxB*maxC)
  # print(minA*minB*maxA,maxA*maxB*maxC)

  return product

    
  # print(maxA,maxB,maxC)

arr = [ 1, -4, 3, -10, 7, 0 ]

print(MaxProducttriplet(arr))
