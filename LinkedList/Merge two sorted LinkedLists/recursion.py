class Node:
  def __init__(self,data):
    self.data = data
    self.next = None

class LinkList:

  def __init__(self):
    self.head = None
    self.tail = None
    self.size = 0
  
  def addAtbegining(self,value):
    new_node = Node(value)
    if self.size == 0:
      self.head = new_node
      self.tail = new_node
    else:
      new_node.next = self.head
      self.head = new_node
  
    self.size += 1
    # return

  def mergeLinkList(self,h1,h2):
    if h1 == None:
      return h2
    if h2 == None:
      return h1
    headq = Node(None)
    if h1.data < h2.data:
      headq = h1
      h1.next = self.mergeLinkList(h1.next,h2)

    else:
      headq = h2
      h2.next = self.mergeLinkList(h1,h2.next)

    return headq

  def display(self):
    temp = self.head
    while temp:
      print(temp.data,end=" ")
      temp = temp.next
  

  def display1(self,m):
    temp = m
    # print(temp.data)
    while temp:
      print(temp.data,end=" ")
      temp = temp.next
  

# str_value = input()
obj = LinkList()

obj2 = LinkList()

obj.addAtbegining(10)
obj.addAtbegining(8)
obj.addAtbegining(6)
obj.addAtbegining(4)
obj.addAtbegining(2)
obj.display()
print()
obj2.addAtbegining(9)
obj2.addAtbegining(7)
obj2.addAtbegining(5)
obj2.addAtbegining(3)
obj2.addAtbegining(1)
obj2.display()
print()
# print(obj2.head,obj2.head.data)
print()
m = obj.mergeLinkList(obj.head,obj2.head)
obj.display1(m)



