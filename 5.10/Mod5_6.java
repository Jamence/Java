public class Mod5_6 {
	public static void main(String[] args) {
		int rownum=0;
		//用于记录一行的个数
		//常量使用小写字母表示
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
