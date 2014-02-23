package edu.asu.voctec.minigames.controller_sizing;

import org.newdawn.slick.Image;

import edu.asu.voctec.GUI.BasicComponent;

import java.util.List;
import java.util.ArrayList;

public class Part extends PartControl{
	
	public static List<ArrayList<Part>> batteryArray = new ArrayList<ArrayList<Part>>();
	public static List<BasicComponent> TransparentBatteriesArray = new ArrayList<BasicComponent>();
    private int seriesIndex, parallelIndex;
    private static ControllerSizingPart3 gameWorld;
    public static final int xBatteryOffset = 35, yBatteryOffset = 85, xLinesOffset = 25, yLinesOffset = -5, xBatteryIntervals = 90, yBatteryIntervals = 90;
    private static final int maxSeriesBatteries = 6, maxParallelBatteries = 4;
    private String label;
	
	public Part(String label, Image image, int x, int y, ControllerSizingPart3 gameWorld) {
		super(image, x, y);
		this.label = label;
		Part.gameWorld = gameWorld;
	}

	@Override
	public void update() {
		
		if(mouseDragEnded())
        {
			removeFromArray();
            if(withinArrayCreationArea())
            {
            	this.checkSuggestedLocations();
            }
            else
            	gameWorld.removeObject(this);
            
            organizeBatteries();
        }
        if(mouseIsBeingDragged())
        {
            setLocation(mouseX()-(getBounds().width/2), mouseY()-(getBounds().height/2));
        }
	}
	
	public String getLabel()
    {
    	return label;
    }
    
    public void setSeriesIndex(int seriesIndex)
    {
        this.seriesIndex = seriesIndex;
    }
    
    public void setParallelIndex(int parallelIndex)
    {
        this.parallelIndex = parallelIndex;
    }
    
    public void initiate()
    {
        checkSuggestedLocations();
    }
    
    private void checkSuggestedLocations()
    {
    	if(!TransparentBatteriesArray.isEmpty())
    	{
    		for(BasicComponent TransparentBattery:TransparentBatteriesArray)
    		{
    			if(withinX(TransparentBattery) && withinY(TransparentBattery))
                {
    				if(this.getY()> (yBatteryOffset+getBounds().height))
    				{
    					setLocation(this.getX(), this.getY()-yBatteryIntervals);
    					addToArray();
    					return;
    				}
                }
    		}
    	}
    	addToArray();
    }
    
    public void addToArray()
    {
       
    	if(!connectedInParallel())
       {
           seriesIndex = batteryArray.size();
           batteryArray.add(new ArrayList<Part>());
           parallelIndex = batteryArray.get(seriesIndex).size();
           batteryArray.get(seriesIndex).add(this);
       }
       organizeBatteries();
    }
    
    private boolean connectedInParallel()
    {
        for(int indexInSeries = 0; indexInSeries < batteryArray.size(); indexInSeries++)
        {
            for(int indexInParallel = 0; indexInParallel < batteryArray.get(indexInSeries).size(); indexInParallel++)
            {
                if(!batteryArray.get(indexInSeries).isEmpty())
                {
                    ArrayList<Part> testedBatteryArray = batteryArray.get(indexInSeries);
                    Part testedBattery = testedBatteryArray.get(indexInParallel);
                    if(withinX(testedBattery) && withinY(testedBattery))
                    {
                       seriesIndex = indexInSeries;
                       parallelIndex = batteryArray.get(indexInSeries).size();
                       batteryArray.get(indexInSeries).add(this);
                       return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean withinX(BasicComponent testedBattery)
    {
        if(this.getX() < testedBattery.getX()+getBounds().width && this.getX()>=testedBattery.getX())
            return true;
        else if(this.getX() > testedBattery.getX()-getBounds().width && this.getX()<=testedBattery.getX())
            return true;
        else
            return false;
    }
    
    private boolean withinY(BasicComponent testedBattery)
    {
        if(this.getY() < testedBattery.getY()+getBounds().height && this.getY()>=testedBattery.getY())
            return true;
        else if(this.getY() > testedBattery.getY()-getBounds().height && this.getY()<=testedBattery.getY())
            return true;
        else
            return false;
    }
    
    private void organizeBatteries()
    {
    	for(int indexInSeries = 0; indexInSeries < batteryArray.size(); indexInSeries++)
        {
            for(int indexInParallel = 0; indexInParallel < batteryArray.get(indexInSeries).size(); indexInParallel++)
            {
                ArrayList<Part> testedBatteryArray = batteryArray.get(indexInSeries);
                Part testedBattery = testedBatteryArray.get(indexInParallel);
                testedBattery.setSeriesIndex(indexInSeries);
                testedBattery.setParallelIndex(indexInParallel);
                testedBattery.setBatteryLocation();
            }
        }
    	addTransparentBatteries();
    }
    
    public static void addToTransparentBatteriesArray(BasicComponent TransparentBattery)
    {
    	TransparentBatteriesArray.add(TransparentBattery);
    }
    
    public static void addTransparentBatteries()
    {
    	removeTransparentBatteries();
    	if(batteryArray.size() < maxSeriesBatteries)
    		addTransparentBatteries(batteryArray.size(), 0);
        if(!batteryArray.isEmpty())
        {
	        for(int indexInSeries = 0; indexInSeries < batteryArray.size(); indexInSeries++)
	        {
	        	if(batteryArray.get(indexInSeries).size() < maxParallelBatteries)
	        		addTransparentBatteries(indexInSeries, batteryArray.get(indexInSeries).size());
	        }
        }
    }
    
    private static void removeTransparentBatteries()
    {
    	if(!TransparentBatteriesArray.isEmpty())
    	{
    		for(BasicComponent TransparentBattery:TransparentBatteriesArray)
    			gameWorld.removeComponent(TransparentBattery);
    	}
    }
    
    private static void addTransparentBatteries(int x, int y)
    {
    	BasicComponent TransparentBattery = new BasicComponent(ControllerSizingPart3.getTransparentBatteryImage(),xBatteryOffset+(x*xBatteryIntervals),yBatteryOffset+(y*yBatteryIntervals));
    	gameWorld.addComponent(TransparentBattery);
    	TransparentBatteriesArray.add(TransparentBattery);
    }
    
    private void setBatteryLocation()
    {
        setLocation(xBatteryOffset+(seriesIndex*xBatteryIntervals), yBatteryOffset+(parallelIndex*yBatteryIntervals));
    }
    
    private void removeFromArray()
    {
        batteryArray.get(seriesIndex).remove(parallelIndex);
        if(batteryArray.get(seriesIndex).isEmpty())
            batteryArray.remove(seriesIndex);
    }   
    
    public static void reset()
    {
    	if(!batteryArray.isEmpty())
    	{
	    	for(int indexRows = 0; indexRows<batteryArray.size(); indexRows++)
	    	{
	    		for(int indexColumn = 0; indexColumn<batteryArray.get(indexRows).size(); indexColumn++)
	        	{
	    			Part removedBattery = batteryArray.get(indexRows).get(indexColumn);
	    			gameWorld.removeObject(removedBattery);
	        	}
	    	}
	    	batteryArray.clear();
	    	addTransparentBatteries();
    	}
    }
}
