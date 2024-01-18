# Given the head of a LinkedList with a cycle, find the length of the cycle.
class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

def has_cycle(head):
    fast = slow = head
    while fast and fast.next:
        fast = fast.next.next
        slow = slow.next
        if slow == fast:
            return calcyclelength(slow)
    return 0

def calcyclelength(slow):
    current = slow
    cycle_len = 0
    while True:
        current = current.next
        print(current.data,'data')
        cycle_len += 1
        if current == slow:
            break
    return cycle_len
        

head = Node(1)
head.next = Node(2)
head.next.next = Node(3)
head.next.next.next = Node(4)
head.next.next.next.next = Node(5)
print(has_cycle(head))
head.next.next.next.next = head.next.next
print(has_cycle(head))