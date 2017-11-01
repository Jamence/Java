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
			//如果正确，
			if(IsIdCardCorrect(id_card)) {
				PrintInformation(id_card);
				if(id_card.length()==15) {
					Tranform_Id(id_card);
				}
			}
		}
	}
	//判断身份证号是否正确
	public static boolean IsIdCardCorrect(String s) 
	{
		if(s.length()!=15&&s.length()!=18) {
			System.out.println("位数错误，不需判断地址，日期，校验码等等");
			return false;
		}
		//为了杜绝短路，输出所有错误信息。
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
	//判断地址码是否正确
	public static boolean IsAddressIdCorrect(String s)
	{
		if(Address.containsKey(GetAddressId(s,0,6))) 
			return true; 
		else {
			System.out.println("地址码错误，不存在该地址码"+GetAddressId(s,0,6));
			return false;
		}
			
	}
	//判断日期是否正确
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
			System.out.println("月份大于12");
			flag_ans=false;
			System.out.println("由于月份过大，无法判断日期是否正确");
			return false;
		}
		if((year%4==0&&year%100!=0)||year%400==0) {
			if(day>runmonth[month_num]) {
				System.out.println("该月日期"+day+"  大于该月允许最大日期"+runmonth[month_num]);
				flag_ans=false;
			}
		}
		else {
			if(day>pinmonth[month_num]) {
				System.out.println("该月日期"+day+"  大于该月允许最大日期"+pinmonth[month_num]);
				flag_ans=false;
			}
		}
		
		return flag_ans;
	}
	//判断校验码是否错误
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
				System.out.println("校验码错误");
				return false;
			}
		}
	}
	//返回子串(int型)
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
			System.out.println("该身份证号为旧版身份证号");
		}
		else {
			year=GetAddressId(s,6,10);
			month_num=GetAddressId(s,10,12);
			day=GetAddressId(s,12,14);
			SexId=GetAddressId(s,14,17);
			System.out.println("该身份证号为新版身份证号");
		}
		String Address_information=Address.get(Address_id/10000*10000)+Address.get(Address_id/100*100)+Address.get(Address_id);
		System.out.println("该用户地址为："+Address_information);
		System.out.println("该用户出生年月为：    "+year+"年"+month_num+"月"+day+"日");
		if(SexId%2==1)
			System.out.println("该用户性别为男");
		else
			System.out.println("该用户性别为女");
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
		
		System.out.println("15位身份证调整为18位身份证为：  "+Final_17+JiaoyanMap[JiaoYan]);
	}
}
