package edu.asu.voctec.minigames.cdmg;

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
import edu.asu.voctec.game_states.MainMenu;
import edu.asu.voctec.game_states.Task;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.information.AttemptData;
import edu.asu.voctec.minigames.energy_assessment.EAPart1IntroScreen;
import edu.asu.voctec.utilities.Position;

/**
 * 
 * @author Gabriel Mann
 * @author Moore, Zachary
 * 
 */

public class CDIntroScreen extends GUI implements Task
{
	private static final String INTRODUCTION = "In the following game, you will be determining the peak sun hours (PSH) for the most appropriate month (critical design month) for a small DC lighting system with constant loads as well as the most appropriate array orientation and tilt angle for the conditions of that month for a location in the Cook Islands.";
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";
	private boolean nextState;
	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		
		TextField welcome = SupportFunctions.generateWelcomeLabel("Welcome!");
		
		// introduction
		TextArea introduction = SupportFunctions
				.generateIntroductionDisplay(INTRODUCTION);
		
		// Start Button
		Button startButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), "Begin!");
		startButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		startButton.addActionListener(new TransitionButtonListener(
				CDPart1.class));
		startButton.positionText(Position.LEFT);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Back");
		backButton.addActionListener(new TransitionButtonListener(
				TaskScreen.class));
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		
		welcome.center();
		
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
	public void onEnter()
	{
		Game.getExitScreen().updateExitScreen(this.getClass());
		SupportFunctions.ensureAttemptData();
		
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
		for (int i = 0; i < MainMenu.UserData.size(); i++){
			System.out.println(MainMenu.UserData.get(i));
		}
			super.update(container, game, delta);
			if (!nextState){
				++lc;
				if (lc == 5){
				try {
			
					Game.getCurrentGame().addState(new CDPart1(), Game.getCurrentGame().getContainer());
			
				
				} catch (SlickException e) {
		
					e.printStackTrace();
				}
			nextState = true;
				}
		}
			
	}
	
	@Override
	public void load()
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
