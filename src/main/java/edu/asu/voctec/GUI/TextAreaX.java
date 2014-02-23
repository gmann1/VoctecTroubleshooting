package edu.asu.voctec.GUI;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Graphics;

import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.utilities.Line;
import edu.asu.voctec.utilities.UtilFunctions;

public class TextAreaX extends TextArea
{
	private static final long serialVersionUID = -7152954056850354098L;
	
	public TextAreaX(Rectangle bounds, Rectangle textBounds, Font awtFont,
			boolean antiAlias, String text)
	{
		super(bounds, textBounds, awtFont, antiAlias, null);
		setText(text);
		//System.out.println("Lines: "
				//+ Arrays.toString(lines.toArray(new Line[lines.size()])));
	}
	
	public TextAreaX(Rectangle bounds, Rectangle textBounds, String text)
	{
		this(bounds, textBounds, Defaults.AWT_FONT, Fonts.ANTI_ALLIAS, text);
	}
	
	public TextAreaX(Rectangle bounds, float textBounds, Font awtFont,
			boolean antiAlias, String text)
	{
		this(bounds,
				UtilFunctions.dialateRelativeRectangle(bounds, textBounds),
				awtFont, antiAlias, text);
	}
	
	public TextAreaX(Rectangle bounds, float textBounds, String text)
	{
		this(bounds,
				UtilFunctions.dialateRelativeRectangle(bounds, textBounds),
				Defaults.AWT_FONT, Fonts.ANTI_ALLIAS, text);
	}
	
	public void setFontSize(float size)
	{
		super.setFontSize(size);
		formatText();
	}
	
	public void wrapText()
	{
		ArrayList<String> wrappedText = new ArrayList<>();
		
		// Handle all lines between null entries as a block
		int startIndex = 0;
		while (startIndex < lines.size())
		{
			// Determine where this block ends
			int blockEndIndex = lines.indexOf(null);
			if (blockEndIndex < 0)
				blockEndIndex = lines.size();
			StringBuilder blockText = new StringBuilder();
			
			// Combine all lines in this block with a space
			for (int index = startIndex; index < blockEndIndex; index++)
			{
				blockText.append(lines.get(index));
				blockText.append(" ");
			}
			
			// Wrap this block across multiple lines
			ArrayList<String> wrappedBlock = TextSupport.wrapText(font,
					blockText.toString(), textBounds.width);
			
			// Add this block to the new line array
			wrappedText.addAll(wrappedBlock);
			
			// Account for new line character
			wrappedText.add(null);
			
			// Determine the bounds of the next block
			startIndex = blockEndIndex;
			if (blockEndIndex < lines.size())
				lines.remove(blockEndIndex);
		}
		
		// One extra "null" will have been added; Remove it
		if (wrappedText.size() > 0)
			wrappedText.remove(wrappedText.size() - 1);
		
		lines.clear();
		lines.addAll(Line.fromStringList(wrappedText, font));
		this.clipedText = determineClipedText();
	}
	
	public void setText(String text)
	{
		this.lines.clear();
		this.clipedText = "";
		
		if (text != null)
			lines.addAll(Line.fromStringList(getTextBlocks(text), font));
		
		formatText();
	}
	
	public void setText(ArrayList<String> text)
	{
		this.lines.clear();
		this.clipedText = "";
		StringBuilder textAsString = new StringBuilder();
		//System.out.println("\tMid4: Current Hints: "
				//+ Arrays.toString(text.toArray()));
		
		if (text != null)
		{
			for (String line : text)
			{
				if (line == null)
					textAsString.append("\n");
				else
					textAsString.append(line + " ");
			}
		}
		//System.out.println("\tMid5: Current Hints: "
				//+ Arrays.toString(text.toArray()));
		
		lines.addAll(Line.fromStringList(
				getTextBlocks(textAsString.toString()), font));
		//System.out.println("\tMid6: Current Hints: "
				//+ Arrays.toString(text.toArray()));
		
		formatText();
		//System.out.println("\tMid7: Current Hints: "
				//+ Arrays.toString(text.toArray()));
	}
	
	protected ArrayList<String> getTextBlocks(String text)
	{
		if (text == null)
		{
			ArrayList<String> nullStringBlock = new ArrayList<>();
			nullStringBlock.add(null);
			return nullStringBlock;
		}
		
		String[] brokenString = text.split("\n");
		ArrayList<String> blocks = new ArrayList<>();
		
		for (String block : brokenString)
		{
			blocks.add(block);
			
			// Account for new line character
			blocks.add(null);
		}
		
		// One extra "null" will have been added; Remove it
		blocks.remove(blocks.size() - 1);
		
		return blocks;
	}
	
	protected String determineClipedText()
	{
		if (lines.size() <= this.maximumDisplayLines)
			return "";
		else
		{
			StringBuilder clip = new StringBuilder();
			
			int startingIndex = maximumDisplayLines;
			for (int lineIndex = startingIndex; lineIndex < lines.size(); lineIndex++)
			{
				String line = lines.get(lineIndex).getText();
				
				if (line == null)
					clip.append("\n");
				else
					clip.append(line + " ");
			}
			
			return clip.toString();
		}
	}
	
	@Override
	protected void drawText(Graphics graphics)
	{
		graphics.setFont(font);
		graphics.setColor(fontColor);
		
		int locationIncrement = font.getHeight();
		int maxLine = (maximumDisplayLines < lines.size()) ? maximumDisplayLines
				: lines.size();
		
		int boundX = textBounds.x + bounds.x;
		int y = textBounds.y + bounds.y;
		
		for (int lineIndex = 0; lineIndex < maxLine; lineIndex++)
		{
			int x = boundX;
			Line currentLine = lines.get(lineIndex);
			String lineText = (currentLine == null) ? null : currentLine
					.getText();
			
			if (center && currentLine != null)
				x += ((textBounds.width - currentLine.getBounds().width) / 2);
			
			// Disregard single newLine marks
			if (lineText != null)
			{
				graphics.drawString(lineText, x, y);
				y += locationIncrement;
			}
			// Treat 2 newLine marks as a blank line
			else if (lineIndex > 0 && lines.get(lineIndex - 1) == null)
				y += locationIncrement;
			// Do not count disregarded NL marks against the line count
			else
				maxLine = (++maxLine < lines.size()) ? maxLine : lines.size();
		}
		
	}
	
	@Override
	protected void formatText()
	{
		this.maximumDisplayLines = calculateMaxDisplayLines();
		wrapText();
	}
	
	@Override
	protected int calculateMaxDisplayLines()
	{
		int lineHeight = font.getHeight();
		
		return textBounds.height / lineHeight;
	}
	
	public void append(String text)
	{
		boolean extraNull = false;
		ArrayList<String> textBlocks = getTextBlocks(text);
		
		for (String textBlock : textBlocks)
		{
			lines.addAll(Line.fromStringList(
					TextSupport.wrapText(font, textBlock, textBounds.width),
					font));
			lines.add(null);
			extraNull = true;
		}
		
		// One extra "null" will have been added; Remove it
		if (extraNull)
			lines.remove(lines.size() - 1);
	}
	
	public void addLine()
	{
		lines.add(null);
	}
	
	public void addLine(String text)
	{
		if (lines.size() > 0)
			addLine();
		
		append(text);
	}
	
	public void addLines(ArrayList<String> lines)
	{
		addLines(lines, false);
	}
	
	public void addLines(ArrayList<String> lines, boolean doubleSpaced)
	{
		if (lines != null)
		{
			for (String line : lines)
			{
				addLine(line);
				if (doubleSpaced)
					addLine();
			}
		}
	}
	
	public void clear()
	{
		lines.clear();
		this.clipedText = "";
	}
	
	public void printLines()
	{
		//System.out.println("Lines: " + Arrays.toString(lines.toArray()));
	}
	
	public ArrayList<String> getText()
	{
		return Line.toStringList(lines);
	}
	
}
