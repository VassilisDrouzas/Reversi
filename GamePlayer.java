import java.util.ArrayList;

public class GamePlayer {

    private Tile.State PlayerSymbol;

    public GamePlayer(Tile.State PlayerSymbol)
    {
        this.PlayerSymbol = PlayerSymbol;
    }

    public boolean Turn(Tile.State GameState)
    {
        return this.PlayerSymbol == GameState;
    }
    
    public void Play(Board board)
    {
    	// calculate all the possible moves
        ArrayList<Move> moves = board.possibleMoves(PlayerSymbol);
        
        if(Main.DeBug) // in DeBug mode print the possible moves
        {
       	 	for(Move m : moves)
       	 	{
       	 		System.out.println(m);
       	 	}
        }
        
        // if we do not have any moves the next player plays
        if (moves.isEmpty())
        {
        	System.out.println("No possible Moves!!!");
        	return;
        }
        
        boolean properMove = false;   
        
        while (!properMove)
        {
        	System.out.println("Give me the coordinates of your next move");
               
            System.out.print("give col: ");
            int col = Main.scanner.nextInt();
               
            System.out.print("give row: ");
            int row = Main.scanner.nextInt();

            Move move = new Move(row - 1, col - 1);
            if (moves.contains(move)) 
            {
                board.makeMove(move, PlayerSymbol); // Note: This call modifies the board.
                properMove = true;

            }
            else
            {
            	System.out.println("This move is not available.Try a new one");
            }
        }
    	
    }

    public void PlayAI(Board board, int maxdepth)
    {
    	Move move = minimax(board, maxdepth, -100, 100, PlayerSymbol == Tile.State.X);
    	
    	if(move == null)
    	{
    		System.out.println("No possible Moves!!!");
    		return;
    	}
    	
    	System.out.println("The AI made the Move " + move.toString());
    	board.makeMove(move, PlayerSymbol);
    }
    
    
    public Move minimax(Board board, int depth, int a, int b, boolean isMaxPlaying)
    {
    	if(depth == 0 || board.isTerminal())
    	{
    		return new Move(board.getLastMove().row(), board.getLastMove().colum(), board.evaluate());
    	}
    	
    	if(isMaxPlaying)
    	{
    		Move maxEval = new Move(-1, -1, -100);
    		ArrayList<Board> children = board.getChildren(Tile.State.X);
    		
    		if(children.isEmpty())
    		{
    			return new Move(board.getLastMove().row(), board.getLastMove().colum(), board.evaluate());
    		}
    		
    		for(int i = 0; i < children.size(); i++)
    		{
    			Move move = minimax(children.get(i), depth - 1, a, b, false);
    			
    			if(maxEval.value < move.value)
    			{
    				maxEval = children.get(i).getLastMove();
    			}
    			
    			a = Math.max(a, move.value);
    			if(b <= a) 
    			{
    				break;
    			}
    			
    		}	
    		return maxEval;
    	}
    	else
    	{
    		Move minEval = new Move(-1, -1, 100);
    		ArrayList<Board> children = board.getChildren(Tile.State.O);
    		
    		if(children.isEmpty())
    		{
    			return new Move(board.getLastMove().row(), board.getLastMove().colum(), board.evaluate());
    		}
    		
    		for(int i = 0; i < children.size(); i++)
    		{
    			Move move = minimax(children.get(i), depth - 1, a, b, true);
    			
    			if(minEval.value > move.value)
    			{
    				minEval = children.get(i).getLastMove();
    			}
    			
    			b = Math.min(a, move.value);
    			if(b <= a) 
    			{
    				break;
    			}
    			
    		}	
    		return minEval;
    	}
    	
    }
}
