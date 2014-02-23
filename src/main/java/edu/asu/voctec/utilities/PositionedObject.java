package edu.asu.voctec.utilities;

import java.awt.Point;

public class PositionedObject<T> extends Point
{
	private static final long serialVersionUID = -2751010902938108277L;
	
	public T data;
	
	public PositionedObject(T data, int x, int y)
	{
		super(x, y);
		this.data = data;
	}
	
	public PositionedObject(T data, Point relativeLocation)
	{
		super(relativeLocation.x, relativeLocation.y);
		this.data = data;
	}
}
