package core;

public class Game_Grid {
	
	private int [] board_data;
	private int stride = 8;
	public Game_Grid(){
		board_data = new int[64];
	}
	
	public void Apply_Move(Game_Move move){
		
	}
	public void Build_Grid(byte[] aBuffer){
		String s = new String(aBuffer).trim();
        String[] boardValues;
        boardValues = s.split(" ");

		for(int i = 0;i< 64 ;i++){
			board_data[i] = Integer.parseInt(boardValues[i]);
		}
	}
	public void PrintCmd(){
		for(int i = 0;i< 64 ;i++){
			if(i%stride == 0 && i !=0){
				System.out.print('\n');
			}
			System.out.print(board_data[i]);
		}
		
	}
	
}
