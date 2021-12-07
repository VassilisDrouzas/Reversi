import java.util.ArrayList;

public class Board 
{
    private Tile[][] board;
    
    private Move lastMove;

    public Board()
    {
    	//Make an empty board.
    	
    	board = new Tile[8][8];
    	
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                board[row][col] = new Tile(row, col, Tile.State.EMPTY); 
            }
        }
        
        //Initialize the first 4 central possessions
        
        board[4][3].setState(Tile.State.O);                                             
        board[4][4].setState(Tile.State.X);
        board[3][3].setState(Tile.State.X);
        board[3][4].setState(Tile.State.O);
        
        lastMove = null;
    }

    public Board(Board b)
    {              
    	board = new Tile[8][8];
    	
    	//Make a new board based on the previous one(board argument here).
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                this.board[i][j] = new Tile(b.board[i][j]);
            }
        }
    }

    //Prints the board
    public void print()
    {

        System.out.print("   1   2   3   4   5   6   7   8 ");

        for (int i=0;i<8;i++){

            System.out.println();
            System.out.print(i+1);
            for (int j=0;j<8;j++){
                System.out.print("  " + board[i][j].toString() + " ");
            }
        }
        System.out.println();
    }


    public ArrayList<Move> possibleMoves(Tile.State state)
    {
        ArrayList<Move> moves=new ArrayList<>(); //Initialize an arraylist for the available moves.
        
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                Move m = new Move(row, col);
                
                if(isMoveValid(m , state))
                {
                	moves.add(m);
                }
            }
        }
               
        return moves;
    }
    
    private boolean isMoveValid(Move m, Tile.State state)
    {
       
        if (board[m.row()][m.colum()].getState() != Tile.State.EMPTY) return false; // we can't place a disk in a tile that has already a disk
              
        // find neighbors
        for (int i = -1; i < 2; i++) 
        {
            for (int j = -1; j < 2; j++) 
            {
            	if(m.row() + i < 0 || m.colum() + j < 0 || m.row() + i >= 8 || m.colum() + j >= 8) continue;
            	Tile neighbor = board[m.row() + i][m.colum() + j];
            	
            	// we need to find at least one neighbor that
            	
            	// 1. has a disk with the other symbol
            	if(neighbor.getState() == state || neighbor.getState() == Tile.State.EMPTY) continue;
            	
            	// 2. if we follow a line in the direction of the neighbor
            	// we must find (before we see a empty tile) a tile that has the same symbol
            	
            	int tilesFar = 2;    	
            	int row = m.row()   + tilesFar * i;
            	int col = m.colum() + tilesFar * j;     	
            	if(row < 0 || col < 0 || row >= 8 || col >= 8) return false;
            	Tile a = board[row][col];
            	
            	while(a.getState() != Tile.State.EMPTY)
            	{
            		if(a.getState() == state) return true;
            		
                	tilesFar++;    	
                	row = m.row()   + tilesFar * i;
                	col = m.colum() + tilesFar * j;     	
                	if(row < 0 || col < 0 || row >= 8 || col >= 8) return false;
                	a = board[row][col];
            	}
            	
            }
        }
        
    	return false;
    }
    
    // !!! it changes the board, it assumes that the move is valid
    public void makeMove(Move m, Tile.State state)
    {  	      
    	lastMove = m;
    	
    	// find neighbors
        for (int i = -1; i < 2; i++) 
        {
            for (int j = -1; j < 2; j++) 
            {
            	if(m.row() + i < 0 || m.colum() + j < 0 || m.row() + i >= 8 || m.colum() + j >= 8) continue;
            	Tile neighbor = board[m.row() + i][m.colum() + j];
            	
            	// we need to find at least one neighbor that
            	
            	// 1. has a disk with the other symbol
            	if(neighbor.getState() == state || neighbor.getState() == Tile.State.EMPTY) continue;
            	
            	// 2. if we follow a line in the direction of the neighbor
            	// we must find (before we see a empty tile) a tile that has the same symbol
            	
            	int tilesFar = 2;    	
            	int row = m.row()   + tilesFar * i;
            	int col = m.colum() + tilesFar * j;     	
            	if(row < 0 || col < 0 || row >= 8 || col >= 8) continue;
            	Tile a = board[row][col];
            	
            	while(a.getState() != Tile.State.EMPTY)
            	{
            		if(a.getState() == state)
            		{
            			 changeLine(m.row(), m.colum(), row, col, i , j, state);
            		}
            		
                	tilesFar++;    	
                	row = m.row()   + tilesFar * i;
                	col = m.colum() + tilesFar * j;     	
                	if(row < 0 || col < 0 || row >= 8 || col >= 8) break;
                	a = board[row][col];
            	}
            	
            }
        }
    }
   
    private void changeLine(int startRow, int startCol,int endRow, int endCol, int i, int j, Tile.State state)
    {
    	int row = startRow;
    	int col = startCol;
    	
    	while(row != endRow || col != endCol)
    	{
    		if(row < 0 || col < 0 || row >= 8 || col >= 8) break;
    		board[row][col].setState(state);
    		
    		row += i;
    		col += j;
    	}
    }
    
    public boolean isTerminal()
    {
    	ArrayList<Move> movesX = possibleMoves(Tile.State.X);
    	ArrayList<Move> movesO = possibleMoves(Tile.State.O);

    	return movesX.isEmpty() && movesO.isEmpty();
    }
    
    // return how good or bad this board is for an player
    // the higher the number the better for the X
    // the smaller the number the better for the O
    public int evaluate()
    {
    	int score = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j].getState() == Tile.State.X)
                {
                    score++;
                }
                else if (board[i][j].getState() == Tile.State.O)
                {
                    score--;
                }
            }
        }
        
        return score;
    }
    
    public int Count(Tile.State state)
    {
    	int score = 0;
    	
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j].getState() == state)
                {
                    score++;
                }
            }
        }
        
        return score;
    }

    public Tile getTile(int row,int col)
    {
        return board[row][col];
    }
   
    public ArrayList<Board> getChildren(Tile.State state)
    {
        ArrayList<Board>children = new ArrayList<Board>();
        
        for (Move move: possibleMoves(state))
        {
            Board child = new Board(this);
            child.makeMove(move,state);
            children.add(child);
        }
        return children;
    }
     
    public Move getLastMove()
    {
    	return lastMove;
    }

}
