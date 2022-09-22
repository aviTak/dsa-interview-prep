#sort 0, 1,2s
def countingNumber(arr):
  dict1 = {}
  count_0 = 0
  count_1 = 0
  count_2 = 0
  count = 1
  for i in range(len(arr)):
    if arr[i] == 0:
      count_0 += 1
    elif arr[i] == 1:
      count_1 += 1
    else:
      count_2 += 1
#   print(count_0,count_1,count_2)
  
  for i in range(count_0):
    # print(i)
    arr[i] = 0
  
  for j in range(count_0,count_0+count_1):
    # print(j,'j')
    arr[j] = 1
  
  for k in range(count_0+count_1,len(arr)):
    # print(k,'k')
    arr[k] = 2
   
 return arr


#Sort an array of 0s, 1s and 2s | Dutch National Flag problem
#when index ki value 0 hai too hm swap krenge mid and high value ko and incerase krenge mid and low pointer ko,
#  1 hai too just increase krenge mid ko and high swap between high and mid and just increase krenge high ko 
def countingNumber2(arr):
  low = 0
  mid = 0
  high = len(arr)-1

  while(mid<=high):
    if arr[mid] == 0:
      arr[low],arr[mid] = arr[mid],arr[low]
      low += 1
      mid +=1
    elif arr[mid] == 1:
      mid += 1
    elif arr[mid] == 2:
      # print(333)
      arr[high],arr[mid] = arr[mid],arr[high]
      high -= 1
    # print(arr)
  
  return arr


arr = [0, 2, 2 , 0 ,  1 , 1,  0 , 1 , 0,  0]
countingNumber2(arr)
      
