class Solution(object):
    def numIslands(self, grid):
        """
        :type grid: List[List[str]]
        :rtype: int
        """
        row = len(grid)
        if row == 0:
            return 0
        col = len(grid[0])
        no_island = 0

        for i in range(row):
            for j in range(col):
                if grid[i][j] == "1":
                    self.mark_as_visited(grid,i,j,row,col)
                    no_island += 1
        
        return no_island

    def mark_as_visited(self,image,sr,sc,row,col):
        if sr<0 or sr>=row or sc<0 or sc>=col:
            return
      
        if image[sr][sc] != "1":
            return
      
        image[sr][sc] = "2"
      
        self.mark_as_visited(image,sr-1,sc,row,col)#top
        self.mark_as_visited(image,sr+1,sc,row,col) #bottom
        self.mark_as_visited(image,sr,sc-1,row,col)#left
        self.mark_as_visited(image,sr,sc+1,row,col) #right
