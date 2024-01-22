# Given the head of a Singly LinkedList that contains a cycle,
#  write a function to find the starting node of the cycle.
class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

def has_cycle(head):
    fast = slow = head
    cycle = 0
    while fast and fast.next:
        fast = fast.next.next
        slow = slow.next
        if slow == fast:
            cycle =  calcyclelength(slow)
            break
    return findstart(head, cycle)
    

def calcyclelength(slow):
    current = slow
    cyclelength = 0

    while True:
        current = current.next
        cyclelength += 1
        if current == slow:
            break
    
    return cyclelength

def findstart(head,cyclelength):
    pointer1 = head
    pointer2 = head
    while cyclelength > 0:
        pointer2 = pointer2.next
        cyclelength -= 1
    
    while pointer2 != pointer1:
        pointer1 = pointer1.next
        pointer2 = pointer2.next
    
    return pointer1


head = Node(1)
head.next = Node(2)
head.next.next = Node(3)
head.next.next.next = Node(4)
head.next.next.next.next = Node(5)
print(has_cycle(head).data)
head.next.next.next.next = head.next.next
print(has_cycle(head).data)