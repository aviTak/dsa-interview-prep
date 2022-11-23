#589. N-ary Tree Preorder Traversal
# Input: root = [1,null,3,2,4,null,5,6]
# Output: [1,3,5,6,2,4]
"""
# Definition for a Node.
"""
class Node(object):
    def __init__(self, val=None):
        self.val = val
        self.child = []

class Solution(object):
    def preorder(self, root):
        """
        :type root: Node
        :rtype: List[int]
        """
        output = []
        self.__pre(root,output)
        return output
    def __pre(self,root,output):
      if root is None:
        return
      output.append(root.val)
      for child in root.child:
        self.__pre(child, output)

    
    def preorderItrative(self,root):
      if root is None:
        return []
      
      stack = [root]
      output = []

      while stack:
         #jb tk stack khali ni hota while loop work krta rhega
         topup = stack.pop()

         output.append(topup.val) #we can store the stack top value into the output list

         stack.extend(topup.child[::-1]) #now extend the stack with reverse order of chlidren because ydi m reverse m ni dalungi too last element se pop hona sure hoga
         # and iss chakkr m mere last element k children bi lost ho skte hai
        
      return output


stm = Solution()
# root = [1,None,3,2,4,None,5,6]
# root = Node(1,[3,2,4])
# # root = Node(-1,[3,2,4])
# # print(root.children)
root = Node(10)
root.child.append(Node(2))
root.child.append(Node(34))
root.child.append(Node(56))
root.child.append(Node(100))
root.child[2].child.append(Node(1))
root.child[3].child.append(Node(7))
root.child[3].child.append(Node(8))
root.child[3].child.append(Node(9))
print(stm.preorder(root))
stm.preorderItrative(root)
