import java.util.Scanner;
public class Money {
	static String[] BigWeight= {"��","��","��","��","��","��","�y","��","��","��","��","��","���ɳ","��ɮ�o","������","����˼��","��������"};
	static String[] weight= {"ʰ","��","Ǫ"};
	static String[] SmallWeight= {"��","��","��","ë","��","��","΢","��","ɳ","��","��","��","Į","ģ��","��Ѳ"};
	static String[] num= {"��","Ҽ","��","��","��","��","½","��","��","��"};
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
				System.out.println("ʰԪ��");
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
							System.out.print("��");
						//���0~9������
						System.out.print(num[mone.charAt(i)-'0']);
						//���СȨֵ��10��100��1000��
						int num_weight=0;
						num_weight=len%4-2;
						if(num_weight==-2)num_weight=2;
						if(num_weight>=0)
							System.out.print(weight[num_weight]);
						zero_count_pre=false;
					}
					
					//�����Ȩֵ
					if(len%4==1&&len!=1) {
						int num_BigWeight=len/4-1;
						System.out.print(BigWeight[num_BigWeight]);
					}
				}
				if(len==0)System.out.print("Ԫ");
					
				if(len<0) {
					
					len=-len-1;
					//��ʾ��һ�γ�������ֵΪ0�����
					//���ǻ���ֺ��涼Ϊ0�������������ʱ������������ֵ�һ�����������������
					if((mone.charAt(i)-'0')==0&&zero_count==false) {
						zero_count=true;
					}
					
					if((mone.charAt(i)-'0')!=0) {
						if(zero_count==true)
							System.out.print("��");
						System.out.print(num[mone.charAt(i)-'0']);
						System.out.print(SmallWeight[len]);
						zero_count=false;
					}
				}
			}
			if(num_flag==mone.length())System.out.print("Ԫ��");
			System.out.println("");
		}
		cin.close();
	}

}
