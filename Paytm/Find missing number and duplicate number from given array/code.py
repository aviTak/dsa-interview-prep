#Find missing number and duplicate number from given array.
def missingDuplicateNumber(arr,n):
  # sum_of_number = (n *(n+1))//2
  # sum_of_arr = sum(set(arr))
  # sum_of_data = sum(arr)
  # print(sum_of_arr,sum_of_data,sum_of_number)
  # missing_number = sum_of_number - sum_of_arr
  # repleat = sum_of_data - sum_of_arr

  # print(missing_number,repleat)
  map = {}
  for i in arr:
    if i not in map:
      map[i] = 1
    else:
      print("repeated number : ",arr[i])
  
  # print(map)
  for i in range(1,n+1):
    if i not in map:
      print("missing number : ", i)



arr = [4, 3, 6, 2, 1, 1]
missingDuplicateNumber(arr,len(arr))
