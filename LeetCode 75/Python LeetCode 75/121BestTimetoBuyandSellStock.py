#121. Best Time to Buy and Sell Stock
class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        
        if len(prices) < 2: #if days are less than 2 
            return 0

        max_profit = 0
        min_stockrate = prices[0] #firstly stock min stock value

        for p in prices:
            min_stockrate = min(min_stockrate,p) #stock rate compare with current value
            max_profit    = max(max_profit,p-min_stockrate) ##profit is current price to stock value assigned

        return max_profit
