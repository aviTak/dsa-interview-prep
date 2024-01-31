#kth smallest element in array
# store the largest element at the root, while min heaps store the smallest element at the root.
import heapq

def kthsmallest(arr,K):
    max_heap = []
    for i in arr:
        heapq.heappush(max_heap,-i) #min heap when i is postive 
    
        # print(max_heap,'flfl')
        if len(max_heap)>K:
            heapq.heappop(max_heap)
        print(max_heap)
    return -max_heap[0]

arr = [100,90,80,40,60,50,70]

print(kthsmallest(arr,3))