#how to get one digit number in python
number = 5678
def getSum(n):
  print(n)
  if n == 0:
    return 0
  if n<9:
    return n
  else:
    m = (n%10) + getSum(n//10)
    if m > 9:
      return m%10 + getSum(m//10)
    else:
      return m
    

getSum(number)
