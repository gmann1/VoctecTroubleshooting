package edu.asu.voctec.minigames.energy_assessment;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.game_states.GUI;

public class PowerBar extends BasicComponent{
	
	private static final String POWER_BAR_BACKGROUND = "resources/default/img/minigames/energyAssessment/PowerBar/EmptyPowerBarBKGD.png";
	private static final String POWER_BAR_ARROWS = "resources/default/img/minigames/energyAssessment/PowerBar/PowerBarArrows.png";
	private static final String POWER_BAR_ARROWS_CORRECT = "resources/default/img/minigames/energyAssessment/PowerBar/PowerBarArrowsCorrect.png";
	private static final String POWER_BAR_INDICATOR = "resources/default/img/minigames/energyAssessment/PowerBar/PowerBarIndicatorCorrect.png";
	
	public BasicComponent powerBarIndicator, maxArrows, minArrows;
	private Image powerBarArrowsImage, powerBarArrowsCorrectImage;
	private int powerBarRelativeX = 16, powerBarRelativeY = 382, powerBarMaxHeight = 366, powerBarWidth = 75, powerBarHeight = 0;
	private int powerBarArrowsRelativeX = 4, powerBarMaxArrowsY = 0, powerBarMinArrowsY = 0,powerBarArrowsWidth = 99, powerBarArrowsHeight = 18;
	private static int powerBarBackgroundWidth = 107, powerBarBackgroundHeight = 426;
	private int minLimit = 0, maxLimit = 0 , maxPower = 0;
	
	public PowerBar(int x, int y, double scale, int minWinningLimit, int maxWinningLimit, int maxTotalPower)
			throws SlickException {
		
		super(POWER_BAR_BACKGROUND, x, y, (int) (powerBarBackgroundWidth*scale), (int) (powerBarBackgroundHeight*scale));
		
		powerBarArrowsImage = new Image(POWER_BAR_ARROWS);
		powerBarArrowsCorrectImage = new Image(POWER_BAR_ARROWS_CORRECT);
		
		minLimit = minWinningLimit;
		maxLimit = maxWinningLimit;
		maxPower = maxTotalPower;
		powerBarWidth = (int) (powerBarWidth*scale);
		powerBarRelativeY = (int) (powerBarRelativeY*scale);
		powerBarMaxHeight = (int) (powerBarMaxHeight*scale);
		powerBarArrowsHeight = (int) (powerBarArrowsHeight*scale);
		powerBarArrowsWidth = (int) (powerBarArrowsWidth*scale);
		
		powerBarMinArrowsY = powerBarRelativeY-translateIntoHeight(minLimit);
		powerBarMaxArrowsY = powerBarRelativeY-translateIntoHeight(maxLimit);
		
		powerBarIndicator = new BasicComponent(POWER_BAR_INDICATOR, x+((int) (powerBarRelativeX*scale)), y+powerBarRelativeY-powerBarHeight);
		powerBarIndicator.resize(powerBarWidth,powerBarHeight);
		
		maxArrows = new BasicComponent(POWER_BAR_ARROWS, x+((int) (powerBarArrowsRelativeX*scale)), y+powerBarMaxArrowsY-(powerBarArrowsHeight/2));
		maxArrows.resize(powerBarArrowsWidth,powerBarArrowsHeight);
		
		minArrows = new BasicComponent(POWER_BAR_ARROWS, x+((int) (powerBarArrowsRelativeX*scale)), y+powerBarMinArrowsY-(powerBarArrowsHeight/2));
		minArrows.resize(powerBarArrowsWidth,powerBarArrowsHeight);
	}
	
	public void addPowerBarComponents(GUI gameWorld)
	{
		gameWorld.addComponent(powerBarIndicator);
		gameWorld.addComponent(maxArrows);
		gameWorld.addComponent(minArrows);
	}
	
	public void updatePowerBar(int totalPower)
	{
		powerBarHeight = translateIntoHeight(totalPower);
		powerBarIndicator.resize(powerBarWidth,powerBarHeight);
		powerBarIndicator.setY(getY()+powerBarRelativeY-powerBarHeight);
		if(targetAchieved(totalPower))
		{
			maxArrows.setCurrentImage(powerBarArrowsCorrectImage, true);
			minArrows.setCurrentImage(powerBarArrowsCorrectImage, true);
		}
		else
		{
			maxArrows.setCurrentImage(powerBarArrowsImage, true);
			minArrows.setCurrentImage(powerBarArrowsImage, true);
		}
	}
	
	private int translateIntoHeight(int totalPower)
	{
		double newHeight;
		newHeight = ((double) (totalPower)/(double) (maxPower));
		if(newHeight < 1)
			newHeight *= powerBarMaxHeight;
		else
			newHeight = powerBarMaxHeight;
		
		return (int) newHeight;
	}
	
	public boolean targetAchieved(int totalPower)
	{
		if(totalPower >= minLimit && totalPower <= maxLimit)
			return true;
		else
			return false;
	}
}
