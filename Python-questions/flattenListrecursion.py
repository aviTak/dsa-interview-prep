l = [[1, 2, 3], [4, 5, 6], [7], [8, 9]]
flat_list = [item for sublist in l for item in sublist]
print(flat_list)


def flattenList(nestedList):
    # check if list is empty
    if not(bool(nestedList)):
        return nestedList
 
     # to check instance of list is empty or not
    if isinstance(nestedList[0], list):
        # call function with sublist as argument
        print(*nestedList[:1],'------sublist-------',nestedList[1:])
        return flattenList(*nestedList[:1]) + flattenList(nestedList[1:])
    # call function with sublist as argument
    return nestedList[:1] + flattenList(nestedList[1:])
 
# Driver Code
nestedList = [[8, 9], [10, 11, 'geeks'], [13]]
print('Nested List:--', nestedList)
 
print("Flattened List:\n", flattenList(nestedList))
