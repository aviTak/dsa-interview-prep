class Solution:
    def spiralOrder(self, matrix: List[List[int]]) -> List[int]:
        res = []
        left,right = 0, len(matrix[0])
        top,bottom = 0, len(matrix)
        
        while left<right and top<bottom:
            #print every i in top row
            for i in range(left,right):
                res.append(matrix[top][i])
            print(res)
            top += 1

            #print every i in right most column
            for i in range(top,bottom):
                res.append(matrix[i][right-1])
            print(res)
            right -=1

            if not(left < right and top<bottom):
                break

            #print evey i in end rows
            for i in range(right-1,left-1,-1):
                res.append(matrix[bottom-1][i])
            print(res)
            bottom -=1

            #print evey i in left column

            for i in range(bottom-1,top-1,-1):
                res.append(matrix[i][left])
            print(res)
            left += 1
        
