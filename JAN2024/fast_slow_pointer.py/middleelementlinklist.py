# Given the head of a Singly LinkedList, write a method to return the middle node of the LinkedList.

# If the total number of nodes in the LinkedList is even, return the second middle node

class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

def middle_node(head):
    slow = fast =  head

    while fast and fast.next:
        fast = fast.next.next
        slow = slow.next

    return slow.data

head = Node(1)
head.next = Node(2)
head.next.next = Node(3)
head.next.next.next = Node(4)
head.next.next.next.next = Node(5)
head.next.next.next.next.next = Node(6)
head.next.next.next.next.next = Node(7)
head.next.next.next.next.next = Node(8)
head.next.next.next.next.next.next = Node(9)

print(middle_node(head))
