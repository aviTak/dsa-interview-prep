#Welcome to Coding Ninjas
#Ninjas Coding to Welcome
#Reverse String Word Wise


#buid in method of python

    # for i in string:
    #     print('i data ',i)  
    # return string[::-1]
    # list1 = string.split(' ')
    # data = list1[::-1] #reversed(list1)
    # # print(id(list1),id(data))
    # return ' '.join(data)
    # data = list1.reverse() 
    # print(list1)
    # return ' '.join(list1)


# 2  Pointer approach

# def reverseStringWordWise(string): 
#     list1 = string.split(' ')
#     high = len(list1)-1
#     low = 0
#     while low < high:
#         list1[low], list1[high] = list1[high],list1[low]
#         low += 1
#         high -= 1
    
#     return ' '.join(list1)