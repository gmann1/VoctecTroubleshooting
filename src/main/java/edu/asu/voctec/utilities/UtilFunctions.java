package edu.asu.voctec.utilities;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import edu.asu.voctec.GameDefaults;
import edu.asu.voctec.GUI.Component;

/**
 * Provides generic support functions, for use in a variety of applications,
 * such as: additional transformation functions for java.awt.Rectangle; batch
 * support for component operations, such as rescale and translate; and display
 * support, for positioning groups of components, relative to the screen and
 * other components.
 * 
 * @author Moore, Zachary
 * 
 */
public abstract class UtilFunctions
{
	public static Rectangle[][] divideScreen(Dimension plane, int rows,
			int columns)
	{
		Rectangle[][] sections = new Rectangle[rows][columns];
		int width = plane.width / columns;
		int height = plane.height / rows;
		
		for (int row = 0; row < rows; row++)
		{
			for (int column = 0; column < columns; column++)
			{
				int x = width * column;
				int y = height * row;
				sections[row][column] = new Rectangle(x, y, width, height);
			}
		}
		
		return sections;
	}
	
	public static Image createImage(String imagePath)
	{
		System.out.println("Loading Image...");
		try
		{
			return new Image(imagePath);
		}
		catch (Exception e)
		{
			System.out.println("Image Load Failed");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void centerRectangleVertically(final Rectangle container,
			Rectangle moveableRectangle)
	{
		int x = moveableRectangle.x;
		int y = container.y
				+ ((container.height - moveableRectangle.height) / 2);
		
		moveableRectangle.setLocation(x, y);
	}
	
	public static void centerRectangleHorizontally(final Rectangle container,
			Rectangle moveableRectangle)
	{
		int x = container.x + ((container.width - moveableRectangle.width) / 2);
		int y = moveableRectangle.y;
		
		moveableRectangle.setLocation(x, y);
	}
	
	public static void centerRectangle(final Rectangle container,
			Rectangle moveableRectangle)
	{
		centerRectangleHorizontally(container, moveableRectangle);
		centerRectangleVertically(container, moveableRectangle);
	}
	
	public static Rectangle newCenteredRectangle(Rectangle container, Rectangle relativeBounds)
	{
		Rectangle newRectangle = new Rectangle(relativeBounds);
		UtilFunctions.centerRectangle(container, newRectangle);
		return newRectangle;
	}
	
	public static Rectangle dialateRectangle(Rectangle bounds, float scale)
	{
		Rectangle scaledBounds = UtilFunctions
				.getScaledRectangle(bounds, scale);
		
		UtilFunctions.centerRectangle(bounds, scaledBounds);
		
		return scaledBounds;
	}
	
	public static Rectangle dialateRectangle(Image image, float scale)
	{
		Rectangle bounds = getImageBounds(image);
		Rectangle scaledBounds = UtilFunctions
				.getScaledRectangle(bounds, scale);
		
		UtilFunctions.centerRectangle(bounds, scaledBounds);
		
		return scaledBounds;
	}
	
	public static Rectangle dialateRelativeRectangle(Rectangle bounds,
			float scale)
	{
		Rectangle relativeBounds = new Rectangle(0, 0, bounds.width,
				bounds.height);
		
		return dialateRectangle(relativeBounds, scale);
	}
	
	public static Rectangle getTranslatedRectangle(Rectangle baseRectangle,
			Point translationAmount)
	{
		int x = baseRectangle.x + translationAmount.x;
		int y = baseRectangle.y + translationAmount.y;
		int width = baseRectangle.width;
		int height = baseRectangle.height;
		
		return new Rectangle(x, y, width, height);
	}
	
	public static Rectangle getScaledRectangle(Rectangle baseRectangle,
			float scale)
	{
		return getScaledRectangle(baseRectangle, scale, scale);
	}
	
	public static Rectangle getScaledRectangle(Rectangle baseRectangle,
			float horizontalScale, float verticalScale)
	{
		// Scale horizontal components
		int x = (int) (baseRectangle.x * horizontalScale);
		int width = (int) (baseRectangle.width * horizontalScale);
		
		// Scale vertical components
		int y = (int) (baseRectangle.y * verticalScale);
		int height = (int) (baseRectangle.height * verticalScale);
		
		// Return a rectangle representing a scaled version of the object
		return new Rectangle(x, y, width, height);
	}
	
	public static Rectangle getImageBounds(Image image)
	{
		return new Rectangle(0, 0, image.getWidth(), image.getHeight());
	}
	
	public static Rectangle getTextBounds(String text, TrueTypeFont font)
	{
		int width = font.getWidth(text);
		int height = font.getHeight();
		Rectangle textBounds = new Rectangle(0, 0, width, height);
		
		return textBounds;
	}
	
	public static void translateAll(Point translationAmount,
			Component... components)
	{
		translateAll(translationAmount.x, translationAmount.y, components);
	}
	
	public static void translateAll(int horizontalAmount, int verticalAmount,
			Component... components)
	{
		for (Component component : components)
			component.translate(horizontalAmount, verticalAmount);
	}
	
	public static <T extends Component> void translateAll(int horizontalAmount,
			int verticalAmount, ArrayList<T> components)
	{
		translateAll(horizontalAmount, verticalAmount,
				components.toArray(new Component[components.size()]));
	}
	
	public static <T extends Component> void translateAll(
			Point translationAmount, ArrayList<T> components)
	{
		translateAll(translationAmount,
				components.toArray(new Component[components.size()]));
	}
	
	public static void centerComponentsStacked(final Rectangle container,
			int spaceBetweenComponents, Component... components)
	{
		// Variables for the bounds of a rectangle that encompasses all give
		// components
		int width = 0;
		int height = 0;
		
		// Determine the bounds for a rectangle that encompasses all give
		// components
		for (Component component : components)
		{
			int componentMaxX = component.getX() + component.getBounds().width;
			width = (componentMaxX > width) ? componentMaxX : width;
			height += component.getBounds().height;
		}
		
		// Account for the space between components
		height += spaceBetweenComponents * (components.length - 1);
		
		// Create rectangle that encompasses all give components
		Rectangle groupBounds = new Rectangle(0, 0, width, height);
		System.out.println("groupBounds: " + groupBounds.toString());
		
		// Center the rectangle (groupBounds) relative to the given container
		UtilFunctions.centerRectangle(container, groupBounds);
		System.out.println("groupBounds Centered: " + groupBounds.toString());
		
		// Set the location of each component
		int currentY = groupBounds.y;
		for (int componentIndex = 0; componentIndex < components.length; componentIndex++)
		{
			// Define the current component
			Component currentComponent = components[componentIndex];
			
			// Define the bounds of the current component
			Rectangle componentBounds = new Rectangle(0, currentY,
					currentComponent.getBounds().width,
					currentComponent.getBounds().height);
			
			// Center the current component horizontally
			UtilFunctions.centerRectangleHorizontally(groupBounds,
					componentBounds);
			
			// Update the component
			currentComponent.setBounds(componentBounds);
			
			// Account for space between components
			currentY += componentBounds.height + spaceBetweenComponents;
		}
	}
	
	public static float[] getScales(Rectangle bounds, int width, int height)
	{
		float horizontalScale = ((float) width) / ((float) bounds.width);
		float verticalScale = ((float) height) / ((float) bounds.height);
		
		return new float[] { horizontalScale, verticalScale };
	}
	
	public static String getOrdinalRepresentation(int number)
	{
		String ordinalRepresentation;
		
		switch (number)
		{
			case 1:
				ordinalRepresentation = GameDefaults.Labels.OrdinalNumbers.FIRST
						.getTranslation();
				break;
			case 2:
				ordinalRepresentation = GameDefaults.Labels.OrdinalNumbers.SECOND
						.getTranslation();
				break;
			case 3:
				ordinalRepresentation = GameDefaults.Labels.OrdinalNumbers.THIRD
						.getTranslation();
				break;
			case 4:
				ordinalRepresentation = GameDefaults.Labels.OrdinalNumbers.FOURTH
						.getTranslation();
				break;
			case 5:
				ordinalRepresentation = GameDefaults.Labels.OrdinalNumbers.FIFTH
						.getTranslation();
				break;
			case 6:
				ordinalRepresentation = GameDefaults.Labels.OrdinalNumbers.SIXTH
						.getTranslation();
				break;
			default:
				ordinalRepresentation = Integer.toString(number);
				break;
		}
		
		return ordinalRepresentation;
	}
	
	public static void populateNull(List<?> list, int numberOfElements)
	{
		list.clear();
		for (int index = 0; index < numberOfElements; index++)
			list.add(null);
	}
	
	public static String formatTime(long miliseconds, boolean showHours,
			boolean showSeconds)
	{
		// Obtain raw values
		long seconds = miliseconds / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		
		// Normalize Values, and Adjust for Preferences
		if (showSeconds)
			seconds = seconds % 60;
		if (showHours)
			minutes = minutes % 60;
		
		// Create String
		StringBuilder timeString = new StringBuilder();
		if (showHours)
			timeString.append(hours + ":");
		if (true)
			timeString.append(minutes);
		if (showSeconds)
			timeString.append(":" + seconds);
		
		return timeString.toString();
	}
}
