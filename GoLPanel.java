import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GoLPanel extends JPanel{
    private char[][] mGrid;
    private int mScale;

    public GoLPanel(char[][] g, int s){
        this.mGrid = g;
        this.mScale = s;
    }

    public void setGrid(char[][] g){
        this.mGrid = g;
    }

    public char[][] getGrid(){
        return mGrid;
    }

    public void updateGraphics(char[][] g){
    	repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        fillGrid(g);
    }

    public void fillGrid(Graphics g2){
    	for(int i = 0; i < mGrid.length; i++)
    		for(int j = 0; j < mGrid[i].length; j++){
                g2.setColor(mGrid[i][j] == 'x' ? Color.BLACK : Color.WHITE);
    			g2.fillRect(i * mScale, j * mScale, mScale, mScale);
    		}
    }
}
