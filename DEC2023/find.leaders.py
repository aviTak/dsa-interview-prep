def findLeader(L):
    n = len(L)
    leaders = []
    leader = L[n-1]
    leaders.append(leader)
    for i in range(n-2,-1,-1):
        # print(L[i])
        if leader < L[i]:
            leader = L[i]
            # leaders.append(L[i])
            leaders.insert(0,L[i])
    return leaders
    
data = [16,17,4,3,5,2]
print(findLeader(data))