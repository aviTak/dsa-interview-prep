def facingSun(height):
    building_count = 0
    if len(height) <= 0:
        return building_count
    
    building = []
    max_h_build = height[0]
    building.append(max_h_build)
    building_count = 1
    for i in range(1,len(height)):
        if height[i] >= max_h_build:
            building_count +=1
            max_h_build = height[i]
            building.append(max_h_build)
    print(building)
    return building_count

building_height = [2,2,2,2] #[2,3,4,5] #[7,4,8,2,9]
facingSun_data = facingSun
print(facingSun_data(building_height))