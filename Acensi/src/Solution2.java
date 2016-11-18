import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Solution2 {
	
	static boolean ACENSI = false;
	
	static int R;
	static int C;
	static int N = 3;
	static boolean EXIT_FOUND = false;
	static Point EXIT_POINT = null;
	static ArrayList<Direction> moves = new ArrayList<Direction>();
	static Direction[] directions = {Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT};
	
	public enum Direction {
		UP (-1, 0),
		DOWN (1, 0),
		LEFT (0, -1),
		RIGHT (0, 1);
		
		private int deltaX, deltaY ;
		
		Direction (int x, int y) {
			this.deltaX = x;
			this.deltaY = y;
		}
		
		public Point getDelta() {
			return new Point(deltaX, deltaY);
		}
		
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner in = null;
		if(ACENSI)
			in = new Scanner(System.in);
		else 
			in = new Scanner(new File("bot2.txt"));
		
		int id = in.nextInt();
		
		
		Character[][] map = readMap(in);
		List<Direction> possibleMoves = possibleMoves(map);
		
		if (moves.size() > 2) {
			System.out.println("YEAAAAAAAAAAAj");
		}
		if(EXIT_FOUND) {
			if (EXIT_POINT.x > 1) {
				if(possibleMoves.contains(Direction.DOWN)) {
					move(Direction.DOWN);
					return;
				}
			}
			
			if (EXIT_POINT.x < 1) {
				if(possibleMoves.contains(Direction.UP)) {
					move(Direction.UP);
					return;
				}
			}
			
			if (EXIT_POINT.y < 1) {
				if(possibleMoves.contains(Direction.LEFT)) {
					move(Direction.LEFT);
					return;
				}
			}
			
			if (EXIT_POINT.y > 1) {
				if(possibleMoves.contains(Direction.RIGHT)) {
					move(Direction.RIGHT);
					return;
				}
			}
		}
		else {
			if(possibleMoves.contains(Direction.UP)) {
				move(Direction.UP);
				return;
			}
			else if (possibleMoves.contains(Direction.RIGHT)) {
				move(Direction.RIGHT);
				return;
			}
			else if (possibleMoves.contains(Direction.DOWN)) {
				move(Direction.DOWN);
				return;
			}
			else {
				move(Direction.LEFT);
				return;
			}
			
		}
		
	}
	
	private static Character[][] readMap(Scanner in) {
		Character[][] map = new Character[N][N];
		
		for (int i=0; i<N; i++) {
			String line = in.next();
			
			for (int j=0; j<N; j++) {
				map[i][j] = line.charAt(j);
				if(map[i][j] == 'e') {
					EXIT_FOUND = true;
					EXIT_POINT = new Point(i,j);
				}
			}
		}
		return map;
	}
	
	private static List<Direction> possibleMoves(Character[][] map) {
		List<Direction> possibleMoves = new ArrayList<Direction>();
		
		int xbot = 1;
		int ybot = 1;
		
		for (Direction d : directions) {
			Point delta = d.getDelta();
			if (map[xbot+delta.x][ybot+delta.y] != '#')
				possibleMoves.add(d);
		}
		
		return possibleMoves;
	}
	
	private static void move(Direction d) {
		moves.add(d);
		System.out.println(d);
		return;
	}
	

}
