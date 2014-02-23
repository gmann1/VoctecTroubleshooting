package edu.asu.voctec.minigames.step_selection;

import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GameDefaults.Labels.Step0;
import edu.asu.voctec.SupportFunctions;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.ScenarioHub;
import edu.asu.voctec.game_states.Task;
import edu.asu.voctec.utilities.Position;

public class ScenarioIntroductionScreen extends GUI implements Task
{
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";
	private boolean nextState;
	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		
		// Title
		TextField welcomeLabel = SupportFunctions
				.generateWelcomeLabel("Welcome!");
		
		// Introduction Body
		TextAreaX introductionText = SupportFunctions
				.generateIntroductionDisplay(Step0.INTRODUCTION
						.getTranslation());
		
		// Start Button
		Button startButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), "Begin!");
		startButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		startButton.addActionListener(new TransitionButtonListener(
				StepSelection.class));
		startButton.positionText(Position.LEFT);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Back");
		backButton.addActionListener(new TransitionButtonListener(
				ScenarioHub.class));
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);

		// Add all components to this menu
		this.addComponent(startButton);
		this.addComponent(welcomeLabel);
		this.addComponent(introductionText);
		this.addComponent(backButton);
	}
	
	@Override
	public void onEnter()
	{
		Game.getExitScreen().updateExitScreen(this.getClass());
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
			super.update(container, game, delta);
			if (!nextState){
				++lc;
				if (lc == 5){
				try {
			
					
					Game.getCurrentGame().addState(new StepSelection(), Game.getCurrentGame().getContainer());
				
				} catch (SlickException e) {
		
					e.printStackTrace();
				}
			nextState = true;
			load();
				}
		}
			
	}
	@Override
	public void load()
	{
		if (nextState){
		StepSelection mainScreen = (StepSelection) Game
				.getGameState(StepSelection.class);
		mainScreen.load();
		}
	}
}
