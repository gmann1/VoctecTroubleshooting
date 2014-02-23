package edu.asu.voctec.minigames.battery_sizing;

import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.SupportFunctions;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.Task;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.minigames.cdmg.CDPart1;
import edu.asu.voctec.utilities.Position;

public class BatteryIntro extends GUI implements Task
{
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";
	public static final String Right_ARROW_TEXT = "Begin!";
	public static final String LEFT_ARROW_TEXT = "Back";
	public static final String WELCOME_Text = "";
	public static final String INTRODUCTION = "In this game you need to figure out the best combination of batteries and how to connect them"
			+ " in order to achieve the required Battery-Bank output and system voltage.";
	private boolean nextState;
	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		
		// Title
		TextField welcomeLabel = SupportFunctions
				.generateWelcomeLabel(WELCOME_Text);
		
		// Introduction Body
		TextArea introductionText = SupportFunctions
				.generateIntroductionDisplay(INTRODUCTION);
		
		// Start Button
		Button startButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), Right_ARROW_TEXT);
		startButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		startButton.addActionListener(new StartListener());
		startButton.positionText(Position.LEFT);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), LEFT_ARROW_TEXT);
		backButton.addActionListener(new TransitionButtonListener(
				TaskScreen.class));
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		
		// Add all components to this menu
		this.addComponent(startButton);
		this.addComponent(welcomeLabel);
		this.addComponent(introductionText);
		this.addComponent(backButton);
		
	}
	
	public class StartListener extends ButtonListener
	{
		private static final long serialVersionUID = 8213945841494743455L;

		@Override
		protected void actionPerformed()
		{
			Game.getCurrentGame().enterState(BatteryGameScreen.class);
		}
	}
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
			super.update(container, game, delta);
			if (!nextState){
				++lc;
				if (lc == 5){
				try {
			
					Game.getCurrentGame().addState(new BatteryGameScreen(), Game.getCurrentGame().getContainer());
			
				
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
		SupportFunctions.ensureAttemptData();
		Game.getExitScreen().updateExitScreen(this.getClass());
	}
	
	@Override
	public void load()
	{
		// TODO Auto-generated method stub
		
	}
	
}
