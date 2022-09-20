#Remove Duplicates in sorted array using 2 pointer approach 
def remove_duplicates(arr):
  # index of the next non-duplicate element
  next_non_duplicate = 1 #assume this next non duplicate

  i = 0
  while(i < len(arr)):
    # print('i--',i,'--next_non_duplicate--',next_non_duplicate-1,'--', arr[i],'--',arr[next_non_duplicate-1],'--',arr)
    if arr[next_non_duplicate - 1] != arr[i]: #compare with perious one of non_duplicate element if not same than swap the element
      arr[next_non_duplicate] = arr[i]        #compare with latest not_duplicate element
      next_non_duplicate += 1
    i += 1

  return next_non_duplicate


# def main():
print(remove_duplicates([2, 3, 3, 3, 6, 9, 9]))
