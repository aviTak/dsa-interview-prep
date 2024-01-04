class Node:
    def __init__(self,data) -> None:
        self.data  = data
        self.left = None
        self.right = None

def createTree():
    data = int(input("enter the value == "))
    if data == -1:
        return 
    
    root = Node(data)

    print(f"enter left for + {data}")
    root.left = createTree()
    print(f"enter right for + {data}")

    root.left = createTree()

    return root

def main():
    root = createTree()
    print(root)

main()