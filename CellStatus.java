public class CellStatus
{
	private int x, y;
	private boolean mStatus;

	public CellStatus(){}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setStatus(boolean b){
		this.mStatus = b;
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
}