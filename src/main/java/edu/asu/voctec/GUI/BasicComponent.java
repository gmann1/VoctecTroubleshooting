package edu.asu.voctec.GUI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BasicComponent extends Component
{
	private static final long serialVersionUID = -5369251305334271852L;
	
	protected Image baseImage;
	protected Image currentImage;
	protected double x;
	protected double y;
	
	public BasicComponent(Image image, int x, int y)
	{
		this.baseImage = image;
		this.currentImage = image;
		this.x = x;
		this.y = y;
	}
	
	public BasicComponent(String imagePath, int x, int y) throws SlickException
	{
		this(new Image(imagePath), x, y);
	}
	
	public BasicComponent(String imagePath, int x, int y, int width, int height)
			throws SlickException
	{
		this((new Image(imagePath)).getScaledCopy(width, height), x, y);
	}
	
	public BasicComponent(String imagePath, Rectangle bounds)
			throws SlickException
	{
		this(imagePath, bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public BasicComponent(String imagePath, Point location)
			throws SlickException
	{
		this(new Image(imagePath), location.x, location.y);
	}
	
	public BasicComponent(Image image, Point location)
	{
		this(image, location.x, location.y);
	}
	
	public void draw(Graphics graphics)
	{
		if (this.currentImage != null)
			graphics.drawImage(currentImage, (int) x, (int) y);
	}
	
	public Image getCurrentImage()
	{
		return currentImage;
	}
	
	public void setCurrentImage(Image currentImage, boolean maintainSize)
	{
		if (maintainSize)
		{
			// Get the current size of this component's image
			Dimension size = new Dimension(this.currentImage.getWidth(),
					this.currentImage.getHeight());
			
			// Rescale the image to match the current size
			this.currentImage = currentImage.getScaledCopy(size.width,
					size.height);
		}
		else
		{
			this.currentImage = currentImage;
		}
		
		// Reset the base image
		this.baseImage = currentImage;
	}
	
	public int getX()
	{
		return (int) x;
	}
	
	public int getY()
	{
		return (int) y;
	}
	
	public Image getBaseImage()
	{
		return baseImage;
	}
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(getX(), getY(), this.currentImage.getWidth(),
				this.currentImage.getHeight());
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		this.currentImage = baseImage.getScaledCopy(width, height);
		
		// Operation was successful if no errors were thrown
		return true;
	}
	
	@Override
	public void setX(int x)
	{
		this.x = x;
	}
	
	@Override
	public void setY(int y)
	{
		this.y = y;
	}
	
}
