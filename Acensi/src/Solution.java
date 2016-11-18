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
	public static int N = 29;
	public static int N_MIN = 29;
	public static int NB_TURNS = 40;
	
	public static boolean ACENSI = false;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
        
        
       
		Scanner in = null;
		if(ACENSI)
			in = new Scanner(System.in);
		else 
			in = new Scanner(new File("bot.txt"));
		
		
		MY_COLOR = in.nextLine().charAt(0);
		
		Character[][] board = readBoard(in);
		
		
		
		Point bestCell = findBestCell(board);
		
		if(bestCell != null) {
			System.out.println(bestCell.x+" "+bestCell.y);
		}
		else {
			int x = -1;
			int y = -1;
			do {
				Random rand = new Random();
				x = rand.nextInt(N_MIN);
				y = rand.nextInt(N_MIN);
				
			}while(board[x][y] != '-');
			
			System.out.println(x+" "+y);
		}
		
		
		
	}
	
	private static Point findBestCell(Character[][] board) {
		
		for (int i=0; i<N_MIN; i++) {
			for (int j=0; j<N_MIN; j++) {
				if(board[i][j] == MY_COLOR) {
					int nbAliveNeigh = nbAliveNeighbors(board, i, j).x;
					HashMap<Point, Integer> neighbors = getDeadNeighBors(board, i, j);
					if(nbAliveNeigh < 3 && !neighbors.isEmpty()) {
						return bestNeighBor(board, neighbors);
					}
				}
			}
		}
		
		return null;
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
	
	
	private static HashMap<Point, Integer> getDeadNeighBors(Character[][] map, int x, int y) {
		HashMap<Point, Integer> neighbors = new HashMap<Point, Integer>();
		
		for (int i=-1; i<2; i++) {
			for (int j=-1; j<2; j++) {
				int X = x+i;
				int Y = y+j;
				
				if(i != 0 || j !=0) {
					if (X<N && X>-1 && Y<N && Y>-1) {
						int n = nbAliveNeighbors(map, X, Y).x;
						//System.out.println("Alive neighbors for ["+X+" "+Y+"] = " + n);
						if(n < 3 && map[X][Y] == '-')
							neighbors.put(new Point(X, Y), n);
						
					}
				}
			}
		}
		
		return neighbors;
	}
	
	private static HashMap<Point, Integer> getAliveNeighBors(Character[][] map, int x, int y, boolean stable) {
		HashMap<Point, Integer> neighbors = new HashMap<Point, Integer>();
		
		for (int i=-1; i<2; i++) {
			for (int j=-1; j<2; j++) {
				int X = x+i;
				int Y = y+j;
				
				if(i != 0 || j !=0) {
					if (X<N && X>-1 && Y<N && Y>-1) {
						int n = nbAliveNeighbors(map, X, Y).x;
						//System.out.println("Alive neighbors for ["+X+" "+Y+"] = " + n);
						if(stable) {
							if(n < 3 && map[X][Y] != '-')
								neighbors.put(new Point(X, Y), n);
						}
						else {
							if(map[X][Y] != '-')
								neighbors.put(new Point(X, Y), n);
						}
						
					}
				}
			}
		}
		
		return neighbors;
	}
	
	private static Point bestNeighBor(Character[][] map, HashMap<Point, Integer> neighbors) {
		
		int minNeigh = 4;
		for (Entry<Point, Integer> neigh : neighbors.entrySet()) {
			if (neigh.getValue() < minNeigh)
				minNeigh = neigh.getValue();
		}
		
		ArrayList<Point> minList =new ArrayList<Point>();
		for (Entry<Point, Integer> neigh : neighbors.entrySet()) {
			if (neigh.getValue() == minNeigh)
				minList.add(neigh.getKey());
		}
		
		
		for (Point p : minList) {
			if(isGood(map, p))
				return p;
		}
		
		
		
		
		return null;
		
	}
	
	private static boolean isGood(Character[][] map, Point p) {
		
		HashMap<Point, Integer> neighborsStable = getAliveNeighBors(map, p.x, p.y, true);
		HashMap<Point, Integer> neighborsAll = getAliveNeighBors(map, p.x, p.y, false);
		
		
		return (neighborsStable.size() == neighborsAll.size());
	}

}
