package edu.asu.voctec.GUI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import edu.asu.voctec.utilities.UtilFunctions;

public class Column<T extends Component> extends Component
{
	private static final long serialVersionUID = 4789499359873994612L;
	
	protected Rectangle bounds;
	protected ArrayList<T> units;
	protected int currentYOffset;
	
	public Column(Rectangle bounds)
	{
		this.bounds = bounds;
		this.units = new ArrayList<>();
		this.currentYOffset = 0;
	}
	
	public void add(T unit)
	{
		// Ensure the unit width does not exceed the width of this column
		if(unit.getBounds().width > bounds.width)
		{
			// Resize unit bounds to the width of this column
			int height = unit.getBounds().height;
			int x = unit.getX();
			int y = unit.getY();
			Rectangle fittedBounds = new Rectangle(x, y, bounds.width, height);
			unit.setBounds(fittedBounds);
		}
		
		// Ensure the unit height does not exceed the height of this column
		if(unit.getBounds().height > bounds.height)
		{
			// Resize unit bounds to the width of this column
			int width = unit.getBounds().width;
			int x = unit.getX();
			int y = unit.getY();
			Rectangle fittedBounds = new Rectangle(x, y, width, bounds.height);
			unit.setBounds(fittedBounds);
		}
		
		// Position unit within this column
		unit.setLocation(new Point(bounds.x, bounds.y + currentYOffset));
		currentYOffset += unit.getBounds().height;
		units.add(unit);
	}
	
	public void remove(T unit)
	{
		units.remove(unit);
	}

	@Override
	public void draw(Graphics graphics)
	{
		int maxHeight = bounds.height;
		int currentHeight = 0;
		
		for (T unit : units)
		{
			currentHeight += unit.getBounds().height;
			
			if (currentHeight > maxHeight)
				break;
			else
				unit.draw(graphics);
		}
		
	}

	@Override
	public int getX()
	{
		return bounds.x;
	}

	@Override
	public void setX(int x)
	{
		int delta = x - bounds.x;
		bounds.translate(delta, 0);
		
		for (T unit : units)
			unit.translate(delta, 0);
	}

	@Override
	public void setY(int y)
	{
		int delta = y - bounds.y;
		bounds.translate(0, delta);
		
		for (T unit : units)
			unit.translate(0, delta);
	}

	@Override
	public int getY()
	{
		return bounds.y;
	}

	@Override
	public Rectangle getBounds()
	{
		return bounds;
	}

	@Override
	public boolean resize(int width, int height)
	{
		float horizontalScale = ((float) width) / ((float) bounds.width);
		float verticalScale = ((float) height) / ((float) bounds.height);
		
		// Translate all units to (0, 0)
		UtilFunctions.translateAll(-bounds.x, -bounds.y, units);
		
		// Scale all units
		for (T unit : units)
			unit.rescale(horizontalScale, verticalScale);
		
		// Translate all units back to this component's location
		UtilFunctions.translateAll(bounds.x, bounds.y, units);
		
		// Change the size of this component's bounds
		bounds.setSize(width, height);
		
		// Return true if no errors occurred
		return true;
	}
	
	public ArrayList<T> getUnits()
	{
		return units;
	}
	
	public T getUnitAt(int index)
	{
		return units.get(index);
	}
	
}
