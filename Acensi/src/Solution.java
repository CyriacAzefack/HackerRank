import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Solution {
	
	public static String COLOR;
	public static int N = 29;
	public static int NB_TURNS = 40;
	
	public static boolean ACENSI = true;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
        NB_TURNS--;
        
        System.out.println("Pyramides 29 ->" + pyramide(29));
		Scanner in = null;
		if(ACENSI)
			in = new Scanner(System.in);
		else 
			in = new Scanner(new File("bot.txt"));
		
		
		COLOR = in.nextLine().trim();
		
		Character[][] board = readBoard(in);
		
		int x = -1;
		int y = -1;
		do {
			Random rand = new Random();
			x = rand.nextInt(10);
			y = rand.nextInt(10);
			
		}while(board[x][y] != '-');
		
		System.out.println(x+" "+y);
		System.err.println(NB_TURNS);
	}
	
	private static Character[][] readBoard(Scanner in) {
		Character[][] board = new Character[N][N];
		
		for (int i=0; i<N; i++) {
			String line = in.nextLine();
			for (int j=0; j<N; j++) {
				board[i][j] = line.charAt(j);
			}
		}
		return board;
	}
	
	public static int pyramide(int a) {
		int steps = 0;
		
		int left = a;
		
		while(left > steps) {
			steps++;
			left = left-steps;
			
		}
		
		return steps;
	}

}
