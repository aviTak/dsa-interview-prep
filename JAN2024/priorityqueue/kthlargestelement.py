# arr : [2,1,4,6,3,9,7]
# k : 2
import heapq
def kthLargestElement(arr,K):
    min_heap = []
    for i in arr:
        heapq.heappush(min_heap,i) #create a min heap in case of largest element
    # print(min_heap)
        if len(min_heap)>K:
            heapq.heappop(min_heap)
    
    print(min_heap)
    return min_heap[0]

    # max_heap = []
    # for i in arr:
    #     heapq.heappush(max_heap,-i) #create a largest heap in case of smallest element
    #     if len(max_heap) > K:
    #         heapq.heappop(max_heap)
        
    # print(max_heap)
    # return -max_heap[0]
        
            




arr = [2,1,4,6,3,9,7]
k = 4
print(kthLargestElement(arr,k))