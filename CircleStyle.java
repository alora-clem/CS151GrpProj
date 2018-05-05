package group;



import java.awt.Shape;
import java.awt.geom.Ellipse2D;


/** 
 * author @Sidqdeep, @Alora, @Patrick
 * 
 */

public class CircleStyle implements BoardStyle
{

	/**
	 *Creates an elliptical pit
	 *@return an ellipse with size 120 by 120
	 */
	public Shape getPit() 
	{
	
		return new Ellipse2D.Double(0, 0, 120, 120);
	}

	/**
	 *Creates an elliptical mancala
	 *@param p the mancala belongs to
	 *@return an ellipse with size 50 by 500
	 */
	public Shape getMancala(Player p) 
	{
		
		return new Ellipse2D.Double(0, 0, 50, 500);

	}


}



