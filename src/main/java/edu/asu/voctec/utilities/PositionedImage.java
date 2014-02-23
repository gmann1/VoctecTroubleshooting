package edu.asu.voctec.utilities;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PositionedImage extends PositionedObject<Image>
{
	private static final long serialVersionUID = 2016668855127995229L;

	public PositionedImage(String image, int x, int y) throws SlickException
	{
		super(new Image(image), x, y);
	}

	public PositionedImage(String image) throws SlickException
	{
		this(image, 0, 0);
	}

	public PositionedImage(String image, Point relativeLocation)
			throws SlickException
	{
		super(new Image(image), relativeLocation);
	}

	public PositionedImage(Image data, int x, int y)
	{
		super(data, x, y);
	}

	public PositionedImage(Image data, Point relativeLocation)
	{
		super(data, relativeLocation);
	}
	
	public void setImage(String image)
	{
		try
		{
			this.data = new Image(image);
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void rescale(float horizontalScale, float verticalScale)
	{
		int width = (int) (data.getWidth() * horizontalScale);
		int height = (int) (data.getHeight() * verticalScale);
		
		data = data.getScaledCopy(width, height);
		this.setLocation(x * horizontalScale, y * verticalScale);
	}
}
