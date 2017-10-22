import java.util.Scanner;
public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int winnumcom=0,winnumper=0;
		Scanner cin=new Scanner(System.in);
		String []str= {"scissor","rock","paper"};
		while(Math.abs(winnumcom-winnumper)<=2) {
			System.out.println("scissor(0),rock(1),paper(2):");
			int tempper,tempcom;
			tempper=cin.nextInt();
			tempcom=(int)(Math.random()*3);
			System.out.println("The computer is "+str[tempcom]+". You are "+str[tempper]+".");
			if(tempper==0) {
				if(tempcom==0) {
					System.out.println("It is a draw In this round");
				}
				if(tempcom==1) {
					System.out.println("Computer Win In this round");
					winnumper++;
				}
				if(tempcom==2) {
					System.out.println("You Win In this round");
					winnumper++;
				}
			}
			if(tempper==1) {
				if(tempcom==0) {
					System.out.println("You Win In this round");
					winnumper++;
				}
				if(tempcom==1) {
					System.out.println("It is a draw In this round");
				}
				if(tempcom==2) {
					System.out.println("Computer Win In this round");
					winnumper++;
				}
			}
			if(tempper==2) {
				if(tempcom==0) {
					System.out.println("Computer Win In this round");
					winnumper++;
				}
				if(tempcom==1) {
					System.out.println("You Win In this round");
					winnumper++;
				}
				if(tempcom==2) {
					System.out.println("It is a draw In this round");
				}
			}
		}
		if(winnumcom>winnumper)
			System.out.println("Finally Computer(Artificial Intelligence) is better than you");
		else
			System.out.println("Finally You are better than Computer And Artificial Intelligence");
	}

}
