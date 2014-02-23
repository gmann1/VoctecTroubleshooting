package edu.asu.voctec.minigames.energy_assessment;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.minigames.battery_sizing.Battery;

public class InitialObjects extends ObjectMove {
	
	private int initialX, initialY;
	private  static EAPart2 gameWorld;
	private Image image;
	private int device, watt;
	
    public InitialObjects(Image image, int x, int y, EAPart2 thisGameWorld, int thisDevice, int thisWatt)
    {
        super(image, x, y);
        this.initialX = x;
        this.initialY = y;
        this.image = image;
        gameWorld = thisGameWorld;
        device = thisDevice;
        watt = thisWatt;
    }
    
    @Override
    public void update() 
    {
        
        if(mouseDragEnded())
        {
			for(int v=0;v<5;v++)
			{
				if(checkLocation(v))
				{
					Object newObject = new Object(image, mouseX()-(objectWidth/2), mouseY()-(objectHeight/2), gameWorld, device, watt);
	        		gameWorld.addObject(newObject);
					break;
				}
			}
            setLocation(initialX, initialY);
        }
        if(mouseDragged())
        {
            setLocation(mouseX()-(objectWidth/2), mouseY()-(objectHeight/2));
        }
        else
        {
            setLocation(initialX, initialY);
        }
    }
    
    public boolean checkLocation(int v)
    {
    	if((getX()+(objectWidth/2)) >= EAPart2.locationArray[v][0] && (getX()+(objectWidth/2)) < (EAPart2.locationArray[v][0]+objectWidth) && (getY()+(objectHeight/2)) >= EAPart2.locationArray[v][1] && (getY()+(objectHeight/2)) < (EAPart2.locationArray[v][1]+objectHeight))
		{
    		if(EAPart2.applianceArray[v] == 0)
    		{
    			return true;
    		}
		}
    	return false;
    }
}
