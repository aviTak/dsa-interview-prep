import functools
list2 = [1,2,3,3,5]
m = functools.reduce(lambda x,y:x+y,list2,5)
print(m)
