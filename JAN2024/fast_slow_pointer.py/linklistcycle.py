#cycle in link list 
class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

class Linklist:
    def __init__(self) -> None:
        self.head = None
        self.tail = None
        self.size = 0
    
    def add_begining(self,value):
        new_node = Node(value)
        if self.size == 0:
            self.head = self.tail = new_node
        else:
            new_node.next = self.head
            self.head = new_node      
        self.size += 1
    
    # def createdcycle(self):
    #     self.tail.next = self.head.next
    
    def displaylist(self):
      temp = self.head
      while(temp):
        print(temp.data)
        temp = temp.next
    
    def cycleInLinkList(self):
        fast = slow = self.head
        while fast != None and fast.next != None:
            fast = fast.next.next
            slow = slow.next
            if slow == fast:
                return True
        
        return False

obj = Linklist()
# obj.add_begining(1)
# obj.add_begining(2)
# obj.add_begining(3)
# obj.add_begining(4)
obj.displaylist()
obj.cycleInLinkList()