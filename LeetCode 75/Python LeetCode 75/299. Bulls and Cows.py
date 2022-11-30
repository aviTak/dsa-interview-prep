class Solution(object):
    def getHint(self, secret, guess):
        """
        :type secret: str
        :type guess: str
        :rtype: str
        input => secret = "1807", guess = "7810"
        output => 1A3B
        bull means right guess of digit at right position so here 8 is right guess so 1 bull
        cow means  not a right guess so 3 is cow
        A and B just random diff digit 
        """
        secret_count = {}
        guess_count = {}
        bull_count = 0
        cow_count = 0
        
        for i,j in zip(secret,guess):
            if i == j:
                bull_count +=1
            else:
                secret_count[i] = 1 +  secret_count.get(i,0)
                guess_count[j]   = 1 + guess_count.get(j,0)

        for k in secret_count:
            if k in guess_count:
                cow_count += min(secret_count[k],guess_count[k])
        return '%sA%sB'%(bull_count,cow_count)
