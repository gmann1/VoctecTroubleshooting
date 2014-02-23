package edu.asu.voctec.minigames.step_selection;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GameDefaults.ImagePaths.SelectorIcons;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.Selector;
import edu.asu.voctec.GUI.SelectorDisplay;
import edu.asu.voctec.GUI.SelectorDisplay.DisplayIsFullException;
import edu.asu.voctec.GUI.SelectorIcon;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.ExitScreen;
import edu.asu.voctec.game_states.GameTemplate;
import edu.asu.voctec.game_states.MainMenu;
import edu.asu.voctec.information.SizingStepsData;
import edu.asu.voctec.information.TaskData;
import edu.asu.voctec.utilities.CircularList;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

public class StepSelection extends GameTemplate
{
	private static final String endingAnimationPath = "resources/default/img/minigames/animations/DiscoSprites.png";
	
	private SelectorDisplay<SelectorIcon> selectorDisplay;
	private Selector<SelectorIcon> selector;
	private Animation endingAnimation;
	private Rectangle endingAnimationBounds;
	private boolean complete;

	private int hints;

	private boolean nextState;

	private int lc;
	
	/**
	 * Listener for the ready button. If the button is pressed before all
	 * choices have been made, a message will be displayed asking the user to
	 * complete his/her selections. Otherwise, the user's choices will be
	 * verified and the instructions and hints will be updated accordingly.
	 * 
	 * The following feature was removed as of Jan 12, 2013, and replaced by
	 * ContinueButtonListener per client preference. If this listener is
	 * activated after the choices have been verified and this state is marked
	 * as complete, the user will be transfered to the StepSelectionExitScreen.
	 * 
	 * @see ContinueButtonListener
	 * @author Moore, Zachary
	 * 
	 */
	public class ReadyButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -914640823203112459L;
		
		@Override
		protected void actionPerformed()
		{
			if (!complete)
			{
				if (!selectorDisplay.isFull())
				{
					instructionBox.setText(Labels.Step0.INSTRUCTIONS_INCOMPLETE
							.getTranslation());
				}
				else
				{
					complete = selectorDisplay.verifyChoices(true);
					
					if (complete)
					{
						try
						{
							updateInstructions();
							hintBox.clear();
							continueButtonOn();
							readyButtonOff();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						updateHints();
						instructionBox.setText(Labels.Step0.INSTRUCTIONS_RED
								.getTranslation());
					}
				}
			}
		}
		
	}
	
	/**
	 * Listener for the hint button. When this button is pressed, the user is
	 * shown a single hint, displayed in the hintBox.
	 * 
	 * @see #displayGenericHint()
	 * @author Moore, Zachary
	 * 
	 */
	public class HintButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -914640823203112459L;
		
		@Override
		protected void actionPerformed()
		{
			if (!complete)
			{
				displayGenericHint();
			}
		}
		
	}
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (!nextState){
			++lc;
			if (lc == 5){
			try {
		
			
				Game.getCurrentGame().addState(new StepSelectionExitScreen(), Game.getCurrentGame().getContainer());
			
			} catch (SlickException e) {
	
				e.printStackTrace();
			}
		nextState = true;
			}
	}
		if(complete){
			int numberOfStars = 6 - hints;
			
			if (numberOfStars <0)
			{
				numberOfStars = 0;
			}
			if (sequenceStep != 4000){
			sequenceStep = initiateStars(numberOfStars, sequenceStep);
			}
		}
	}
	
	/**
	 * Listener for the continue button.This event can only be
	 * 
	 * @author Moore, Zachary
	 * 
	 */
	public class ContinueButtonListener extends ButtonListener
	{
		private static final long serialVersionUID = -914640823203112459L;
		
		@Override
		protected void actionPerformed()
		{
			if (complete)
				
			{
				Game.updateExitText(
						"Good Job!",
						"You have successfully completed the sizing process. Now get ready to play with each of the sizing steps.");
				if (MainMenu.UserData.size() <3){
					MainMenu.UserData.add("Step Selection");
					MainMenu.UserData.add(Integer.toString(Game.getCurrentTask().getCurrentAttempt().getNumberOfUniqueHints()));
					MainMenu.UserData.add(String.valueOf(UtilFunctions.formatTime(Game.getCurrentTask().getCurrentAttempt().getTimeSpent(),false, true)));
				
					
					File file = new File(System.getProperty("user.dir")+"/userData.txt");
				
					// if file doesnt exists, then create it
					try {
					if (!file.exists()) {
						
							file.createNewFile();
						}
					
					FileWriter fw = new FileWriter(file, true);
					BufferedWriter bw = new BufferedWriter(fw);
				
					String s = new String();
					s = MainMenu.UserData.get(0) + "'s data(minigame, hints used, time spent): ";
					s += MainMenu.UserData.get(1);
					s += ", ";
					s += MainMenu.UserData.get(2);
					s += ", ";
					s += MainMenu.UserData.get(3);
					s += "; ";
					BufferedReader br = new BufferedReader(new FileReader(file));     
					if (br.readLine() != null) {
					    
					    bw.newLine();
					}
					
					bw.write(s);
					bw.close();
					} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				Game.getCurrentGame().enterState(ExitScreen.class);
			}
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.asu.voctec.utilities.gameTemplate#init(org.newdawn.slick.GameContainer
	 * , org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		System.out.println("\nSelectorTest: Initializing...");
		
		// Keep track of how much time is spent playing this minigame
		trackTime = true;
		
		// Standard Components (Hint Box, Instructions, and Buttons)
		super.init(container, game);
		
		// Selector
		instantiateSelector();
		
		// Selector Display
		instantiateSelectorDisplay();
		
		// Ending Animation
		instantiateEndingAnimation();
		
		// Add Listeners to all Buttons
		setupButtonListeners();
		
		// TODO REMOVE
		this.removeComponent(hintButton);
		
		System.out.println("SelectorTest: Initialization Finished.\n");
	}
	
	/**
	 * Add all action listeners to the buttons on this screen. Note: the buttons
	 * are initialized by GameTemplate; they are formatted here.
	 */
	public void setupButtonListeners()
	{
		continueButton.addActionListener(new ContinueButtonListener());
		readyButton.addActionListener(new ReadyButtonListener());
		hintButton.addActionListener(new HintButtonListener());
		backButton.addActionListener(new TransitionButtonListener(
				ScenarioIntroductionScreen.class));
	}
	
	/**
	 * Initiates the selector field of this class, with a new Selector object,
	 * centered in the control bar at the bottom of the screen. This method
	 * depends on the control bar being instantiated. As such, super.init()
	 * should be called prior to calling this method.
	 * 
	 * Note: the content of the selector is handled by the load method, more
	 * specifically by populateSelectorContents.
	 * 
	 * @see #populateSelectorContents(CircularList)
	 * @see #generateDefaultData()
	 * @throws SlickException
	 *             Indicates a failure to load or resize the selector images.
	 */
	public void instantiateSelector() throws SlickException
	{
		// Create and size a new selector object
		selector = new Selector<>(0, 0, true);
		selector.rescale(0.75f);
		
		// Center the selector, along the bottom of the screen
		Rectangle centered = new Rectangle(selector.getBounds());
		UtilFunctions.centerRectangle(control.getBounds(), centered);
		selector.translate(centered.x, centered.y);
		
		// Format selector, and add it to this screen
		selector.displayLabel();
		this.addComponent(selector);
	}
	
	/**
	 * Initiates the selectorDisplay field of this class, with a new
	 * SelectorDisplay object, centered in the main portion of this screen (the
	 * top-left section, not occupied by the control panel or the side bar) of
	 * the screen. This method depends on the control bar and sideBar being
	 * instantiated. As such, super.init() should be called prior to calling
	 * this method.
	 * 
	 * Note: the content of the selectorDisplay is handled by the load method,
	 * and by the user's actions during gameplay.
	 * 
	 * @see #load()
	 * @throws SlickException
	 *             Indicates a failure to load or resize the selectorDisplay
	 *             images.
	 */
	public void instantiateSelectorDisplay() throws SlickException
	{
		// Setup a new selector display (using the default appearance)
		selectorDisplay = new SelectorDisplay<>(0, 0, true);
		
		// Size Display
		selectorDisplay.rescale(0.80f);
		
		// Link Display to Selector
		selectorDisplay.link(selector);
		
		// Center the display in the middle of the play-area
		Rectangle playArea = new Rectangle(Game.getCurrentScreenDimension());
		int width = playArea.width - sidePanel.getBounds().width;
		int height = playArea.height - control.getBounds().height;
		playArea.setSize(width, height);
		Rectangle centered = new Rectangle(selectorDisplay.getBounds());
		UtilFunctions.centerRectangle(playArea, centered);
		selectorDisplay.translate(centered.x, centered.y);
		
		// Add the display to this screen
		this.addComponent(selectorDisplay);
	}
	
	/**
	 * Instantiate and format the animation to be played when the user selects a
	 * correct combination.
	 * 
	 * Note: This method depends on the hintBox being instantiated. As such,
	 * super.init() should be called prior to calling this method.
	 * 
	 * @throws SlickException
	 */
	private void instantiateEndingAnimation() throws SlickException
	{
		// Ending Animation
		Image spriteSheetImage = new Image(endingAnimationPath);
		int fps = 6;
		int frameWidth = 115;
		int frameHeight = 150;
		endingAnimationBounds = new Rectangle(0, 0, frameWidth, frameHeight);
		UtilFunctions.centerRectangle(hintBox.getBounds(),
				endingAnimationBounds);
		SpriteSheet endingAnimationSprites = new SpriteSheet(spriteSheetImage,
				frameWidth, frameHeight);
		endingAnimation = new Animation(endingAnimationSprites, 1000 / fps);
	}
	
	private void instantiateHintBox() throws SlickException
	{
		// Hint Bounds
		Rectangle hintBounds = new Rectangle(398, 62, 370, 320);
		Rectangle relativeHintTextBounds = UtilFunctions.dialateRectangle(
				new Rectangle(0, 0, 370, 320), 0.92f);
		
		// Hint Box
		hintBox = new TextAreaX(hintBounds, relativeHintTextBounds, null);
		Image hintBoxBackground = new Image(
				ImagePaths.Selector.HINT_BOX_BACKGROUND);
		hintBox.setCurrentImage(hintBoxBackground, true);
		
		// Format hint box
		hintBox.setFontSize(Fonts.FONT_SIZE_MEDIUM);
		hintBox.setFontColor(Fonts.FONT_COLOR);
		// instructionBox.center();
		instructionBox.setFontColor(Fonts.FONT_COLOR);
		instructionBox.setFontSize(Fonts.FONT_SIZE_MEDIUM);
		
		// Add hint box to this screen
		this.addComponent(hintBox);
		this.addComponent(instructionBox);
	}
	
	private void instantiateButtons() throws SlickException
	{
		// Ready Button
		Image readyButtonImage = new Image(ImagePaths.Buttons.BASE);
		Rectangle textBounds = UtilFunctions.getImageBounds(readyButtonImage);
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.75f);
		readyButton = new Button(readyButtonImage, 600, 500, textBounds,
				"Ready");
		readyButton.addActionListener(new ReadyButtonListener());
		readyButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		this.addComponent(readyButton);
		
		// Back Button
		Button backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Back");
		backButton.addActionListener(new TransitionButtonListener(
				ScenarioIntroductionScreen.class));
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		this.addComponent(backButton);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.asu.voctec.game_states.GUI#onExit()
	 */
	@Override
	public void onExit()
	{
		save();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.asu.voctec.game_states.GUI#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics graphics) throws SlickException
	{
		super.render(container, game, graphics);
		
		/*
		 * if (complete) endingAnimation.draw(endingAnimationBounds.x,
		 * endingAnimationBounds.y);
		 */
	}
	
	public void updateInstructions()
	{
		try
		{
			// Determine which step is to be decided next
			int firstEmpty = this.selectorDisplay.getCurrentIndex() + 1;
			String instructions;
			
			// Set instructions label text
			if (firstEmpty == 1)
			{
				// Unique instructions for Instruction1
				instructions = Labels.Step0.INSTRUCTIONS_BEGIN.getTranslation();
			}
			else
			{
				// Standard format for instructions 2-5
				instructions = Labels.Step0.INSTRUCTIONS1.getTranslation()
						+ " " + firstEmpty
						+ Labels.Step0.INSTRUCTIONS2.getTranslation();
			}
			
			this.instructionBox.setText(instructions);
			System.out.println("Update Instructions: " + instructions);
		}
		catch (DisplayIsFullException e)
		{
			// Determine instruction text
			String instructions;
			if (complete)
				instructions = Labels.Step0.INSTRUCTIONS_CORRECT
						.getTranslation();
			else
				instructions = Labels.Step0.INSTRUCTIONS_COMPLETE
						.getTranslation();
			
			// Set instructions label text
			this.instructionBox.setText(instructions);
			System.out.println("Update Instructions: " + instructions);
		}
	}
	
	public void updateHints()
	{
		hintBox.clear();
		
		// Derive hints from the user's current choices (shuffle the hints)
		ArrayList<String> hints = selectorDisplay.deriveHints(true);
		
		// Determine number of hints (2 wrong answers = 1 hint; min 1 hint)
		int answerHintRatio = 2;
		int numberOfHints = hints.size();
		numberOfHints = (answerHintRatio > numberOfHints) ? 1
				: (numberOfHints / answerHintRatio);
		
		// Reduce number of hints to numberOfHints
		while (hints.size() > numberOfHints)
			hints.remove(0);
		
		// Update the number of hints used
		Game.getCurrentTask().getCurrentAttempt().addHints(hints.size());
		
		// Add hints to the hint box (double-spaced)
		hintBox.addLines(hints, true);
	}
	
	public void displayGenericHint()
	{
		hintBox.clear();
		String hint = selectorDisplay.deriveHint();
		hintBox.addLine(hint);
		
		// Update the number of hints used
		++hints;
		Game.getCurrentTask().getCurrentAttempt().addHints(1);
	}
	
	public void load()
	{
		TaskData currentTask = Game.getCurrentTask();
		SizingStepsData currentAttempt = (SizingStepsData) currentTask
				.getCurrentAttempt();
		
		// Create a new attempt instance, if necessary
		if (currentAttempt == null)
		{
			resetButtons();
			currentTask.setCurrentAttempt(generateDefaultData());
			currentAttempt = (SizingStepsData) currentTask.getCurrentAttempt();
			System.out.println("Current Attempt is null... Resetting...");
		}
		System.out.println("\nLoad Start\n\tCurrent Hints: "
				+ Arrays.toString(currentAttempt.getCurrentHints().toArray()));
		
		// Load from data
		selector.setElements(currentAttempt.getSelectorContents());
		selectorDisplay
				.setElements(currentAttempt.getSelectorDisplayContents());
		ArrayList<String> currentHints = currentAttempt.getCurrentHints();
		hintBox.setText(currentHints);
		selector.updateChoiceLabel();
		updateInstructions();
		complete = currentAttempt.isPartOneComplete();
		
		if (selectorDisplay.isFull() && complete)
		{
			selectorDisplay.updateChoiceBorders();
			continueButtonOn();
		}
		else
		{
			selectorDisplay.restoreChoiceBorders();
			readyButton.getTextField().setText("Ready");
		}
		System.out.println("Loaded!\nCurrent Hints: "
				+ Arrays.toString(currentAttempt.getCurrentHints().toArray()));
		System.out.println("HintBox: "
				+ Arrays.toString(hintBox.getText().toArray()));
	}
	
	public SizingStepsData generateDefaultData()
	{
		ArrayList<SelectorIcon> selectorDisplayContents = new ArrayList<>();
		CircularList<SelectorIcon> selectorContents = new CircularList<>();
		ArrayList<String> currentHints = this.hintBox.getText();
		
		// Add each of 5 steps to the selectorContent list
		populateSelectorContents(selectorContents);
		
		// Shuffle the selector choices
		selectorContents.shuffle();
		
		return new SizingStepsData(selectorDisplayContents, selectorContents,
				currentHints, false);
	}
	
	public void populateSelectorContents(
			CircularList<SelectorIcon> selectorContents)
	{
		// Add each of 5 steps to the selectorContent list
		try
		{
			selectorContents.add(new SelectorIcon(
					SelectorIcons.ENERGY_ASSESSMENT,
					Labels.TaskScreen.ENERGY_ASSESSMENT.getTranslation(), 0));
			selectorContents
					.add(new SelectorIcon(SelectorIcons.CRITICAL_DESIGN_MONTH,
							Labels.TaskScreen.CRITICAL_DESIGN_MONTH
									.getTranslation(), 1));
			selectorContents.add(new SelectorIcon(SelectorIcons.BATTERY_SIZING,
					Labels.TaskScreen.BATTERY_SIZING.getTranslation(), 2));
			selectorContents.add(new SelectorIcon(SelectorIcons.PV_SIZING,
					Labels.TaskScreen.PV_SIZING.getTranslation(), 3));
			selectorContents.add(new SelectorIcon(
					SelectorIcons.CONTROLLER_SIZING,
					Labels.TaskScreen.CONTROLLER_SIZING.getTranslation(), 4));
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void save()
	{
		SizingStepsData currentAttempt = (SizingStepsData) Game
				.getCurrentTask().getCurrentAttempt();
		
		ArrayList<SelectorIcon> selectorDisplayContents = this.selectorDisplay
				.getElements();
		CircularList<SelectorIcon> selectorContents = this.selector
				.getElements();
		
		ArrayList<String> currentHints = new ArrayList<>();
		for (String string : this.hintBox.getText())
		{
			currentHints.add(string);
		}
		
		currentAttempt.setCurrentHints(currentHints);
		currentAttempt.setSelectorContents(selectorContents);
		currentAttempt.setSelectorDisplayContents(selectorDisplayContents);
		currentAttempt.setPartOneComplete(complete);
	}
}
