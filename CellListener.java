public class CellListener extends EventListener
{
	private int mWidth, mHeight;
	private char[][] mState;

	public CellListener(CellNetwork cn){
		this.mWidth = cn.getWidth();
		this.mHeight = cn.getHeight();
		this.mState = new char[mWidth][mHeight];
	}

	public void stateChange(Atomic m, double t)
	{
		showState((Cell)m, t);
	}

	public void lambdaEvent(Event e, double t){}

	public void showState(Cell c, double t)
	{
		if(c.getStatus() == true)
			mState[c.getX()][c.getY()] = 'x';
		else
			mState[c.getX()][c.getY()] = ' ';
	}

	public void update(){
		for(int r = 0; r < mWidth; r++){
			for(int c = 0; c < mHeight; c++)
				System.out.print(mState[r][c]);
			System.out.println();
		}
	}

	public char[][] getState(){
		return mState;
	}
}