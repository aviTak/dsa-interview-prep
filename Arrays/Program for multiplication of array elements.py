def multiple(array,n):
    print('n',n,array[n])
    if n == 0:
        return array[n]
    else:
        return (array[n] * multiple(array,n-1))


array = [1,2,3,4,5,6]
print(multiple(array,len(array)-1))

# arr = [1,2,3,4]

# pro = 1
# for i in range(len(arr)):
#     pro = pro * arr[i]

# print(pro)
