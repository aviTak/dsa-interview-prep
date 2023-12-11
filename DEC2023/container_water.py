#Container With Most Water
# Input: height[] = [1, 5, 6, 3, 4, 2], Output: 12
"""def containerwater(height):
    area = 0
    n = len(height)
    for i in range(n):
        for j in range(n):
            res_area = (j-i)*min(height[i],height[j])
            area = max(area,res_area)
    return area
"""
#two pointer technique
def containerwater(height):
    area = 0
    i = 0 
    j = len(height)-1
    while i<j:
        res_area = (j-i)*min(height[i],height[j])
        area = max(area,res_area)
        print(f"{height[i]},{height[j]},{res_area},{area},{i},{j}")
        if height[i] < height[j]:
            i+= 1
        else:
            j-=1
    return area
he = [1,5,6,3,4,2,4,6]
print(containerwater(he))
