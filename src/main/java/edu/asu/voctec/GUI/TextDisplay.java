package edu.asu.voctec.GUI;

import java.awt.Font;
import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * A container for text that can be drawn to the screen. All text displays have
 * customizable text, borders, backgroundImage, fillColor, and relative text
 * location. Support is included for automatic formatting, including sizing and
 * clipping support.
 * 
 * @author Moore, Zachary
 * 
 */
public abstract class TextDisplay extends Component
{
	private static final long serialVersionUID = -7772366088271229841L;

	public static interface Defaults
	{
		public static final double BORDER_SCALE = 0.95;
		public static final Font AWT_FONT = Fonts.AWT_FONT;
		public static final TrueTypeFont FONT = Fonts.TRUE_TYPE_FONT;
		public static final Color FONT_COLOR = Fonts.FONT_COLOR;
		public static final Color BORDER_COLOR = Color.red;
		public static final Color FILL_COLOR = Color.white;
	}
	
	public static enum FormattingOption
	{
		CLIP_TEXT, FIT_TEXT, FIT_TEXT_VERTICALLY;
	}
	
	/** Describes the area of the screen on which this text will display */
	protected Rectangle bounds;
	
	/**
	 * Describes the area in which text is allowed to display, relative to this
	 * text field (i.e. the position and boundaries of the text, relative to the
	 * field)
	 */
	protected Rectangle textBounds;
	
	/** The awtFont used to generate this object's font */
	protected Font awtFont;
	
	/** The font used to draw this object's text to the screen */
	protected TrueTypeFont font;
	
	/** Desired font color. If this value is null, the text will not be drawn */
	protected Color fontColor;
	
	/**
	 * Desired border color. If this value is null, the border will not be drawn
	 */
	protected Color borderColor;
	
	/**
	 * Desired background color. If this value is null, the background will not
	 * be drawn
	 */
	protected Color fillColor;
	
	/**
	 * If this object has to clip text due to sizing issues, it will store the
	 * text that cannot be displayed here
	 */
	protected String clipedText;
	
	/** Whether or not to apply antiAliasing to this textDisplay */
	protected boolean antiAlias;
	
	/** Whether or not to center the text of this textDisplay */
	protected boolean center;
	
	protected Image baseImage;
	protected Image currentImage;
	
	public TextDisplay(Rectangle bounds, Rectangle textBounds, Font awtFont,
			boolean antiAlias)
	{
		super();
		this.bounds = new Rectangle(bounds.x, bounds.y, bounds.width,
				bounds.height);
		this.textBounds = new Rectangle(textBounds.x, textBounds.y,
				textBounds.width, textBounds.height);
		this.awtFont = awtFont;
		this.font = new TrueTypeFont(awtFont, antiAlias);
		this.antiAlias = antiAlias;
		
		this.fontColor = Defaults.FONT_COLOR;
		this.borderColor = null;
		this.fillColor = null;
	}
	
	public TextDisplay(Rectangle bounds, Rectangle textBounds)
	{
		this(bounds, textBounds, Defaults.AWT_FONT, Fonts.ANTI_ALLIAS);
	}
	
	protected abstract void drawText(Graphics graphics);
	
	protected abstract void formatText();
	
	public abstract void setText(String text);
	
	public void center()
	{
		center = true;
	}
	
	// TODO Implement
	/*
	 * protected abstract void centerText();
	 * 
	 * protected abstract void alignTextLeft();
	 * 
	 * protected abstract void alignTextRight();
	 * 
	 * protected abstract void resizeText(FormattingOption option);
	 * 
	 * protected abstract void resizeText(float size);
	 * 
	 * protected abstract void clear();
	 */
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.voctec.GUI.Displayable#draw(org.newdawn.slick.Graphics)
	 */
	@Override
	public void draw(Graphics graphics)
	{
		// Fill background
		if (fillColor != null)
		{
			graphics.setColor(fillColor);
			graphics.fillRect(bounds.x, bounds.y, bounds.width,
					bounds.height);
		}
		
		// Draw background image
		if (currentImage != null)
			graphics.drawImage(currentImage, bounds.x, bounds.y);
		
		// Draw text
		drawText(graphics);
		
		// Draw border
		if (borderColor != null)
		{
			graphics.setColor(borderColor);
			graphics.drawRect(bounds.x, bounds.y, bounds.width,
					bounds.height);
		}
	}
	
	@Override
	public boolean setBounds(Rectangle bounds)
	{
		// Record current location
		int x = this.bounds.x;
		int y = this.bounds.y;
		
		// Translate all bounds to (0, 0), keeping relative locations
		// Translate all bounds to newBounds location, keeping relativity
		this.textBounds.translate(bounds.x - x, bounds.y - y);
		this.bounds.translate(bounds.x - x, bounds.y - y);
		
		// Resize bounds
		this.textBounds.setSize(bounds.width, bounds.height);
		this.bounds.setSize(bounds.width, bounds.height);
		
		// Resize image
		if (this.currentImage != null)
			this.currentImage = this.baseImage.getScaledCopy(bounds.width, bounds.height);
		
		// Reformat text
		formatText();
		
		return true;
	}
	
	public static Rectangle defaultTextBounds(final Rectangle bounds,
			float textBoundScale)
	{
		return UtilFunctions.dialateRelativeRectangle(bounds, textBoundScale);
	}
	
	/**
	 * Display a border around this text field, using the provided color. If the
	 * borderColor parameter is null, this object's font color will be used as
	 * the border color.
	 * 
	 * @param borderColor
	 *            the desired border color.
	 */
	public void enableBorder(Color borderColor)
	{
		if (borderColor != null)
			this.borderColor = borderColor;
		else
			this.borderColor = this.fontColor;
	}
	
	/**
	 * Display a border around this text field, using the default border color.
	 */
	public void enableBorder()
	{
		enableBorder(Defaults.BORDER_COLOR);
	}
	
	/**
	 * Display a solid-color background behind this text field, using the
	 * backgroundColor parameter. Passing a null value as backgroundColor will
	 * have no impact on this object's current background.
	 * 
	 * @param backgroundColor
	 *            the desired background color.
	 */
	public void enableBackgroundFill(Color backgroundColor)
	{
		if (backgroundColor != null)
			this.fillColor = backgroundColor;
	}
	
	/**
	 * Display a solid-color background behind this text field, using the
	 * default fillColor as the background color.
	 */
	public void enableBackgroundFill()
	{
		enableBackgroundFill(Defaults.FILL_COLOR);
	}
	
	/**
	 * Stop displaying the background of this text field.
	 */
	public void disableBackgroundFill()
	{
		this.fillColor = null;
	}
	
	/**
	 * Stop displaying the border of this text field.
	 */
	public void disableBorder()
	{
		this.borderColor = null;
	}
	
	public void setFontSize(float size)
	{
		this.awtFont = awtFont.deriveFont(size);
		this.font = new TrueTypeFont(awtFont, antiAlias);
	}
	
	public Rectangle getTextBounds()
	{
		return textBounds;
	}
	
	public void setTextBounds(Rectangle textBounds)
	{
		this.textBounds = textBounds;
	}
	
	public void scaleTextBounds(float scale)
	{
		this.textBounds = UtilFunctions.dialateRelativeRectangle(bounds, scale);
	}
	
	public Color getFontColor()
	{
		return fontColor;
	}
	
	public void setFontColor(Color fontColor)
	{
		this.fontColor = fontColor;
	}
	
	public Color getBorderColor()
	{
		return borderColor;
	}
	
	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}
	
	public Color getFillColor()
	{
		return fillColor;
	}
	
	public void setFillColor(Color fillColor)
	{
		this.fillColor = fillColor;
	}
	
	public Font getAwtFont()
	{
		return awtFont;
	}
	
	public TrueTypeFont getFont()
	{
		return font;
	}
	
	public String getClipedText()
	{
		return clipedText;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.voctec.GUI.Displayable#getX()
	 */
	@Override
	public int getX()
	{
		return bounds.x;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.voctec.GUI.Displayable#getY()
	 */
	@Override
	public int getY()
	{
		return bounds.y;
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.voctec.GUI.Displayable#getBounds()
	 */
	@Override
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.voctec.utilities.Resizable#resize(int, int)
	 */
	@Override
	public boolean resize(int width, int height)
	{
		Rectangle newBounds = new Rectangle(bounds.x, bounds.y, width, height);
		return setBounds(newBounds);
	}

	public Image getCurrentImage()
	{
		return currentImage;
	}

	public void setCurrentImage(Image currentImage, boolean maintainSize)
	{
		this.baseImage = currentImage;
		
		if (maintainSize)
			this.currentImage = this.baseImage.getScaledCopy(bounds.width, bounds.height);
		else
			this.currentImage = this.baseImage.copy();
	}
	
	
	
}
