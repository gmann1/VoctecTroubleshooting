package edu.asu.voctec.minigames.battery_sizing;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.utilities.Position;

public class BatteryExitScreen extends GUI
{
	
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";
	public static final String Right_ARROW_TEXT = "Exit";
	public static final String LEFT_ARROW_TEXT = "Play Again";
	
	//private static TextField congratulation;
	private static TextArea congratulation, congratulation2, congratulation3;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
			
		Rectangle textLocation = new Rectangle(35, 45, 600, 600);
		congratulation = new TextArea(textLocation, 0.95f,
				"");
		congratulation.setFontSize(40);
		congratulation.setFontColor(Color.white);
		this.addComponent(congratulation);
		
		Rectangle textLocation2 = new Rectangle(35, 140, 500, 500);
		congratulation2 = new TextArea(textLocation2, 0.95f,
				"");
		congratulation2.setFontSize(25);
		congratulation2.setFontColor(Color.white);
		this.addComponent(congratulation2);
		
		Rectangle textLocation3 = new Rectangle(35, 300, 700, 700);
		congratulation3 = new TextArea(textLocation3, 0.95f,
				"");
		congratulation3.setFontSize(30);
		congratulation3.setFontColor(Color.white);
		this.addComponent(congratulation3);
		
		// Start Button
		Button startButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), Right_ARROW_TEXT);
		startButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		startButton.addActionListener(new ExitListener());
		startButton.positionText(Position.LEFT);
		this.addComponent(startButton);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 100, 25), LEFT_ARROW_TEXT);
		backButton.addActionListener(new PlayAgainListener());
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		this.addComponent(backButton);
		
	}
	
	public class ExitListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			Game.getCurrentGame().enterState(TaskScreen.class);
		}
	}
	
	public class PlayAgainListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			Game.getCurrentGame().enterState(BatteryGameScreen.class);
		}
	}
	
	public static void passEndGameMessage(String gameStatusHeadline, String gameStatusMessage, String personalizedMessage, Color personalizedMessageColor)
	{
		congratulation.setText(gameStatusHeadline);
		congratulation2.setText(gameStatusMessage);
		congratulation3.setText(personalizedMessage);
		congratulation3.setFontColor(personalizedMessageColor);
	}
	
}
