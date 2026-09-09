import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		
		
	System.out.println("________ FINAL VERSION ________");
		long startTime=System.nanoTime();//to measure time
		//long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		
		////////////////////////// read txt file with pieces //////////////////////////////////
		
		int num_lines=0;
		File myObj= new File ("src\\16x16 pieces.txt");
		Scanner reader= new Scanner (myObj);
		reader.nextLine();
		reader.nextLine();
		reader.nextLine();
		 while (reader.hasNextLine()) {
			 num_lines++;
			 
		       reader.nextLine();
		        
		      }
		
		 Piece[] pieces =new Piece[num_lines];
		 
		 
		 
		 
		Scanner read= new Scanner (myObj);
		read.nextLine();
		read.nextLine();
		read.nextLine();
		int i=0;
		 while (read.hasNextLine()) {
		
			 String line=read.nextLine();
			pieces[i]=new Piece(i,Integer.parseInt(line.substring(0,3).trim()),Integer.parseInt(line.substring(3,6).trim()),Integer.parseInt(line.substring(6,9).trim()),Integer.parseInt(line.substring(9,11).trim()));
			 i++;
		      }
		 
		//////////////////////////////////////////////////////////////////////////////////////
			
		
		//create puzzle and enqueue pieces
		Puzzle board1=new Puzzle(num_lines);
		for (int k=0;k<num_lines;k++) {board1.pieces.enqueue(pieces[k]);}
		board1.build(); //build puzzle until a certain number of matching edges is reached, then print board
		
		
		//following commented part was used to verify edge calculation and matching pieces (amongst other things)
		/**  
		Piece p1=new Piece (2,2,2,2,2);
		Piece p2=new Piece (2,3,3,3,3);
		Piece p3=new Piece (5,4,4,4,4);
		Piece p4=new Piece (5,5,5,5,5);
		Piece p5=new Piece (6,6,6,6,6);
		Piece p6=new Piece (6,7,7,7,7);
		Piece p7=new Piece (7,8,8,8,8);
		Piece p8=new Piece (3,9,9,9,9);
		Piece p9=new Piece (7,9,9,9,9);
		Puzzle random_board=new Puzzle(9);
		random_board.pieces.enqueue(p1);
		random_board.pieces.enqueue(p2);
		random_board.pieces.enqueue(p3);
		random_board.pieces.enqueue(p4);
		random_board.pieces.enqueue(p5);
		random_board.pieces.enqueue(p6);
		random_board.pieces.enqueue(p7);
		random_board.pieces.enqueue(p8);
		random_board.pieces.enqueue(p9);
		//random_board.board[1][2]=p1;
		random_board.build();
		*/
		
		//FINAL MESSAGE WITH RUNTIME -> END OF PROGRAM
		System.out.println("Done!");
		long Time=System.nanoTime() - startTime;//to measure time
		//long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		//long actualMemUsed=afterUsedMem-beforeUsedMem;
		
		System.out.println("Elapsed Time (ms) : "+ (Time/1000000));
		//System.out.println("Memory used (bytes) : "+ (actualMemUsed));
	}

}
