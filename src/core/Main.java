package core;

import java.util.List;

public class Main {

	
	public static void PrintMoveList(List<Game_Move> moves){
		for(Game_Move c_move : moves){
			c_move.Print();
			System.out.print("\n");
		}
	}
	
	//Important : Pour tester il faut lancer l'application serveur du prof dans le projet nomé LAB3Serveur + .exe
	public static void main(String[] args) throws InterruptedException {
			
		boolean Offline = false;
		if(Offline == true){

			int [] data_2 = { 
					0,0,0,2,0,0,0,2, // 2 start	
					0,0,0,0,0,0,0,0, 
					0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,
					0,0,0,0,0,0,0,0,
					0,0,4,0,0,0,0,0,
					0,0,0,0,0,4,0,0};// 4 start	
			Game_Grid test_grid_1 = new Game_Grid(data_2);
			List<Game_Move> test_move_availaible = test_grid_1.GetAvailableMove(2);
			PrintMoveList(test_move_availaible);
			

			
			Game_Instance offline_game = new Game_Instance(test_grid_1,2,4);	
			

			Game_Move found_move2 = offline_game.alpha_beta_MiniMax();
			System.out.println("\nDone ");
			found_move2.Print();
			System.out.println("\n "+found_move2.MoveValue);
			offline_game.game_grid.Apply_Move(found_move2);
			test_grid_1.PrintCmd();
			
			
		}
		else{
			Game_Instance game = new Game_Instance();	
			game.Start_Session();
			
			
			
		}
		

		
		
		
			
			
			
	}

}
