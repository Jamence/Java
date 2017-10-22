import java.util.Scanner;;
public class Finance {

	//4388576018402626
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter a credit card number as a long integer:");
		Scanner cin=new Scanner(System.in);
		
		long number=cin.nextLong();
		cin.close();
		if(isValid(number)) 
			System.out.println(number+" is valid");
		else
			System.out.println(number+" is invalid");
	}
	
	public static boolean isValid(long number) {
		int sum=sumOfDoubleEvenPlace(number)+sumOfOddPlace(number);
		
		System.out.println(sumOfDoubleEvenPlace(number)+"");
		System.out.println(sumOfOddPlace(number)+"");
		
		if(sum%10==0)return true;
		else
			return false;
	}
	//计算算法中第二步的结果
	public static int sumOfDoubleEvenPlace(long number) {
		String str=number+"";
		int sum=0;
		for(int i=0;i<str.length();i++) {
			if(i%2==0) {
				int temp_num=str.charAt(i)-'0';
//				System.out.println(getDigit(temp_num*2)+"");
				sum+=getDigit(temp_num*2);
			}
		}
		return sum;
	}
	//得到一个数，若一位，返回本身；若两位，返回两位和
	public static int getDigit(int number) {
		if(number<=9)return number;
		else 
			return number%10+number/10;
	}
	//得到算法中第三步的结果
	public static int sumOfOddPlace(long number) {
		int sum=0;
		String str=number+"";
		for(int i=0;i<str.length();i++) {
			if(i%2==0) {
				sum+=str.charAt(str.length()-1-i)-'0';
//				System.out.println(str.charAt(str.length()-1-i)-'0');
			}
		}
		return sum;
	}
	/*
	public static boolean prefixMatched(long number,int d) {
		
	}
	public static int getSize(long d) {
		
	}
	public static long getPrefix(long number,int k) {
		
	}
	*/
}
