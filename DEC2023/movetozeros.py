# def move_all_zeros(arr):
#     n = len(arr)
#     dict = {}
#     list1 = []
#     for i in range(n):
#         if arr[i] !=0:
#             list1.append(arr[i])
#         else:
#             if dict.get(arr[i],None):
#                 dict[arr[i]] += 1
#             else:
#                 dict[arr[i]] = 1
#     # return dict
#     count = dict[0]
#     # print(count)
#     for i in range(count):
#         # list1.append(0) #move at end of the list
#         list1.insert(i,0) #move at starting of list
#     return list1
# def move_all_zeros(arr):
#     n = len(arr)
#     output = [0]*n
#     j = 0
#     for i,x in enumerate(arr):
#         if x != 0:
#             output[j] = x
#             j += 1
#             print(output,j,i)
#     return output
def move_all_zeros(arr):
    j = 0
    for i in range(len(arr)):
        if arr[i] !=0:
            arr[i],arr[j] = arr[j],arr[i]
            j+= 1
    
    return arr


data =[4, 8, 0, 0, 2, 0, 1, 0] #[0,0,1,2,0,3,4]
print(move_all_zeros(data))