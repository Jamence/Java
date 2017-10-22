
public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//该标志位用于判断是否为第一次掷骰子
		boolean flag=true;
		int presum=0,cursum=0;
		while(true) {
			int left,right;
			left=(int)(Math.random()*6+1);
			right=(int)(Math.random()*6+1);
			cursum=left+right;
			System.out.println("You rolled"+left+" + "+right+" = "+cursum);
			if(flag) {
				if(left+right==2||left+right==3||left+right==12) {
					System.out.println("You lose");
					break;
				}
				else
				if(left+right==7||left+right==11) {
					System.out.println("You win");
					break;
				}
				else {
					flag=false;
					System.out.println("Point is "+cursum);
					presum=cursum;
				}
			}
			else {
				if(cursum==7) {
					System.out.println("You lose");
					break;
				}
				else
				if(cursum==presum){
					System.out.println("You win");
					break;
				}
				else {
					System.out.println("point is "+cursum);
					presum=cursum;
				}
			}
			
		}
	}

}
