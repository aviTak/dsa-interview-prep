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

def dtb(decimal):
  binary = '0'
  while decimal >= 1:
    binary = str(decimal%2) + binary
    decimal = decimal //2
  
  print(binary)

dtb(16)
