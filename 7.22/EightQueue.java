import java.util.Scanner;
public class EightQueue {
	private static int numtotal=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin=new Scanner(System.in);
		System.out.println("���������̿�ȣ�");
		int n=cin.nextInt();
		cin.close();
		int []pos=new int[n+1];
		numtotal=0;
		System.out.println("����Ϊ����n�ʺ�����");
		queue(1,n,pos);
		System.out.println("���Ͽɵã�");
		System.out.println("һ����"+numtotal+"��n�ʺ����");
	}
	//������ʾ�˻ʺ�ĵ�ǰ�У����̵ĳ��ȣ����ӵ�ÿһ�����ڵ�λ��
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
				//�ж��Ƿ������iλ�÷���Q
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
