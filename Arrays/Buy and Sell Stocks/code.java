// Java program for the above approach
// import java.io.*;

class GFG {

	static int maxProfit(int prices[], int size)
	{

		// maxProfit adds up the difference between
		// adjacent elements if they are in increasing order
		int maxProfit = 0, MS = 0, ML = -1, profit = 0;

		// The loop starts from 1
		// as its comparing with the previous
		for (int i = 1; i < size; i++) {
			if (prices[i] > prices[i - 1]) {
				maxProfit += prices[i] - prices[i - 1];
              	profit += prices[i] - prices[i - 1];
              	ML = i;
              
              if(i == size - 1) {
                System.out.println("Buy: " + MS + " Sell: " + ML + " Profit: " + profit); 
              }
            } else {
              if(ML != -1) {
              	System.out.println("Buy: " + MS + " Sell: " + ML + " Profit: " + profit);
              } 
              MS = i;
              ML = -1;
              profit = 0;
            }
        }
		return maxProfit;
	}

	// Driver code
	public static void main(String[] args)
	{

		// stock prices on consecutive days
		int price[] = { 100, 180, 260, 310, 40, 30, 535, 695 };
		int n = price.length;

		// function call
		System.out.println(maxProfit(price, n));
	}
}
