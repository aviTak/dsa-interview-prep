        matrix = [[0 for _ in range(n)] for x in range(n)]
        left, right = 0, len(matrix[0])
        top, bottom = 0, len(matrix)
        num = 1
        # total = num*num

        while left<right and top<bottom:

          for i in range(left,right):
            matrix[top][i] = num
            num += 1
          
          top += 1

          for i in range(top,bottom): #print right most column
            matrix[i][right-1] = num
            num += 1
         
          right-=1

          if not(left<right and top<bottom):
            break
          
          for i in range(right-1,left-1,-1):
            matrix[bottom-1][i] = num
            num += 1
          
          bottom-=1

          for i in range(bottom-1,top-1,-1):
            matrix[i][left] = num
            num += 1
         
          left += 1
        
        return matrix
