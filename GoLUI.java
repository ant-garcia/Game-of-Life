import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

public class GoLUI extends JPanel 
{
    public static char[][] mGrid;
    private static GoLPanel gp;
    private static int mScale;
    private final static int time = 50;

    public GoLUI(int row, int col, char[][] g, int scale)
    {
        this.mGrid = g;
        this.mScale = scale;
    	setLayout(new BorderLayout());
    	gp = new GoLPanel(mGrid, mScale);
		gp.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));    	
    	add(gp, BorderLayout.CENTER);
    	setPreferredSize(new Dimension(row, col));

    }

    private void createAndShowGUI(int row, int col, char[][] g, int s)
    {
    	JFrame nf = new JFrame("Game of Life");
        nf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JComponent newContentPane = new GoLUI(row, col, g, s);
        newContentPane.setOpaque(true);
        nf.setContentPane(newContentPane);
        
        nf.pack();
        nf.setResizable(false);
        nf.setVisible(true);
    }

    public static void initGui(int rows, int cols, char[][] g, int upscale)
    {
        final GoLUI gui = new GoLUI(rows, cols, g, upscale);

    	javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                gui.createAndShowGUI(rows, cols, g, upscale);
            }
        });
    }

    public static void update(){
        gp.updateGraphics(mGrid);
    }
}