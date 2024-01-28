def ishappynumber(num):
    slow = num
    fast = num 
    while True:
        #Move one step
        slow = findSqurenumber(slow)
        #move two steps
        fast = findSqurenumber(findSqurenumber(fast))
        print(slow,fast,'slow fast')
        if slow == fast:
            break
    return slow == 1


def findSqurenumber(num):
    sum1 = 0
    print(num)
    while num>0:
        digit = num % 10
        sum1 += digit **2
        num = num // 10
        print(f"{digit = },{num=},{sum1=}")
    return sum1

print(ishappynumber(23))