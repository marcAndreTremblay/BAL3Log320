package core;

public class Game_Move {
	String S_From;
	String S_To;
	int From;
	int To;
	
	private int ParseStringToInt(String current){
		String best = current.trim();
		int value = -1;
		int row  = -1;
		int col  = -1;
		switch(best.charAt(0)) {
			case 'A' : col  = 0;{break;}
			case 'B' : col  = 1;{break;}
			case 'C' : col  = 2;{break;}
			case 'D' : col  = 3;{break;}
			case 'E' : col  = 4;{break;}
			case 'F' : col  = 5;{break;}
			case 'G' : col  = 6;{break;}
			case 'H' : col  = 7;{break;}
		}
		switch(best.charAt(1)) {
			case '1' : row = 7;{break;}
			case '2' : row = 6;{break;}
			case '3' : row = 5;{break;}
			case '4' : row = 4;{break;}
			case '5' : row = 3;{break;}
			case '6' : row = 2;{break;}
			case '7' : row = 1;{break;}
			case '8' : row = 0;{break;}
		}
		
		return row*8 + col;
	}
	public Game_Move(String [] move_info){
		S_From  =move_info[0];
		S_To  =move_info[1];
		
		this.From = ParseStringToInt(this.S_From);
		this.To = ParseStringToInt(this.S_To);
	}
	public void Print(){
		System.out.print("\n Coup de : "+  S_From +"|"+From+"| "+ "À : "+ S_To+"|"+To+"| "); 
		
	}
}
