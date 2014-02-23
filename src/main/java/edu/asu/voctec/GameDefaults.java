package edu.asu.voctec;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import edu.asu.voctec.language.LabelName;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * Contains all constants used across multiple GameStates. This includes all
 * resources (images, sounds, etc) as well as the defaults for all game text (in
 * case it cannot be loaded from the appropriate file). GameDefaults also
 * contains paths for save files, default settings for various fonts, L&F
 * information, etc.
 * 
 * @author Moore, Zachary
 */
// TODO This file should be reviewed and cleaned. Unused constants may be
// removed.
public interface GameDefaults
{
	public interface MainDefaults
	{
		public static final int DEFAULT_WINDOW_WIDTH = 800;
		public static final int DEFAULT_WINDOW_HEIGHT = 600;
		public static final int TARGET_FRAME_RATE = 30;
		public static final String GAME_TITLE = "Sizing";
		public static final boolean SHOW_FPS = false;
		public static final String DEFAULT_LANGUAGE = "english";
		public static final float DISABLED_COMPONENT_OPACITY = 0.9f;
	}
	
	public interface ImagePaths
	{
		// Backgrounds
		public static final String MainMenuBackground = "resources/default/img/backgrounds/gameBackground.png";
		public static final String OptionsMenuBackground = "resources/default/img/backgrounds/gameBackground.png";
		public static final String ScenarioHubBackground = "resources/default/img/backgrounds/gameBackground.png";
		public static final String TaskHubBackground = "resources/default/img/backgrounds/taskScreen2.png";
		public static final String BLACK_BACKGROUND = "resources/default/img/blackBackground.jpg";
		
		// Buttons
		public static final String BASE_BUTTON = "resources/default/img/buttons/readyButton.png";
		public static final String NEW_GAME_BUTTON = "resources/default/img/buttons/newProfileButtonR.png";
		public static final String OPTIONS_BUTTON = "resources/default/img/buttons/optionsButtonR.png";
		// TODO replace with non-grey button
		public static final String INSTRUCTOR_CONTROL_PANEL_BUTTON = "resources/default/img/buttons/optionsButton_grey.png";
		public static final String BACK_BUTTON = "resources/default/img/buttons/backButton.png";
		// TODO replace with non-grey button
		public static final String LANGUAGE_BUTTON = "resources/default/img/buttons/languagesButton_grey.png";
		public static final String READY_BUTTON = "resources/default/img/buttons/readyButton.png";
		public static final String CONTINUE_BUTTON_ON = "resources/default/img/buttons/continueButton.png";
		public static final String CONTINUE_BUTTON_OFF = "resources/default/img/buttons/continueHidden.png";
		public static final String REPLAY_BUTTON = "resources/default/img/buttons/ReplayButton.png";
		public static final String SKIP_BUTTON = "resources/default/img/buttons/SkipButton.png";
		
		// changes
		public static final String HINT_BUTTON = "resources/default/img/buttons/hintButton.png";
		// Labels
		public static final String BASE_LABEL = "resources/default/img/testButton.png";
		public static final String HINT_BOX = "resources/default/img/misc/hintBox.png";
		
		// Selector Resources
		public static final String ARROW_LEFT = "resources/default/img/selector/ArrowLeft.png";
		public static final String ARROW_RIGHT = "resources/default/img/selector/ArrowRight.png";
		public static final String SELECTOR_LARGE = "resources/default/img/selector/SelectorLarge.png";
		public static final String SELECTOR_SMALL = "resources/default/img/selector/SelectorSmall.png";
		public static final String SELECTOR_SHADOW = "resources/default/img/selector/SelectorShadow.png";
		
		// game template
		
		public static final String HINT_BOX_TEMPLATE = "resources/default/img/gameTemplate/BottomHintBox.png";
		public static final String CONTROL_PANEL = "resources/default/img/gameTemplate/ControlPanel.png";
		public static final String READY_BOX = "resources/default/img/gameTemplate/EmptyBox.png";
		public static final String SIDE_PANEL = "resources/default/img/gameTemplate/SidePanel.png";
		
		public interface Buttons
		{
			public static final String BASE = "resources/default/img/buttons/blankReadyButton.png";
			public static final String NEW_GAME = "resources/default/img/buttons/newProfileButtonR.png";
			public static final String OPTIONS = "resources/default/img/buttons/optionsButtonR.png";
			public static final String INSTRUCTOR_CONTROL_PANEL = "resources/default/img/buttons/optionsButtonR.png";
			public static final String BACK = "resources/default/img/buttons/backButton.png";
			public static final String LANGUAGE = "resources/default/img/buttons/languagesButtonR.png";
			public static final String READY = "resources/default/img/buttons/readyButton.png";
		}
		
		public interface TaskScreen
		{
			public static final String STEP_ONE = "resources/default/img/taskScreen/Task1.png";
			public static final String STEP_TWO = "resources/default/img/taskScreen/Task2.png";
			public static final String STEP_THREE = "resources/default/img/taskScreen/Task3.png";
			public static final String STEP_FOUR = "resources/default/img/taskScreen/Task4.png";
			public static final String STEP_FIVE = "resources/default/img/taskScreen/Task5.png";
			
			public static final String STEP_ONE_COMPLETE = "resources/default/img/taskScreen/Task1Complete.png";
			public static final String STEP_TWO_COMPLETE = "resources/default/img/taskScreen/Task2Complete.png";
			public static final String STEP_THREE_COMPLETE = "resources/default/img/taskScreen/Task3Complete.png";
			public static final String STEP_FOUR_COMPLETE = "resources/default/img/taskScreen/Task4Complete.png";
			public static final String STEP_FIVE_COMPLETE = "resources/default/img/taskScreen/Task5Complete.png";
			
			public static final String STEP_ONE_SELECTED = "resources/default/img/taskScreen/Task1Current.png";
			public static final String STEP_TWO_SELECTED = "resources/default/img/taskScreen/Task2Current.png";
			public static final String STEP_THREE_SELECTED = "resources/default/img/taskScreen/Task3Current.png";
			public static final String STEP_FOUR_SELECTED = "resources/default/img/taskScreen/Task4Current.png";
			public static final String STEP_FIVE_SELECTED = "resources/default/img/taskScreen/Task5Current.png";
			
			public static final String PROGRESS_BAR_BORDER = "resources/default/img/taskScreen/ProgressBorder.png";
			public static final String PROGRESS_BAR_EMPTY = "resources/default/img/taskScreen/ProgressEmpty.png";
			public static final String PROGRESS_BAR_FULL = "resources/default/img/taskScreen/ProgressFull.png";
		}
		
		public interface SelectorIcons
		{
			public static final String ENERGY_ASSESSMENT = "resources/default/img/selector/icons/energyAssessment.png";
			public static final String CRITICAL_DESIGN_MONTH = "resources/default/img/selector/icons/criticalDesignMonth.png";
			public static final String BATTERY_SIZING = "resources/default/img/selector/icons/batterySizing.png";
			public static final String PV_SIZING = "resources/default/img/selector/icons/pvSizing.png";
			public static final String CONTROLLER_SIZING = "resources/default/img/selector/icons/controllerSizing.png";
		}
		
		public interface SelectorDisplayBorders
		{
			public static final String DEFAULT = "resources/default/img/selector/display/EmptyBox.png";
			public static final String HIGHLIGHTED = "resources/default/img/selector/display/EmptyBoxHighlighted.png";
			public static final String CORRECT = "resources/default/img/selector/display/EmptyBoxCorrect.png";
			public static final String INCORRECT = "resources/default/img/selector/display/EmptyBoxWrong.png";
			
			public static final String SMALL_ARROW = "resources/default/img/selector/display/smallArrow.png";
			public static final String LARGE_ARROW = "resources/default/img/selector/display/largeArrow.png";
		}
		
		public interface Selector
		{
			public static final String HINT_BOX_BACKGROUND = "resources/default/img/selector/hintBox.png";
		}
		
	}
	
	public interface SoundPaths
	{
		// Critical Design Month Resources
		public static final String CHARGE = "resources/default/sounds/charge.wav";
	}
	
	public interface Fonts
	{
		public static final String FONT_NAME = "Meiryo"; // "Hiragino Kaku Gothic Pro";
		public static final int FONT_SIZE_DEFAULT = 36;
		public static final boolean ANTI_ALLIAS = true;
		public static final Color FONT_COLOR = Color.white;
		public static final Color TRANSITION_FONT_COLOR = Color.white;
		public static final Color BUTTON_FONT_COLOR = Color.black;
		public static final Color DISABLED_BUTTON_FONT_COLOR = Color.lightGray;
		
		public static final float FONT_SIZE_SMALL = 8f;
		public static final float FONT_SIZE_MEDIUM = 12f;
		public static final float FONT_SIZE_LARGE = 18f;
		public static final float FONT_SIZE_EXTRA_LARGE = 26f;
		
		public static final Font AWT_FONT = new Font(FONT_NAME, Font.BOLD,
				Fonts.FONT_SIZE_DEFAULT);
		public static final TrueTypeFont TRUE_TYPE_FONT = new TrueTypeFont(
				AWT_FONT, ANTI_ALLIAS);
	}
	
	public interface MainMenuDefaults
	{
		public static final Color BUTTON_FONT_COLOR = Color.darkGray;
	}
	
	public interface IntroductionScreenDefaults
	{
		public static final Rectangle TITLE_BOUNDS = new Rectangle(0, 50, 300,
				50);
		public static final Rectangle BODY_TEXT_BOUNDS = UtilFunctions
				.newCenteredRectangle(new Rectangle(0, 0, 800, 600),
						new Rectangle(0, 0, 500, 400));
	}
	
	/**
	 * Positions and Dimensions of all selector components (relative to the
	 * selector).
	 */
	public interface SelectorDefaults
	{
		public static Dimension ARROW_SIZE = new Dimension(41, 123);
		public static Dimension PRIMARY_SELECTION_SIZE = new Dimension(183, 184);
		public static Dimension SECONDARY_SELECTION_SIZE = new Dimension(124,
				123);
		
		// Center Left-Arrow Vertically (far left)
		public static Point ARROW_LOCATION_LEFT = new Point(0,
				(PRIMARY_SELECTION_SIZE.height - ARROW_SIZE.height) / 2);
		
		// Center Right-Arrow Vertically (far right)
		public static Point ARROW_LOCATION_RIGHT = new Point(535,
				(PRIMARY_SELECTION_SIZE.height - ARROW_SIZE.height) / 2);
		
		// Center Primary Selection (dead-center)
		public static Point PRIMARY_SELECTION_LOCATION = new Point(
				(ARROW_LOCATION_RIGHT.x + ARROW_SIZE.width
						- ARROW_LOCATION_LEFT.x - PRIMARY_SELECTION_SIZE.width) / 2,
				0);
		
		// Center Left-Selection between primary selection and left-arrow
		public static Point SECONDARY_SELECTION_LOCATION_LEFT = new Point(
				(((PRIMARY_SELECTION_LOCATION.x
						- SECONDARY_SELECTION_SIZE.width
						- ARROW_LOCATION_LEFT.x - ARROW_SIZE.width) / 2)
						+ ARROW_LOCATION_LEFT.x + ARROW_SIZE.width),
				(PRIMARY_SELECTION_SIZE.height - SECONDARY_SELECTION_SIZE.height) / 2);
		
		// Center Right-Selection between primary selection and right-arrow
		public static Point SECONDARY_SELECTION_LOCATION_RIGHT = new Point(
				(((ARROW_LOCATION_RIGHT.x - PRIMARY_SELECTION_LOCATION.x
						- PRIMARY_SELECTION_SIZE.width - SECONDARY_SELECTION_SIZE.width) / 2)
						+ PRIMARY_SELECTION_LOCATION.x + PRIMARY_SELECTION_SIZE.width),
				(PRIMARY_SELECTION_SIZE.height - SECONDARY_SELECTION_SIZE.height) / 2);
		
		// Position Label above selector (relative)
		public static Rectangle ICON_LABEL_BOUNDS = new Rectangle(0, -75, 320,
				75);
		
		// Desired font size for selector label
		public static float LABEL_FONT_SIZE = Fonts.FONT_SIZE_MEDIUM;
	}
	
	public interface TaskScreenDefaults
	{
		public static final Point INFORMATION_OFFSET = new Point(350, 175);
		public static final float INFORMATION_SCALE = 0.92f;
		public static final boolean WRITE_TO_BUTTONS = false;
	}
	
	public interface XMLPaths
	{
		public static final String Dictionary = "resources/default/globalDictionary.xml";
	}
	
	public interface DictionaryTags
	{
		public static final String LABEL_NAME = "label";
		public static final String SUPPORTED_LANGUAGE = "supported_language";
	}
	
	public interface Labels
	{
		// Generic Button Labels
		public interface Buttons
		{
			public static final LabelName START = new LabelName("startButton",
					"Start");
			public static final LabelName READY = new LabelName("readyButton",
					"Ready");
			public static final LabelName REPLAY = new LabelName(
					"replayButton", "Replay");
			public static final LabelName BACK = new LabelName("backButton",
					"Back");
		}
		
		// Numbers
		public interface OrdinalNumbers
		{
			public static final LabelName FIRST = new LabelName("ordinal1",
					"first");
			public static final LabelName SECOND = new LabelName("ordinal2",
					"second");
			public static final LabelName THIRD = new LabelName("ordinal3",
					"thrid");
			public static final LabelName FOURTH = new LabelName("ordinal4",
					"fourth");
			public static final LabelName FIFTH = new LabelName("ordinal5",
					"fifth");
			public static final LabelName SIXTH = new LabelName("ordinal6",
					"sixth");
		}
		
		public interface Step0
		{
			public interface Hints
			{
				public static final LabelName CORRECT_STEP = new LabelName(
						"correctStep", "is placed correctly!");
				public static final LabelName BEFORE = new LabelName("before",
						"before");
				public static final LabelName AFTER = new LabelName("after",
						"after");
				public static final LabelName HINT_BODY = new LabelName(
						"hintBody", "should be completed");
			}
			
			public static final LabelName INSTRUCTIONS_BEGIN = new LabelName(
					"instructionsBegin",
					"Use the arrows to cycle through the items of the sizing process. Then, click on the item in the middle box to select and place as step 1.");
			public static final LabelName INSTRUCTIONS1 = new LabelName(
					"instructions1",
					"Click on the item in the middle box to select and place as step");
			public static final LabelName INSTRUCTIONS2 = new LabelName(
					"instructions2", ".");
			public static final LabelName INSTRUCTIONS_RED = new LabelName(
					"instructionsRed", "Click the red icons to deselect them.");
			public static final LabelName INSTRUCTIONS_INCOMPLETE = new LabelName(
					"instructionsIncomplete", "There are still more steps!");
			public static final LabelName INSTRUCTIONS_COMPLETE = new LabelName(
					"instructionsComplete",
					"Press the ready button to verify your choices.");
			public static final LabelName INSTRUCTIONS_CORRECT = new LabelName(
					"instructionsCorrect", "Correct! Press ready to continue.");
			
			public static final LabelName INTRODUCTION = new LabelName(
					"step0Intro",
					"Welcome! You are about to take an interactive journey to sizing a small standalone PV system. Before you start, however, it is important to understand the project and customer requirements and the major steps involved in sizing a PV system. On the next screen, you will see 5 major sizing items (not in the right order). Use the arrows to cycle through each option, and decide the order in which you would like  to complete the sizing process.");
		}
		
		public interface TaskScreen
		{
			public static final LabelName INACCESSIBLE_TEXT = new LabelName(
					"inaccessibleText",
					"This step cannot be completed until all previous steps are completed.");
			public static final LabelName ENERGY_ASSESSMENT = new LabelName(
					"energyAssessment", "Assess Energy Requirements");
			public static final LabelName CRITICAL_DESIGN_MONTH = new LabelName(
					"criticalDesignMonth", "Find the Critical Design Month");
			public static final LabelName BATTERY_SIZING = new LabelName(
					"batterySizing", "Size the Battery");
			public static final LabelName PV_SIZING = new LabelName(
					"pvSizing", "Size the PV Array");
			public static final LabelName CONTROLLER_SIZING = new LabelName(
					"controllerSizing", "Size the Controller");
		}
	}
}
