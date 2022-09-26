#buy and sell stock
#Stock Buy Sell to Maximize Profit using Valley Peak Approach:

def maxProfit(price):
  maxprofit = 0
  
  profit = 0 #for profit from combination of days
  first_index = 0
  last_index  = 0


  for i in range(1,len(price)):
    #check the difference between between current price and previous price
    if price[i] > price[i-1]:
      maxprofit += price[i]- price[i-1] #stored in maxprofit
      
      profit    += price[i]- price[i-1] #store in profit these both ration
      last_index = i #make last_index as current index value

      if last_index == len(price)-1:
        print('start day: {}  End day: {} profit: {} '.format(first_index,last_index,profit))
    else:
      if last_index !=0: #if last index is not equal to 0 than print all the data
        print('start day: {}  End day: {} profit: {} '.format(first_index,last_index,profit))
      
      first_index = i #make first index as current i value
      last_index = 0 #last index again to 0 
      profit     = 0 #make profit is 0 for next price details

  return maxprofit



price = [100, 180, 260, 310, 40, 30, 535, 695]
# price = [100, 180, 260, 310, 40, 535, 695]
# price = []
maxProfit(price)
