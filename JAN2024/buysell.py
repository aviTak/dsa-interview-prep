def max_profit(price):
    buy = 0
    sell = 1
    max_profit = 0

    while sell < len(price):
        if price[buy] < price[sell]:
            profit = price[sell] - price[buy]
            max_profit = max(max_profit,profit)
        else:
            buy = sell #no need to increment by one because if its less than it will be small values only
        sell += 1
    
    return max_profit

prices = [7,6,3,2,1] #[7,1,5,3,6,4]
print(max_profit(prices))
            