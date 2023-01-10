arr=[231,430,230,301,232,402,400,300,500,499,501]
output = [[230,231,232],[321,322]]

def subsetData(arr):
  n = len(arr)
  #sorting a list
  for i in range(n):
    for j in range(i+1,n):
      if arr[i]>arr[j]:
        arr[i],arr[j] = arr[j],arr[i]
  set1= []
  list2 = []
  print(arr)
  for i in range(1,n):
    if (arr[i] - arr[i-1]) == 1:
      set1.append(i)
      # print((arr[i] - arr[i-1]),arr[i],arr[i-1],set1)
    else:
      # continue
      # print(set1)
      if set1:
        m = set1[0]
        n = set1[-1]
        list2.append([arr[i] for i in range(m-1,n+1)])
        set1.clear()
  else:
    if set1:
      m = set1[0]
      n = set1[-1]
      list2.append([arr[i] for i in range(m-1,n+1)])
      set1.clear()

  return list2

print(subsetData(arr))
