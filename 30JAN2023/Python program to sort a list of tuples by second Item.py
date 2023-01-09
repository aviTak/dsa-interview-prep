list1 = [(57,3),(1,2),(3,4),(76,56)]
#  m = [5,6,1,2]
# m.sort()
# print(m)

#  sorted_list1 = 
# for i in range(len(list1)):
#   for j in range(i+1,len(list1)):
#     m = list1[i][len(list1[i])-1]
#     n = list1[j][len(list1[j])-1]
#     if m > n:
#       list1[i],list1[j] = list1[j],list1[i]
# def sortListTouple(list1):
def sorte(list1):
  for x in range(len(list1)):
    # print(list1[x][len(list1[x])-1])
    m = lambda x: list1[x][len(list1[x])-1]
    yield m(x)


key = lambda x: x[len(x)-1]
print(key(list1))
key1 = sorte(list1)
print(key1.__next__())
print(key1.__next__())
print(key1.__next__())

print(sorted(list1,key = lambda x: x[len(x)-1]))
# print(list1)
