package edu.asu.voctec.minigames.step_selection;

import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.minigames.energy_assessment.EAPart1IntroScreen;
import edu.asu.voctec.utilities.Position;

public class StepSelectionExitScreen extends GUI
{
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";
	public TextArea bodyText;
	public boolean updateHints = false;
	private boolean nextState;
	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		Rectangle textLocation = new Rectangle(0, 50, 300, 50);
		
		// Title
		TextField feedback = new TextField(textLocation, 0.95f, "Good Job!",
				TextDisplay.FormattingOption.FIT_TEXT);
		feedback.setFontColor(Fonts.FONT_COLOR);
		feedback.center();
		
		// Body Text
		textLocation.setLocation(50, 250);
		textLocation = new Rectangle(150, 200, 500, 350);
		bodyText = new TextArea(textLocation, 0.95f, "");
		bodyText.setFontSize(Fonts.FONT_SIZE_LARGE);
		bodyText.setText("");
		bodyText.setFontColor(Fonts.FONT_COLOR);
		
		// Continue Button
		Button continueButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), "Exit!");
		continueButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		continueButton.addActionListener(new TransitionButtonListener(
				TaskScreen.class));
		continueButton.positionText(Position.LEFT);
		
		this.addComponent(continueButton);
		this.addComponent(feedback);
		this.addComponent(bodyText);
		
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
			super.update(container, game, delta);
		
			if (!nextState){
				++lc;
				if (lc == 5){
				//try {
			
					//Game.getCurrentGame().addState(new EAPart1IntroScreen(), Game.getCurrentGame().getContainer());
			
				
				//} catch (SlickException e) {
		
					//e.printStackTrace();
				//}
			nextState = true;
				}
		}
			
	}
	
	@Override
	public void onEnter()
	{
		Game.getCurrentScenario().getEntryStep().getCurrentAttempt()
				.setPercentCompletion(100);
	}
	
}
