#Palindrome LinkedList (medium)
# head = [1,2,2,1]
# Given the head of a Singly LinkedList, write a method to check if the LinkedList is a palindrome or not.

# Your algorithm should use constant space and the input LinkedList should be in the original 
# form once the algorithm is finished. 
# The algorithm should have O(N) time complexity where N is the number of nodes in the LinkedList.

class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

def isPalidrom(head):
    slow = fast = head
    while fast and fast.next:
        fast = fast.next.next
        slow = slow.next
    headofbackward = reversebackwardlist(slow)

    print(head.data,headofbackward.data,slow.data,'helele')

    while head and headofbackward:
        print(head.data,headofbackward.data,'idduud')
        if head.data != headofbackward.data:
            return False

        head = head.next
        headofbackward = headofbackward.next

    return True

    
    

def reversebackwardlist(head):
    prev = None
    curr = head
    while curr:
        next = curr.next
        curr.next = prev
        prev = curr
        curr = next
    return prev


head = Node(1)
head.next = Node(2)
head.next.next = Node(5)
head.next.next.next = Node(3)
head.next.next.next.next = Node(5)
head.next.next.next.next.next = Node(2)
head.next.next.next.next.next.next = Node(1)
# head = Node(1)
# head.next = Node(2)
# # head.next.next= Node(2)
# # head.next.next.next = Node(1)
print(isPalidrom(head))
