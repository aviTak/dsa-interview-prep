#kadne's array
def kadenAlgo(arr):
  sum = 0
  ans = float("-inf") #for ans of max subarray sum
  start_index = 0
  end_index   = 0
  storage_index = 0 #storage upto get max right sum

  for i in range(len(arr)-1):
    sum = sum + arr[i]
    if sum < 0 : #if sum are less than zero than all sum become 0 ans storage index make increase by one
      sum = 0
      storage_index = i+1
    if ans < sum : #if ans is less than sum than ans become sum 
      ans = sum
      end_index = i
      start_index = storage_index
  print(start_index, end_index)
  return ans

# a = [-2, -3, 4, -1, -2, 1, 5, -3]
a =   [5, -3, 2, -7, 1, -2, 4, 1]
kadenAlgo(a)
