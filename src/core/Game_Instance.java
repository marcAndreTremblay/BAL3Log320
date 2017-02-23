package core;


import java.io.*;
import java.net.*;
import java.util.List;

public class Game_Instance {

	Game_Grid game_grid;
	int current_color = 0; //Note(Marc): 0 -> Unknown, 4 -> White, 2 -> Black
	
	Socket MyClient; //Todo(Marc) : Rename client_socket
	BufferedInputStream input;  //Todo(Marc) : Rename socket_input
	BufferedOutputStream output;//Todo(Marc) : Rename socket_output
	BufferedReader console;
   
	
	public Game_Instance(){
		try {
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
		for(Game_Move i :move_list){
			System.out.print("# "+cpt);
			i.Print();
			cpt++;
		}
	}
	private Game_Move CalculateNextMove(){
		List<Game_Move> move_list= game_grid.GetAvailableMove(this.current_color);
		
		
		
		
		
		
		Game_Move selected_move = move_list.get(0);
		return selected_move;
	}
	private void OnCommandReceive(char cmd){
		try {
			switch(cmd){
				case '1':{	//Note(Marc): Starting game with white 
					  byte[] aBuffer = new byte[1024];						
					  int size = input.available();
					  input.read(aBuffer,0,size);
		               					
		                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : \n");
		                current_color = 4;
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
	                

					System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs\n");
					current_color = 2;
					
					
					
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
