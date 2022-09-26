class Node:
  def __init__(self,data = None):
    self.data = data
    self.next = None

class LinkList:

  def __init__(self):
    self.head = None
    self.tail = None
    self.size = 0
  
  def atlast(self,value):
    new_node = Node(value)
    if self.size == 0:
      self.head = new_node
      self.tail = new_node
    else:
      self.tail.next = new_node
      self.tail = new_node
    
    self.size += 1
  
  def mergeList(self,h1,h2):
    head = Node() #making new Node 
    if h1.data < h2.data:
      head = h1
      h1 = h1.next
    else:
      head = h2
      h2 = h2.next
    
    t = head #temp data for storage which  head for update next node until both link list get empty

    while h1 and h2:
      
      if h1.data < h2.data:
        t.next = h1 #less data value go too temp data next 
        t = t.next  #temp data next become latest temp node
        h1 = h1.next
      else:
        t.next = h2
        t = t.next
        h2 = h2.next
    
    if(h1):
      t.next = h1
    else:
      t.next = h2
    
    return head

  
  def display(self):
    temp = self.head
    while temp:
      print(temp.data,end=" ")
      temp = temp.next


obj1  = LinkList()
obj2 = LinkList()

obj1.atlast(1)
obj1.atlast(3)
obj1.atlast(5)
obj1.atlast(7)
obj1.atlast(9)
obj1.display()
print()
obj2.atlast(2)
obj2.atlast(4)
obj2.atlast(8)
obj2.atlast(10)
# obj2.atlast(9)
obj2.display()
print()
m = obj1.mergeList(obj1.head,obj2.head)
obj1.display()
