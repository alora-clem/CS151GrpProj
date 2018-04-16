package group;

import java.awt.*;
import java.awt.geom.*;
import java.net.URL;


public class OakBoardFormat implements BoardFormat
{

	@Override
	public Shape formatPitShape(PitShape ps) {
		return new Rectangle2D.Double(ps.getX(), ps.getY(), ps.getWidth(), ps.getHeight());
	}

	
}
