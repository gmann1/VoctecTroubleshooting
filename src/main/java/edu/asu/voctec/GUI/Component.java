package edu.asu.voctec.GUI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import edu.asu.voctec.Game;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.utilities.Resizable;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * Represents a displayable object on the screen. Components have the ability to
 * listen for events, including mouse, keyboard, and joystick.
 * 
 * Note: all components should be associated with a GUI instance. The events
 * will be fired from said GUI. A user can easily associate this button with a
 * GUI using the associate() method.
 * 
 * @author Moore, Zachary
 * @see #associate(GUI)
 * 
 */
public abstract class Component implements Displayable, Resizable, Serializable
{
	private static final long serialVersionUID = 5335900166012598528L;
	
	protected final ArrayList<ActionListener> listeners = new ArrayList<>();
	protected Class<? extends GUI> associatedGUI;
	
	public ActionListener[] getListeners()
	{
		return listeners.toArray(new ActionListener[listeners.size()]);
	}
	
	public GUI getAssociatedGUI()
	{
		GUI associatedGUI = (GUI) Game.getGameState(this.associatedGUI);
		return associatedGUI;
	}
	
	public void associate(GUI associatedGUI)
	{
		if (associatedGUI != null)
		{
			this.associatedGUI = associatedGUI.getClass();
			// Make GUI listen for all listeners associated with this component.
			associatedGUI.getListenerList().addAll(listeners);
		}
		else
		{
			associatedGUI = null;
		}
	}
	
	public void addActionListener(ActionListener listener)
	{
		// Add listener to this component
		listener.associate(this);
		this.listeners.add(listener);
		
		// Alert the associated GUI that the new event should be listened for.
		if (this.associatedGUI != null)
			getAssociatedGUI().getListenerList().add(listener);
	}
	
	@Override
	public boolean setBounds(Rectangle bounds)
	{
		boolean success = resize(bounds.width, bounds.height);
		
		// Only reposition the object if the resize was successful.
		if (success)
		{
			setX(bounds.x);
			setY(bounds.y);
		}
		else
			System.out.println("Component: setBounds FAILED");
		
		return success;
	}
	
	@Override
	public final boolean rescale(float scale)
	{
		return rescale(scale, scale);
	}
	
	@Override
	public final boolean rescale(int width, int height)
	{
		float[] scales = getScales(width, height);
		return rescale(scales[0], scales[1]);
	}
	
	public float[] getScales(int width, int height)
	{
		int currentWidth = getBounds().width;
		int currentHeight = getBounds().height;
		
		float horizontalScale = ((float) width) / ((float) currentWidth);
		float verticalScale = ((float) height) / ((float) currentHeight);
		
		return new float[]{horizontalScale, verticalScale};
	}
	
	@Override
	public boolean rescale(float horizontalScale, float verticalScale)
	{
		Rectangle newBounds = UtilFunctions.getScaledRectangle(getBounds(),
				horizontalScale, verticalScale);
		
		return setBounds(newBounds);
	}
	
	@Override
	public boolean resize(Dimension dimension)
	{
		return resize(dimension.width, dimension.height);
	}
	
	@Override
	public void translate(int horizontalAmount, int verticalAmount)
	{
		int x = getX() + horizontalAmount;
		int y = getY() + verticalAmount;
		
		setX(x);
		setY(y);
	}
	
	@Override
	public void translate(Point amount)
	{
		int x = getX() + amount.x;
		int y = getY() + amount.y;
		
		setX(x);
		setY(y);
	}
	
	public void setLocation(Point location)
	{
		setX(location.x);
		setY(location.y);
	}
}
