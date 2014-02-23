package edu.asu.voctec.GUI;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.newdawn.slick.TrueTypeFont;

/**
 * Support class for displaying text using OpenGL. Includes methods to scale,
 * and auto-size fonts, as well as methods for text wrapping and clipping.
 * 
 * @author Moore, Zachary
 * 
 */
public abstract class TextSupport
{
	private static final int MAX_FONT_SIZE = 43;
	
	/**
	 * Default value to search for the desired font size.
	 * 
	 * @see #getMaxScaledFontSize(Font, Rectangle, double, int)
	 * @see #getMaxScaledFontSize(Font, String, Rectangle, double, int)
	 */
	private static final int DEFAULT_SEARCH_INCREMENT = 5;
	
	/**
	 * Removes words from a string that exceed the maximum width of the string.
	 * The clipped string will, then, be able to be displayed in a box
	 * "maxWidth" pixels wide, when displayed using the provided font. If the
	 * given string is null or empty, then an array of two empty strings will be
	 * returned. This ensures that the returned text can always be drawn to the
	 * screen without null pointer exceptions.
	 * 
	 * @param font
	 *            font to consider when calculating the size of the string
	 * @param string
	 *            string to clip
	 * @param maxWidth
	 *            the clipped string will be a maximum of this many pixels wide,
	 *            when displayed using the provided font.
	 * @return an array of 2 strings. The first element is the clipped string.
	 *         The second element is the remainder of the original string (i.e.
	 *         the portion that was clipped).
	 */
	public static String[] clipString(TrueTypeFont font, String string,
			int maxWidth)
	{
		// TODO add support for words that are longer than the max width
		
		String empty = "";
		
		// Array of two empty strings. Return this as the default value.
		String[] blanks = { empty, empty };
		
		// Split string into individual words.
		String[] allWords = string.split(" ");
		ArrayList<String> clipedWords = new ArrayList<>();
		
		// Account for empty strings
		if (allWords.length < 1)
			return blanks;
		
		// TODO replace with binary algorithm; optimize
		// Determine the number of words that can fit in the desired width.
		int wordIndex;
		int lineWidth = 0;
		int spaceWidth = font.getWidth(" ");
		for (wordIndex = 0; wordIndex < allWords.length; wordIndex++)
		{
			// Get current word, and determine its width.
			String word = allWords[wordIndex];
			int wordWidth = font.getWidth(word);
			
			// If the word will not fit within the desired width, clip the
			// string here. Otherwise, continue adding words to the line.
			if (lineWidth + wordWidth > maxWidth)
				break;
			else
			{
				// Add the new word to the list of words that fit on this line.
				clipedWords.add(word);
				lineWidth += wordWidth;
				
				// Account for white space
				lineWidth += spaceWidth;
			}
		}
		
		// Join the words that fit into a single string.
		String clippedString = StringUtils.join(clipedWords, " ");
		
		// Join all leftover words into a single string
		String leftoverString;
		if (wordIndex < allWords.length)
			leftoverString = StringUtils.join(
					Arrays.copyOfRange(allWords, wordIndex, allWords.length),
					" ");
		else
			// If there are no leftover words, make the leftovers an empty
			// string.
			leftoverString = empty;
		
		// Return a 2-element array.
		// The first element is the clipped string. The second element is the
		// leftover string (the portion that was clipped).
		return new String[] { clippedString, leftoverString };
	}
	
	/**
	 * Separates the provided string into a number of lines, each no longer than
	 * the provided line width. This method is intended to take a long body of
	 * text, and wrap it in a text display. The returned array will be in order
	 * of how the original text appears.
	 * 
	 * @param font
	 *            The font to consider while sizing the text.
	 * @param text
	 *            The text to wrap.
	 * @param lineWidth
	 *            The maximum length (in pixels) that each line can be.
	 * @return An array of strings, representing all lines of the wrapped text.
	 */
	public static ArrayList<String> wrapText(TrueTypeFont font, String text,
			int lineWidth)
	{
		// TODO test
		// TODO add support for newline characters
		
		if (text == null)
		{
			ArrayList<String> splitNullString = new ArrayList<>();
			splitNullString.add(null);
			return splitNullString;
		}
		
		ArrayList<String> lines = new ArrayList<>();
		String remainder = text;
		
		do
		{
			// Clip the string to obtain a line no longer than the given
			// lineWidth.
			String[] clipResults = clipString(font, remainder, lineWidth);
			lines.add(clipResults[0]);
			remainder = clipResults[1];
		} while (!(remainder.isEmpty() || remainder.length() < 1));
		
		return lines;
	}
	
	public static Font getMaxScaledFont(Font font, String text, Rectangle bounds)
	{
		return getMaxScaledFont(font, text, bounds, DEFAULT_SEARCH_INCREMENT);
	}
	
	public static Font getMaxScaledFont(Font font, String text,
			Rectangle bounds, int searchIncrement)
	{
		return font.deriveFont((float) getMaxScaledFontSize(font, text, bounds,
				searchIncrement));
	}
	
	public static Font getMaxVerticalScaledFont(Font font, Rectangle bounds)
	{
		return getMaxVerticalScaledFont(font, bounds, DEFAULT_SEARCH_INCREMENT);
	}
	
	public static Font getMaxVerticalScaledFont(Font font, Rectangle bounds,
			int searchIncrement)
	{
		return font.deriveFont((float) getMaxVerticalScaledFontSize(font,
				bounds, searchIncrement));
	}
	
	public static int getMaxScaledFontSize(Font font, String text,
			Rectangle bounds, int searchIncrement)
	{
		return getMaxScaledFontSize(font, text, bounds, searchIncrement, true);
	}
	
	public static int getMaxVerticalScaledFontSize(Font font, Rectangle bounds,
			int searchIncrement)
	{
		return getMaxScaledFontSize(font, "", bounds, searchIncrement, false);
	}
	
	public static int getMaxScaledFontSize(Font font, String text,
			Rectangle bounds, int searchIncrement, boolean considerWidth)
	{
		//System.out.println("Scaling font...");
		
		// Scale the font to fit the current bounds
		TrueTypeFont fontContainer = new TrueTypeFont(font, false);
		//System.out.println("Size: " + font.getSize());
		
		double textHeight = fontContainer.getHeight();
		double verticalScale = bounds.height / textHeight;
		double trueScale = verticalScale;
		
		if (considerWidth)
		{
			double textWidth = fontContainer.getWidth(text);
			double horizontalScale = bounds.width / textWidth;
			if (horizontalScale < verticalScale)
				trueScale = horizontalScale;
		}
		
		// Account for font variance
		trueScale = trueScale * 0.98;
		int scaledSize = (int) (font.getSize() * trueScale);
		
		//System.out.println("Size: " + scaledSize);
		if (scaledSize > MAX_FONT_SIZE)
		{
			scaledSize = MAX_FONT_SIZE;
			//System.out.println("Scale Capped: " + scaledSize);
		}
		//System.out.println("Scaling Complete.");
		return scaledSize;
	}
	
	public static int getMaxScaledFontSize(Font font, String text,
			Rectangle bounds)
	{
		return getMaxScaledFontSize(font, text, bounds,
				DEFAULT_SEARCH_INCREMENT);
	}
	
	public static int getMaxVerticalScaledFontSize(Font font, Rectangle bounds)
	{
		return getMaxVerticalScaledFontSize(font, bounds,
				DEFAULT_SEARCH_INCREMENT);
	}
}
