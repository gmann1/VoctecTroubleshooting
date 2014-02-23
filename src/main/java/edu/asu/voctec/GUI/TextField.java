package edu.asu.voctec.GUI;

import java.awt.Font;
import java.awt.Rectangle;

import org.apache.commons.lang3.StringUtils;
import org.newdawn.slick.Graphics;

import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * Contains a single line of text that can be displayed to the screen using
 * LWJGL. A TextField object can have other options specified, such as a visible
 * border or background, font size and style, etc. Text can be automatically
 * sized or clipped, depending on the constructor input.
 * 
 * @author Moore, Zachary
 * @see #TextField(Rectangle, float, Font, boolean, String, FormattingOption)
 * @see #TextField(Rectangle, Rectangle, Font, boolean, String,
 *      FormattingOption)
 * 
 */
public class TextField extends TextDisplay
{
	private static final long serialVersionUID = 5766807312140072208L;
	
	public static final FormattingOption DEFAULT_FORMAT = FormattingOption.CLIP_TEXT;
	protected String text;
	protected FormattingOption formatting;
	protected Rectangle trueTextBounds;
	
	public TextField(Rectangle bounds, Rectangle textBounds, Font awtFont,
			boolean antiAlias, String text, FormattingOption option)
	{
		super(bounds, textBounds, awtFont, antiAlias);
		
		// Do not allow null formatting. Treat a null format as the default.
		this.formatting = (option != null) ? option : DEFAULT_FORMAT;
		
		// Do not allow null text. If text is null, treat it as an empty string.
		this.text = (text != null) ? text : "";
		
		this.trueTextBounds = textBounds;
		
		// Set font size based on provided formating option.
		formatText();
	}
	
	public TextField(Rectangle bounds, float textBounds, Font awtFont,
			boolean antiAlias, String text, FormattingOption option)
	{
		this(bounds,
				UtilFunctions.dialateRelativeRectangle(bounds, textBounds),
				awtFont, antiAlias, text, option);
	}
	
	public TextField(Rectangle bounds, float textBounds, String text,
			FormattingOption option)
	{
		this(bounds,
				UtilFunctions.dialateRelativeRectangle(bounds, textBounds),
				Defaults.AWT_FONT, Fonts.ANTI_ALLIAS, text, option);
	}
	
	public TextField(Rectangle bounds, Rectangle textBounds, String text,
			FormattingOption option)
	{
		this(bounds, textBounds, Defaults.AWT_FONT, Fonts.ANTI_ALLIAS, text,
				option);
	}
	
	@Override
	protected void formatText()
	{
		if (text.length() > 0)
		{
			if (this.clipedText != null)
				this.text = StringUtils.join(new String[] { text, clipedText },
						" ");
			
			if (formatting == FormattingOption.FIT_TEXT)
			{
				super.setFontSize(TextSupport.getMaxScaledFontSize(awtFont,
						text, textBounds));
			}
			else if (formatting == FormattingOption.FIT_TEXT_VERTICALLY)
			{
				super.setFontSize(TextSupport.getMaxVerticalScaledFontSize(
						awtFont, textBounds));
			}
			
			String[] clippings = TextSupport.clipString(font, text,
					textBounds.width);
			this.text = clippings[0];
			this.clipedText = clippings[1];
			
			if (center)
				center();
		}
	}
	
	@Override
	public void setFontSize(float size)
	{
		this.setFontSize(size, true);
	}
	
	public void setFontSize(float size, boolean ensureSize)
	{
		super.setFontSize(size);
		
		// Ensure the text does not get resized
		if (ensureSize)
			this.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		
		// Re-format text
		formatText();
	}
	
	protected void drawText(Graphics graphics)
	{
		graphics.setFont(font);
		graphics.setColor(fontColor);
		graphics.drawString(text, trueTextBounds.x + bounds.x, trueTextBounds.y
				+ bounds.y);
	}
	
	public void setText(String text)
	{
		this.text = text;
		this.clipedText = "";
		formatText();
	}
	
	@Override
	public void center()
	{
		super.center();
		center(true, true);
	}
	
	public void center(boolean vertical, boolean horizontal)
	{
		int textWidth = textBounds.width;
		int textHeight = textBounds.height;
		int relativeTextX = textBounds.x;
		int relativeTextY = textBounds.y;
		
		if (horizontal)
		{
			textWidth = font.getWidth(text);
			relativeTextX = (bounds.width / 2) - (textWidth / 2);
		}
		
		if (vertical)
		{
			textHeight = font.getHeight(text);
			relativeTextY = (bounds.height / 2) - (textHeight / 2);
		}
		
		this.trueTextBounds = new Rectangle(relativeTextX, relativeTextY,
				textWidth, textHeight);
		
		this.center = vertical || horizontal;
	}
	
	public void setFormatting(FormattingOption formatting)
	{
		this.formatting = formatting;
	}

	public String getText()
	{
		return text;
	}
	
	
}
