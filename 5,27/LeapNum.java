
public class LeapNum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int leaprownum=0;
		int leaptotalnum=0;
		System.out.println("���101��2100�����꣬10��һ�У����£�");
		for(int i=101;i<=2100;i++) {
			if(i%4==0&&i%100!=0||i%400==0) {
				leaprownum++;
				leaptotalnum++;
				if(leaprownum==1) 
					System.out.print(i+"");
				else
					System.out.print(" "+i);
				if(leaprownum==10) {
					leaprownum=0;
					System.out.println("");
				}
			}
		}
		System.out.println("");
		System.out.println("�������Ϊ��"+leaptotalnum);
	}

}
