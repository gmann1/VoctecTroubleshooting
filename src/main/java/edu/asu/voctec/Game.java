package edu.asu.voctec;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.GameDefaults.MainDefaults;
import edu.asu.voctec.game_states.ExitScreen;
import edu.asu.voctec.game_states.InstructorControlPanel;
import edu.asu.voctec.game_states.LanguageMenu;
import edu.asu.voctec.game_states.MainMenu;
import edu.asu.voctec.game_states.MenuTest;
import edu.asu.voctec.game_states.ModifiedGameState;
import edu.asu.voctec.game_states.ScenarioHub;
import edu.asu.voctec.game_states.Task;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.information.ScenarioData;
import edu.asu.voctec.information.TaskData;
import edu.asu.voctec.information.UserProfile;
import edu.asu.voctec.language.Dictionary;
import edu.asu.voctec.minigames.battery_sizing.BatteryExitScreen;
import edu.asu.voctec.minigames.battery_sizing.BatteryGameScreen;
import edu.asu.voctec.minigames.battery_sizing.BatteryIntro;
import edu.asu.voctec.minigames.cdmg.CDExtra;
import edu.asu.voctec.minigames.cdmg.CDIntroScreen;
import edu.asu.voctec.minigames.cdmg.CDPart1;
import edu.asu.voctec.minigames.cdmg.CDPart2;
import edu.asu.voctec.minigames.cdmg.CDPart3;
import edu.asu.voctec.minigames.controller_sizing.ControllerSizingExit;
import edu.asu.voctec.minigames.controller_sizing.ControllerSizingIntroScreen;
import edu.asu.voctec.minigames.controller_sizing.ControllerSizingPart1;
import edu.asu.voctec.minigames.controller_sizing.ControllerSizingPart2;
import edu.asu.voctec.minigames.controller_sizing.ControllerSizingPart3;
import edu.asu.voctec.minigames.energy_assessment.EAPart1IntroScreen;
import edu.asu.voctec.minigames.energy_assessment.EAPart2;
import edu.asu.voctec.minigames.pv_game.PVExit;
import edu.asu.voctec.minigames.pv_game.PVGame;
import edu.asu.voctec.minigames.pv_game.PVIntro;
import edu.asu.voctec.minigames.step_selection.ScenarioIntroductionScreen;
import edu.asu.voctec.minigames.step_selection.StepSelection;
import edu.asu.voctec.minigames.step_selection.StepSelectionExitScreen;
import edu.asu.voctec.utilities.AspectRatio.ResolutionNotSupportedException;
import edu.asu.voctec.utilities.Resizable;
import edu.asu.voctec.utilities.ScreenResolution;
import edu.asu.voctec.utilities.Singleton;
import edu.asu.voctec.utilities.Translatable;

/**
 * Singleton class representing the currently running game. The singleton Game
 * object can be accessed statically via {@link #getCurrentGame()}. Using this
 * object, the game state can be changed using a Class object to reference a
 * Singleton gameState (i.e. any extension of ModifiedGameState).
 * 
 * All states added to this game should implement Singleton, and extend
 * ModifiedGameState. States that do not meet these requirements will not be
 * added to the state list of this game.
 * 
 * This class handles information tracking, saving, and loading as follows: When
 * a user beings a "task," a new AttemptData object will be created. That object
 * will be set as the"currentAttempt" of the "currentTask" in this Class, which
 * can be accessed by other classes using {@link #getCurrentTask()}. It is up to
 * each task (i.e. each minigame) to update the current AttemptData object, as
 * progress is made by the user.
 * 
 * @author Zach Moore
 * @see ModifiedGameState
 * @see Singleton
 * @see #getCurrentGame()
 * @see #enterState(Class)
 */
public class Game extends StateBasedGame implements Singleton
{
	/**
	 * Map of all GameState IDs that have been added. @see #addState(GameState)
	 */
	private static HashMap<Class<?>, Integer> gameStates = new HashMap<>();
	private static Game currentGame;
	private static ScenarioData currentScenario;
	private static TaskData currentTask;
	private static UserProfile currentUser;
	private static ScreenResolution currentScreenDimension;
	private static Dictionary currentLanguage;
	
	/** GameState to enter upon launching the application */
	public static final Class<?> DEFAULT_GAME_STATE = MainMenu.class;
	
	/**
	 * Private constructor to enforce Singleton.
	 * 
	 * @param gameTitle
	 *            the name of the window this game will run in.
	 * @throws DuplicateInstantiationException
	 *             if an attempt is made to create more than one instance of
	 *             this class.
	 * @throws ResolutionNotSupportedException
	 *             Thrown if the defaults are set to an unsupported resolution
	 *             (e.g. 0x0)
	 */
	private Game(String gameTitle) throws DuplicateInstantiationException,
			ResolutionNotSupportedException
	{
		super(gameTitle);
		currentLanguage = Dictionary.constructDictionary("default");
		currentScreenDimension = new ScreenResolution(
				MainDefaults.DEFAULT_WINDOW_WIDTH,
				MainDefaults.DEFAULT_WINDOW_HEIGHT);
		
		if (Game.currentGame == null)
			Game.currentGame = this;
		else
			throw new DuplicateInstantiationException();
		
		//System.out.println("Game constructed successfully.");
	}
	
	/**
	 * Constructs a game using the title defined in Main.
	 * 
	 * @return the current Game object.
	 * @see #constructGame(String)
	 * @see edu.asu.voctec.Main
	 */
	public static Game constructGame()
	{
		return constructGame(MainDefaults.GAME_TITLE);
	}
	
	/**
	 * Pseudo-Constructor. If a game has already been instantiated, this method
	 * will return that Game object. Otherwise, a new Game object will be
	 * constructed and returned.
	 * 
	 * @param the
	 *            name of the window this game will run in.
	 * @return the current Game object.
	 * @see #Game(String)
	 */
	protected static Game constructGame(String gameTitle)
	{
		if (Game.currentGame == null)
		{
			try
			{
				return new Game(gameTitle);
			}
			catch (DuplicateInstantiationException exception)
			{
				return Game.currentGame;
			}
			catch (ResolutionNotSupportedException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return Game.currentGame;
		}
	}
	
	/**
	 * Resize all game states, so that they display at the desired resolution.
	 * 
	 * @param screenDimension The desired resolution.
	 * @return Whether or not the resize was successful.
	 * @see ScreenResolution
	 */
	public static boolean resize(final ScreenResolution screenDimension)
	{
		boolean resizeSuccessfull;
		
		// Update dimension information
		Game.currentScreenDimension = screenDimension;
		
		try
		{
			// Resize all gameStates
			for (int id : Game.getGameStates())
			{
				// Iterate through each GameState
				GameState gameState = currentGame.getState(id);
				if (gameState instanceof Resizable)
				{
					// TODO implement resizing
					// ((Resizable) gameState).resize();
				}
			}
			
			// Resize container (user window)
			Main.getGameContainer().setDisplayMode(screenDimension.width,
					screenDimension.height, false);
			
			// If no exceptions were thrown while resizing, then resizing was
			// successful
			resizeSuccessfull = true;
		}
		catch (SlickException e)
		{
			e.printStackTrace();
			resizeSuccessfull = false;
		}
		
		return resizeSuccessfull;
	}
	
	/**
	 * @return a copy of the current resolution Dimension object (i.e. current
	 *         game window resolution)
	 */
	public static ScreenResolution getCurrentScreenDimension()
	{
		// TODO replace with copy
		return currentScreenDimension;
	}
	
	/**
	 * @return The current language being used by this Game
	 */
	public static Dictionary getCurrentLanguage()
	{
		return currentLanguage;
	}
	
	/**
	 * Updates the current language of all game states, so that all text will
	 * display in the desired language.
	 * 
	 * @see Dictionary
	 */
	public static void setCurrentLanguage(Dictionary currentLanguage)
	{
		Game.currentLanguage = currentLanguage;
		//System.out.println("Updating Language...");
		// translate gameStates
		for (int id : Game.getGameStates())
		{
			// Iterate through each GameState
			GameState gameState = Game.getCurrentGame().getState(id);
			// TODO only translate the current state
			// TODO translate each state upon entry
			// label updates are handled in each gamestate
			if (gameState instanceof Translatable)
				((Translatable) gameState).updateTranslation();
		}
		
		//System.out.println("Language Updates Complete.");
	}
	
	/**
	 * @return the Game object representing the currently running game.
	 */
	public static Game getCurrentGame()
	{
		return currentGame;
	}
	
	/**
	 * @return A collection of all GameStates used by this Game.
	 * @see HashMap#values()
	 */
	public static Collection<Integer> getGameStates()
	{
		return Game.gameStates.values();
	}
	
	/*
	 * (non-Javadoc) Create, add, and initialize all states associated with this
	 * game
	 * 
	 * @see
	 * org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick
	 * .GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException
	{
		// Initialize & Add all GameStates
		this.addState(new InstructorControlPanel());
		this.enterState(InstructorControlPanel.class);
	
	}
	
	public void secondaryStatesList(GameContainer container) throws SlickException
	{
		this.addState(new MainMenu(), container);
		
		//this.addState(new MenuTest(), container);
		//this.addState(new ScenarioHub(), container);
		this.addState(new ExitScreen(), container);
		//this.addState(new LanguageMenu(), container);
		this.addState(new TaskScreen(), container);
		//this.addState(new ScenarioIntroductionScreen(), container);
		//this.addState(new StepSelection(), container);
		//this.addState(new StepSelectionExitScreen(), container);
		//this.addState(new CDPart1(), container);
		//this.addState(new CDPart2(), container);
		//this.addState(new CDPart3(), container);
		//this.addState(new CDExtra(), container);
		//this.addState(new CDIntroScreen(), container);
		//this.addState(new BatteryExitScreen(), container);
		//this.addState(new BatteryIntro(), container);
		//this.addState(new BatteryGameScreen(), container);
		//this.addState(new PVIntro(), container);
		//this.addState(new PVGame(), container);
		//this.addState(new PVExit(), container);
		this.addState(new EAPart1IntroScreen(), container);
		//this.addState(new EAPart2(), container);
		//this.addState(new ControllerSizingIntroScreen(), container);
		//this.addState(new ControllerSizingExit(), container);
		//this.addState(new ControllerSizingPart1(), container);
		//this.addState(new ControllerSizingPart2(), container);
		//this.addState(new ControllerSizingPart3(), container);
		
	
		
		// Move to the default game state
		this.enterState(Game.DEFAULT_GAME_STATE);
	}
	
	/*
	 * (non-Javadoc) adds the provided GameState to the list of gameStates, and
	 * maps the state, so it can be accessed statically.
	 * 
	 * @see #enterstate(Class<?>)
	 * 
	 * @see
	 * org.newdawn.slick.state.StateBasedGame#addState(org.newdawn.slick.state
	 * .GameState)
	 */
	@Override
	public void addState(GameState state)
	{
		if (state instanceof ModifiedGameState)
		{
			super.addState(state);
			gameStates.put(state.getClass(), state.getID());
		}
		else
		{
			throw new IllegalArgumentException(
					"Game only accepts ModifiedGameStates.");
		}
	}
	
	public void addState(GameState state, GameContainer container) throws SlickException
	{
		if (state instanceof ModifiedGameState)
		{
			super.addState(state);
			gameStates.put(state.getClass(), state.getID());
			this.getState(state.getID()).init(container, this);
		}
		else
		{
			throw new IllegalArgumentException(
					"Game only accepts ModifiedGameStates.");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.StateBasedGame#enterState(int)
	 */
	@Override
	public void enterState(int id)
	{
		//System.out.println("Switching States...");
		
		ModifiedGameState currentState = (ModifiedGameState) getCurrentState();
		GameState newState = getState(id);
		
		currentState.onExit();
		
		if (newState instanceof Task)
			((Task) newState).load();
		if (newState instanceof ModifiedGameState)
			((ModifiedGameState) newState).onEnter();
		
		super.enterState(id);
		//System.out.println("Switch Successful. Current State: "
				//+ this.getCurrentStateID());
	}
	
	/**
	 * @see #enterState(int)
	 */
	public void enterState(Class<?> state)
	{
		this.enterState(getStateID(state));
	}
	
	/**
	 * @see TaskData
	 */
	public static TaskData getCurrentTask()
	{
		return currentTask;
	}
	
	/**
	 * Returns the id of the GameState referenced by the provided class. If this
	 * Game does not have a matching state, null will be returned.
	 * 
	 * @param state
	 *            The class of the desired state.
	 * @return The GameState object matching the procided class.
	 */
	public static int getStateID(Class<?> state)
	{
		//System.out.println("State Receieved: " + state);
		//System.out.println("State ID: " + gameStates.get(state));
		if (gameStates.get(state) != null){
		return gameStates.get(state);
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * Should be called when the current Task changes (i.e. the user starts a
	 * new task).
	 * 
	 * @see TaskScreen
	 * @see TaskData
	 * @param currentTask
	 *            The task started by the user.
	 */
	public static void setCurrentTask(TaskData currentTask)
	{
		Game.currentTask = currentTask;
	}
	
	/**
	 * Returns the GameState used by this Game, that is of the type matching the
	 * "state" parameter.
	 * 
	 * @param state
	 *            The class of the requested gameState.
	 * @return The GameState object that corresponds to the provided class
	 *         object. If the "state" parameter is null, null will be returned.
	 */
	public static GameState getGameState(Class<?> state)
	{
		if (state != null)
			return currentGame.getState(getStateID(state));
		else
			return null;
	}
	
	/**
	 * Equivalent to a call to {@link #getCurrentGame()}{@link #getState(int)}
	 * using the return of {@link #getStateID(ExitScreen.class)} as a parameter.
	 * 
	 * @return The ExitScreen object used by this Game.
	 */
	public static ExitScreen getExitScreen()
	{
		return (ExitScreen) currentGame.getState(getStateID(ExitScreen.class));
	}
	
	/**
	 * Wrapper for exitScreen.updateExitText(String, String).
	 * 
	 * @see ExitScreen#updateExitText(String, String)
	 * @param titleField
	 *            Text to display at the top left of the screen (e.g. "Good Job"
	 *            or "Well Done!")
	 * @param feedback
	 *            Feedback paragraph to display underneath the titleField.
	 */
	public static void updateExitText(String titleField, String feedback)
	{
		getExitScreen().updateExitText(titleField, feedback);
	}
	
	/**
	 * Wrapper for exitScreen.updateExitText(String, ArrayList).
	 * 
	 * @see ExitScreen#updateExitText(String, ArrayList)
	 * @param titleField
	 *            Text to display at the top left of the screen (e.g. "Good Job"
	 *            or "Well Done!")
	 * @param feedback
	 *            Feedback paragraph to display underneath the titleField.
	 */
	public static void updateExitText(String titleField,
			ArrayList<String> feedback)
	{
		getExitScreen().updateExitText(titleField, feedback);
	}
	
	/**
	 * Used to update the generic ExitScreen from a static call. This should be
	 * called whenever a mini-game finishes, and transitions to ExitScreen.
	 * Note: all data related to the current attempt will be loaded
	 * automatically (hints used, time spent, etc).
	 * 
	 * @param titleField
	 *            Text to display at the top of the screen
	 * @param feedback
	 *            Paragraph to display below the titleField
	 * @param backgroundImage
	 *            Desired background for the exit screen. If this value is null,
	 *            the background will not be updated.
	 */
	public static void updateExitText(String titleField, String feedback,
			Image backgroundImage)
	{
		ExitScreen exitScreen = getExitScreen();
		
		exitScreen.updateExitText(titleField, feedback);
		
		if (backgroundImage != null)
			exitScreen.updateExitScreen(backgroundImage);
	}
	
	/**
	 * @see UserProfile
	 */
	public static UserProfile getCurrentUser()
	{
		return currentUser;
	}
	
	/**
	 * Sets the currentUser, but does not overwrite the profile in the (hard)
	 * save-file. In order to save the profile to a file,
	 * {@link #saveToFile(String)} must be called.
	 * 
	 * @see #saveToFile(String)
	 */
	public static void setCurrentUser(UserProfile currentUser)
	{
		Game.currentUser = currentUser;
	}
	
	/**
	 * @see ScenarioData
	 */
	public static ScenarioData getCurrentScenario()
	{
		return currentScenario;
	}
	
	/**
	 * @see ScenarioData
	 */
	public static void setCurrentScenario(ScenarioData currentScenario)
	{
		Game.currentScenario = currentScenario;
	}
	
	/**
	 * Saves the current UserProfile to a save file. The old file (referenced by
	 * the same UserProfile object) will be overwritten.
	 * 
	 * @param relativePath
	 *            Location to save the current data.
	 */
	public static void saveToFile(String relativePath)
	{
		try
		{
			// Open save file
			FileOutputStream fileOutputStream = new FileOutputStream(
					relativePath);
			ObjectOutputStream outputStream = new ObjectOutputStream(
					fileOutputStream);
			
			// Save the current UserProfile
			outputStream.writeObject(currentUser);
			// TODO save only altered profiles
			// TODO overwrite the altered profile, but leave all other profiles
			// in tact.
			
			// Free Resources
			outputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
