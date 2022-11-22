#142. Linked List Cycle II
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def detectCycle(self, head):
        """
        :type head: ListNode
        :rtype: ListNode
        """
        slow = fast = head
        while slow and fast and fast.next:
            # print(slow.val,fast.val)
            slow = slow.next #kachua 1 step
            fast = fast.next.next #khargos 2 step
            if slow == fast: #ek point k baad too brabar hii aa jayega because fast hone ka mtlb ye ni ki tum shi chal chlo
                slow = head #now slowly move 
                while slow != fast:
                    fast = fast.next
                    slow = slow.next
                return slow
                
        
