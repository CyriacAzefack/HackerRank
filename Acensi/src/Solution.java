import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

public class Solution {
	
	public static Character MY_COLOR;
	public static Character ENN_COLOR;
	
	public static int N = 29;
	public static int MAX_PREDICTS = 100;
	public static int MAX_RANDOM_TRIALS = 100;
	public static boolean ACENSI = false;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
        
        
       
		Scanner in = null;
		if(ACENSI)
			in = new Scanner(System.in);
		else 
			in = new Scanner(new File("bot.txt"));
		
		
		MY_COLOR = in.nextLine().charAt(0);
		if(MY_COLOR == 'w')
			ENN_COLOR = 'b';
		else {	
			ENN_COLOR = 'w';
		}
		
		Character[][] board = readBoard(in);
		
		
		long startTime = System.currentTimeMillis();
		Point bestCell = findBestCell(board);
		
		if(bestCell != null) {
			System.out.println(bestCell.x+" "+bestCell.y);
		}
		else {
			
			//ATTACK
			boolean attackSuccessfull = false;
			
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(board[i][j] == ENN_COLOR) {
						int alive = nbAliveNeighbors(board, i, j).x;
						if(alive == 3) {
							for(int k=-1; k<2; k++) {
								for (int l=-1; l<2; l++) {
									int X = i+k;
									int Y = j+l;
									if(X>-1 && X<N && Y>-1 && Y<N) {
										if(board[X][Y] == '-') {
											attackSuccessfull = true;
											System.out.println(X+" "+Y);
											return;
										}
									}
								}
							}
						}
					}
				}
			}
			 
			
			if(!attackSuccessfull) {
				
				
				//RANDOM
				int x = -1;
				int y = -1;
				char c = ' ';
				do {
					Random rand = new Random();
					x = rand.nextInt(N);
					y = rand.nextInt(N);
					c = board[x][y];
				}while(board[x][y] != '-');
				
				System.out.println(x+" "+y);
			}
		}
		
		 long stopTime = System.currentTimeMillis();
	     long elapsedTime = (stopTime - startTime);
	     
	     System.err.println("Time spent : " + elapsedTime + " ms");
		
	}
	
	private static Point findBestCell(Character[][] map) {
		
		
	
		Point best = null;
		for(int x=0; x<N; x++) {
			for (int y=0; y<N; y++) {
				if (map[x][y] == '-') {
					Point w = win(map, x, y);
					if(w.x == 1 ) {
						best = new Point(x, y);
					}
				}
			}
		}
		
		if(best == null) {
			System.err.println("IMPOSSIBLE TO WIN");
			for(int x=0; x<N; x++) {
				for (int y=0; y<N; y++) {
					if (map[x][y] == '-') {
						Point w = win(map, x, y);
						if(w.x == 0) {
							System.err.println("Tied game");
							return new Point(x, y);
						}
					}
				}
			}
		}
		
		if(best != null)
			System.err.println("WIN MIN : " + win(map, best.x, best.y));
		else 
			System.err.println("NO BEST FOUND!!!");
		
		return best;
		
		
		
		
		
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
	
	/**
	 * Get the cell alive neighbors
	 * @param map
	 * @param x
	 * @param y
	 * @return Point [x: number of alive neighbors; y: number of alive neighbors which belong to me]
	 */
	private static Point nbAliveNeighbors(Character[][] map, int x, int y) {
		int nbNeighbors = 0;
		int nbNeighborsMine = 0;
		
		for (int i=-1; i<2; i++) {
			for (int j=-1; j<2; j++) {
				int X = x+i;
				int Y = y+j;
				if(i != 0 || j !=0) {
					if (X<N && X>-1 && Y<N && Y>-1) {
						if (map[X][Y] != '-') {
							nbNeighbors++;
							if (map[X][Y] == MY_COLOR)
								nbNeighborsMine++;
						}
					}
				}
			}
		}
		
		return new Point(nbNeighbors, nbNeighborsMine);
	}
	
	public static Character[][] predict(Character[][] map) {
		Character[][] result = new Character[N][N];
		for (int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				Point p = nbAliveNeighbors(map, i, j);
				int alive = p.x;
				int aliveMine = p.y;
				if(map[i][j] == '-') { //DEAD CELL
					if(alive == 3) { //DEAD TO ALIVE
						if(aliveMine > 1)
							result[i][j] = MY_COLOR;
						else 
							result[i][j] = ENN_COLOR;
					}
					else { //STAY DEAD
						result[i][j] = '-';
					}
				}
				else { //ALIVE CELL
					if (alive < 2 || alive > 3) { //ALIVE TO DEAD
						result[i][j] = '-';
					}
					else {
						result[i][j] = map[i][j];
					}
				}
			}
		}
		
		return result;
	}
	
	public static Point countAlive(Character[][] map) {
		int me = 0;
		int enn = 0;
		for(int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				if(map[i][j] == MY_COLOR)
					me++;
				else if (map[i][j] == ENN_COLOR)
					enn++;
			}
		}
		
		return new Point(me, enn);
		
	}
	
	public static Point win(Character[][] board, int x, int y) {
	
		
		int nPredictsMax = MAX_PREDICTS;
		
		Character[][] map = new Character[N][N];
		
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = board[i][j];
			}
		}
		
		map[x][y] = MY_COLOR;
		
		
		Point p = null;
		do {
			map = predict(map);
			p = countAlive(map);
			nPredictsMax--;
		}while(p.x !=0 && p.y !=0 && nPredictsMax > 0) ;
		
		if(p.x == 0) {
			return new Point(-1, MAX_PREDICTS-nPredictsMax);
		} else if (p.y == 0){
			return new Point(1, MAX_PREDICTS-nPredictsMax);
		}
		
		return new Point(0, MAX_PREDICTS-nPredictsMax);
		
		
		
		
	}
	

}
