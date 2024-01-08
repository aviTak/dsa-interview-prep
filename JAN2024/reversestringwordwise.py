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


#no build in method of python but using slicing 
# def reverseStringWordWise(string): 
#     reversed_string = ""
#     word_start = 0  #start point defined krna hai aage kaam aayega
#     n = len(string)

#     for i in range(n):
#         # print(string[i])
#         if string[i] == " " or i == n-1:
#             if i == n-1:
#                 word_end = i
#             else:
#                 word_end = i-1
            
#             # word_end= i if i== n-1 else i-1
            
#             #Now create the word again
#             word = string[word_start:word_end+1]

#             reversed_string = word + " " + reversed_string  #purani string m starting m word add kr dena hai

#             word_start = i+1
    
#     return reversed_string

#no built in functions of python just normal string but using list

def reverseStringWordWise(string): 
    reversed_string = ""
    list_data = list(string)
    n = len(list_data)

    def reverse_word(start,end):
        while(start<end):
            list_data[start], list_data[end] = list_data[end],list_data[start]
            start += 1
            end -= 1
    
    reverse_word(0,n-1) #reverse the whole string 
    print(list_data)
    #so as we now now I have all the reverse string but again/
    # we need to reverse the string word according to our requirment
    word_start = 0
    for i in range(n):
        if list_data[i] == " " or i == n-1:
            word_end = i if i==n-1 else i-1
            reverse_word(word_start,word_end)
            word_start = i+1
    
    return ''.join(list_data)