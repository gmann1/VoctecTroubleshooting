package edu.asu.voctec;

import java.util.Arrays;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.language.Dictionary;
import edu.asu.voctec.utilities.AspectRatio.ResolutionNotSupportedException;

/**
 * Initializes game, and sets up the container and all other necessities. Game,
 * along with AppGameContainer, handles all central game logic.
 * 
 * @see #main(String[])
 * @see Game
 * @author Moore, Zachary
 * 
 */
public class Main implements GameDefaults
{
	private static AppGameContainer gameContainer;
	
	/**
	 * Initializes the game, and sets up the current and supported languages and
	 * settings. All central game logic is handled by AppGameContainer and Game.
	 * 
	 * @see Game
	 * @see AppGameContainer
	 * @param args
	 *            Unused
	 */
	public static void main(String[] args) throws SlickException,
			ResolutionNotSupportedException
	{
		gameContainer = new AppGameContainer(Game.constructGame());
		
		// load languages and translations
		Dictionary.loadDictionaries();
		
		// set language to default setting
		Game.setCurrentLanguage(Dictionary
				.getDictionary(MainDefaults.DEFAULT_LANGUAGE));
		System.out.println("Additional Characters Loaded: "
				+ Arrays.toString(Dictionary.getExtraCharacters()));
		
		// TODO load settings from file
		// adjust settings
		gameContainer.setShowFPS(MainDefaults.SHOW_FPS);
		gameContainer.setTargetFrameRate(MainDefaults.TARGET_FRAME_RATE);
		gameContainer.setDisplayMode(MainDefaults.DEFAULT_WINDOW_WIDTH,
				MainDefaults.DEFAULT_WINDOW_HEIGHT, false);
		gameContainer.setForceExit(false);
		
		// launch game
		gameContainer.start();
	}
	
	/**
	 * @see AppGameContainer
	 * @return The container that hosts this game.
	 */
	public static AppGameContainer getGameContainer()
	{
		return gameContainer;
	}
}
