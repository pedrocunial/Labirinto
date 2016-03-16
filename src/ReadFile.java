import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class ReadFile {
	
	public static final boolean[][] getLabyrinth() {
		String texto = readString("labyrinth.txt");
//		System.out.println(texto);
		String[] lines = texto.split("\n");
		
		int height = lines.length;
		int width = lines[0].length();
		
		
		char[] chars = new char[height*width];
		int counterChars = 0;
		for (int i = 0; i < lines.length; i++) { 
			
			int counterLetters = 0;
		    for (int j = 0; j < lines[i].length(); j++) {
		    	
		    	chars[counterChars] = lines[i].charAt(counterLetters);
		    	counterChars++;
		    	counterLetters++;
		    }
		}
		
		char[][] cells = new char[height][width];
		
		for(int i=0;i<height;i++) {			
			for(int j=0;j<width;j++) {				
				cells[i][j] = chars[(i*width)+j];
			}			
		}
		
		System.out.println(chars.length);
		System.out.println(cells[0].length + " Largura " + cells.length + " Altura");
		
		boolean[][] labyrinth = new boolean[height][width];
		
		for(int i=0;i<height;i++) {			
			for(int j=0;j<width;j++) {	
				
				if(cells[i][j] == '#') {
					labyrinth[i][j] = false;
					
				} else {
					labyrinth[i][j] = true;
				}
			}
		}
		
		return labyrinth;
	}
	
	public static String readString(String file){
		
		String texto = "";
		
		try{
			@SuppressWarnings("resource")
			Scanner s = new Scanner(new File(file));
			while (s.hasNextLine()){
				texto = texto + s.nextLine() + "\n";
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Arquivo não encontrado");
		}
		
	return texto;

	}
}