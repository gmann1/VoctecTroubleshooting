package edu.asu.voctec.minigames.battery_sizing;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;

import org.lwjgl.input.Mouse;

public abstract class BatteryControl extends BasicComponent {

	protected boolean mouseIsPressed = false;
	protected static BatteryControl objectSelected;
	
	public BatteryControl(Image image, int x, int y) {
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
	
	protected boolean withinArrayCreationArea()
    {
		return withinArrayCreationArea(getX(), getY());
    }
	
	protected boolean withinArrayCreationArea(int x, int y)
    {
        return (y >= BatteryGameScreen.getBatteryBankAreaBounds().y 
        		&& y <= (BatteryGameScreen.getBatteryBankAreaBounds().y+BatteryGameScreen.getBatteryBankAreaBounds().height) 
        		&& x >= BatteryGameScreen.getBatteryBankAreaBounds().x 
        		&& x <= (BatteryGameScreen.getBatteryBankAreaBounds().x+BatteryGameScreen.getBatteryBankAreaBounds().width));
    }
    
    protected boolean thisObjectSelected(int x, int y)
    {
        return (x >= getX() && x <= getX()+this.currentImage.getWidth() && y >= getY() && y <= getY()+this.currentImage.getHeight());
    }
    
    protected boolean mouseIsBeingDragged()
    {
    	if(mouseDragEnded())
    	{
    		mouseIsPressed = false;
    		objectSelected = null;
    	}
    	if(Mouse.isButtonDown(0)&& thisObjectSelected(mouseX(), mouseY()) && objectSelected == null)
    		objectSelected = this;
    	if (objectSelected == this && !mouseDragEnded() && Mouse.isButtonDown(0) && (thisObjectSelected(mouseX(), mouseY())||mouseIsPressed)) {
    		mouseIsPressed = true;
    		objectSelected = this;
    		return true;
    	}
    	else
    		return false;
    }
    
    
    protected boolean mouseDragEnded()
    {
    	if (!Mouse.isButtonDown(0) && mouseIsPressed) {
    		mouseIsPressed = false;
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
