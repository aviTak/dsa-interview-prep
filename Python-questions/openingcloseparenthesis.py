#Write a python function that, given a string , along with the position of an opening parenthesis in the string, finds the corresponding closing parenthesis.
#"Sometimes (when I nest them (my parentheticals) too much (like this (and this))) they get confusing."
#Example: if the example string above is input with the number 10 (position of the first parenthesis), the output should be 79 (position of the last parenthesis).

def countPar(s,number):

    list1 = []
    dict1 = {}
    list2 = []
    latest_index = -1

    for i in range(len(s)):
        if s[i] == '(':
            list1.append(i)
            latest_index = i
            # dict1[s[i]] = i
            dict1[i] = s[i]
        if s[i] == ')':
            m = list1.pop()
            if m == number:
                print(i)
            # dict1[i] = s[i]
            # list2.append(i)
    
    # print(list2)
    # print(dict1)
            
input = "Sometimes (when I nest them (my parentheticals) too much (like this (and this))) they get confusing."
print(countPar(input,57))
