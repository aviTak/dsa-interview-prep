# Definition for a binary tree node.
class TreeNode(object):
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right
class Solution(object):
    def levelOrder(self, root):
        """
        :type root: TreeNode
        :rtype: List[List[int]]
        """
        if root is None:
          return
        queue = [root]
        output = []
        # while len(queue):
        #   output.append([q.val for q in queue])
        #   temp_node = queue.pop(0)

        #   if temp_node.left:
        #     queue.append(temp_node.left)
          
        #   if temp_node.right:
        #     queue.append(temp_node.right)
        while root and queue:
          currentNodes = []
          nextLevel = []
          for node in queue:
            currentNodes.append(node.val)
            if node.left:
              nextLevel.append(node.left)
            if node.right:
              nextLevel.append(node.right)
          output.append(currentNodes)
          # queue.pop(0)
          queue = nextLevel
        
        return output

# [3,9,20,null,null,15,7]
root = TreeNode(3)
root.left = TreeNode(9)
root.right = TreeNode(20)
root.right.left = TreeNode(15)
root.right.right = TreeNode(7)  
sol = Solution()
sol.levelOrder(root)  
