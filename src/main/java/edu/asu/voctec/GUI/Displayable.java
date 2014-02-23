package edu.asu.voctec.GUI;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Graphics;

/**
 * Represents an object that can be displayed using LWJGL. Displayable objects
 * must be able to handle drawing (to the given window) by themselves, and
 * should have the capability to resize based on constraints defined by a
 * Rectangle object.
 * 
 * @see #setBounds(Rectangle)
 * @author Moore, Zachary
 * 
 */
public interface Displayable
{
	/**
	 * Draws the object to the screen using the provided Graphics object.
	 * 
	 * @param graphics
	 *            object to use when drawing this component to the screen.
	 */
	public void draw(Graphics graphics);
	
	/**
	 * @return x-location of this object, relative to the screen.
	 */
	public int getX();
	
	public void setX(int x);
	
	public void setY(int y);
	
	/**
	 * @return y-location of this object, relative to the screen.
	 */
	public int getY();
	
	/**
	 * Returns the bounds of this displayable object. The bounds of an object
	 * define the area on the screen which the object is allowed to be visible
	 * within. This includes the location and size of the displayable area.
	 * 
	 * @return Rectangle representing the visual area occupied by this object.
	 */
	public Rectangle getBounds();
	
	/**
	 * Define the area on the screen which the object is allowed to be visible
	 * within. This includes the location and size of the displayable area.
	 * 
	 * @return True if this operation was successful.
	 * @see #getBounds()
	 */
	public boolean setBounds(Rectangle bounds);
	
	public void translate(Point amount);
	
	public void translate(int horizontalAmount, int verticalAmount);
	
	public void setLocation(Point location);
}
