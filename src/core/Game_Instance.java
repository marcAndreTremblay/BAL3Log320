package core;


import java.io.*;
import java.net.*;
import java.util.List;

import javax.management.timer.Timer;

public class Game_Instance {

	Game_Grid game_grid;
	int max_player= 0; //Note(Marc): 0 -> Unknown, 4 -> White, 2 -> Black
	int min_player = 0;
	Socket MyClient; //Todo(Marc) : Rename client_socket
	BufferedInputStream input;  //Todo(Marc) : Rename socket_input
	BufferedOutputStream output;//Todo(Marc) : Rename socket_output
	BufferedReader console;
   
	FastTimer timer;
	
	public Game_Instance(Game_Grid offline_grid , int max_player, int min_player){
		this.game_grid = offline_grid;
		this.max_player = max_player;
		this.min_player = min_player;
		this.timer = new FastTimer();
	}
	
	public Game_Instance(){
		try {
			this.timer = new FastTimer();
			this.game_grid = new Game_Grid();
			this.MyClient = new Socket("localhost", 8888);
			this.input    = new BufferedInputStream(MyClient.getInputStream());
			this.output   = new BufferedOutputStream(MyClient.getOutputStream());
			this.console = new BufferedReader(new InputStreamReader(System.in)); 
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	private static void PrintMoveList(List<Game_Move> move_list){
		int cpt = 0;
		System.out.print("\n");
		for(Game_Move i :move_list){
			System.out.print("# "+cpt);
			i.Print();
			cpt++;
		}
	}
	
	
	private int MinMax(Game_Grid grid, int player , int dept ){
		if(grid.IsFinal(player) == true || dept == 1){
			if(player == min_player){
				return grid.GenerateGridHeristiqueValue(min_player) - grid.GenerateGridHeristiqueValue(max_player);
			}
			else{
				return grid.GenerateGridHeristiqueValue(max_player) - grid.GenerateGridHeristiqueValue(min_player);	
			}
		}
		if(player == max_player){
			int max_score = -99999999;
			List<Game_Move> move_list= game_grid.GetAvailableMove(max_player);
			
			for(Game_Move current : move_list){
				
				game_grid.Apply_Move(current);
				int score = MinMax(game_grid,min_player,dept);
				if(score > max_score){ 
					max_score = score;
					}
				game_grid.Undo_Move(current);
				
			}
			return  max_score;
		}
		if(player == min_player){
			int min_score = 99999999;
			List<Game_Move> move_list= game_grid.GetAvailableMove(min_player);
			dept++;
			for(Game_Move current : move_list){
				game_grid.Apply_Move(current);
				
				int score = MinMax(game_grid,max_player,dept);
				if(score < min_score){ 
					min_score = score;
					}
				game_grid.Undo_Move(current);
			}
			return  min_score;
		}
		
		return 0;
	}
	public Game_Move CalculateNextMove(){
		Game_Move best_move = new Game_Move();
		int Best_score = -99999999;
		List<Game_Move> move_list= game_grid.GetAvailableMove(max_player);
		System.out.println(move_list.size());
		timer.StartTime();
		for(Game_Move current : move_list){
			game_grid.Apply_Move(current);
			int score = MinMax(game_grid,min_player,0);
			
			if(score > Best_score){
				best_move = current;
				Best_score = score;
			}
			game_grid.Undo_Move(current);
		}
		timer.StopTimeAndPrint();
		best_move.Print();
		System.out.println("score : "+Best_score);
		return best_move;
	}
	

		public Game_Move alpha_beta_MiniMax(){
			Game_Move best_move = alpha_beta_max(this.game_grid, -1000000000, 1000000000,2,null);			 	
			return best_move;
		}
		public Game_Move alpha_beta_min(Game_Grid grid,int alpha, int beta,int dept,Game_Move move){
			if((grid.IsFinal(min_player) == true || dept == 0) &&  move != null){
				move.SetMoveValue(grid.GenerateGridHeristiqueValue(min_player));
				return move;
			}
			dept--;
			List<Game_Move> move_list= game_grid.GetAvailableMove(min_player);
			Game_Move new_b_move = null;
			int min_score = 99999999;
			for(Game_Move current : move_list){
				game_grid.Apply_Move(current);
				Game_Move move_result  = alpha_beta_max(game_grid,alpha,beta,dept,current);		
				game_grid.Undo_Move(current);
				min_score = Math.min(move_result.MoveValue, min_score);
				if(min_score < alpha){
					return current;
				}
				beta = Math.min(beta, min_score);
				new_b_move = current;
				
			}
			
			return new_b_move;
		}
		public Game_Move alpha_beta_max(Game_Grid grid,int alpha, int beta,int dept,Game_Move move){ 				
			if((grid.IsFinal(max_player) == true || dept == 0 ) &&  move != null){
				move.SetMoveValue(grid.GenerateGridHeristiqueValue(max_player));
				return move;
			}
			List<Game_Move> move_list= game_grid.GetAvailableMove(max_player);
			Game_Move new_b_move = null;
			int max_score = -99999999;
			for(Game_Move current : move_list){
				game_grid.Apply_Move(current);
				Game_Move move_result = alpha_beta_min(game_grid,alpha,beta,dept,current);
				game_grid.Undo_Move(current);
				max_score = Math.max(move_result.MoveValue, max_score);
				if ( max_score  > beta){
					return current;
				}
				alpha = Math.max(alpha, max_score);
				new_b_move = current;
				
			}
			return new_b_move;
		}
		private void OnCommandReceive(char cmd){
		try {
			switch(cmd){
				case '1':{	//Note(Marc): Starting game with white 
					  byte[] aBuffer = new byte[1024];						
					  int size = input.available();
					  input.read(aBuffer,0,size);
		               					
		                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : \n");
		                max_player = 4;
		                min_player = 2;
		                game_grid.Build_Grid(aBuffer);
		                game_grid.PrintCmd();
		               Game_Move selected_move =this.CalculateNextMove(); 
		             // Game_Move selected_move =this.alpha_beta_MiniMax();
		                selected_move.Print();
						this.game_grid.Apply_Move(selected_move);
						this.game_grid.PrintCmd();
						
				
						String move_message = selected_move.ToMessage();
						output.write(move_message.getBytes(),0,move_message.length());
						output.flush();
					break;} 
				case '2':{	//Note(Marc): Starting game with black 
	                byte[] aBuffer = new byte[1024];
					int size = input.available();
					input.read(aBuffer,0,size);
	                

					System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des Noir\n");
					max_player = 2;
					min_player = 4;
					
					
					game_grid.Build_Grid(aBuffer);
	                game_grid.PrintCmd();
	                
	                
					break;} 
				case '3':{	//Note(Marc): Server ask for next move , with last move in memory
					
					byte[] aBuffer = new byte[16];
					int size = input.available();
					input.read(aBuffer,0,size);
					String trim_s = new String(aBuffer);
					String[] moves_info = trim_s.split("-");
					
					Game_Move last_move = new Game_Move(moves_info);					
						last_move.Print();
					this.game_grid.Apply_Move(last_move);
					this.game_grid.PrintCmd();
					
					 Game_Move selected_move =this.CalculateNextMove(); 
		             //Game_Move selected_move =this.alpha_beta_MiniMax();
					selected_move.Print();
					//Todo(Check best move); availalle
									
						this.game_grid.Apply_Move(selected_move);
						this.game_grid.PrintCmd();
						
				
					String move_message = selected_move.ToMessage();
					output.write(move_message.getBytes(),0,move_message.length());
					output.flush();
					break;}
				case '4':{	//Note(Marc): Invalid last move , serveur ask for a move
					System.out.println("Coup invalide, entrez un nouveau coup : ");
			       	String move = null;
					move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					break;}
			}
		} catch (IOException e) {
			 System.out.println(e);
			 e.printStackTrace();
		}
	}
	public void Start_Session(){
		 try {
			 while(1 == 1){
				 char cmd = 0;
				 cmd = (char)input.read();
				 OnCommandReceive(cmd);

			}
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	