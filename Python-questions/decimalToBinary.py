#decimal to binary
# 5 = 5%2 = 1 5//2 = 2
def dtb(n):
  if n >= 1:
    dtb(n//2)
  print(n % 2, end='')

dtb(16)
