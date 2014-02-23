package edu.asu.voctec.game_states;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.Column;
import edu.asu.voctec.GUI.StarDisplay;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.information.AttemptData;
import edu.asu.voctec.information.SizingStepsData;
import edu.asu.voctec.information.TaskData;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * GameState that serves as an exit "splash" screen for all minigames, that
 * displays text (i.e. feedback or "next steps") to the user, in addition to
 * other data such as numberOfHintsUsed and timeSpent.
 * 
 * The text to be displayed on this screen should be set by the function that
 * transitions the game to this specific state (presumably each minigame). All
 * other (applicable) data will be loaded from the current AttemptData, as
 * defined by the currentGame; this will be done automatically. As such, all
 * minigames are expected to update the current Task- and Attempt- Data at least
 * before transitioning to this screen.
 * 
 * @author Moore, Zachary
 * @see #updateExitText(String, String)
 * 
 */
public class ExitScreen extends GUI
{
	/**
	 * Listens for a mouse click to the replay button. When this listener is
	 * activated (i.e. when the replay button is clicked), a new attempt will be
	 * created for the current Task, and the game will transition to the screen
	 * described by the associatedClass.
	 * 
	 * @author Moore, Zachary
	 * @see edu.asu.voctec.game_states.ExitScreen#associatedTask
	 * 
	 */
	public class ReplayButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -3113125282264208671L;
		
		@Override
		protected void actionPerformed()
		{
			if (associatedTask != null)
			{
				Game.getCurrentTask().addAttempt(null);
				Game.getCurrentGame().enterState(associatedTask);
			}
		}
		
	}
	
	// TODO move to GameDefaults
	public static final String ARROW_RIGHT = "resources/default/img/arrow-right.png";
	public static final String ARROW_LEFT = "resources/default/img/arrow-left.png";
	
	/** Single line of text to display at the top of the screen (aligned left) */
	protected TextField titleField;
	
	/** Paragraph to display below the title field */
	protected TextAreaX feedback;
	
	/** The display for the StarScore of the current Task */
	protected StarDisplay starDisplay;
	
	/** The first column of the data table to display AttemptData statistics */
	protected Column<TextField> dataLabels;
	
	/** The second column of the data table to display AttemptData statistics */
	protected Column<TextField> dataDisplay;
	
	// TODO move to StarDisplay
	//protected Rectangle starDisplayBounds;
	
	/** Indicates which minigame (i.e. task) this ExitScreen is displaying */
	protected Class<?> associatedTask;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		// Setup Background
		this.backgroundImage = new Image("resources/default/img/scoreScreenBackgrounds/ScoreBackgroundTask0.png");
		
		// Divide the screen into 4 quadrants (2 rows, 2 columns)
		Rectangle[][] screenDivisions = UtilFunctions.divideScreen(
				Game.getCurrentScreenDimension(), 2, 2);
		
		// Text Displays
		initiateTextDisplays();
		
		// Star Display (bottom-right quadrant)
		initiateStarDisplay(screenDivisions[1][1]);
		
		// Data Table (bottom-left quadrant)
		initiateDataTable(screenDivisions[1][0]);
		
		// Buttons
		initiateButtons();
	}
	
	private void initiateTextDisplays()
	{
		// Title
		Rectangle componentBounds = new Rectangle(0, 50, 300, 50);
		titleField = new TextField(componentBounds, 0.95f, "Good Job!",
				TextDisplay.FormattingOption.FIT_TEXT);
		titleField.setFontColor(Fonts.FONT_COLOR);
		titleField.center();
		this.addComponent(titleField);
		
		// Feedback Body
		componentBounds = new Rectangle(0, 0, 500, 225);
		UtilFunctions.centerRectangle(new Rectangle(0, 100, 800, 200),
				componentBounds);
		feedback = new TextAreaX(componentBounds, 0.95f,
				"Test Test Test \nTest 2 TEST 2\nTEST 3");
		feedback.setFontSize(Fonts.FONT_SIZE_LARGE);
		feedback.setFontColor(Fonts.FONT_COLOR);
		this.addComponent(feedback);
	}
	
	private void initiateStarDisplay(Rectangle subContainer)
			throws SlickException
	{
		// Star Display (center in bottom-right section)
		Rectangle componentBounds = new Rectangle(0, 0, 300, 150);
		UtilFunctions.centerRectangle(subContainer, componentBounds);
		// TODO verify
		Rectangle starDisplayBounds = new Rectangle(componentBounds);
		starDisplay = new StarDisplay(0, 0, 0);
		starDisplay.setBounds(starDisplayBounds);
		this.addComponent(starDisplay);
	}
	
	private void initiateDataTable(Rectangle subContainer)
	{
		// Label Column
		float fontSize = 26f;
		Rectangle componentBounds = new Rectangle(0, 0, 250, 100);
		UtilFunctions.centerRectangle(subContainer, componentBounds);
		componentBounds.translate(-50, 0);
		dataLabels = new Column<>(componentBounds);
		componentBounds = new Rectangle(0, 0, 250, 50);
		TextField unit = new TextField(componentBounds, 0.95f, "Time Spent:",
				TextDisplay.FormattingOption.CLIP_TEXT);
		unit.setFontSize(fontSize);
		unit.center(true, false);
		dataLabels.add(unit);
		unit = new TextField(componentBounds, 0.95f, "Hints Used:",
				TextDisplay.FormattingOption.CLIP_TEXT);
		unit.setFontSize(fontSize);
		unit.center(true, false);
		dataLabels.add(unit);
		this.addComponent(dataLabels);
		
		// Data Column
		componentBounds = new Rectangle(0, 0, 150, 100);
		UtilFunctions.centerRectangle(subContainer, componentBounds);
		componentBounds.translate(80, 0);
		dataDisplay = new Column<>(componentBounds);
		componentBounds = new Rectangle(0, 0, 150, 50);
		unit = new TextField(componentBounds, 0.95f, "00:00",
				TextDisplay.FormattingOption.CLIP_TEXT);
		unit.setFontSize(fontSize);
		unit.center();
		dataDisplay.add(unit);
		unit = new TextField(componentBounds, 0.95f, "0",
				TextDisplay.FormattingOption.CLIP_TEXT);
		unit.setFontSize(fontSize);
		unit.center();
		dataDisplay.add(unit);
		this.addComponent(dataDisplay);
	}
	
	private void initiateButtons() throws SlickException
	{
		// Continue Button
		Button continueButton = new Button(new Image(ARROW_RIGHT), 750, 550,
				new Rectangle(0, 0, 50, 25), "Exit!");
		continueButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		continueButton.addActionListener(new TransitionButtonListener(
				TaskScreen.class));
		continueButton.positionText(Position.LEFT);
		this.addComponent(continueButton);
		
		// Replay Button
		Button replayButton = new Button(new Image(ARROW_LEFT), 5, 5,
				new Rectangle(0, 0, 50, 25), "Replay");
		replayButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		replayButton.addActionListener(new ReplayButtonListener());
		replayButton.positionText(Position.RIGHT);
		this.addComponent(replayButton);
	}
	
	@Override
	public void onEnter()
	{
		load();
	}
	
	public void load()
	{
		TaskData currentTask = Game.getCurrentTask();
		AttemptData currentAttempt = null;
		
		if (currentTask != null)
			currentAttempt = currentTask.getCurrentAttempt();
		
		if (currentAttempt != null)
		{
	
			
			dataDisplay.getUnitAt(0).setText(
					UtilFunctions.formatTime(currentAttempt.getTimeSpent(),
							false, true));
			dataDisplay.getUnitAt(1).setText(
					Integer.toString(currentAttempt.getNumberOfUniqueHints()));
			starDisplay.setScore(currentAttempt.calculateStarScore());
			
			// Mark the attempt as complete
			currentAttempt.setPercentCompletion(100);
		}
	}
	
	/**
	 * Set the text to be displayed by this screen.
	 * 
	 * @param titleField
	 *            Single line of text to display at the top of the screen
	 *            (aligned left)
	 * @param feedback
	 *            Paragraph to display below the title field, in the upper-half
	 *            of the screen.
	 */
	public void updateExitText(String titleField, String feedback)
	{
		this.titleField.setText(titleField);
		this.feedback.setText(feedback);
	}
	
	/**
	 * @see #updateExitText(String, String)
	 */
	public void updateExitText(String titleField, ArrayList<String> feedback)
	{
		this.titleField.setText(titleField);
		this.feedback.setText(feedback);
	}
	
	/**
	 * Set the background image and associate class for this screen. A value of
	 * null for backgroundImage will remove the current background of this
	 * screen.
	 * 
	 * @param backgroundImage
	 *            The desired background. Null will remove the background.
	 * @param associatedTask
	 *            The task which this screen is to display. The replay button
	 *            will correspond to this task.
	 */
	public void updateExitScreen(Image backgroundImage, Class<? extends Task> associatedTask)
	{
		updateExitScreen(associatedTask);
		updateExitScreen(backgroundImage);
	}
	
	// TODO replace with this.setBackgroundImage
	public void updateExitScreen(Image backgroundImage)
	{
		this.backgroundImage = backgroundImage;
	}
	
	// TODO refactor to: setAssociatedTask(Class<?>) OR associate(Class<?>)
	public void updateExitScreen(Class<? extends Task> associatedTask)
	{
		this.associatedTask = associatedTask;
	}
	
}
