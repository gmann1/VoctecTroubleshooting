package edu.asu.voctec.GUI;

import java.awt.Point;

import org.newdawn.slick.Input;

/**
 * Listener that activates based on hover events (i.e. the mouse has entered or
 * exited the bounds of the associated component). Children of this class must
 * define the protocol for mouseEntered and mouseExited events.
 * 
 * @author Moore, Zachary
 * @see #mouseEntered()
 * @see #mouseExited()
 * @see ActionListener
 * 
 */
public abstract class HoverListener extends ActionListener
{
	private static final long serialVersionUID = 2850481430338481612L;
	
	/** Indicates if the mouse is currently hovering over this component */
	boolean activated;
	
	/**
	 * Verifies that an event should be triggered. Hover events are triggered in
	 * two cases: when the mouse enters the bounds of the associated component,
	 * and when the mouse leaves the bounds of the associated component.
	 * 
	 * @see edu.asu.voctec.GUI.ActionListener#verify(org.newdawn.slick.Input)
	 */
	@Override
	protected boolean verify(Input input)
	{
		Point mousePosition = new Point(input.getMouseX(), input.getMouseY());
		boolean collision = (associatedComponent.getBounds()
				.contains(mousePosition));
		
		// Return true if the mouse has entered OR exited the component (xor)
		return (activated ^ collision);
	}
	
	/**
	 * Evaluates the type of event (mouseEnter or mouseExit) and calls the
	 * appropriate function (see the {@link #mouseEntered()} and
	 * {@link #mouseExited()} methods).
	 * 
	 * @see #mouseEntered()
	 * @see #mouseExited()
	 */
	@Override
	protected void actionPerformed()
	{
		// React differently depending on the type of event (enter or exit)
		if (activated)
			mouseExited();
		else
			mouseEntered();
		
		// Update the status of this listener
		activated = !activated;
	}
	
	/**
	 * Performs actions when the mouse enters the bounds of the associated
	 * component. This will not (necessarily) be fired in every frame that the
	 * mouse is currently over the component; only in a frame in which the mouse
	 * is NOW hovering over the component, but was outside of the component as
	 * of the previous frame.
	 */
	protected abstract void mouseEntered();
	
	/**
	 * Performs actions when the mouse exits the bounds of the associated
	 * component. This will not (necessarily) be fired in every frame that the
	 * mouse is outside the bounds of the component; only in a frame in which
	 * the mouse is NOW outside the component, but was over of the component as
	 * of the previous frame.
	 */
	protected abstract void mouseExited();
	
}
