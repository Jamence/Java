import java.util.Scanner;
public class Calendar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin=new Scanner(System.in);
		System.out.println("��������Լ�����1��1�������ڼ�");
		System.out.println("��ݣ�");
		int year=cin.nextInt();
		
		System.out.println("���ڼ���");
		int week=cin.nextInt();
		cin.close();
		for(int i=1;i<=12;i++) {
			if(i==1){
				//ÿһ����45��λ��
				System.out.println("              January "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==2){
				System.out.println("             February "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==3){
				System.out.println("                March "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==4){
				System.out.println("                April "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==5){
				System.out.println("                  May "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==6){
				System.out.println("                 June "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==7){
				System.out.println("                 July "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==8){
				System.out.println("               August "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==9){
				System.out.println("            September "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==10){
				System.out.println("              October "+year+"               ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==11){
				System.out.println("              November "+year+"              ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
			if(i==12){
				System.out.println("              December "+year+"              ");
				System.out.println("_____________________________________________");
				System.out.println("Sun    Mon    Tue    Wed    Thu    Fri    Sat");
				week=display(year,i,week);
				System.out.println("");
			}
		}	
	}
	
	public static int display(int year,int mon,int week)
	{
		int []month= {0,31,28,31,30,31,30,31,31,30,31,30,31};
		if((year%4==0&&year%100!=0)||year%400==0)month[2]=month[2]+1;
		int daytempnum=1;
		for(int j=0;;j=(j+1)%7) {
			//����ת������
			int temp_week;
			if(j==0)temp_week=7;
			else
				temp_week=j;
			
			//����Ѿ�����˵�һ��
			if(daytempnum!=1) {
				if(temp_week==7)
					System.out.format("%3d", daytempnum++);
				else
					System.out.format("    %3d", daytempnum++);
			}
			
			//�ҵ�����1�ŵ�����
			if(daytempnum==1&&week==temp_week) {
//				System.out.println(temp_week);
				System.out.format("%3d", daytempnum++);
//				continue;
			}
			//�ڵ�һ�����û���ҵ���һ�����ϵ����ڣ�����ո�
			if(daytempnum==1&&week!=temp_week) {
				System.out.print("       ");
//				System.out.println("meiyou");
			}
			
			
			if(temp_week==6)System.out.println("");
			if(daytempnum>month[mon]) {
				if(temp_week==7)week=1;
				else
					week=temp_week+1;
				System.out.println("");
				break;
			}
		}
		return week;
	}
}
  