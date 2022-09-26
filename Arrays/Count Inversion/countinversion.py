def mergesort(arr):

  return mergeSort(arr,0,len(arr)-1) #recursion function

def mergeSort(arr,low,high):
  
  if low >= high: #base condition for recursion
    return 0
  
  mid = (low+high)//2
  # print(mid,'mid')
  left_data = mergeSort(arr,low,mid) #partion of left data and count the inversion upto mid point
  # print("left_data ",left_data)
  right_data = mergeSort(arr,mid+1,high) #partion of right data and count the inverison upto right point
  # print("right_data ",right_data)
  merge_both = merge(arr,low,mid,high) #merge both merge points
  # print("both ",merge_both)

  total_count = left_data + right_data + merge_both

  return total_count 


def merge(arr,low,mid,high):
  i = low    #low part
  j = mid+1 #low part for right parition
  k = 0     #k id 0 for temp array lengh
  count = 0
  temp = [0 for i in range(high-low+1)] #make a temp array for put the both merge data
  print(i,j,mid,len(temp))

  while i <= mid and j <= high:
    # print(i,j,'ij')
    if arr[i] < arr[j]: #if left data is less than put into temp
      temp[k] = arr[i]
      i += 1
      k += 1
      # print(temp,'temp1')
    else:
      temp[k] = arr[j] #if right data is less than put into temp
      j += 1
      print(mid,i) 
      count += mid -i +1 #because inversion happen when right array data move to first so 
      k += 1
      # print(temp,'temp2')
    
  
  while i <= mid: #bache hue element dal do temp m 
      temp[k] = arr[i]
      i += 1
      k += 1
      # print(temp,'temp1')
  
  while j <= high:
    temp[k] = arr[j]
    j += 1
    k += 1
    # print(temp,'temp2')
  
  print(temp,count,low,high)
  # Copy the sorted subarray into Original array
  k = 0
  for z in range(low,high+1):
    arr[z] = temp[k]
    k += 1

  
  # print(arr)

  
  return count

arr = [1, 20, 6, 4, 5] #[4, 2, 1,3] #[3,8]
#arr = [3, 8, 6, 4, 2, 1]
mergesort(arr)
