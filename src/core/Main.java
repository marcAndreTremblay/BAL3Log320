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
			
		int [] data_1 = { 
				0,0,0,4,0,0,0,0, // 2 start	
				0,0,0,0,0,0,0,0, 
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,0,0,0,0,0,
				0,0,0,2,0,0,0,0};// 4 start	
		Game_Grid test_grid_1 = new Game_Grid(data_1);
		
		List<Game_Move> black_2_move = test_grid_1.GetAvailableMove(2);
		System.out.println("\nBlack 2 move");
		System.out.println("Move cpt : " + black_2_move.size());
		PrintMoveList(black_2_move);
		
		List<Game_Move> white_4_move = test_grid_1.GetAvailableMove(4);
		System.out.println("\nWhite 4 move");
		System.out.println("Move cpt : " + white_4_move.size());
		PrintMoveList(white_4_move);
		
		test_grid_1.PrintCmd();
		
		int black_h_value =  test_grid_1.GenerateGridHeristiqueValue(2);
		int white_h_value = test_grid_1.GenerateGridHeristiqueValue(4);

		System.out.println("\nWhite board value  : " + white_h_value + "\nBlack board value  : " + black_h_value);
		
		Thread.sleep(1000);
		
		//	Game_Instance game = new Game_Instance();	
		//	game.Start_Session();
			
			
			
			
			
	}

}
