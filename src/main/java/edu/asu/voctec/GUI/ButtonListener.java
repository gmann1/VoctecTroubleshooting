package edu.asu.voctec.GUI;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Input;

/**
 * Listens for a left-mouse click over the associated component. This listener
 * will only fire an event if it has an associated component, and that component
 * was clicked by the left-mouse button.
 * 
 * @author Moore, Zachary
 *
 */
public abstract class ButtonListener extends ActionListener
{
	private static final long serialVersionUID = -5311227445488305713L;

	@Override
	protected boolean verify(Input input)
	{
		return verify(input, associatedComponent.getBounds());
	}
	
	protected static boolean verify(Input input, Rectangle bounds)
	{
		boolean actionVerified;
		
		if (mouseButton == Input.MOUSE_LEFT_BUTTON)
		{
			// Get mouse location
			Point mouseLocation = new Point(input.getMouseX(),
					input.getMouseY());
			
			// Verify action if mouse is pressed over the associated component
			// (i.e. the component was clicked with the left button).
			actionVerified = bounds.contains(mouseLocation);
		}
		else
		{
			actionVerified = false;
		}
		
		return actionVerified;
	}
	
}
