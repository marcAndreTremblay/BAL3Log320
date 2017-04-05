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
		if(grid.IsFinal(player) == true 
				|| dept == 1){
			return grid.GenerateGridHeristiqueValue(max_player);
		}
		if(player == max_player){
			int max_score = -99999988;
			List<Game_Move> move_list= game_grid.GetAvailableMove(max_player);
			dept = dept+ 1;
			for(Game_Move current : move_list){
				
				game_grid.Apply_Move(current);
				int score = MinMax(game_grid,min_player,dept);
			//	System.out.println("Max : " + dept);
			//	current.Print();
			//	System.out.println("Score : "+ score);
				
				if(score > max_score){ 
					max_score = score;
					}
				game_grid.Undo_Move(current);
				
			}
		//	System.out.println("	Selected score  : " +max_score );
	
			return  max_score;
		}
		if(player == min_player){
			int min_score = 99999988;
			List<Game_Move> move_list= game_grid.GetAvailableMove(min_player);
			
			for(Game_Move current : move_list){
				game_grid.Apply_Move(current);
				
				int score = MinMax(game_grid,max_player,dept);
			//	System.out.print("Min : " + dept);
			//	current.Print();
			//	System.out.println("Score : "+ score);
				if(score < min_score){ 
					min_score = score;
					}
				game_grid.Undo_Move(current);
			}
		//	System.out.println("	Selected score  : " +min_score );
			
			return  min_score;
		}
		
		return 0;
	}
	public Game_Move CalculateNextMove(){
		Game_Move best_move = new Game_Move();
		int Best_score = -1000000000;
		List<Game_Move> move_list= game_grid.GetAvailableMove(max_player);
		System.out.println(move_list.size());
		timer.StartTime();
		for(Game_Move current : move_list){
			game_grid.Apply_Move(current);
			int score = MinMax(game_grid,min_player,0);
			current.Print();
			System.out.println("Score : "+ score);
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
			Game_Move best_move = new Game_Move();
			//Start with a min
			List<Game_Move> move_list= game_grid.GetAvailableMove(max_player);
			 	
			return best_move;
		}
		public void alpha_beta_min(Game_Grid grid,int alpha, int beta){
			
		}
		public void alpha_beta_max(Game_Grid grid,int alpha, int beta){ 	
			
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
	