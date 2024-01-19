def checkPrime(num):
    if num == 1:
        return 'not prime'
    elif  num > 1:
        for i in range(2,num):
            print(num % i)
            if (num % i) == 0:
                print(num,'is not prime')
                print(f"{i},{num//i}")
                break
        else:
            return 'prime'
    

checkPrime(407)
