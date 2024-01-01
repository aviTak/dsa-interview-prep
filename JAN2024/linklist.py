class Node:
    def __init__(self,data) -> None:
        self.data = data
        self.next = None

class LinkList:
    def __init__(self) -> None:
        self.head = None
        self.tail = None
        self.size = 0
    
    def add_end(self,value):#add into stack
        node = Node(value)
        if self.size == 0:
            self.head = node
            self.tail = node
        else:
            # self.tail.next = node
            # self.tail = node
            curr = self.head
            while curr.next:
                curr = curr.next
                print(curr.data,curr.next)
            curr.next = node
            self.tail = node

        self.size += 1
    
    def add_start(self,value): #for queue
        node = Node(value)
        if self.size == 0:
            self.head = self.tail = node
        else:
            node.next = self.head
            self.head = node
        self.size += 1
    
    def remove_last(self): #stack pop
        temp = self.head
        if self.size == 0:
            print('list empty')
        elif self.size == 1:
            self.head = self.tail = None
        else:
            while temp.next.next:
                temp = temp.next
                # print(temp.data)
            
            # for i in range(self.size-2):
            #     temp = temp.next
                
            # print(temp.data)
            self.tail = temp
            temp.next = None
        self.size -=1
    
    def remove_first(self): #for dequeue
        if self.size == 0:
            return f'{self.size},list empty1'
        if self.size == 1:
            self.head = self.tail = None
        else:
            self.head = self.head.next
            self.size -= 1
    
    def get_at(self,index):
        print(self.size)
        if self.size == 0:
            return f"list is empty"
        if index < 0 or index > self.size:
            return f"invalid argument"
        else:
            current = self.head
            for i in range(1,index):
                current = current.next
            return current.data 
    def insert_at_index(self,index,value): #add data at index 3
        print(self.size)
        node = Node(value) #new value
        if index<0 or index>self.size:
            return 'return invalid argument'
        # elif index == self.size:  
        elif index == 0:
            self.add_start(value)
        else:
            curr = self.head 
            for i in range(index-1):
                # print(i,'index')
                curr = curr.next
            
            node.next = curr.next
            curr.next = node

            self.size += 1

    def traverse(self):
        temp = self.head
        list = []
        while temp:
            # print(temp.data)
            list.append(temp.data)
            temp = temp.next
        return list

obj = LinkList()
obj.add_start(1)
obj.add_start(2)
obj.add_start(3)
obj.add_end(-1)
obj.add_end(-2)
obj.add_end(-3)
# obj.remove_last()
# obj.remove_first()
# obj.insert_at_index(6,-4)
obj.insert_at_index(0,4)
print(obj.traverse())
# obj.get_at(6)
# def main():
#     n1 = Node(1)
#     n2 = Node(2)
#     n3 = Node(3)
#     head = n1
#     n1.next = n2
#     n2.next = n3
#     n3.next = None
#     print(head,n1.next,id(head))
# main()