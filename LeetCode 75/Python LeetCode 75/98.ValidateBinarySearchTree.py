# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution(object):
    def isValidBST(self, root):
        """
        :type root: TreeNode
        :rtype: bool
        """
        mini = float('-inf')
        maxi = float('inf')
        return self.isBST(root,mini,maxi)
      
    def isBST(self,root,mini,maxi):
        if root is None:
            return True

        if root.val < mini or root.val > maxi:
            return False

        left = self.isBST(root.left,mini,root.val-1)
        right = self.isBST(root.right,root.val+1,maxi)
        if left == True and right == True:
            return True
        else:
            return False
