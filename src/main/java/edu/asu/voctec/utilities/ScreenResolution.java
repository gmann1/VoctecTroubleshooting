package edu.asu.voctec.utilities;

import java.awt.Dimension;

import org.newdawn.slick.Image;

import edu.asu.voctec.utilities.AspectRatio.ResolutionNotSupportedException;

public class ScreenResolution extends Dimension
{
	private static final long	serialVersionUID	= -5575416306076775945L;
	private AspectRatio			aspectRatio;
	
	public ScreenResolution(Image backgroundImage)
			throws ResolutionNotSupportedException
	{
		this(backgroundImage.getWidth(), backgroundImage.getHeight());
	}
	
	public ScreenResolution(int width, int height)
			throws ResolutionNotSupportedException
	{
		super(width, height);
		this.aspectRatio = AspectRatio.getAspectRatio(width, height);
	}
	
	protected void recalculateAspectRatio()
			throws ResolutionNotSupportedException
	{
		this.aspectRatio = AspectRatio.getAspectRatio(width, height);
	}
	
	public AspectRatio getAspectRatio()
	{
		return aspectRatio;
	}
	
	@Override
	public void setSize(int width, int height)
	{
		int currentWidth = this.width;
		int currentHeight = this.height;
		
		if (!((currentWidth == width) || (currentHeight == height)))
		{
			// Change dimension
			super.setSize(width, height);
			
			// Recalculate aspect ratio, and revert to previous dimension if
			// necessary
			try
			{
				// Recalculate
				recalculateAspectRatio();
			}
			catch (ResolutionNotSupportedException e)
			{
				// If recalculation failed, revert back to previous dimension
				this.setSize(width, height);
				
				// Alert console that an exception was caught
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setSize(double width, double height)
	{
		int currentWidth = this.width;
		int currentHeight = this.height;
		
		if (!((currentWidth == width) || (currentHeight == height)))
		{
			// Change dimension
			super.setSize(width, height);
			
			// Recalculate aspect ratio, and revert to previous dimension if
			// necessary
			try
			{
				// Recalculate
				recalculateAspectRatio();
			}
			catch (ResolutionNotSupportedException e)
			{
				// If recalculation failed, revert back to previous dimension
				this.setSize(width, height);
				
				// Alert console that an exception was caught
				e.printStackTrace();
			}
		}
	}
}
