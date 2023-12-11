#longest substring with unrepeated characters

def longest_substring(arr):
    #assign variable left, right, max and set
    n = len(arr)
    left = 0
    right = 0
    max_length = 0
    set_data = set()
    while(right < n):
        #add data into set until it is not repeated
        char_data = arr[right]
      
        if char_data not in set_data:
            max_length = max(max_length,right-left+1)
            set_data.add(char_data)
            right +=1
        else:
            #if data is repeated that remove the element from set and increase the
            #pointer from set

            #but here is a catch we also remove all element until we findout reptead
            while (arr[left] != char_data):
                set_data.remove(arr[left])
                left += 1
                # print(f"{char_data},{arr[left]},{left=},{right=}")
            set_data.remove(char_data)
            left += 1
        print(f"{max_length=},{left=},{right=},{set_data}")
    return max_length


string = "enjoyalgorithms" #'abcbbcabc'
print(longest_substring(string))