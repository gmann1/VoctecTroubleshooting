package edu.asu.voctec.utilities;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.TrueTypeFont;

public class Line
{
	protected String text;
	protected Rectangle bounds;
	
	public Line(String text, TrueTypeFont font)
	{
		this.text = text;
		recalculateBounds(font);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text, TrueTypeFont font)
	{
		this.text = text;
		recalculateBounds(font);
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void recalculateBounds(TrueTypeFont font)
	{
		if (text != null)
			this.bounds = UtilFunctions.getTextBounds(text, font);
		else
			bounds = null;
	}
	
	public static ArrayList<String> toStringList(ArrayList<Line> lines)
	{
		ArrayList<String> linesAsStrings = new ArrayList<>();
		for (Line line : lines)
			linesAsStrings.add(line.text);
		
		return linesAsStrings;
	}
	
	public static ArrayList<Line> fromStringList(ArrayList<String> lines, TrueTypeFont font)
	{
		ArrayList<Line> linesList = new ArrayList<>();
		
		for (String line : lines)
			linesList.add(new Line(line, font));
		
		return linesList;
	}
	
	public String toString()
	{
		return text;
	}
}
