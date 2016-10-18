public class Cell extends Atomic<CellStatus>
{
	private int x, y;
	private int mNeighborsAlive;
	private boolean mStatus;

	public Cell(int x, int y, boolean b, Network<CellStatus> p){
		super();
		this.x = x;
		this.y = y;
		this.mStatus = b;
		this.setParent(p);
		this.mNeighborsAlive = -1;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public boolean getStatus(){
		return mStatus;
	}

	public void deltaInt(){
		if(mNeighborsAlive < 0)
			mNeighborsAlive = 0;
		if(checkDeathRule())
			mStatus = false;
		else if(checkAliveRule())
			mStatus = true;
	}

	public void deltaExt(double t, Bag<CellStatus> b){
		if(mNeighborsAlive < 0)
			mNeighborsAlive = 0;
		for(int i = 0; i < b.list.size(); i++){
			if(b.list.get(i).getStatus() == false)
				mNeighborsAlive--;
			else
				mNeighborsAlive++;
		}
	}

	public void deltaConf(Bag<CellStatus> b){
		deltaInt();
		deltaExt(0.0, b);
	}

	public void lambda(Bag<CellStatus> b){
		CellStatus cs = new CellStatus();
		cs.setX(x);
		cs.setY(y);

		if(mNeighborsAlive == -1)
			cs.setStatus(mStatus);
		else if(checkAliveRule())
			cs.setStatus(true);
		else
			cs.setStatus(false);
		b.addIfNotFound(cs);
	}

	public double timeAdv(){
		if(mNeighborsAlive == -1 && mStatus == true)
			return 0.0;
		if(checkAliveRule() || checkDeathRule())
			return 1.0;
		return Double.POSITIVE_INFINITY;
	}

	public boolean checkAliveRule(){
		return mStatus == false && mNeighborsAlive == 3;
	}

	public boolean checkDeathRule(){
		return mStatus == true && (mNeighborsAlive < 2 || mNeighborsAlive > 3);
	}
}