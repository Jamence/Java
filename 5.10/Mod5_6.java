public class Mod5_6 {
	public static void main(String[] args) {
		int rownum=0;
		//���ڼ�¼һ�еĸ���
		//����ʹ��Сд��ĸ��ʾ
		for(int i=100;i<=1000;i++) {
			if(i%5==0&&i%6==0) {
				rownum++;
				if(rownum==1) 
					System.out.print(i);
				else
					System.out.print(" "+i);
				if(rownum==5) {
					System.out.println();
					rownum=0;
				}
			}
		}
	}

}
