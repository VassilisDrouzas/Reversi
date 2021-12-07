import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Thread;

public class Main {
	
	public static final boolean DeBug = true; 
    
    public static Scanner scanner = new Scanner(System.in);
    
    public static void printPlayer(Tile.State GameState)
    {
        if (GameState == Tile.State.X)
        {                                                   
        	System.out.println("X is Playing!!!");
        }
        else if (GameState == Tile.State.O)
        {
        	System.out.println("O is Playing!!!");
        }
    }

    public static void main(String[] args)
    {
    	
        Board board = new Board();
        Tile.State GameState = Tile.State.X;

        GamePlayer human;
        GamePlayer computer;
        
        System.out.println("How much depth do you want?");
        int depth = scanner.nextInt();
        scanner.nextLine();

        System.out.println();
        
        
        // The first player to play will have the X disks 
        System.out.print("Would you like to play first? (y for yes, anything else for no)");
        String answer = scanner.nextLine();
        if (answer.equals("y") || answer.equals("Y")){
            human = new GamePlayer(Tile.State.X);
            computer = new GamePlayer(Tile.State.O);
        }
        else{
            computer = new GamePlayer(Tile.State.X);
            human = new GamePlayer(Tile.State.O);
        }
        
        // Print the board so the user can see the starting board.
        printPlayer(GameState);
        board.print();
        
        while(!board.isTerminal())
        {
        	// Note: the Play() method modifies the board.
        	
        	//It's human's turn.
            if(human.Turn(GameState))
            {                                        
                human.Play(board); 
            }
            else //It's computer's turn.
            {                                                                  
                System.out.println("It's the computer's turn now.");
                
                try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                computer.PlayAI(board, depth);
            }
                        
            //Change who is Playing
            if (GameState == Tile.State.X)
            {                                                   
            	GameState = Tile.State.O;
            }
            else if (GameState == Tile.State.O)
            {
                GameState = Tile.State.X;
            }
            
            printPlayer(GameState);
            board.print();
            System.out.println("X: " + board.Count(Tile.State.X) + " | O: " + board.Count(Tile.State.O) + "\n");

        }
        
        System.out.println("");
        
        int score = board.evaluate();   
        if(score > 0)
        {
        	System.out.println("The X Won");
        }
        else if (score < 0)
        {
        	System.out.println("The O Won");
        }
        else
        {
        	System.out.println("TIE!!!");
        }

    }


}