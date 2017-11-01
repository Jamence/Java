import java.util.Scanner;
public class Money {
	static String[] BigWeight= {"万","亿","兆","京","垓","秭","穣","沟","涧","正","载","极","恒河沙","阿僧祇","那由他","不可思议","无量大数"};
	static String[] weight= {"拾","佰","仟"};
	static String[] SmallWeight= {"角","分","厘","毛","糸","忽","微","纤","沙","尘","埃","渺","漠","模糊","逡巡"};
	static String[] num= {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin=new Scanner(System.in);
		while(cin.hasNext()){
			String ten="10";
			String mone=cin.next();
			int num_flag=mone.length();
			int first_zero=0;
			for(int i=0;i<mone.length();i++) {
				if(mone.charAt(i)!='0') {
					first_zero=i;
					break;
				}
			}
			for(int i=0;i<mone.length();i++)
				if(mone.charAt(i)=='.') {
					num_flag=i;
					break;
				}
			boolean zero_count_pre=false;
			boolean zero_count=false;
			
			if(mone.equals(ten)) {
				System.out.println("拾元整");
				continue;
			}
			
			for(int i=first_zero;i<mone.length();i++) {
				int len=num_flag-i;
				if(len>0) {
					
					
					if((mone.charAt(i)-'0')==0&&zero_count_pre==false) {
						zero_count_pre=true;
					}
					if((mone.charAt(i)-'0')!=0) {
						if(zero_count_pre==true)
							System.out.print("零");
						//输出0~9的数据
						System.out.print(num[mone.charAt(i)-'0']);
						//输出小权值（10，100，1000）
						int num_weight=0;
						num_weight=len%4-2;
						if(num_weight==-2)num_weight=2;
						if(num_weight>=0)
							System.out.print(weight[num_weight]);
						zero_count_pre=false;
					}
					
					//输出大权值
					if(len%4==1&&len!=1) {
						int num_BigWeight=len/4-1;
						System.out.print(BigWeight[num_BigWeight]);
					}
				}
				if(len==0)System.out.print("元");
					
				if(len<0) {
					
					len=-len-1;
					//表示第一次出现了数值为0的情况
					//但是会出现后面都为0的情况，所以暂时不输出，当出现第一个不是零的情况输出。
					if((mone.charAt(i)-'0')==0&&zero_count==false) {
						zero_count=true;
					}
					
					if((mone.charAt(i)-'0')!=0) {
						if(zero_count==true)
							System.out.print("零");
						System.out.print(num[mone.charAt(i)-'0']);
						System.out.print(SmallWeight[len]);
						zero_count=false;
					}
				}
			}
			if(num_flag==mone.length())System.out.print("元整");
			System.out.println("");
		}
		cin.close();
	}

}
