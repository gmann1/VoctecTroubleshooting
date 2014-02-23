package edu.asu.voctec.minigames.controller_sizing;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;

public class InitialPart extends PartControl {
	
	private int initialX, initialY;
	private  static ControllerSizingPart3 gameWorld;
	private Image image;
	private static BasicComponent horizontalLine;
	private String label;
	
    public InitialPart(String label, int initialX, int initialY, Image image, int x, int y, ControllerSizingPart3 batteryGameWorld)
    {
        super(image, x, y);
        this.label = label;
        this.initialX = initialX;
        this.initialY = initialY;
        this.image = image;
        gameWorld = batteryGameWorld;
    }
    
    @Override
    public void update() 
    {
        
        if(mouseDragEnded() && withinArrayCreationArea())
        {
        	Part newBattery = new Part(label, image, mouseX()-(getBounds().width/2), mouseY()-(getBounds().height/2), gameWorld);
            gameWorld.addObject(newBattery);
            newBattery.initiate();
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
    
    public String getLabel()
    {
    	return label;
    }
}
