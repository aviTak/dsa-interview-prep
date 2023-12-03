#rotate matrix at 90* anticlockwise
mat = [[1,2,3,4],
         [5,6,7,8],
         [9,10,11,12]]
out = [[1, 5, 9], 
       [2, 6, 10], 
       [3, 7, 11], 
       [4, 8, 12]]
# output = [[4,8,12],
#           [3,7,11],
#           [2,6,10],
#           [1,5,9]]
#means first row will be 1st column at reverse order
row = 4
col = 3
# mat = [[(i+1)*(j+1) for j in range(col)] for i in range(row)]
print(mat)

transpose = [[mat[j][i] for j in range(len(mat))]
             for i in range(len(mat[0]))]
print(transpose,len(transpose[0]),len(transpose))
data = []
for i in range(len(transpose)-1,-1,-1):
    data.append(transpose[i])
#     for j in range(len(transpose)):
#        #  print(f'mat[i][j] -- {mat[i][j]}')
#         print(transpose[i][j])

print(data)