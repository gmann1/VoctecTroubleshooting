package edu.asu.voctec.GUI;

import java.awt.Rectangle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ProgressBar extends Component
{
	private static final long serialVersionUID = -5424844532468524510L;

	protected Rectangle bounds;
	
	protected Image baseFillBar;
	protected Image baseEmptyBar;
	protected Image baseBorder;
	
	protected Image currentFillBar;
	protected Image currentEmptyBar;
	protected Image currentBorder;
	protected Image currentBar;
	
	public ProgressBar(Rectangle bounds)
	{
		this.bounds = bounds;
	}
	
	public void setImages(String baseFillBar, String baseEmptyBar,
			String baseBorder) throws SlickException
	{
		this.baseFillBar = new Image(baseFillBar);
		this.baseEmptyBar = new Image(baseEmptyBar);
		this.baseBorder = new Image(baseBorder);
		
		resize(bounds.width, bounds.height);
	}
	
	public void setPercentComplete(int percent)
	{
		double scale = ((double) percent / 100.0);
		int width = (int) (scale * this.currentFillBar.getWidth());
		int height = this.currentFillBar.getHeight();
		
		this.currentBar = this.currentFillBar.getSubImage(0, 0, width, height);
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		// Draw empty space
		if (currentEmptyBar != null)
			graphics.drawImage(currentEmptyBar, getX(), getY());
		
		// Draw the portion of the bar that is "filled"
		if (currentBar != null)
			graphics.drawImage(currentBar, getX(), getY());
		
		// Draw the border
		if (currentBorder != null)
			graphics.drawImage(currentBorder, getX(), getY());
	}
	
	@Override
	public int getX()
	{
		return bounds.x;
	}
	
	@Override
	public void setX(int x)
	{
		bounds.setLocation(x, bounds.y);
	}
	
	@Override
	public void setY(int y)
	{
		bounds.setLocation(bounds.x, y);
	}
	
	@Override
	public int getY()
	{
		return bounds.y;
	}
	
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(bounds);
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		try
		{
			// Determine the percentage that this bar is filled
			double currentWidth = (currentBar == null) ? 0 : currentBar
					.getWidth();
			double fullWidth = baseFillBar.getWidth();
			double percent = currentWidth / fullWidth;
			
			// Resize independent assests
			this.currentBorder = baseBorder.getScaledCopy(width, height);
			this.currentEmptyBar = baseEmptyBar.getScaledCopy(width, height);
			this.currentFillBar = baseFillBar.getScaledCopy(width, height);
			
			// Crop the currentBar based on the current percent
			int currentBarWidth = (int) (width * percent);
			this.currentBar = currentFillBar.getSubImage(0, 0, currentBarWidth,
					height);
			
			// Adjust Bounds
			bounds.setSize(width, height);
			
			// Return true if no exceptions were thrown
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
}
