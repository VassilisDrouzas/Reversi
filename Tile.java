

public class Tile 
{
	public enum State
	{
		EMPTY, // there is no disk in this tile
		X,     // has a disk with an X
		O,     // has a disk with an O
	}
	
    // Coords
    private int row;
    private int col;

    private State state;

    public Tile(int r, int c)
    {
        this.row = r;
        this.col = c;
        this.state = State.EMPTY;
    }
    
    public Tile(int c, int r, State state) 
    {
        this.row = r;
        this.col = c;
        this.state = state;
    }
    
    public Tile(Tile t)
    {
        this.row = t.row;
        this.col = t.col;
        this.state = t.state;
    }
    
    public String toString()
    {
    	switch(this.state)
    	{
    	case EMPTY:
    		return "_";
    	case X:
    		return "X";
    	case O:
    		return "O";
    	}
		return null;
    }

    public State getState()
    {
        return this.state;
    }

    public void setState(State state)
    {
        this.state = state;
    }
    
    public int row()
    {
        return this.row;
    }

    public int colum()
    {
        return this.col;
    }
    
}
