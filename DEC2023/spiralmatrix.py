def spiralM(mat):
    res = []
    left,right = 0,len(mat[0]) #this for columns
    top,bottom = 0,len(mat) #this for rows

    while left<right and top<bottom:

        #print first row with column increment
        for i in range(left,right): #so here top is fixed means same row but column increment
            res.append(mat[top][i])
        print(res,'left_to_right')
        top += 1

        #print last column with row increment
        for j in range(top,bottom): #so here row is increase and col is fixed
            res.append(mat[j][right-1])
        print(res,'top_to_bottom')
        right -= 1
        print(left,right,top,bottom)
        if not(top<bottom and left<right):
            break
        #print the last row with col decrement 
        for k in range(right-1,left-1,-1): #so her row fixed and column decrese
            res.append(mat[bottom-1][k])
        print(res,'right_to_left')
        bottom -= 1
        # print the left row where column is fixed
        for l in range(bottom-1,top-1,-1): #here row change and column fixed
            res.append(mat[l][left])
        print(res,'bottom_up_left')
        
        left += 1
    
    return res

matrix = [[1,2,3],
          [4,5,6],
          [7,8,9],
          [10,11,12]]
print(spiralM(matrix))
        