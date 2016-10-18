import java.util.List;

public class CellNetwork extends Network<CellStatus>{
	private int mWidth, mHeight;
	private Cell[][] mCells;

	public CellNetwork(int w, int h, double p){
		super();
		this.mWidth = w;
		this.mHeight = h;
		mCells = new Cell[w][h];

		for(int r = 0; r < mWidth; r++)
			for(int c = 0; c < mHeight; c++)
				mCells[r][c] = new Cell(r, c, Math.random() < p, this);
	}

	public int getWidth(){
		return mWidth;
	}

	public int getHeight(){
		return mHeight;
	}

	public void getComponents(List<DiscreteModel<CellStatus>> l){
		for(int r = 0; r < mWidth; r++)
			for(int c = 0; c < mHeight; c++)
				l.add(mCells[r][c]);
	}

	public void route(CellStatus cs, DiscreteModel<CellStatus> src,
					  Bag<Event<CellStatus>> b){
		Cell c = (Cell) src;
		int cx = c.getX();
		int cy = c.getY();

		for(int row = -1; row <= 1; row++)
			for(int col = -1; col <= 1; col++){
				if(row != col){
					int xt = (cx+row)%mWidth;
					int yt = (cy+col)%mHeight;

					if(xt < 0)
						xt = mWidth - 1;
					else if(xt >= mWidth)
						xt = 0;
					if(yt < 0)
						yt = mHeight - 1;
					else if(yt >= mHeight)
						yt = 0;

					Event<CellStatus> e = new Event<CellStatus>(mCells[xt][yt], cs);
					b.addIfNotFound(e);
				}
			}
	}

	public void placeCell(Cell c, int x, int y){
		mCells[x][y] = c;
		c.setParent(this);
	}

	public void print(){
		for(int r = 0; r < mWidth; r++){
			for(int c = 0; c < mHeight; c++)
				System.out.print(mCells[r][c].getStatus() + " ");
			System.out.println();
		}
	}
}