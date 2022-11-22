#141. Linked List Cycle
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def hasCycle(self, head):
        """
        :type head: ListNode
        :rtype: bool
        """
        slow = fast = head
        while slow and fast and fast.next:
            slow = slow.next #kachua 1 step
            fast = fast.next.next #khargos 2 step

            if slow == fast: #ek point k baad too brabar hii aa jayega because fast hone ka mtlb ye ni ki tum shi chal chlo
                return True
        return False
        
