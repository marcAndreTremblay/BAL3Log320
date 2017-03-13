package core;


public class Main {

	
	//Important : Pour tester il faut lancer l'application serveur du prof dans le projet nomé LAB3Serveur + .exe
	public static void main(String[] args) throws InterruptedException {
			
		
		Game_Grid test_grid = new Game_Grid();
		test_grid.Build_Grid();
		
		test_grid.PrintCmd();
		
		Thread.sleep(1000);
		
			Game_Instance game = new Game_Instance();	
			game.Start_Session();
			
			
			
			
			
	}

}
