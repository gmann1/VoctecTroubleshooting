package edu.asu.voctec.minigames.controller_sizing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.SupportFunctions;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.Task;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.minigames.cdmg.CDPart1;
import edu.asu.voctec.utilities.Position;

public class ControllerSizingIntroScreen extends GUI implements Task
{
	private static String INTRODUCTION = "This is the final step you need to do in order to complete the sizing of this PV system. In this step you will be sizing the charge controller for the system. The charge controller will help in regulating and controlling, the flow of current to and from the battery in order to protect it from overcharging and to increase its life. ";
	
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";

	private boolean nextState;

	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(
				"resources/default/img/minigames/ControllerSizing/Step1Background.png");
		

		
		// introduction
		TextArea introduction = SupportFunctions
				.generateIntroductionDisplay(INTRODUCTION);
		
		// Start Button
		Button startButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), "Begin!");
		startButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		startButton.addActionListener(new TransitionButtonListener(
				ControllerSizingPart2.class));
		startButton.positionText(Position.LEFT);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Back");
		backButton.addActionListener(new TransitionButtonListener(
				TaskScreen.class));
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		
	
		
		this.addComponent(startButton);
		//this.addComponent(welcome);
		this.addComponent(introduction);
		this.addComponent(backButton);
		// this.addComponent(new Selector<SelectorIcon>(100, 100));
		
		System.out
				.println("Listeners: " + Arrays.toString(this.getListeners()));
	}
	
	@Override
	public Dimension getDesignResolution()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void load()
	{
		// TODO Auto-generated method stub
		
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
			super.update(container, game, delta);
			if (!nextState){
				++lc;
				if (lc == 5){
				try {
			
					Game.getCurrentGame().addState(new ControllerSizingPart2(), Game.getCurrentGame().getContainer());
			
				
				} catch (SlickException e) {
		
					e.printStackTrace();
				}
			nextState = true;
				}
		}
			
	}
	
	@Override
	public void onEnter()
	{
		Game.getExitScreen().updateExitScreen(this.getClass());
		SupportFunctions.ensureAttemptData();
	}
	
}
