package edu.asu.voctec.GUI;

import java.awt.Rectangle;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.utilities.PositionedImage;
import edu.asu.voctec.utilities.UtilFunctions;

public class StarDisplay extends Component
{
	public static final String STAR_IMAGE = "resources/default/img/misc/EmptyStar.png";
	public static final String STAR_IMAGE_HALF = "resources/default/img/misc/HalfStar.png";
	public static final String STAR_IMAGE_FULL = "resources/default/img/misc/FullStar.png";
	private static final long serialVersionUID = -3236086672740804897L;
	public static final int rotationAmount = 25;
	public static final int horizontalStarOffset = 100;
	public static final int verticalStarOffset = 20;
	
	protected final PositionedImage[] stars = new PositionedImage[3];
	protected int score;
	protected int x;
	protected int y;
	
	public StarDisplay(int score, int x, int y) throws SlickException
	{
		this.score = score;
		this.x = x;
		this.y = y;
		
		updateStars();
	}
	
	public void updateStars() throws SlickException
	{
		PositionedImage star = stars[1];
		int starWidth = 0;
		int starHeight = 0;
		
		if (star != null)
		{
			starWidth = stars[1].data.getWidth();
			starHeight = stars[1].data.getHeight();
		}
		else
			resetStars();
		
		fillStars();
		resizeStars(starWidth, starHeight);
		formatStars();
	}
	
	public void fillStars()
	{
		int numberOfFullStars = score / 2;
		int numberOfHalfStars = score % 2;
		
		for (int index = 0; index < stars.length; index++)
		{
			
			if (numberOfFullStars > 0)
			{
				stars[index].setImage(STAR_IMAGE_FULL);
				numberOfFullStars--;
			}
			else if (numberOfHalfStars > 0)
			{
				stars[index].setImage(STAR_IMAGE_HALF);
				numberOfHalfStars--;
			}
			else
				stars[index].setImage(STAR_IMAGE);
		}
	}
	
	protected void resetStars() throws SlickException
	{
		for (int index = 0; index < stars.length; index++)
			stars[index] = new PositionedImage(STAR_IMAGE);
	}
	
	protected void resizeStars(int width, int height)
	{
		// Do nothing if width or height is 0.
		if (width == 0 || height == 0)
			return;
		
		// Resize all stars to the desired width and height.
		for (int index = 0; index < stars.length; index++)
			stars[index].data = stars[index].data.getScaledCopy(width, height);
	}
	
	protected void formatStars()
	{
		rotateStars();
		positionStars();
	}
	
	protected void rotateStars()
	{
		stars[0].data.rotate(-rotationAmount);
		stars[2].data.rotate(rotationAmount);
	}
	
	protected void positionStars()
	{
		stars[0].setLocation(x, y + verticalStarOffset);
		stars[1].setLocation(x + horizontalStarOffset, y);
		stars[2].setLocation(x + 2 * horizontalStarOffset, y
				+ verticalStarOffset);
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		for (PositionedImage star : stars)
			graphics.drawImage(star.data, star.x, star.y);
		
		// Ensure the center star is displayed on top of the side stars
		PositionedImage centerStar = stars[1];
		graphics.drawImage(centerStar.data, centerStar.x, centerStar.y);
	}
	
	@Override
	public int getX()
	{
		return x;
	}
	
	@Override
	public void setX(int x)
	{
		int delta = x - this.x;
		
		for (PositionedImage star : stars)
			star.translate(delta, 0);
		
		this.x = x;
	}
	
	@Override
	public void setY(int y)
	{
		int delta = y - this.y;
		
		for (PositionedImage star : stars)
			star.translate(0, delta);
		
		this.y = y;
	}
	
	@Override
	public int getY()
	{
		return y;
	}
	
	@Override
	public Rectangle getBounds()
	{
		int width = stars[2].x + stars[2].data.getWidth() - stars[0].x;
		int height = stars[0].y + stars[0].data.getHeight() - stars[1].y;
		
		return new Rectangle(x, y, width, height);
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		float[] scales = UtilFunctions.getScales(getBounds(), width, height);
		float horizontalScale = scales[0];
		float verticalScale = scales[1];
		
		for (PositionedImage star: stars)
		{
			// Translate relatively to (0, 0)
			star.translate(-x,  -y);
			
			// Rescale
			star.rescale(horizontalScale, verticalScale);
			
			// Reposition
			star.translate(x, y);
		}
		
		rotateStars();
		
		// Return true if no exceptions were thrown
		return true;
	}
	
	public void setScore(int score)
	{
		this.score = score;
		try
		{
			updateStars();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
