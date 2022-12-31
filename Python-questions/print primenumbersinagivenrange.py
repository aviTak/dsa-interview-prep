#How much prime number in given range
import time

def primeNumber(n):
  t1 = time.time()
  for i in range(1,n):
    for j in range(2,i):
      # print(i,j,i//2)
      if i%j == 0:
        break
    else:
      print(i,'pe')
  t2 = time.time()
  print(t2-t1)

print(primeNumber(20))
