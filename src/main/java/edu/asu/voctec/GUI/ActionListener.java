package edu.asu.voctec.GUI;

import java.io.Serializable;

import org.newdawn.slick.Input;

/**
 * Abstract class to receive user input events. The implementation of this class
 * should define action(s) to take when the event is fired. Additionally,
 * listeners must be registered with a Component, via the addActionListener()
 * method.
 * 
 * For applications, consider extending a listener other than Action listener.
 * 
 * @author Moore, Zachary
 * @see HoverListener
 * @see ButtonListener
 * 
 */
public abstract class ActionListener implements Serializable
{
	private static final long serialVersionUID = -8919399115693140311L;
	
	/** Which mouse button was pressed in the current frame. <0 indicates none */
	protected static int mouseButton = -1;
	
	/** The component to which this listener is added */
	protected Component associatedComponent;
	
	/**
	 * Associates this listener with a specific component. This listener will
	 * act on the provided component, using the logic defined in the
	 * {@link #actionPerformed()} method.
	 * 
	 * @param associatedComponent
	 *            The component to which this listener is added.
	 */
	public void associate(Component associatedComponent)
	{
		this.associatedComponent = associatedComponent;
	}
	
	/**
	 * Verifies that an event should be triggered, and fires an event when
	 * appropriate.
	 * 
	 * @param input
	 *            Current user input.
	 * @return Whether or not an event was fired.
	 */
	public final boolean check(Input input)
	{
		boolean fireEvent = verify(input);
		
		if (fireEvent)
			actionPerformed();
		
		return fireEvent;
	}
	
	/**
	 * Input objects reset their information once it has been accessed. In order
	 * to provide access to this information from multiple sources within the
	 * same frame (if applicable), the mouse information is read and stored.
	 * 
	 * Note: a mouseButton value less than 0 indicates that the mouse was not
	 * pressed during the current frame.
	 * 
	 * @param input
	 *            Current user input.
	 */
	public static void update(Input input)
	{
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
			mouseButton = Input.MOUSE_LEFT_BUTTON;
		else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
			mouseButton = Input.MOUSE_RIGHT_BUTTON;
		else if (input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON))
			mouseButton = Input.MOUSE_MIDDLE_BUTTON;
		else
			mouseButton = -1;
	}
	
	/**
	 * Verifies that an event should be triggered. Note: the actions to take
	 * when an event is triggered should not be handled here. Instead, event
	 * actions should be defined in {@link #actionPerformed()}
	 * 
	 * @param input
	 *            Current user input.
	 * @return Boolean indicating whether or not an event should be triggered.
	 * @see #actionPerformed()
	 */
	protected abstract boolean verify(Input input);
	
	/**
	 * Defines the actions to take when this event has been triggered.
	 */
	protected abstract void actionPerformed();
}
