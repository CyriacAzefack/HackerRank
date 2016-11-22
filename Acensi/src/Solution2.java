import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Solution2 {
	public static void main(String[] args) throws IOException {
		
		String[] m = {"UP", "LEFT", "DOWN", "RIGHT"};
		
		
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		
		Character[][] map = new Character[3][3];
		
		
		
		for (int i=0; i<3; i++) {
			String line = in.next();
			for(int j=0; j<3; j++) {
				map[i][j] = line.charAt(j);
			}
		}
		Character[] dir = {map[0][1], map[1][0], map[2][1], map[1][2]};
		
		
		
		for (int i=0; i<4; i++) {
			if(dir[i] == 'e') {
				move(m[i], map);
				return;
			}
		}
		
		for (int i=0; i<4; i++) {
			if(dir[i] == '-') {
				move(m[i], map);
				break;
			}
		}
			
	}
	
	public static void move(String s, Character[][] map) throws IOException {
		String dir = s;
		File bot = new File("bot.txt");
		int level = 0;
		if(bot.createNewFile()) {
			
			
			
			PrintWriter writer = new PrintWriter("bot.txt", "UTF-8");
			
			
			writer.println(s);
			writer.close();
		} 
		else {
			Scanner botIn = new Scanner(bot);
			String line = "";
			while(botIn.hasNext()) {
				line += botIn.nextLine();
			}
			
			System.out.println(line);
			
			if(line.endsWith("UPUP") && dir.equals("UP")) {
				if(map[2][1] == '-')
					dir = "RIGHT";
			}
			String filename= "bot.txt";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(s);//appends the string to the file
		    fw.close();
		}
		
		//System.out.println(dir);
	}
}