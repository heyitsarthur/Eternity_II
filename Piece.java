
public class Piece {

	public int id;
	public int top;
	public int bottom;
	public int left;
	public int right;
	public int rotation;
	
//-----------------------------------HASH PIECE ACCORDING TO ID-------------------------	
	
public int hash_piece() { //since the hash table will be used when verifying if we have matching pieces (based on id) we want to hash pieces according to id
	return this.id;
}


	
//--------------------------------------------------------------------------------------

	public Piece () {
		this.id=-1;
		this.top=0;
		this.bottom=0;
		this.left=0;
		this.right=0;
		this.rotation=0;
		//System.out.println("PIECE CREATED ID :"+this.id);
    
	}
	
	public Piece (int ident, int t, int r, int b, int l) {
		this.id=ident;
		this.top=t;
		this.bottom=b;
		this.left=l;
		this.right=r;
		this.rotation=0;
		//System.out.println("PIECE CREATED ID :"+this.id);
        
	}
	
	public static void rotate(Piece a) { //rotate piece 90 degrees to the right.
		a.rotation+=1;
		int temp;
		
		temp=a.top;
		a.top=a.left;
		a.left=temp;
		
		temp=a.top;
		a.top=a.bottom;
		a.bottom=temp;
		
		temp=a.top;
		a.top=a.right;
		a.right=temp;
		
		if (a.rotation>3) {a.rotation=a.rotation-4;}
		
	}
	
	public void equals (Piece b) {
			this.id=b.id;
			this.top=b.top;
			this.bottom=b.bottom;
			this.left=b.left;
			this.right=b.right;
			this.rotation=b.rotation;
	 }
 
}
