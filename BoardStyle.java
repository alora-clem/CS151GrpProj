

import java.awt.Shape;

/** 
 * This interface contains the methods pertaining to functionality for the display
 *  author @Sidqdeep, @Alora, @Patrick
 */

public interface BoardStyle 
{
	
	
	/**
	 * Returns the shape of the pit 
	 * @return a Shape object of the pit
	 */
	public Shape getPit();
	
	
	/**
	 * Returns the shape of the mancala 
	 * @return a Shape object of the mancala
	 */
	public Shape getMancala(Player p);

}

