package edu.asu.voctec.minigames.battery_sizing;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;

public class InitialBattery extends BatteryControl {
	
	private int voltage, capacity, initialX, initialY;
	private  static BatteryGameScreen gameWorld;
	private Image image;
	private static BasicComponent horizontalLine;
	private static boolean firstBattery = true;
	
    public InitialBattery(int voltage, int capacity, int initialX, int initialY, Image image, int x, int y, BatteryGameScreen batteryGameWorld)
    {
        super(image, x, y);
    	this.voltage = voltage;
        this.capacity = capacity;
        this.initialX = initialX;
        this.initialY = initialY;
        this.image = image;
        gameWorld = batteryGameWorld;
    }
    
    public int getCapacity()
    {
    	return capacity;
    }
    
    
    public int getVoltage()
    {
    	return voltage;
    }
    
    @Override
    public void update() 
    {
        
        if(mouseDragEnded() && withinArrayCreationArea())
        {
        	if(firstBattery)
        		addHorizontalLine();
        	Battery newBattery = new Battery(image, mouseX()-(getBounds().width/2), mouseY()-(getBounds().height/2), gameWorld);
            gameWorld.addObject(newBattery);
            newBattery.initiate(voltage, capacity);
            setLocation(initialX, initialY);
        }
        if(mouseIsBeingDragged())
        {
            setLocation(mouseX()-(getBounds().width/2), mouseY()-(getBounds().height/2));
        }
        else
        {
            setLocation(initialX, initialY);
        }
    }
    
    public static void addHorizontalLine()
    {
    	horizontalLine = new BasicComponent(BatteryGameScreen.getHorizontalLineImage(),gameWorld.getBatteryBankAreaBounds().x, gameWorld.getBatteryBankAreaBounds().y);
    	gameWorld.addComponent(horizontalLine);
    	BatteryGameScreen.changeBatteryBankText();
    	firstBattery = false;
    }
    
    public static void removeHorizontalLine()
    {
    	gameWorld.removeComponent(horizontalLine);
    	firstBattery = true;
    }
}
