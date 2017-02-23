package core;

import java.util.LinkedList;
import java.util.List;

public class Game_Grid {
	
	private int [] white_atk_pos_index= new int[64];;
	private int [] black_atk_pos_index= new int[64];;
	
	
	private int [] board_data;
	private int stride = 8;
	public Game_Grid(){

		board_data = new int[64];
	}
	
	public int GenerateGridHeristiqueValue(int player_value){
		
		return 0;
	}
	
	public List<Game_Move> GetAvailableMove(int player){
		List<Game_Move> result = new LinkedList<Game_Move>();		
		for(int i = 0; i< 64 ; i++)
		{
			if(this.board_data[i] == player){
				if(this.board_data[i] == 2){ //Noir
					
					//next row min and max
					int min  = (i - i %stride)+this.stride;
					int max = (min+stride-1);
					
					int bottom_s = i + this.stride;
					int bottom_l = i + this.stride - 1;
					int bottom_r = i + this.stride + 1;
					
					//Check bottom move
					if(bottom_s < 64){ //Check if is in play
						if(this.board_data[bottom_s] == 0){
							result.add(new Game_Move(i,bottom_s));
						}
					}
					//Check bottom left move
					if(bottom_l >= min){
						if(this.board_data[bottom_l] == 0 || this.board_data[bottom_l] == 4){
							result.add(new Game_Move(i,bottom_l));
							
						}
					}
					//Check bottom right move
					if(bottom_r <= max && bottom_r < 64){
						if(this.board_data[bottom_r] == 0 || this.board_data[bottom_r] == 4){
							result.add(new Game_Move(i,bottom_r));
							
						}
					}
				}
				if(this.board_data[i] == 4){ //White
					//next row min and max
					int min  = (i - i %stride)-this.stride;
					int max = (min+stride-1);
					
					int top_s = i - this.stride;
					int top_l = i - this.stride - 1;
					int top_r = i - this.stride + 1;
					
					//Check top straith move
					if(top_s >= 0){ //Check if is in play
						if(this.board_data[top_s] == 0){
							result.add(new Game_Move(i,top_s));
						}
					}
					//Check top left move
					if(top_l >= min && top_l >= 0){
						if(this.board_data[top_l] == 0 || this.board_data[top_l] == 2){
							result.add(new Game_Move(i,top_l));
							
						}
					}
					//Check bottom right move
					if(top_r <= max ){
						if(this.board_data[top_r] == 0 || this.board_data[top_r] == 2){
							result.add(new Game_Move(i,top_r));
							
						}
					}
				}
			}
		}
		return result;
	}
	public void Apply_Move(Game_Move move){
		this.board_data[move.To]  =this.board_data[move.From];
		this.board_data[move.From]  =0;
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
		System.out.print('\n');
		for(int i = 0;i< 64 ;i++){
			if(i%stride == 0 && i !=0){
				System.out.print('\n');
			}
			System.out.print(board_data[i]);
		}
		System.out.print('\n');
		for(int i = 0;i< 64 ;i++){
			if(i%stride == 0 && i !=0){
				System.out.print('\n');
			}
			System.out.print(black_atk_pos_index[i]);
		}
		System.out.print('\n');
		for(int i = 0;i< 64 ;i++){
			if(i%stride == 0 && i !=0){
				System.out.print('\n');
			}
			System.out.print(white_atk_pos_index[i]);
		}
		System.out.print('\n');
	}
	
}
