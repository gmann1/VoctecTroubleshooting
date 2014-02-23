package edu.asu.voctec.minigames.energy_assessment;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.minigames.battery_sizing.Battery;
import edu.asu.voctec.minigames.battery_sizing.BatteryGameScreen;
import edu.asu.voctec.minigames.battery_sizing.InitialBattery;

import java.util.List;
import java.util.ArrayList;

public class Object extends ObjectMove
{
	public static List<Object> objectArray = new ArrayList<Object>();
	private int index = 10;
	private int device, watt;
	private static EAPart2 gameWorld;
	
	public Object(Image image, int x, int y, EAPart2 thisGameWorld,int thisDevice,int thisWatt) 
	{
		super(image, x, y);
		gameWorld = thisGameWorld;
		device = thisDevice;
		watt = thisWatt;
		for(int v=0;v<5;v++)
		{
			if(checkLocation(v))
			{
				EAPart2.applianceArray[v] = device;
				index = v;
				setObjectLocation();
				addToArray();
				EAPart2.updatePowerRating();
				break;
			}
		}
	}

	@Override
	public void update() 
	{
		if(mouseDragEnded())
        {
			EAPart2.applianceArray[index] = 0;
			index = 10;
			for(int v=0;v<5;v++)
			{
				if(checkLocation(v))
				{
					EAPart2.applianceArray[v] = device;
					index = v;
					setObjectLocation();
					addToArray();
					EAPart2.updatePowerRating();
					break;
				}
			}
			if(index == 10)
			{
				/*for(int v=0;v<5;v++)
				{
					System.out.println(EAPart2.applianceArray[v]);
				}*/
				gameWorld.removeObject(this);
				EAPart2.updatePowerRating();
			}
				
        }
		if(mouseDragged())
        {
            setLocation(mouseX()-(objectWidth/2), mouseY()-(objectHeight/2));
        }
		
	}
    
    private void setObjectLocation()
    {
        setLocation(EAPart2.locationArray[index][0],EAPart2.locationArray[index][1]);
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
    
    public void addToArray()
    {
       objectArray.add(this);
    }
    
    public void removeFromArray()
    {
       objectArray.add(this);
    }
    
    public static void reset()
    {
    	System.out.println(objectArray.size());
    	if(!objectArray.isEmpty())
    	{
	    	for(int indexRows = 0; indexRows<objectArray.size(); indexRows++)
	    	{
	    		System.out.println(objectArray.size());
	    			Object removedObject = objectArray.get(indexRows);
	    			gameWorld.removeObject(removedObject);
	    	}
	    	objectArray.clear();
    	}
    }
}
