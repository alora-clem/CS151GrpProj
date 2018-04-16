package group;

import java.awt.*;

/**
 *Interface that defines BoardFormat. Strategy Pattern
 * 
 */
public interface BoardFormat
{

	/**
	 * Gets the shape of the formatted Pit
	 * @param ps the Pit to be formatted
	 * @return the shape of the formatted Pit
	 */
	Shape formatPitShape(PitShape ps);
	
	
}
