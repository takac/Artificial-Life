package net.cammann.tom.fyp.core;

import java.awt.Graphics2D;

/**
 * Interface to provide a method to draw any object.
 *
 * @author TC
 * @version $Id: $
 */
public interface Paintable {
	
	/**
	 * <p>draw.</p>
	 *
	 * @param g2
	 *            recieved from frame. This will be painted upon.
	 */
	void draw(Graphics2D g2);
}
