
public class huiwensushu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// π”√…∏∑®
		final int MAX=1000000;
		int []vis=new int [MAX];
		for(int i=0;i<MAX;i++)vis[i]=0;
		
		for(int i=2;i<=Math.sqrt(MAX)+1;i++) {
			if(vis[i]==0)
				for(int j=i*i;j<MAX;j=j+i)vis[j]=1;
		}
		
//		StringBuffer str=new StringBuffer("");
		int numrow=0;
		for(int i=2;i<MAX;i++) {
//			System.out.println(new String( i+"")+"   "+ new StringBuffer(i+"").reverse().toString());
			if(vis[i]==0&&(new StringBuffer(i+"").reverse().toString().equals(new String(i+"")))) {
				System.out.printf("%10d", i);
				numrow++;
				if(numrow%10==0)System.out.println("");
				if(numrow==100)break;
			}
		}
	}

}
