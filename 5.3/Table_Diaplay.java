
public class Table_Diaplay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Top=new String("ǧ��                                                                                                                                          ��");
		int len_max=50; 
		System.out.println(Top);
		for(int i=1;i<=199;i++) {
			//�ó����
			String str1=new String(i+"");
			//�ó��ұ�
			String str2=new String(i*22+"");
			StringBuilder Str2=new StringBuilder(str2);
			Str2.insert(str2.length()-1,".");
			//����ո�
			String str_block=new String("");
			for(int j=0;j<len_max-str1.length()-Str2.length();j++)str_block=str_block+" ";
			System.out.println(str1+str_block+Str2);
		}
	}

}
