package edu.asu.voctec.game_states;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.GameDefaults.ImagePaths;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.MainMenu;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.information.ScenarioData;
import edu.asu.voctec.minigames.energy_assessment.EAPart2.ReadyButtonListener;
import edu.asu.voctec.minigames.step_selection.ScenarioIntroductionScreen;
import edu.asu.voctec.utilities.Position;

public class ScenarioHub extends GUI
{
	private static final String BACKGROUND = "resources/default/img/scenarioHub/Region.png";
	private static final String HOUSE = "resources/default/img/scenarioHub/House.png";
	private static final String HOUSE_GRAY = "resources/default/img/scenarioHub/HouseGray.png";
	private static final String SCENARIO1_BUTTON = "resources/default/img/scenarioHub/Scenario1Button.png";
	private static final String SCENARIO_BUTTON_GRAY = "resources/default/img/scenarioHub/ScenarioButtonGray.png";
	
	public static boolean scenerio1On = true;
	public static boolean scenerio2On = false;
	public static boolean scenerio3On = false;
	boolean nextState = false;
	
	TextAreaX descriptionText;
	Button scenario1Button, scenario2Button, scenario3Button;
	
	private int[][] scenarioPosition = { { 500, 355 }, { 500, 155 },
			{ 500, 305 } };
	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		
		// Scenario Buttons
		scenario1Button = new Button(new Image(SCENARIO1_BUTTON), 225, 162,
				new Rectangle(75, 0, 300, 75), "Scenario 1");
		scenario2Button = new Button(new Image(SCENARIO_BUTTON_GRAY), 225, 262,
				new Rectangle(75, 0, 300, 75), "Scenario 2");
		scenario3Button = new Button(new Image(SCENARIO_BUTTON_GRAY), 225, 362,
				new Rectangle(75, 0, 300, 75), "Scenario 3");
		scenario1Button.setFontColor(Color.black);
		scenario2Button.setFontColor(Color.gray);
		scenario3Button.setFontColor(Color.gray);
		scenario1Button.addActionListener(new Scenario1ButtonListener());
		scenario2Button.addActionListener(new Scenario2ButtonListener());
		scenario3Button.addActionListener(new Scenario3ButtonListener());
		this.addComponent(scenario1Button);
		this.addComponent(scenario2Button);
		this.addComponent(scenario3Button);
		
		// add Text
		// initializeText();
		
		// update values
		// updateStart();
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Back");
		backButton.addActionListener(new TransitionButtonListener(
				MainMenu.class));
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		this.addComponent(backButton);
	}
	
	public class Scenario1ButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = 8411140729169329954L;
		
		@Override
		protected void actionPerformed()
		{
			ScenarioData scenario = Game.getCurrentScenario();
			
			if (scenario.getEntryStep().isComplete()){
				
				
			
				Game.getCurrentGame().enterState(TaskScreen.class);
			}
			else{
				
				
				Game.getCurrentGame().enterState(
						ScenarioIntroductionScreen.class);
			}
		}
	}
	
	public class Scenario2ButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = 3638193519542906603L;
		
		@Override
		protected void actionPerformed()
		{
			if (scenerio2On == true)
			{
				// Game.getCurrentGame().enterState(TaskScreen.class);
			}
		}
	}
	
	public class Scenario3ButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -7788789344205910517L;
		
		@Override
		protected void actionPerformed()
		{
			if (scenerio3On == true)
			{
				// Game.getCurrentGame().enterState(TaskScreen.class);
			}
		}
	}
	
	public void updateStart() throws SlickException
	{
		if (scenerio1On == true)
			scenario1Button = new Button(new Image(HOUSE), 132, 355,
					new Rectangle(0, 90, 90, 40), "");
		if (scenerio2On == true)
			scenario2Button = new Button(new Image(HOUSE), 354, 155,
					new Rectangle(0, 90, 90, 40), "");
		if (scenerio3On == true)
			scenario3Button = new Button(new Image(HOUSE), 576, 305,
					new Rectangle(0, 90, 90, 40), "");
	}
	
	private void initializeText()
	{
		// Scenario Names
		/*
		 * TextAreaX scenario1Text = new TextAreaX(new
		 * Rectangle(scenarioPosition[0][0],
		 * scenarioPosition[0][1]+90,200,100),0.95f,"Scenario 1"); TextAreaX
		 * scenario2Text = new TextAreaX(new Rectangle(scenarioPosition[1][0],
		 * scenarioPosition[1][1]+90,200,100),0.95f,"Scenario 2"); TextAreaX
		 * scenario3Text = new TextAreaX(new Rectangle(scenarioPosition[2][0],
		 * scenarioPosition[2][1]+90,200,100),0.95f,"Scenario 3");
		 * scenario1Text.setFontSize(16); scenario2Text.setFontSize(16);
		 * scenario3Text.setFontSize(16);
		 * scenario1Text.setFontColor(Color.white);
		 * scenario2Text.setFontColor(Color.white);
		 * scenario3Text.setFontColor(Color.white);
		 * this.addComponent(scenario1Text); this.addComponent(scenario2Text);
		 * this.addComponent(scenario3Text);
		 */
		
		// description Area Text
		descriptionText = new TextAreaX(new Rectangle(50, 400, 600, 300),
				0.95f, "Here you can choose a Scenario to complete.");
		descriptionText.setFontSize(16);
		descriptionText.setFontColor(Color.white);
		this.addComponent(descriptionText);
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	{
		//Game.getCurrentGame();
			super.update(container, game, delta);
			if (!nextState){
				++lc;
				if (lc == 5){
				try {
			
					
					Game.getCurrentGame().addState(new ScenarioIntroductionScreen(), Game.getCurrentGame().getContainer());
				
				} catch (SlickException e) {
		
					e.printStackTrace();
				}
			nextState = true;
				}
		}
			
	}
}
