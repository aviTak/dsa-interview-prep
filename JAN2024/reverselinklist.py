#reverse a link list
class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None
class LinkList:
    def __init__(self) -> None:
        self.size = 0
        self.head = None
        self.tail = None
    def add_end(self,value):#add into stack
        node = Node(value)
        if self.size == 0:
            self.head = self.tail = node
        else:
            # self.tail.next = node
            # self.tail = node
            curr = self.head
            while curr.next:
                curr = curr.next
            curr.next = node
            self.tail = node
        self.size += 1  

    def reverselinklist(self):
        prev = None
        current = self.head
        forward = None
        while current:
            forward = current.next
            current.next = prev
            prev = current
            current = forward
        self.head = prev
        return prev
    
    def traverse(self):
        temp = self.head
        list = []
        while temp:
            # print(temp.data)
            list.append(temp.data)
            temp = temp.next
        return list

obj = LinkList()
obj.add_end(-1)
obj.add_end(-2)
obj.add_end(-3)
print(obj.traverse())
obj.reverselinklist()
print(obj.traverse())
