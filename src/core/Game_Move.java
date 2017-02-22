package core;

public class Game_Move {
	String S_From;
	String S_To;
	int From;
	int To;
	private boolean form;
	
	private String  ParseIntToString(int index){
		String text = new String();
		
		switch(index) {
		case 0 :{ text  = "A8";break;}
		case 1 :{ text  = "B8";break;}
		case 2 :{ text  = "C8";break;}
		case 3 :{ text  = "D8";break;}
		case 4 :{ text  = "E8";break;}
		case 5 :{ text  = "F8";break;}
		case 6 :{ text  = "G8";break;}
		case 7 :{ text  = "H8";break;}

		case 8 :{ text  = "A7";break;}
		case 9 :{ text  = "B7";break;}
		case 10 :{ text  = "C7";break;}
		case 11 :{ text  = "D7";break;}
		case 12 :{ text  = "E7";break;}
		case 13 :{ text  = "F7";break;}
		case 14 :{ text  = "G7";break;}
		case 15 :{ text  = "H7";break;}

		case 16 :{ text  = "A6";break;}
		case 17 :{ text  = "B6";break;}
		case 18 :{ text  = "C6";break;}
		case 19 :{ text  = "D6";break;}
		case 20 :{ text  = "E6";break;}
		case 21 :{ text  = "F6";break;}
		case 22 :{ text  = "G6";break;}
		case 23 :{ text  = "H6";break;}
	
	
		case 24 :{ text  = "A5";break;}
		case 25 :{ text  = "B5";break;}
		case 26 :{ text  = "C5";break;}
		case 27 :{ text  = "D5";break;}
		case 28 :{ text  = "E5";break;}
		case 29 :{ text  = "F5";break;}
		case 30 :{ text  = "G5";break;}
		case 31 :{ text  = "H5";break;}

		case 32 :{ text  = "A4";break;}
		case 33 :{ text  = "B4";break;}
		case 34 :{ text  = "C4";break;}
		case 35 :{ text  = "D4";break;}
		case 36 :{ text  = "E4";break;}
		case 37 :{ text  = "F4";break;}
		case 38 :{ text  = "G4";break;}
		case 39 :{ text  = "H4";break;}

		case 40 :{ text  = "A3";break;}
		case 41 :{ text  = "B3";break;}
		case 42 :{ text  = "C3";break;}
		case 43 :{ text  = "D3";break;}
		case 44 :{ text  = "E3";break;}
		case 45 :{ text  = "F3";break;}
		case 46 :{ text  = "G3";break;}
		case 47 :{ text  = "H3";break;}
		

		case 48 :{ text  = "A2";break;}
		case 49 :{ text  = "B2";break;}
		case 50 :{ text  = "C2";break;}
		case 51 :{ text  = "D2";break;}
		case 52 :{ text  = "E2";break;}
		case 53 :{ text  = "F2";break;}
		case 54 :{ text  = "G2";break;}
		case 55 :{ text  = "H2";break;}
		

		case 56 :{ text  = "A1";break;}
		case 57 :{ text  = "B1";break;}
		case 58 :{ text  = "C1";break;}
		case 59 :{ text  = "D1";break;}
		case 60 :{ text  = "E1";break;}
		case 61 :{ text  = "F1";break;}
		case 62 :{ text  = "G1";break;}
		case 63 :{ text  = "H1";break;}
		}
		
		
		return text;
		
	}
	private int ParseStringToInt(String current){
		String best = current.trim();
		int value = -1;
		int row  = -1;
		int col  = -1;
		switch(best.charAt(0)) {
			case 'A' :{ col  = 0;break;}
			case 'B' :{ col  = 1;break;}
			case 'C' :{ col  = 2;break;}
			case 'D' :{ col  = 3;break;}
			case 'E' :{ col  = 4;break;}
			case 'F' :{ col  = 5;break;}
			case 'G' :{ col  = 6;break;}
			case 'H' :{ col  = 7;break;}
		}
		switch(best.charAt(1)) {
			case '1' :{ row = 7;break;}
			case '2' :{ row = 6;break;}
			case '3' :{ row = 5;break;}
			case '4' :{ row = 4;break;}
			case '5' :{ row = 3;break;}
			case '6' :{ row = 2;break;}
			case '7' :{ row = 1;break;}
			case '8' :{ row = 0;break;}
		}
		
		return row*8 + col;
	}
	public Game_Move(int from, int to){
		this.From = from;
		this.To= to;	
		form = true;
	}
	public Game_Move(String [] move_info){
		S_From  =move_info[0];
		S_To  =move_info[1];
		
		this.From = ParseStringToInt(this.S_From);
		this.To = ParseStringToInt(this.S_To);
		form = false;
	}
	public void Print(){
		if(form == true){
			System.out.println("Coup de :|"+From+"| "+ "À : |"+To+"| "); 
			
		}else{
			System.out.println("Coup de : "+  S_From +"|"+From+"| "+ "À : "+ S_To+"|"+To+"| "); 
		}
	}
	public String ToMessage(){
		return ParseIntToString(From)+""+ParseIntToString(To);
	}
}
