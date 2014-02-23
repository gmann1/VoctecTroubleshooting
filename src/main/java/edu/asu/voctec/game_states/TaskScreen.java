package edu.asu.voctec.game_states;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.Component;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.information.ScenarioData;
import edu.asu.voctec.information.TaskData;
import edu.asu.voctec.minigames.step_selection.ScenarioIntroductionScreen;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

public class TaskScreen extends GUI
{
	public static final String SIDE_BAR = "resources/default/img/taskScreen/SideBar.png";
public static final String TASK_SCREEN_BACKGROUND = "resources/default/img/taskScreenBackgrounds/background0.png";
	public static BackButtonListener activeListener;
	private ArrayList<TaskData> tasks;
	private ArrayList<Component> confirmationComponents;
	private boolean scenarioLoaded;
	
	public static int currentImage = 0;
	
	public class BackButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -1242073189843483437L;
		
		boolean displaying;
		
		@Override
		protected void actionPerformed()
		{
			if (displaying)
			{
				Game.getCurrentGame().enterState(MainMenu.class);
				stopDisplaying();
			}
			else
			{
				queueAddComponents(confirmationComponents);
				displaying = true;
				
				if (activeListener != null)
					activeListener.stopDisplaying();
				if (TaskData.activeListener != null)
					TaskData.activeListener.stopDisplaying();
				
				activeListener = this;
			}
		}
		
		public void stopDisplaying()
		{
			queueRemoveComponents(confirmationComponents);
			displaying = false;
			activeListener = null;
		}
		
	}
	
	public class ReplayButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -5618531297688635125L;

		@Override
		protected void actionPerformed()
		{
			TaskData step0Data = Game.getCurrentScenario().getEntryStep();
			step0Data.addAttempt(null);
			Game.setCurrentTask(step0Data);
			Game.getCurrentGame().enterState(ScenarioIntroductionScreen.class);
		}
		
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		this.setBackgroundImage(new Image(TASK_SCREEN_BACKGROUND));
		//System.out.println("\nTaskScreen: Initializing...");
		
		confirmationComponents = new ArrayList<>();
		tasks = new ArrayList<>();
		//System.out.println("TaskScreen: Defaults Set.");
		
		// Side Bar
		BasicComponent sideBar = new BasicComponent(SIDE_BAR, 0, 0);
		sideBar.resize(226, 600);
		this.addComponent(sideBar);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Exit");
		backButton.addActionListener(new BackButtonListener());
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		this.addComponent(backButton);
		
		// Confirmation Components
		// Define the location of the component block relative to the screen
		Point informationLocation = TaskScreenDefaults.INFORMATION_OFFSET;
		float scale = TaskScreenDefaults.INFORMATION_SCALE;
		Rectangle relativeBounds = new Rectangle(0, 0, 400, 100);
		TextField nameLabel = new TextField(relativeBounds, 0.92f,
				"Replay Step 0?", TextDisplay.FormattingOption.FIT_TEXT);
		nameLabel.center();
		nameLabel.setFontColor(Fonts.FONT_COLOR);
		
		// Exit Button
		Image exitButtonImage = new Image(ImagePaths.Buttons.BASE);
		Rectangle textBounds = UtilFunctions.getImageBounds(exitButtonImage);
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.80f);
		Button exitButton = new Button(exitButtonImage, 0, 100, textBounds,
				"Exit");
		exitButton.addActionListener(new TransitionButtonListener(
				MainMenu.class));
		exitButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		
		// Replay Button
		Image replayButtonImage = new Image(ImagePaths.Buttons.BASE);
		textBounds = UtilFunctions.getImageBounds(replayButtonImage);
		int x = 400 - textBounds.width;
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.80f);
		Button replayButton = new Button(replayButtonImage, x, 100, textBounds,
				"Replay");
		replayButton.addActionListener(new ReplayButtonListener());
		replayButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		
		confirmationComponents.add(nameLabel);
		confirmationComponents.add(exitButton);
		confirmationComponents.add(replayButton);

		// Scale Components
		for (Component component : confirmationComponents)
			component.rescale(scale);
		
		// Set the position relative to the screen
		UtilFunctions.translateAll(informationLocation, confirmationComponents);
		
		
		
		//System.out.println("TaskScreen: Initialization Finished.\n");
	}
	
	@Override
	public void onEnter()
	{
		if (!scenarioLoaded)
		{
			load();
			//System.out.println("Scenario Load End");
		}
		
		// Disable task display
		if (TaskData.activeListener != null)
			TaskData.activeListener.stopDisplaying();
		
		// Disable exit confirmation display
		if (activeListener != null)
			activeListener.stopDisplaying();
		
		setNextAccessible();
	}
	
	public void load()
	{
		ScenarioData scenario = Game.getCurrentScenario();
		//System.out.println("Loading Scenario: " + scenario);
		tasks = new ArrayList<>();
		
		for (TaskData task : scenario.getTasks())
		{
			System.out.println("\tTask Loaded: " + task);
			task.reload();
			
			tasks.add(task);
			this.addComponent(task.getTaskIcon());
		}
		
		scenarioLoaded = true;
	}
	
	public void setNextAccessible()
	{
		for (int index = 0; index < tasks.size(); index++)
		{
			TaskData task = tasks.get(index);
			
			if (!task.isComplete())
			{
				task.setAccessible(true);
				break;
			}
		}
	}
	
}
