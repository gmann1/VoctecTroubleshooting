package edu.asu.voctec.GUI;

import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

public class Button extends BasicComponent
{
	private static final long serialVersionUID = -5092146334779020324L;
	protected TextDisplay textDisplay;
	
	public Button(Image image, int x, int y, Rectangle relativeTextBounds,
			String text)
	{
		super(image, x, y);
		
		if (relativeTextBounds != null)
		{
			Rectangle buttonBounds = new Rectangle(x, y, image.getWidth(),
					image.getHeight());
			
			// Determine textBounds relative to the screen
			Rectangle absoluteTextBounds = UtilFunctions
					.getTranslatedRectangle(relativeTextBounds, new Point(x, y));
			
			// Center text bounds vertically, with respect to this button
			UtilFunctions.centerRectangleVertically(buttonBounds,
					absoluteTextBounds);
			
			// Create and format text field
			TextField textField = new TextField(absoluteTextBounds, 1.0f, text,
					TextDisplay.FormattingOption.FIT_TEXT);
			textField.center();
			this.textDisplay = textField;
		}
	}
	
	public Button(Image image, int x, int y, float relativeTextBounds,
			String text) throws SlickException
	{
		this(image, x, y, UtilFunctions.dialateRectangle(image,
				relativeTextBounds), text);
	}
	
	public Button(String imagePath, int x, int y, Rectangle relativeTextBounds,
			String text) throws SlickException
	{
		this(new Image(imagePath), x, y, relativeTextBounds, text);
	}
	
	public Button(String imagePath, int x, int y, int width, int height,
			Rectangle relativeTextBounds, String text) throws SlickException
	{
		this((new Image(imagePath)).getScaledCopy(width, height), x, y,
				relativeTextBounds, text);
	}
	
	public Button(String imagePath, Rectangle bounds,
			Rectangle relativeTextBounds, String text) throws SlickException
	{
		this((new Image(imagePath)).getScaledCopy(bounds.width, bounds.height),
				bounds.x, bounds.y, relativeTextBounds, text);
	}
	
	public static void addTransitionListener(Button button,
			Class<?> transitionScreen)
	{
		
		button.addActionListener(new TransitionButtonListener(transitionScreen));
	}
	
	public void draw(Graphics graphics)
	{
		super.draw(graphics);
		if (textDisplay != null)
			textDisplay.draw(graphics);
	}
	
	public void setX(int x)
	{
		if (textDisplay != null)
		{
			int deltaX = x - (int) this.x;
			textDisplay.translate(deltaX, 0);
		}
		super.setX(x);
	}
	
	public void setY(int y)
	{
		if (textDisplay != null)
		{
			int deltaY = y - (int) this.y;
			textDisplay.translate(0, deltaY);
		}
		super.setY(y);
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		boolean success;
		
		if (textDisplay != null)
		{
			// Give textField a relative position
			textDisplay.setX(textDisplay.getX() - (int) this.x);
			textDisplay.setY(textDisplay.getY() - (int) this.y);
			
			// Deterime horizontal and vertical scales, for resizing the text
			float[] scales = getScales(width, height);
			
			// Resize this button, and rescale the relative text field
			success = super.resize(width, height)
					&& textDisplay.rescale(scales[0], scales[1]);
			
			// Give textField an absolute location
			textDisplay.setX(textDisplay.getX() + (int) this.x);
			textDisplay.setY(textDisplay.getY() + (int) this.y);
		}
		else
			success = super.resize(width, height);
		
		return success;
	}
	
	public TextDisplay getTextField()
	{
		return textDisplay;
	}
	
	public void setFontColor(Color color)
	{
		
		this.textDisplay.setFontColor(color);
	}
	
	public void positionText(Position position)
	{
		Point translationVector;
		
		switch (position)
		{
			case BOTTOM:
				translationVector = new Point(0, currentImage.getHeight() + 2);
				break;
			case LEFT:
				translationVector = new Point(-currentImage.getWidth() - 5, 0);
				break;
			case RIGHT:
				translationVector = new Point(currentImage.getWidth() + 2, 0);
				break;
			case TOP:
				translationVector = new Point(0, -currentImage.getHeight() - 2);
				break;
			default:
				translationVector = new Point(0, 0);
				break;
		}
		
		this.textDisplay.translate(translationVector);
	}
	
}
