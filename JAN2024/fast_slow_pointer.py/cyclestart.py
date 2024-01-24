# Given the head of a Singly LinkedList that contains a cycle,
#  write a function to find the starting node of the cycle.
class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

def has_cycle(head):
    fast = slow = head
    print(fast.data,slow.data,'flflfl')
    cycle = 0
    while fast and fast.next:
        # print(fast.next.data,'fastnextdata')
        fast = fast.next.next
        slow = slow.next
        # print(slow.data,fast.data,'has_cycle')
        if slow == fast:
            # print(f"{slow.data},{fast.data}")
            cycle =  calcyclelength(slow)
            break
    return findstart(head, cycle)
    # return cycle

def calcyclelength(slow):
    current = slow
    cyclelength = 0

    while True:
        current = current.next
        cyclelength += 1
        # print(current.data,slow.data,'current_slow')
        if current == slow:
            break
    return cyclelength

def findstart(head,cyclelength):
    pointer1 = head
    pointer2 = head
    if cyclelength == 0:
        return -1
    while cyclelength>0:
        pointer2 = pointer2.next
        cyclelength -=1
    print(pointer2.data,'datllt')
    while pointer1 != pointer2:
        pointer2 = pointer2.next
        pointer1 = pointer1.next
        print(pointer2.data,pointer1.data)
    
    return pointer1.data


def display(head):
    temp = head
    while temp:
        print(temp.data)
        temp = temp.next
    

head = Node(3)
head.next = Node(2)
head.next.next = Node(0)
head.next.next.next = Node(-4)
head.next.next.next.next = Node(5)
# print(has_cycle(head))
# print(display(head))
head.next.next.next.next.next = head.next
print(has_cycle(head))