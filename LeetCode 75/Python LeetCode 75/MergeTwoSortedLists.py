#21. Merge Two Sorted Lists
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution(object):

    def mergeTwoLists(self, first, second):
        """
        :type list1: Optional[ListNode]
        :type list2: Optional[ListNode]
        :rtype: Optional[ListNode]
        """
        if first is None:
            return second
        if second is None:
            return first
        
        ll3 = ListNode()
        if first.val <= second.val:
            ll3 = head = ListNode(first.val)
            first = first.next
        else:
            ll3 = head = ListNode(second.val)
            second = second.next
            
        while first and second:
            if first.val < second.val:
                ll3.next = ListNode(first.val)
                first = first.next
            else:
                ll3.next = ListNode(second.val)
                second = second.next
            ll3 = ll3.next
            # print(first,"---",second)
            # print(ll3,'head')
        while first:
            ll3.next = ListNode(first.val)
            first = first.next
            ll3 = ll3.next
        while second:
            ll3.next = ListNode(second.val)
            second = second.next
            ll3 = ll3.next

        return head

        
