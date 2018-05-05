



import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JComponent;

/** 
 * author @Sidqdeep, @Alora, @Patrick
 * 
 */

public class PitShape extends JComponent
{

	int marbles;
	int location;
	Player playr;
	BoardStyle style;

	/**
	 * creates a pit with the given parameters
	 * @param n number of marbles to initialize the pit with
	 * @param index for the location of the mancala
	 * @param player the mancala belongs to
	 * @param styl implementation of BoardStyle style
	 */        
	public PitShape (int n, int index, Player player, BoardStyle styl)
	{
		
		marbles = n;
		location = index;
		playr = player;
		style = styl;
		
	}

	/**
	 * 
	 *Get the player the pit belongs to
	 *@return the pit's player
	 */
	public Player getPlayer()
	{
		return playr;
	}

	/**
	 *Change the number of marbles in the current pit
	 *@param n number of marbles to be set to
	 */
	public void setMarbles(int n)
	{
		
		marbles = n;
	}

	/**
	 *Get the number of marbles in the current pit
	 *@return the pit's marbles
	 */
	public int getMarbles()
	{
		
		return marbles;
	}

	
	
	/**
	 *Get the index or location of each pit
	 *@return the location of each pit
	 */
	public int getIndex()
	{
		
		return location;
	}

	
	
	/**
	 *Check if the pit is empty or not
	 *@return true if the pit is empty and false otherwise
	 */
	public boolean isEmpty()
	{
		
		if(marbles == 0)
		{
			return true;
		}
		
		else
		{
			return false;
		}
		
	}

	
	
	/**
	 *Get the style of the board either circle or rectangel
	 *@return the style of the board
	 */
	public BoardStyle getStyle()
	{
			return style;
	}

	/**
	 *Get the shape of the pit to be drawn
	 *@param b the board style determining the shape
	 *@return the shape based on the board style
	 */
	public Shape drawPit(BoardStyle b)
	{
			return b.getPit();
	}	

	
	/**
	 *Get the player the pit belongs to
	 *@param g graphics object used to draw shape
	 */
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(this.drawPit(style));
		
		
		int shapeHeight=this.drawPit(style).getBounds().height;
		int shapeWidth=this.drawPit(style).getBounds().width;
		
		int col = shapeWidth/2-5;
		int y = 0;
		int row = shapeHeight/2-5;
		int x = 0;
		
		for(int i = 0; i< getMarbles(); i++)
		{

			if( y< shapeHeight)
			{
				g2.drawOval(col,y, 10,10);
				y+= 10;				
			}
			else{
				
				g2.drawOval(x,row, 10,10);
				x += 10;
			}
			
		}
		
	}
	
	
	
	
}
