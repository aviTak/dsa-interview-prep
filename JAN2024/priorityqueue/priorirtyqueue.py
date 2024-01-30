class PrioriryQueue: #min heap
    def __init__(self) -> None:
        self.data = []
    
    def add(self,value):
        self.data.append(value)
        new_node_index = len(self.data)-1
        self.upheaify(new_node_index)
    
    def upheaify(self,child_index):
        if child_index == 0:
            return 
        parent_index = (child_index-1)//2
        if self.data[parent_index] > self.data[child_index]:
            self.swap(parent_index,child_index)
            self.upheaify(parent_index)

    def remove(self,rem):
        last_node_index = len(self.data)-1
        self.swap(rem,last_node_index)
        val = self.data.pop(last_node_index)
        self.downheapify(rem)
    
    def downheapify(self,parent):
        mini = parent
        left_child = 2*parent + 1
        if left_child < len(self.data) and self.data[left_child] < self.data[mini]:
            mini = left_child
        right_child = 2*parent + 2
        if right_child < len(self.data) and self.data[right_child] < self.data[mini]:
            mini = right_child
        
        if mini != parent:
            self.swap(mini,parent)
            self.downheapify(mini)

    def swap(self,parent,child):
        self.data[parent],self.data[child] = self.data[child],self.data[parent]

    def size(self):
        return len(self.data)

obj = PrioriryQueue()
obj.add(12)
obj.add(16)
obj.add(7)
obj.add(1)
obj.add(18)
obj.add(20)

print(obj.data)
obj.remove(1)
print(obj.data)