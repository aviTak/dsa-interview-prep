#rain water trapping
def trappingwater(height):
    n = len(height)
    left_max = [0]*n
    right_max = [0]*n
    left_max[0] = height[0]
    right_max[-1] = height[-1]
    for i in range(1,n):
        left_max[i] = max(height[i],left_max[i-1])
    for i in range(n-2,-1,-1):
        # print(height[i],right_max[i-1])
        right_max[i] = max(height[i],right_max[i+1])
    trapped_water = 0
    print(f"{left_max=},{right_max=}")
    for i in range(n):
        trapped_water += (min(left_max[i],right_max[i])-height[i])
    
    return trapped_water

# Input: height[] = [1, 0, 2, 1, 0, 1, 2, 1, 2, 1], Output: 6
h = [1, 0, 2, 1, 0, 1, 2, 1, 2, 1]
print(trappingwater(h))