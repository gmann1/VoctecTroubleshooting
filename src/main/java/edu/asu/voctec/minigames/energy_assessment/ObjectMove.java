package edu.asu.voctec.minigames.energy_assessment;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;

import org.lwjgl.input.Mouse;

public abstract class ObjectMove extends BasicComponent {

	protected boolean mousePress = false;
	protected static ObjectMove objectSelected;
	
	public final int objectHeight = 90;
	public final int objectWidth = 90;
	
	public ObjectMove(Image image, int x, int y) {
		super(image, x, y);
	}
	
	public abstract void update();
	
	protected int mouseX()
	{
		return (Mouse.getX());
	}
	
	protected int mouseY()
	{
		return (600-Mouse.getY());
	}
	
	protected boolean withinDropArea()
    {
		return withinDropArea( getX(), getY());
    }
	
	protected boolean withinDropArea(int x, int y)
    {
        return (y >= 75 && y <= 500 && x >= 25 && x <= 775);
    }
    
    protected boolean thisObjectSelected(int x, int y)
    {
        return (x >= getX() && x <= getX()+objectWidth && y >= getY() && y <= getY()+objectHeight);
    }
    
    protected boolean mouseDragged()
    {
    	if(mouseDragEnded())
    	{
    		mousePress = false;
    		objectSelected = null;
    	}
    	if(Mouse.isButtonDown(0)&& thisObjectSelected(mouseX(), mouseY()) && objectSelected == null)
    		objectSelected = this;
    	if (objectSelected == this && !mouseDragEnded() && Mouse.isButtonDown(0) && (thisObjectSelected(mouseX(), mouseY())||mousePress)) {
    		mousePress = true;
    		objectSelected = this;
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    
    protected boolean mouseDragEnded()
    {
    	if (!Mouse.isButtonDown(0) && mousePress) {
    		mousePress = false;
    		objectSelected = null;
    		return true;
    	}
    	else
			return false;
    }
    
    protected void setLocation(int x, int y)
    {
    	this.setX(x);
		this.setY(y);
    }
}
