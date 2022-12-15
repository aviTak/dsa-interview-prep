#binary to decimal
def btd(binary): #binary = 1001

  decimal = 0
  i = 0
  while binary != 0:
    dec = binary % 10  #dec = 1
    decimal = decimal + dec * pow(2,i) #decimal = 0 + 1*2
    binary = binary // 10 #binary = 100
    i += 1

  return decimal 

print(btd(10000))
