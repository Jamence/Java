import java.util.Scanner;
public class EightQueue {
	private static int numtotal=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin=new Scanner(System.in);
		System.out.println("请输入棋盘宽度：");
		int n=cin.nextInt();
		cin.close();
		int []pos=new int[n+1];
		numtotal=0;
		System.out.println("以下为所有n皇后的情况");
		queue(1,n,pos);
		System.out.println("综上可得：");
		System.out.println("一共有"+numtotal+"种n皇后组合");
	}
	//参数表示八皇后的当前行，棋盘的长度，棋子的每一行所在的位置
	public static void queue(int cur,int n,int []pos) {
		if(cur==n+1) {
			numtotal++;
			for(int i=1;i<=n;i++) {
				for(int j=1;j<=2*n+1;j++) {
					if(j%2==1) {
						System.out.print("|");
					}
					if(j%2==0) {
						if(j/2==pos[i])
							System.out.print("Q");
						else
							System.out.print(" ");
					}
				}
				System.out.println("");
			}
			System.out.println("");
		}
		else {
			for(int i=1;i<=n;i++) {
				//判断是否可以在i位置放入Q
				boolean flag=true;
				for(int j=1;j<cur;j++) {
					if(i==pos[j])flag=false;
					if(Math.abs(cur-j)==Math.abs(i-pos[j]))flag=false;
				}
				if(flag==true) {
					pos[cur]=i;
					queue(cur+1,n,pos);
				}
				else
					continue;
			}
		}
	}

}
