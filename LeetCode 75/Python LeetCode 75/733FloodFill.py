# 733. Flood Fill
class Solution(object):
    def floodFill(self, image, sr, sc, color):
        """
        :type image: List[List[int]]
        :type sr: int
        :type sc: int
        :type color: int
        :rtype: List[List[int]]
        """
        if image[sr][sc] == color : #if image row,col same as new color just return it 
            return image
        
        row = len(image)
        col = len(image[0])
        source = image[sr][sc]
        self.dfs(image,sr,sc,color,row,col,source)
        return image
    
    def dfs(self,image,sr,sc,color,row,col,source):
        if sr<0 or sr>=row or sc<0 or sc>=col:
            return
        if image[sr][sc] != source: #if side row and side column value not same as source image
            return
        image[sr][sc] = color #make the current row column as new color
      
        self.dfs(image,sr-1,sc,color,row,col,source)#top
        self.dfs(image,sr+1,sc,color,row,col,source) #bottom
        self.dfs(image,sr,sc-1,color,row,col,source)#left
        self.dfs(image,sr,sc+1,color,row,col,source) #right
