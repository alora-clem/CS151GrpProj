package group;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/** 
 * author @Sidqdeep, @Alora, @Patrick
 * 
 */


public class RectangleStyle implements BoardStyle
{

	/**
	 *Creates a rectangular pit
	 *@return a rectangle with size 120 by 120
	 */
	
	public Shape getPit() 
	{
	
			return new Rectangle2D.Double(0, 0, 120, 120);
	}

	/**
	 *Creates a rectangular mancala
	 *@param p the mancala belongs to
	 *@return a rectangle with size 50 by 500
	 */
	public Shape getMancala(Player p) 
	{
			return new Rectangle2D.Double(0,0,50, 500);
	}


}
