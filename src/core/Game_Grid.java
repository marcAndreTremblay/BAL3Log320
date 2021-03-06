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
	public Game_Grid(int [] data){
		
		board_data = data;
	}
	public int GenerateGridHeristiqueValue(int player_value){
		int value = 0;
		if(this.IsFinal(player_value)){
			return 99999999;
		}
		
		//Check le nombre de pion
		value += CountPion(player_value)*100;	
		if(player_value == 4){
			int count = 0;
			for(int i = 0;i<64;i++){
				if(board_data[i] == player_value){					
					//next row min and max
					int min  = (i - i %stride)+this.stride;
					int max = (min+stride-1);
					int top_s = i - this.stride;
					int top_l = i - this.stride - 1;
					int top_r = i - this.stride + 1;
					
					int bottom_s = i + this.stride;
					int bottom_l = i + this.stride - 1;
					int bottom_r = i + this.stride + 1;
					//Check bottom left move
				
						if((bottom_l >= min && bottom_l < 64 && this.board_data[bottom_l] == 4) || 
								(bottom_r <= max && bottom_r < 64 && this.board_data[bottom_r] == 4)){
							count++;			
						}
					
					
				}
			}
			value += count*70;
			value += CountFilePowerValue(player_value);
		}
		if(player_value ==2){	
			int count = 0;
			for(int i = 0;i<64;i++){
				if(board_data[i] == player_value){
					int min  = (i - i %stride)-this.stride;
					int max = (min+stride-1);
					
					int top_s = i - this.stride;
					int top_l = i - this.stride - 1;
					int top_r = i - this.stride + 1;
					

					//Check top left move
					if((top_l >= min  && top_l <= max && top_l >= 0 && this.board_data[top_l] == 2) || 
							top_r >= min && top_r <= max && top_r >= 0 && this.board_data[top_r] == 2){							
							count++;
						}
					}
				
			}
			value += count*70;
			value += CountFilePowerValue(player_value);
		}	
		
		return value;
	}
	public int CountPawnAtkValue(int player , int grid_index){
		board_data[grid_index] = 0; 
		//To do maybe we should keep the data in the atk poss grid and only querry the index for the number of atker
		return 0;
	}
	private int CountFilePowerValue(int player_value){
		int mid_c_value= 0 ;
		for(int i = 1;i<64;i=i+stride){
			if(board_data[i] == player_value){
				mid_c_value += 50;
			}
		}		
		for(int i = 3;i<64;i=i+stride){
			if(board_data[i] == player_value){
				mid_c_value += 10;
			}
		}
		for(int i = 4;i<64;i=i+stride){
			if(board_data[i] == player_value){
				mid_c_value += 10;
			}
		}
		for(int i = 6;i<64;i=i+stride){
			if(board_data[i] == player_value){
				mid_c_value += 50;
			}
		}
		return mid_c_value;
	}
	private int CountPion(int Player){
		int count = 0;
		for(int i = 0;i<64;i++){
			if(board_data[i] == Player){
				count++;
			}
		}
		return count;
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
							result.add(new Game_Move(player,i,bottom_s));
						}
					}
					//Check bottom left move
					if(bottom_l >= min && bottom_l < 64 ){
						if(this.board_data[bottom_l] == 0 || this.board_data[bottom_l] == 4){
							result.add(new Game_Move(player,i,bottom_l));
			
						}
					}
					//Check bottom right move
					if(bottom_r <= max && bottom_r < 64){
						if(this.board_data[bottom_r] == 0 || this.board_data[bottom_r] == 4){
							result.add(new Game_Move(player,i,bottom_r));
					
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
							result.add(new Game_Move(player,i,top_s));
						}
					}
					//Check top left move
					if(top_l >= min  && top_l <= max && top_l >= 0){
						if(this.board_data[top_l] == 0 || this.board_data[top_l] == 2){
							result.add(new Game_Move(player,i,top_l));
						
						}
					}
					//Check bottom right move
					if(top_r >= min && top_r <= max && top_r >= 0){
						if(this.board_data[top_r] == 0 || this.board_data[top_r] == 2){
							result.add(new Game_Move(player,i,top_r));
			
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
	public void Undo_Move(Game_Move move){
		this.board_data[move.From]  = move.owner_player;
		if(move.is_atk == true){
			if(move.owner_player == 4){
				this.board_data[move.To] = 2;
			}
			if(move.owner_player == 2){
				this.board_data[move.To] = 4;
			}
			
		}	
		else{
			this.board_data[move.To] = 0;
		}
	}
	public boolean IsFinal(int player){
		boolean won_check = false;
		if(player == 2){
			for(int i  = 63; i> 55;i--){
				if(board_data[i] == 2){
					won_check = true;
				}
			}
		}
		if(player == 4){
			for(int i  = 0; i<8;i++){
				if(board_data[i] == 4){
					won_check = true;
				}
			}
		}
		return won_check;
	}
	public void Build_Grid(byte[] aBuffer){
		String s = new String(aBuffer).trim();
        String[] boardValues;
        boardValues = s.split(" ");

		for(int i = 0;i< 64 ;i++){
			board_data[i] = Integer.parseInt(boardValues[i]);
		}
	}
	public void Build_New_Grid_Grid(){
		for(int i = 0 ;i<64;i++){
			if(i >= 0 && i < 16){
				board_data[i] = 2;
			}
			if(i >= 48 && i < 64){
				board_data[i] = 4;
			}
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
	/*	for(int i = 0;i< 64 ;i++){
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
		*/
		System.out.print('\n');
		
		
	}
	public void PrintMoveValue(int depth, List<Game_Move> moves){
		for(Game_Move move : moves){
			
		}
	}
}
