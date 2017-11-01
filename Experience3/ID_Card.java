import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
public class ID_Card {
	static public Map<Integer, String> Address = new HashMap<Integer, String>();  
	static public int[] runmonth= {0,31,29,31,30,31,30,31,31,30,31,30,31};
	static public int[] pinmonth= {0,31,28,31,30,31,30,31,31,30,31,30,31};
	public static void main(String[] args) throws IOException
	{
		java.io.File file=new java.io.File("D:\\Java_Eclipse\\Eclipse_IDE\\Experience3\\ID_Address.txt");
		Scanner input=new Scanner(file);
		while(input.hasNext()) {
			int id=input.nextInt();
			String add=input.next();
			Address.put(id,add);
		}
		
		Scanner cin=new Scanner(System.in);
		while(cin.hasNext()) {
			String id_card=cin.next();
			//�����ȷ��
			if(IsIdCardCorrect(id_card)) {
				PrintInformation(id_card);
				if(id_card.length()==15) {
					Tranform_Id(id_card);
				}
			}
		}
	}
	//�ж����֤���Ƿ���ȷ
	public static boolean IsIdCardCorrect(String s) 
	{
		if(s.length()!=15&&s.length()!=18) {
			System.out.println("λ�����󣬲����жϵ�ַ�����ڣ�У����ȵ�");
			return false;
		}
		//Ϊ�˶ž���·��������д�����Ϣ��
		boolean flag_ans=true;
		if(!IsAddressIdCorrect(s)) {
			flag_ans=false;
		}
		if(!IsDataCorrect(s)) {
			flag_ans=false;
		}
		if(!IsCorrectJiaoYan(s)) {
			flag_ans=false;
		}
		if(flag_ans==true)return true;
		else
			return false;
	}
	//�жϵ�ַ���Ƿ���ȷ
	public static boolean IsAddressIdCorrect(String s)
	{
		if(Address.containsKey(GetAddressId(s,0,6))) 
			return true; 
		else {
			System.out.println("��ַ����󣬲����ڸõ�ַ��"+GetAddressId(s,0,6));
			return false;
		}
			
	}
	//�ж������Ƿ���ȷ
	public static boolean IsDataCorrect(String s) {
		boolean flag_ans=true;
		int year=0,month_num=0,day=0;
		if(s.length()==15) {
			year=GetAddressId(s,6,8)+1900;
			month_num=GetAddressId(s,8,10);
			day=GetAddressId(s,10,12);
		}
		else {
			year=GetAddressId(s,6,10);
			month_num=GetAddressId(s,10,12);
			day=GetAddressId(s,12,14);
		}
		if(month_num<0||month_num>12) {
			System.out.println("�·ݴ���12");
			flag_ans=false;
			System.out.println("�����·ݹ����޷��ж������Ƿ���ȷ");
			return false;
		}
		if((year%4==0&&year%100!=0)||year%400==0) {
			if(day>runmonth[month_num]) {
				System.out.println("��������"+day+"  ���ڸ��������������"+runmonth[month_num]);
				flag_ans=false;
			}
		}
		else {
			if(day>pinmonth[month_num]) {
				System.out.println("��������"+day+"  ���ڸ��������������"+pinmonth[month_num]);
				flag_ans=false;
			}
		}
		
		return flag_ans;
	}
	//�ж�У�����Ƿ����
	public static boolean IsCorrectJiaoYan(String s) {
		char []JiaoyanMap= {'1' ,'0' ,'X', '9', '8', '7' ,'6', '5', '4', '3', '2'};
		if(s.length()==15)return true;
		else
		{
			int JiaoYan=0;
			int JiaoYanSum=0;
			for(int i=16;i>=0;i--) {
				JiaoYanSum+=(2*(18-i-1)%11)*(s.charAt(i)-'0');
			}
			JiaoYan=JiaoYanSum%11;
			if(s.charAt(17)==JiaoyanMap[JiaoYan]) {
				return true;
			}
			else {
				System.out.println("У�������");
				return false;
			}
		}
	}
	//�����Ӵ�(int��)
	public static int GetAddressId(String s,int start,int end)
	{
		return  Integer.parseInt(s.substring(start, end));
	}
	public static void PrintInformation(String s)
	{
		int Address_id=GetAddressId(s,0,6);
		int year=0,month_num=0,day=0,SexId;
		if(s.length()==15) {
			year=GetAddressId(s,6,8)+1900;
			month_num=GetAddressId(s,8,10);
			day=GetAddressId(s,10,12);
			SexId=GetAddressId(s,12,15);
			System.out.println("�����֤��Ϊ�ɰ����֤��");
		}
		else {
			year=GetAddressId(s,6,10);
			month_num=GetAddressId(s,10,12);
			day=GetAddressId(s,12,14);
			SexId=GetAddressId(s,14,17);
			System.out.println("�����֤��Ϊ�°����֤��");
		}
		String Address_information=Address.get(Address_id/10000*10000)+Address.get(Address_id/100*100)+Address.get(Address_id);
		System.out.println("���û���ַΪ��"+Address_information);
		System.out.println("���û���������Ϊ��    "+year+"��"+month_num+"��"+day+"��");
		if(SexId%2==1)
			System.out.println("���û��Ա�Ϊ��");
		else
			System.out.println("���û��Ա�ΪŮ");
	}
	public static void Tranform_Id(String s)
	{
		char []JiaoyanMap= {'1' ,'0' ,'X', '9', '8', '7' ,'6', '5', '4', '3', '2'};
		String Final_17=s.substring(0, 6)+"19"+s.substring(6,15);
		int JiaoYan=0;
		int JiaoYanSum=0;
		for(int i=16;i>=0;i--) {
			JiaoYanSum+=(2*(18-i-1)%11)*(Final_17.charAt(i)-'0');
		}
		JiaoYan=JiaoYanSum%11;
		
		System.out.println("15λ���֤����Ϊ18λ���֤Ϊ��  "+Final_17+JiaoyanMap[JiaoYan]);
	}
}
