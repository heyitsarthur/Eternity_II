import java.util.Random;


public class Puzzle {

//******************************************* QUEUE CLASS FOR PIECES *************
public class Piecequeue{
		Node first;
		Node last;
		int size;
		
		public class Node {
			Node next;
			Piece item;
		}
		
		public Piecequeue() {
			first=null;
			last=null;
			size=0;
		}
		
		public boolean isEmpty() {
			return first==null;
		}
		
		public void enqueue(Piece a) {
			Node oldlast=last;
			last=new Node();
			last.item=a;
			last.next=null;
			if (isEmpty()) {first=last;}
			else oldlast.next=last;
			
			this.size++;
			
		}
		
		public Piece dequeue() {
			Piece a=first.item;
			first=first.next;
			if (isEmpty()) {last=null;size=0;}
			else this.size--;
			return a;
		}
		
	
		
	}
	
//*********************************************************************************
	
	public Piecequeue pieces; //Make queue
	public int numpieces; //number of pieces
	public int dimension; //DIMENSIONS OF THE BOARD (with border)
	public int notplaced; //number of pieces which could not be placed by build_line() function
	public int backtrack_counter; //counts the number of times a line was backtracked for total execution 
	public Piece[][] board;
	public Random rand;
	
	public Puzzle (int size) {
		this.numpieces=size;
		this.dimension=(int) (Math.sqrt(this.numpieces)) + 2;
		this.pieces=new Piecequeue();
		this.board=new Piece[dimension][dimension];
		this.rand=new Random();
		}

	
//-----------------------------------HASH PIECE ACCORDING TO ID-------------------------	
	
public int hash (Piece key) {
		return (key.hash_piece() & 0x7fffffff) % this.numpieces;
	}
	
//--------------------------------------------------------------------------------------	
	


public Piece find_best_fit( int i, int j) {//O(N)  find best fit in Piecequeue to a specific place in board. returns best piece and removes it from queue
	
	int num_pieces=pieces.size;
	
	int top;
	if (board[i-1][j].id!=-2)
	top=board[i-1][j].bottom;
	else
		top=-1;//any piece counts as unmatchable
	
	int left;
	if (board[i][j-1].id!=-2)
	left=board[i][j-1].right;
	else
		left=-1;//any piece
	
	int right;
	if (board[i][j+1].id!=-2)
	right=board[i][j+1].left;
	else
		right=-1;//any piece
	
	int bottom;
	if (board[i+1][j].id!=-2)
	bottom=board[i+1][j].top;
	else
		bottom=-1;//any piece

	int count_rotation;
	
	for (int k=0;k<num_pieces;k++){ //O(N) //4N
		
		Piece bestrotation=new Piece();
		Piece temp=pieces.dequeue();
		int count_best_rotation=0;
		
		for (int h=0;h<4;h++) {
			
	count_rotation=0;
	if (temp.top==top)
		count_rotation++;
	if (temp.left==left)
		count_rotation++;
	if (temp.right==right)
		count_rotation++;
	if (temp.bottom==bottom)
		count_rotation++;
	
	if (count_rotation>=count_best_rotation) {
		bestrotation.equals(temp);
		count_best_rotation=count_rotation;
	}
	
	Piece.rotate(temp);
			
	}///found rotation for one piece
			pieces.enqueue(bestrotation);
		
		}//best_rotation_pieces contains all the pieces rotated in the best way for that spot in the board
	//compare for all the rotations and only retain the piece that fits the most edges
	
	int count_best=0;
	Piecequeue bestfit=new Piecequeue();
	boolean found=false;
	
	for (int k=0;k<num_pieces;k++){ //O(N)
	Piece temp=pieces.dequeue();
	int count_edges=0;
	
	if (temp.top==top)
		count_edges++;
	if (temp.left==left)
		count_edges++;
	if (temp.right==right)
		count_edges++;
	if (temp.bottom==bottom)
		count_edges++;
	
	if (count_edges>=count_best) {
		if (!bestfit.isEmpty())
			pieces.enqueue(bestfit.dequeue());
		bestfit.enqueue(temp);
		found=true;	
	}
	
	if (!found)
		pieces.enqueue(temp);
	found=false;
	}
	
	return bestfit.dequeue();
}



public void place_leftover() { //O(N^2)
	
			this.shuffle_pieces();
	for (int i=1;i<dimension-1;i++) {
		for (int j=1;j<dimension-1;j++) { //O(N^2)
			
			if (board[i][j].id==-2) {
				//see from all of our pieces, which one is the best fit for that position
				board[i][j]=this.find_best_fit(i, j); //O(N)
				}
				
				
		}
		}
		
		
	return;
	}


public void shuffle_pieces() { //O(N)
	Piece[] a=new Piece[pieces.size];
	int count=0;
	while (!this.pieces.isEmpty()) { //N=number of pieces
		a[count]=pieces.dequeue();
		count++;
	}
	
	//knuth shuffle
	for (int i=0;i<a.length;i++) {  //O(N)
		int r= this.rand.nextInt(i+1);
		Piece temp=a[i];
		a[i]=a[r];
		a[r]=temp;
	}
	
	for (int j=0;j<a.length;j++) //O(N)
	{
		this.pieces.enqueue(a[j]);
	}
}


public void print_puzzle() {//O(N)
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println("_____________________ ETERNITY II _________________________");
	System.out.println();System.out.println();
	System.out.println("Total edges: 544");
	this.calculate_edges(); //function to calculate matching edges; //O(N)
	this.print_board(); //O(N)
	System.out.println("___________________________________________________________");
	System.out.println();	System.out.println();	System.out.println();	System.out.println();	System.out.println();	System.out.println();

	this.verify_board(); //O(N)
	return;
}


public void print_board() { //O(N)
	System.out.println();System.out.println();
	System.out.println("------ BOARD :");
	String s0="   ";
	
	for (int j=0;j<dimension-2;j++) {
		if (j >= 10)
			s0+="    "+Integer.toString(j+1) +"    ";
			else
				s0+="    "+Integer.toString(j+1) +"     ";
	}
		
	
	System.out.println(s0);
		for (int i=1;i<dimension-1;i++) {//build line //O(N)
			String s1="   ";
			String s2="   ";
			String s3="   ";
			String s4;
			if (i >= 10)
			s4=i + " ";
			else
				s4=i + "  ";
			String s5="   ";
			String s6="   ";
			for (int j=1;j<dimension-1;j++) {
				s1+=" ________ ";
				if (board[i][j].top>=10)
					s2+="|   " + board[i][j].top+ "   |";
				else if (board[i][j].top<10)
					s2+="|    " + board[i][j].top+ "   |";
				
				if (board[i][j].left>=10)
					s3+="|" + board[i][j].left+ "    ";
				else if (board[i][j].left<10)
					s3+="|" + board[i][j].left+ "     ";
				
				if (board[i][j].right>=10)
					s3+=board[i][j].right+ "|";
				else if (board[i][j].right<10)
					s3+=" " + board[i][j].right+ "|";
				
				if (board[i][j].id>=100)
					s4+="|   "+board[i][j].id+ "  |";
				else if (board[i][j].id>=10)
					s4+="|   " + board[i][j].id+ "   |";
				else if (board[i][j].id==-2 |board[i][j].id==-1)
					s4+="|   " + board[i][j].id+ "   |";
				else if (board[i][j].id<10)
					s4+="|    " + board[i][j].id+ "   |";
				
				s5+="|  ^id^  |";
				
				if (board[i][j].bottom>=10)
					s6+="|___" + board[i][j].bottom+ "___|";
				else if (board[i][j].bottom<10)
					s6+="|____" + board[i][j].bottom+ "___|";
				
				
			}
			
			System.out.println(s1);
			System.out.println(s2);
			System.out.println(s3);
			System.out.println(s4);
			System.out.println(s5);
			System.out.println(s6);
			
		}
		
		/**
		  
		 ________ 
		|   00   | top
		|00    00|  left right
		|   00   |  id 
		|  ^id^  |
		|___00___|  bottom
		
		 ________ 
		|    0   | top
		|0      0|  left right
		|    0   |  id 
		|        |
		|____0___|  bottom
		 
		  */
	}


public void verify_board() { //O(N)
	int counter=0;
	int[] ids= new int[(dimension-2)*(dimension-2)];
	for (int k=0;k<ids.length;k++) { //N
	ids[k]=-1;	
	}
	
	for (int i=1;i<this.dimension-1;i++) {
		
		for (int j=1;j<this.dimension-1;j++) { //N
			
			 //* !!!!!!!!!!!DO NOT DELETE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (PREVIOUS SOLUTION TO VERIFY) O(N^2) (with hash map could be N!!!!!!)
			/**
			ids[(i-1)*(dimension-2)+j-1]=board[i][j].id;
			for (int k=0 ; k< ((i-1)*(dimension-2)+j-1) ; k++) //O(N^2) (with hash map could be N!!!!!!)
				if (board[i][j].id==ids[k]) {
					counter++;
					break;
					//System.out.println("Matching id!!!!!!!!!!"+" "+ i + " " + j);
				}
				
				*/
			
		//Using ids as a hash table, verify if we have a value (ID) for that key in our hash table 
			if (!(ids[this.hash(board[i][j])]==-1)) { //piece already present in board
			counter++;
		}
			else ids[this.hash(board[i][j])]=board[i][j].id;
			
		
		}
	}
	System.out.println("Matching pieces in board: "+ counter);

	return;
}


public int calculate_edges() { //O(N)
	
	int edges=0;
	for (int i=1;i<this.dimension-1;i++) { //O(N)
		for (int j=1;j<this.dimension-1;j++) {
			if (board[i][j].id != -2) {
			
			int top;
			if (board[i-1][j].id!=-2)
			top=board[i-1][j].bottom;
			else
				top=-1;//any piece counts as unmatchable
			
			int left;
			if (board[i][j-1].id!=-2)
			left=board[i][j-1].right;
			else
				left=-1;//any piece
			
			int right;
			if (board[i][j+1].id==-2)
				right=-1;//any piece
			
			int bottom;
			if (board[i+1][j].id!=-2)
			bottom=board[i+1][j].top;
			else
				bottom=-1;//any piece
			
			
			
			if (j==this.dimension-2) { //last in row
				right=0;
				if(i==this.dimension-2) { //last line and last in row
					bottom=0;
					if (board[i][j].top==top)
						edges++;
					if (board[i][j].left==left)
						edges++;
					if (board[i][j].bottom==bottom)
					    edges++;
					if (board[i][j].right==right)
						edges++;
					
				}
				else {//last in row but not last line
					if (board[i][j].top==top)
						edges++;
					if (board[i][j].left==left)
						edges++;
					if (board[i][j].right==right)
						edges++;
				}
			}
			else {//not last row
				if(i==this.dimension-2) { //last line but not last in row
					bottom=0;
					if (board[i][j].top==top)
						edges++;
					if (board[i][j].left==left)
						edges++;
					if (board[i][j].bottom==bottom)
					    edges++;
					
				}
				else {//not last in row and not last line
					if (board[i][j].top==top)
						edges++;
					if (board[i][j].left==left)
						edges++;
				}
			}
			
			}
			
		}
		}
	System.out.println("Matching edges: " + edges);
	return edges;
}


public void place_pieces_back() {//O(N)
	for (int i=1;i<this.dimension-1;i++) { //O(N)
		for (int j=1;j<this.dimension-1;j++) {
			pieces.enqueue(this.board[i][j]);
			this.board[i][j]=new Piece(-2,0,0,0,0);
		}
	}
} 

//***********BUILDING PUZZLE*************************************************************
	
public void build() { //O(N^2)

		
		//shuffle pieces 
	this.shuffle_pieces(); 
	
		//build border

					Piece border=new Piece();
					for (int i=0;i<this.dimension;i++) {  
						board[0][i]=border;
						board[this.dimension-1][i]=border;
						board[i][0]=border;
						board[i][this.dimension-1]=border;
					}
				
		//Build lines (all but the first and last one in the board, which are only border pieces)
	    int built_lines=1; //since technically we built the first line of the board which is the border
	    
	    int errors=0;
	   //counter that keeps track of the number of times we had to backtrack (remake a line we already started)
	   this.build_line(errors, built_lines);//builds lines recursively, if no piece fits, redo line with different pieces, if no match after 500 tries, place any piece
	   this.place_leftover(); 
	   int attempt_counter=1;
	   System.out.println("PREVIOUS ATTEMPTS:");
	  
	  // int totedges=0;
	  while (this.calculate_edges()<430){ // O(N^2) //430 edges has a very high speed execution, 440 is a viable choice but run-time can be longer than a minute in some cases
		   //while (attempt_counter<1000){ //totedges+=this.calculate_edges();
	   
		  this.place_pieces_back(); //O(N)
		  this.shuffle_pieces(); //O(N)
		  built_lines=1;
		  errors=0;
		  this.notplaced=0;
		  
		  this.build_line(errors, built_lines); //O(N^2)
		  this.place_leftover(); //O(N^2)
		  attempt_counter++;
	   }
	
	   System.out.println();
	   System.out.println("---------------------------------");
	   System.out.println("TOTAL ATTEMPTS: "+ attempt_counter);
	  // System.out.println("Average of matching edges : " + (totedges/10000) + " ;");
	   System.out.println("NUMBER OF TIMES A LINE WAS BACKTRACKED : " + backtrack_counter + " ;");
	   this.print_puzzle(); //O(N)
		return;
	}


public void build_line(int errors, int built_lines) {//O(N^2)
		
		if (built_lines==this.dimension-1) {

			return;
		}
		

			
		for (int i=1;i<dimension-1;i++) { //O(N)
			
			boolean found=false;
			int count=0;
			while(count<=pieces.size) { //O(N^2)
				if (fits( pieces.first.item, built_lines,i)) {
					board[built_lines][i]=pieces.dequeue();
				found=true;
				break;
				}
				else {
					pieces.enqueue(pieces.dequeue());
					count++;
				}
		}
			
			if (!found & errors<500) { //500 seems to be optimal optimal //O(N)
				
				for (int j=i-1;j>0;j--) {
				pieces.enqueue(board[built_lines][j]);
				board[built_lines][j]=null;
				}
				errors=errors+1;
				//System.out.println("Redo Line " + built_lines + " !");
				backtrack_counter++;
				built_lines=built_lines-1;
				break;
			}
		
			
			if (!found & errors>=500)//place an any piece and consider that we cannot place one of our pieces
			{
				notplaced++;
				board[built_lines][i]=new Piece(-2,0,0,0,0);//place the "any" piece
				
			}
			
			if (found & i==dimension-2) {
				errors=0;
			}
			
			
		}
		
		
	build_line(errors, ++built_lines);
}


//////////////////FITS FUNCTIONS/////////////////////////////////////////////////////////
public boolean fits( Piece a, int built_lines,int position) {

	if (board[built_lines-1][position].id == -2 | board[built_lines][position-1].id == -2 ) //detect if we have an any piece
		return fits_any(a, built_lines, position); 
	
		int top=board[built_lines-1][position].bottom;
		int left=board[built_lines][position-1].right;
		int right;
		
		if (built_lines==this.dimension-2){//we have to consider that bottom edges must be 0 for last row before border
			int bottom=0;
		if (position==dimension-2){//if last piece in row, right edge has to be 0
				right=0;
			for (int i=0;i<4;i++) {
				if (a.top==top & a.left==left & a.bottom==bottom & a.right==right)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
		}
		else { //if not last piece in row, dont check right edge
			for (int i=0;i<4;i++) {
				if (a.top==top & a.left==left & a.bottom==bottom)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
			
		}
		}
		else { //if not last row, do not check bottom edge
			if (position==dimension-2){//if last piece in row, right edge has to be 0
				right=0;
			for (int i=0;i<4;i++) {
				if (a.top==top & a.left==left & a.right==right)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
			}
			else { //if not last piece in row, only check top and left edges
				for (int i=0;i<4;i++) {
					if (a.top==top & a.left==left)
						return true;
					else
						Piece.rotate(a);
					}
					return false;
			}
		}
}


public boolean fits_any(Piece a, int built_lines,int position) {
	int top=board[built_lines-1][position].bottom;
	int left=board[built_lines][position-1].right;
	int right;
	
	if (board[built_lines-1][position].id == -2 & board[built_lines][position-1].id == -2) { //if left and top piece are any (dont check left and top edge since its any)

		if (built_lines==this.dimension-2){//we have to consider that bottom edges must be 0 for last row before border
			int bottom=0;
		if (position==dimension-2){//if last piece in row, right edge has to be 0
				right=0;
			for (int i=0;i<4;i++) {
				if (a.bottom==bottom & a.right==right)
				return true;
				else
					Piece.rotate(a);
				}
				return false;
		}
		else { //if not last piece in row, dont check right edge
			for (int i=0;i<4;i++) {
				if ( a.bottom==bottom)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
			
		}
		}
		else { //if not last row, do not check bottom edge
			if (position==dimension-2){//if last piece in row, right edge has to be 0
				right=0;
			for (int i=0;i<4;i++) {
				if (a.right==right)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
			}
			else  
				return true;
			
			}
	}
	
else if (board[built_lines-1][position].id == -2) {//dont check the top since its any
		
	if (built_lines==this.dimension-2){//we have to consider that bottom edges must be 0 for last row before border
		int bottom=0;
	if (position==dimension-2){//if last piece in row, right edge has to be 0
			right=0;
		for (int i=0;i<4;i++) {
			if (a.left==left & a.bottom==bottom & a.right==right)
				return true;
			else
				Piece.rotate(a);
			}
			return false;
	}
	else { //if not last piece in row, dont check right edge
		for (int i=0;i<4;i++) {
			if ( a.left==left & a.bottom==bottom)
				return true;
			else
				Piece.rotate(a);
			}
			return false;
		
	}
	}
	else { //if not last row, do not check bottom edge
		if (position==dimension-2){//if last piece in row, right edge has to be 0
			right=0;
		for (int i=0;i<4;i++) {
			if (a.left==left & a.right==right)
				return true;
			else
				Piece.rotate(a);
			}
			return false;
		}
		else { //if not last piece in row, only check top and left edges
			for (int i=0;i<4;i++) {
				if (a.left==left)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
		}
	}
	}
	
else {// if board[built_lines][position-1].id == -2 dont check left edge since its any

		if (built_lines==this.dimension-2){//we have to consider that bottom edges must be 0 for last row before border
			int bottom=0;
		if (position==dimension-2){//if last piece in row, right edge has to be 0
				right=0;
			for (int i=0;i<4;i++) {
				if (a.top==top & a.bottom==bottom & a.right==right)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
		}
		else { //if not last piece in row, dont check right edge
			for (int i=0;i<4;i++) {
				if ( a.top==top & a.bottom==bottom)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
			
		}
		}
		else { //if not last row, do not check bottom edge
			if (position==dimension-2){//if last piece in row, right edge has to be 0
				right=0;
			for (int i=0;i<4;i++) {
				if (a.top==top & a.right==right)
					return true;
				else
					Piece.rotate(a);
				}
				return false;
			}
			else { //if not last piece in row, only check top 
				for (int i=0;i<4;i++) {
					if (a.top==top)
						return true;
					else
						Piece.rotate(a);
					}
					return false;
			}
		}
		}
	
	
}
//////////////////////////////////////////////////////////////////////////////////////////


//***************************************************************************************

}