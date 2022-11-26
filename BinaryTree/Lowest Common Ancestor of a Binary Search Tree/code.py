# 235. Lowest Common Ancestor of a Binary Search Tree
# Definition for a binary tree node.
class TreeNode(object):
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution(object):
    def lowestCommonAncestor(self, root, p, q):
        """
        :type root: TreeNode
        :type p: TreeNode
        :type q: TreeNode
        :rtype: TreeNode
        """
        if root == None:
          return None
        
        if root.val == p or root.val == q: #if current not is one of p or q value
          return root
        
        #if p and q in left and right subtree
        left = self.lowestCommonAncestor(root.left,p,q) 
        right = self.lowestCommonAncestor(root.right,p,q) 

        #if p and q in any subtree
        if left == None:
          return root.right
        if right == None:
          return root.left
        #if p and q are not in any subtree
        return root

# [6,2,8,0,4,7,9,null,null,3,5]

root = TreeNode(6)
root.left = TreeNode(2)
root.right = TreeNode(8)
root.left.left = TreeNode(0)
root.left.right = TreeNode(4)  
root.right.left = TreeNode(7)
root.right.right = TreeNode(9) 
root.left.right.left = TreeNode(3)  
root.left.right.right = TreeNode(5)  
# root = TreeNode(5)
# root.left = TreeNode(1)
# root.right = TreeNode(4)  
# root.right.left = TreeNode(3)
# root.right.right = TreeNode(6)  
sol = Solution()
# print(root.right.data)
sol.lowestCommonAncestor(root,2,8).val        
