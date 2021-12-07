

public class Move 
{

    private int row;
    private int col;
    
    public int value;

    public Move(int row,int col)
    {
        this.row=row;
        this.col=col;
        this.value = 0;
    }
    
    public Move(int row,int col, int value )
    {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int row()
    {
        return row;
    }

    public int colum()
    {
        return col;
    }

    @Override
	public boolean equals(Object o)
    {
    	Move m = (Move)o;
    	
		return row == m.row && col == m.col;	
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public void setCol(int col)
    {
        this.col = col;
    }
    
    public String toString()
    {
    	return "( " + (col + 1) + " , " + (row + 1) + " )";
    }

}
