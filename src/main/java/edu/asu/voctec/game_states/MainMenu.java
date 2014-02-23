package edu.asu.voctec.game_states;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GameDefaults;
import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.GameDefaults.ImagePaths;
import edu.asu.voctec.information.ScenarioData;
import edu.asu.voctec.information.TaskData;
import edu.asu.voctec.information.UserProfile;
import edu.asu.voctec.minigames.energy_assessment.EAPart1IntroScreen;
import edu.asu.voctec.minigames.step_selection.ScenarioIntroductionScreen;
import edu.asu.voctec.utilities.UtilFunctions;

public class MainMenu extends GUI implements GameDefaults, KeyListener

{
	boolean nextState = false;
	private int lc = 0;
	private Button startButton;
	private boolean enterUser;
	private String userName = "";
	Scanner reader = new Scanner(System.in);
	private Button readyButton;
	private BasicComponent sidePanel;
	private TextAreaX hintBox;

	public static ArrayList<String> UserData = new ArrayList<>();
	public class StartButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -325855184018136406L;
		

		@Override
		protected void actionPerformed()
		{
			startButton.getTextField().setText(userName);
			
				//Game.getCurrentGame().enterState(ScenarioHub.class);
		}
	}
	public class ReadyButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -325855184018136406L;
		

		@Override
		protected void actionPerformed()
		{
			
				if (UserData.isEmpty()){
					UserData.add(userName);
				}
				else{
					UserData.remove(0);
					UserData.add(0, userName);
				}
				Game.getCurrentGame().enterState(ScenarioHub.class);
		}
	}
	
	 public void keyPressed(int key, char c) {

		 if ((key >= 30 && key <= 38) || key == 57 || key == 48 || key == 46 || key == 18 || key == 23 || key == 50 || key == 49 || key == 24 || key == 25 || key == 16 || key == 19 || key == 20 || key == 22 || key == 47 || key == 17 || key == 45 || key == 21 || key == 44){
			 if (userName.length() <20){
	        userName += c;
	        
			 }
		 }
		 if (key == 14){
			 if (userName.length() > 0){
			 userName = userName.substring(0, userName.length()-1);
			 }
		 }
		 startButton.getTextField().setText(userName);
	    
	 }
	    public void keyReleased(int key, char c) {
	        
	    }
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		
		Rectangle hintBounds = new Rectangle(600, 208, 192, 192); 
		Rectangle relativeHintTextBounds = UtilFunctions.dialateRectangle(
				new Rectangle(0, 0, 192, 192), 0.92f);
		hintBox = new TextAreaX(hintBounds, relativeHintTextBounds,
				null);
		//System.out.println("\nMainMenu: Initializing...");
		sidePanel = new BasicComponent(ImagePaths.SIDE_PANEL, 592, 0);
		sidePanel.rescale(208, 600);
		sidePanel.setX(592);
		sidePanel.setY(0);
		Image readyButtonImage = new Image(ImagePaths.READY_BUTTON);
		Rectangle textBounds = UtilFunctions.getImageBounds(readyButtonImage);
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.80f);
		readyButton = new Button(readyButtonImage, sidePanel.getX() + sidePanel.getBounds().width/2 - UtilFunctions.getImageBounds(readyButtonImage).width/2, hintBox.getY() + hintBox.getBounds().height + 50, textBounds,"Ready");
		readyButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		readyButton.addActionListener(new ReadyButtonListener());
		
		// TODO Replace with profile
		UserProfile profile = new UserProfile("Default User");
		ScenarioData scenario = profile.getScenario(0);
		TaskData task = scenario.getEntryStep();
		Game.setCurrentUser(profile);
		Game.setCurrentScenario(scenario);
		Game.setCurrentTask(task);
		
		int buttonSpacing = 15;
		int buttonWidth = 350;
		int buttonHeight = 75;
		float borderScale = 0.9f;
	
		// Determine text and button bounds, relative to each button
		Rectangle buttonBounds = new Rectangle(0, 0, buttonWidth, buttonHeight);
		Rectangle relativeTextBounds = new Rectangle(0, 0, buttonWidth
				- buttonHeight, buttonHeight);
		relativeTextBounds = UtilFunctions.dialateRectangle(relativeTextBounds,
				borderScale);
		
		// Declare Buttons
		// Start Button
		startButton = new Button(ImagePaths.NEW_GAME_BUTTON,
				buttonBounds, relativeTextBounds, "Click to enter username");
		startButton.addActionListener(new StartButtonListener());
		
		
		// Language Button
		Button languageButton = new Button(ImagePaths.LANGUAGE_BUTTON,
				buttonBounds, relativeTextBounds, "Language");
		/*languageButton.addActionListener(new TransitionButtonListener(
				TaskScreen.class/* LanguageMenu.class ));*/
		// TODO remove; Enable language button
		languageButton.getBaseImage().setAlpha(MainDefaults.DISABLED_COMPONENT_OPACITY);
		
		// Instructor Control Panel Button
		Button instructorButton = new Button(
				ImagePaths.INSTRUCTOR_CONTROL_PANEL_BUTTON, buttonBounds,
				relativeTextBounds, "Instructor");
		// TODO uncomment / enable instructor button
		/*instructorButton.addActionListener(new TransitionButtonListener(
				InstructorControlPanel.class));*/
		// TODO remove; Disable language button
		languageButton.getBaseImage().setAlpha(MainDefaults.DISABLED_COMPONENT_OPACITY);
		
		// Color text
		// TODO set all to constant colour (MainMenuDefaults.BUTTON_FONT_COLOR)
		setButtonFontColor(Fonts.DISABLED_BUTTON_FONT_COLOR, startButton, languageButton,
				instructorButton);
		startButton.setFontColor(MainMenuDefaults.BUTTON_FONT_COLOR);
		
		//Game Title
		Rectangle textLocation = new Rectangle(0, 0, 2*buttonWidth, buttonHeight);
		TextField gameTitle = new TextField(textLocation, 0.95f,
				"PV System Sizing Game",
				TextDisplay.FormattingOption.FIT_TEXT);
		gameTitle.setFontColor(Color.white);
		gameTitle.center();
	
		TextField extraObject = new TextField(textLocation, 0.95f,
				"",
				TextDisplay.FormattingOption.FIT_TEXT);
		
		// Add buttons to this menu
		this.addComponent(gameTitle);
		this.addComponent(extraObject);
		this.addComponent(startButton);
		this.addComponent(readyButton);
		//this.addComponent(languageButton);
		//this.addComponent(instructorButton);
		
		this.centerComponentsStacked(buttonSpacing);
		gameTitle.setY(gameTitle.getY()-100);
		startButton.setY(startButton.getY()-50);
		readyButton.setY(readyButton.getY()-35);
		Image background = new Image(ImagePaths.MainMenuBackground);
		setBackgroundImage(background.getScaledCopy(800, 600));
		//TODO
		//Game.getCurrentGame().addState(new ScenarioHub(), container);
	
		//System.out.println("MainMenu: Initialization Finished.\n");
	}
	
	private void setButtonFontColor(Color color, Button... buttons)
	{
		for (Button button : buttons)
			button.setFontColor(color);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
			super.update(container, game, delta);
			
			if (!nextState){
				++lc ;
				if (lc == 5){
					try {
						Game.getCurrentGame().addState(new ScenarioHub(), Game.getCurrentGame().getContainer());
						
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					nextState = true;
				}
		}
			
	}
	
	
}
