# Given a Roman numeral, write a program to find its corresponding decimal value. Roman numerals are represented by seven different symbols:

def integervalue(c):
    if c == "I":
        return 1
    elif c == "V":
        return 5
    elif c == "X":
        return 10
    elif c == "L":
        return 50
    elif c == "C":
        return 100
    elif c == "D":
        return 500
    elif c == "M":
        return 1000
def romantostring(string):
    n = len(string)
    data = 0
    for i in range(n):
        current = integervalue(string[i])
        if i+1 < n:
            next = integervalue(string[i+1])
            print(f'next-{next},current-{current},data-{data}')
            if current >= next:
                data = data + current
            else:
                data = data - current
        else:
            data = data + current
        # print(string[i],current,data)
    
    return data


# print(romantostring("XL"))
# print(romantostring("MCMIV"))
print(romantostring("MCDXCIV"))
