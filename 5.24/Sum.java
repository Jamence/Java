import java.math.*;
public class Sum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * 小数结果
		 */
		double sum=0;
		for(int i=1;i<=49;i++) {
			sum+=(double)(2*i-1)/(double)(2*i+1);
		}
		System.out.println(sum);
		
		
		/*
		 * 分数结果
		 */	
		BigInteger ans_fenmu=new BigInteger("1");
		BigInteger ans_fenzi=new BigInteger("0");
		BigInteger final_fenmu=new BigInteger("1");
		for(long i=1;i<=49;i++) {
			final_fenmu=final_fenmu.gcd(new BigInteger(2*i+1+"")).multiply(new BigInteger(2*i+1+"")).multiply(final_fenmu);
//			System.out.println(final_fenmu);
			ans_fenzi=ans_fenzi.multiply(final_fenmu).divide(ans_fenmu).add(new BigInteger(2*i-1+"").multiply(final_fenmu).divide(new BigInteger(2*i+1+"")));
			
//			ans_fenzi=ans_fenzi*final_fenmu/ans_fenmu+(2*i-1)*final_fenmu/(2*i+1);
			ans_fenmu=final_fenmu;
		}
		String str=new String();
		for(int i=0;i<=Math.max(ans_fenmu.toString().length(), ans_fenzi.toString().length());i++)
			str=str+"_";
		System.out.println(ans_fenzi);
		System.out.println(str);
		System.out.println(ans_fenmu);
	}
	
	/*
	 * 求得最小公倍数,可以直接调用方法
	 */
//	public static BigInteger gcd(BigInteger a,BigInteger b) {
//		return b.equals("0")?a:gcd(b,a%b);
//	}
	
}
