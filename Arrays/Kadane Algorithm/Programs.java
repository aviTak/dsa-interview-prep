public class Programs
{
    public static void main(String[] args) {
		int [] arr = {5, -3, 2, -7, 1, -2, 4, 1};
        int ab_tak_ka_sabse_bda = Integer.MIN_VALUE;
        int filhal_ka_sabse_bda = 0;
        int ML = -1, MS = 0, ms = 0;

        for(int i = 0; i < arr.length; i++) {
            filhal_ka_sabse_bda += arr[i];
            if(filhal_ka_sabse_bda > ab_tak_ka_sabse_bda) {
                ab_tak_ka_sabse_bda = filhal_ka_sabse_bda;
                ML = i;
                MS = ms;
            }
            if(filhal_ka_sabse_bda < 0) {
                filhal_ka_sabse_bda = 0;
                ms = i + 1;
            }
        }

        System.out.println(ab_tak_ka_sabse_bda);
	}
}
