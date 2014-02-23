package edu.asu.voctec.minigames.pv_game;

import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.ExitScreen;
import edu.asu.voctec.game_states.GameTemplate;
import edu.asu.voctec.game_states.MainMenu;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.minigames.cdmg.CDPart1;
import edu.asu.voctec.minigames.controller_sizing.ControllerSizingIntroScreen;
import edu.asu.voctec.utilities.UtilFunctions;

public class PVGame extends GameTemplate
{

	public static final String GameBackground = "resources/default/img/minigames/BatterySizing/GameBackground.jpg";
	public static final String GraySquare = "resources/default/img/minigames/BatterySizing/BatteryBank.png";
	//public static final String AvailableBatteriesAreaImage = "resources/default/img/minigames/BatterySizing/AvailableBatteriesArea.png";
	public static final String BlankButton = "resources/default/img/minigames/BatterySizing/blankReadyButton.png";
	public static final String BlankHintButton = "resources/default/img/minigames/BatterySizing/BlankHintButton.png";
	public static final String BlueBattery = "resources/default/img/minigames/BatterySizing/BluePV.png";
	public static final String YellowBattery = "resources/default/img/minigames/BatterySizing/YellowPV.png";
	public static final String RedBattery = "resources/default/img/minigames/BatterySizing/RedPV.png";
	public static final String GreenBattery = "resources/default/img/minigames/BatterySizing/GreenPV.png";
	public static final String Trash = "resources/default/img/minigames/BatterySizing/GarbageBin.png";
	public static final String END_BACKGROUND = "resources/default/img/scoreScreenBackgrounds/ScoreBackgroundTask4.png";
	public static final String TASK_SCREEN_BACKGROUND = "resources/default/img/taskScreenBackgrounds/background4.png";
	
	public static final String HorizontalLine = "resources/default/img/minigames/BatterySizing/pvMainLine.png";
	private static Image horizontalLineImage;
	
	public static final String VerticalLines = "resources/default/img/minigames/BatterySizing/TwoLines_PVGame.png";
	private static Image verticalLinesImage;
	
	public static final String TransparentPVPanel = "resources/default/img/minigames/BatterySizing/TransparentPVPanel.png";
	private static Image TransparentPVPanelImage;
	
	private static final String[] hintsTextArray = {"The array can be solved using 1 or 2 PV panels.",
													"Two panels could be connected in parallel to solve the game.",
													"The game could be solved using only one PV panel."};
	
	public static final String BatteryBankStartLabel = "Drag a panel to the gray area in order to place it in the PV Panels Array";
	public static final String AvailableBatteriesLabel = "Available PV Panels:";
	public static final String LEFT_ARROW_TEXT = "Back";
	public static final String DoneButtonText = "Done";
	public static final String HintButtonText = "Hint";
	public static final String CapacityUnit = " Watts";
	public static final String VoltageUnit = " V";
	public static final String UncalculatableVoltageHint = "Parallel PV panels should share the same voltage but can have different power.";
	public static final String UncalculatableCapacityHint = "PV panels connected in series should have the same power but can have different voltage values.";
	public static final String RequiredOutputLabel = "Required Charging Power: ";
	public static final String RequiredVoltageLabel = "DC System Voltage: ";
	public static final String CurrentOutputLabel = "Total Watts: ";
	public static final String CurrentVoltageLabel = "Total System Voltage: ";
	public static final String BatteryBankLabel = "PV Panels Array";
	public static final String ExtraObjectsUsedMessage = "You need to use fewer number of PV panels to solve the game.";
	public static final String CompletingGameMessage = "You were able to figure out one of the solutions for this game";
	public static final String CorrectAnswerCongratulation = "Well Done...";
	public static final String CorrectAnswerMessage = " Press continue when you are ready to move on.";
	public static final String IncorrectAnswerMessage = "Remember: ";
	public static final String GameAnswer = "Using a 130Watts and 12V PV panel will solve the game.";
	
	public static final int [] Capacities = {65,130,260};
	public static final int [] Voltages = {12,12,24};
	
	private  TextField currentVoltage, currentCapacity;
	private static TextField batteryBankText;
	private static int currentHintText = 0;
	public static List<BatteryControl> objectsArray = new ArrayList<BatteryControl>();
	private List<InitialBattery> initialBatteries = new ArrayList<InitialBattery>();
	private static final int RequiredCapacity = 127, RequiredVoltage = 12, maxChances = 5;
	private boolean firstRoundOfHints = true, parallelHintNOtDisplayed = true, seriesHintNOtDisplayed = true;
	private boolean nextState;
	private int lc;
	public static int totalNumberOfHintsUsed = 0, doneButtonCounter = 0, deductedScore = 0;
	private static BasicComponent batteryBankArea;
	private static boolean CompletedGame = false;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		super.init(container,game);
		
	    batteryBankArea = new BasicComponent(new Image(GraySquare),20,70);
	    this.addComponent(batteryBankArea);
	    
	   // BasicComponent AvailableBatteriesArea = new BasicComponent(AvailableBatteriesAreaImage,20,514);
	   // this.addComponent(AvailableBatteriesArea);
	    
	    Rectangle textLocation = new Rectangle(batteryBankArea.getBounds().x, 235, batteryBankArea.getBounds().width, 60);
	    batteryBankText = new TextField(textLocation, 0.95f,
	    		BatteryBankLabel,
	        TextDisplay.FormattingOption.FIT_TEXT);
	    batteryBankText.setFontColor(Color.white);
	    batteryBankText.center();
	    this.addComponent(batteryBankText);
	    
	    Rectangle textLocation2 = new Rectangle(21, 500, 270, 20);
	    TextField AvailableBatteriesText = new TextField(textLocation2, 0.95f,
	    		AvailableBatteriesLabel,
	        TextDisplay.FormattingOption.FIT_TEXT);
	    AvailableBatteriesText.setFontColor(Color.white);
	    //AvailableBatteriesText.center();
	    this.addComponent(AvailableBatteriesText);

		verticalLinesImage = new Image(VerticalLines);
		horizontalLineImage = new Image(HorizontalLine);
		TransparentPVPanelImage = new Image(TransparentPVPanel);
		
		initialBatteries.add(new InitialBattery(Voltages[0], Capacities[0], 50, 520, new Image(BlueBattery), 50, 520, this));
		initialBatteries.add(new InitialBattery(Voltages[1], Capacities[1], 200, 520, new Image(YellowBattery), 200, 520, this));
		initialBatteries.add(new InitialBattery(Voltages[2], Capacities[2], 350, 520, new Image(RedBattery), 350, 520, this));
		//initialBatteries.add(new InitialBattery(12, 640, 500, 535, new Image(GreenBattery), 500, 535, this));
		
		BasicComponent GarbageBin = new BasicComponent(new Image(Trash),520,520);
	    this.addComponent(GarbageBin);
		
		initializeText();
		
		BasicComponent TransparentBattery = new BasicComponent(TransparentPVPanelImage, Battery.xBatteryOffset, Battery.yBatteryOffset);
	    Battery.addToTransparentBatteriesArray(TransparentBattery);
	    this.addComponent(TransparentBattery);
		
		for(InitialBattery addInitialBattery :initialBatteries)
		{
			addObject(addInitialBattery);
		}
		
		backButton.addActionListener(new TransitionButtonListener(PVIntro.class));
		readyButton.addActionListener(new DoneButtonListener());
		hintButton.addActionListener(new HintsButtonListener());
		continueButton.addActionListener(new ContinueButtonListener());
	}
	
	public void addObject(BatteryControl object)
	{
		objectsArray.add(object);
		this.addComponent(object);
	}
	
	public void removeObject(BatteryControl object)
	{
		objectsArray.remove(object);
		this.removeComponent(object);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException
	{
		
			//Game.getCurrentGame();
				super.update(container, game, delta);
				if (!nextState){
					++lc;
					if (lc == 5){
					try {
				
						Game.getCurrentGame().addState(new PVExit(), Game.getCurrentGame().getContainer());
						Game.getCurrentGame().addState(new ControllerSizingIntroScreen(), Game.getCurrentGame().getContainer());
					
					} catch (SlickException e) {
			
						e.printStackTrace();
					}
				nextState = true;
					}
			}
			
		if(CompletedGame)
		{
			continueButtonOn();
			readyButtonOff();
		}
		else
		{
			continueButtonOff();
			readyButtonOn();
		}
		
		super.update(container,game,delta);
		
		for(int index =0; index<objectsArray.size(); index++)
		{
			BatteryControl invokedObject = objectsArray.get(index);
			invokedObject.update();
		}
		
		if(CompletedGame){
			   if (sequenceStep != 4000){
			   sequenceStep = initiateStars(6-deductedScore, sequenceStep);
			   }
			  }
	}

	public static Image getVerticalLinesImage() {
		return verticalLinesImage;
	}
	
	public static Image getHorizontalLineImage() {
		return horizontalLineImage;
	}
	
	public static Image getTransparentPVPanelImage() {
	    return TransparentPVPanelImage;
	  }
	
	public static Rectangle getBatteryBankAreaBounds()
	{
		return batteryBankArea.getBounds();
	}
	
	public void changeCurrentCapacity(int capacity)
	{
		currentCapacity.setText(capacity+CapacityUnit);
	}
	
	public void changeCurrentCapacity(String capacity)
	{
		currentCapacity.setText(capacity+CapacityUnit);
	}
	
	public void changeCurrentVoltage(int voltage)
	{
		currentVoltage.setText(voltage+VoltageUnit);
	}
	
	public void changeCurrentVoltage(String voltage)
	{
		currentVoltage.setText(voltage+VoltageUnit);
	}
	
	public static int getRequiredCapacity()
	{
		return RequiredCapacity;
	}
	
	public static int getRequiredVoltage()
	{
		return RequiredVoltage;
	}
	
	private void showNextHintText()
	{
		if(!Battery.allParallelsHaveSameVoltage())
		{
			hintBox.setText(UncalculatableVoltageHint);
			hintBox.setFontColor(Color.white);
			if(parallelHintNOtDisplayed)
			{
				if(!CompletedGame)
				{
					totalNumberOfHintsUsed++;
					// TODO fix crash (initialize taskData)
					Game.getCurrentTask().getCurrentAttempt().addHints(1);
					if(deductedScore < 5)
						deductedScore++;
				}
				parallelHintNOtDisplayed = false;
			}
		}
		else if(!Battery.allSeriesHaveSameCapacity())
		{
			hintBox.setText(UncalculatableCapacityHint);
			hintBox.setFontColor(Color.white);
			if(seriesHintNOtDisplayed)
			{
				if(!CompletedGame)
				{
					totalNumberOfHintsUsed++;
					// TODO fix crash (initialize taskData)
					Game.getCurrentTask().getCurrentAttempt().addHints(1);
					if(deductedScore < 5)
						deductedScore++;
				}
				seriesHintNOtDisplayed = false;
			}
		}
		else
		{
			hintBox.setText(hintsTextArray[currentHintText]);
			hintBox.setFontColor(Color.white);
			if(firstRoundOfHints && !CompletedGame)
			{
				totalNumberOfHintsUsed++;
				// TODO fix crash (initialize taskData)
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				if(deductedScore < 5)
					deductedScore++;
			}
			if(currentHintText == (hintsTextArray.length-1))
			{
				currentHintText = 0;
				firstRoundOfHints = false;
			}
			else
				currentHintText++;
		}
	}
	
	private void showNextHintText(String doneButtonMessage)
	{
		if(!Battery.allParallelsHaveSameVoltage())
		{
			hintBox.setText(doneButtonMessage+UncalculatableVoltageHint);
			hintBox.setFontColor(Color.white);
			if(parallelHintNOtDisplayed)
			{
				if(!CompletedGame)
				{
					totalNumberOfHintsUsed++;
					// TODO fix crash (initialize taskData)
					Game.getCurrentTask().getCurrentAttempt().addHints(1);
					if(deductedScore < 5)
						deductedScore++;
				}
				parallelHintNOtDisplayed = false;
			}
		}
		else if(!Battery.allSeriesHaveSameCapacity())
		{
			hintBox.setText(doneButtonMessage+UncalculatableCapacityHint);
			hintBox.setFontColor(Color.white);
			if(seriesHintNOtDisplayed)
			{
				if(!CompletedGame)
				{
					totalNumberOfHintsUsed++;
					// TODO fix crash (initialize taskData)
					Game.getCurrentTask().getCurrentAttempt().addHints(1);
					if(deductedScore < 5)
						deductedScore++;
				}
				seriesHintNOtDisplayed = false;
			}
		}
		else
		{
			hintBox.setText(doneButtonMessage+hintsTextArray[currentHintText]);
			hintBox.setFontColor(Color.white);
			if(firstRoundOfHints && !CompletedGame)
			{
				totalNumberOfHintsUsed++;
				// TODO fix crash (initialize taskData)
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				if(deductedScore < 5)
					deductedScore++;
			}
			if(currentHintText == (hintsTextArray.length-1))
			{
				currentHintText = 0;
				firstRoundOfHints = false;
			}
			else
				currentHintText++;
		}
	}
	
	private void initializeText()
	{
		Rectangle textLocation = new Rectangle(145, 15, 400, 30);
		TextField requiredCapacityText = new TextField(textLocation, 0.95f,
				RequiredOutputLabel+RequiredCapacity+CapacityUnit,
				TextDisplay.FormattingOption.FIT_TEXT);
		requiredCapacityText.setFontColor(Color.white);
		this.addComponent(requiredCapacityText);
		
		Rectangle textLocation2 = new Rectangle(145, 45, 400, 30);
		TextField requiredVoltageText = new TextField(textLocation2, 0.95f,
				RequiredVoltageLabel+RequiredVoltage+VoltageUnit,
				TextDisplay.FormattingOption.FIT_TEXT);
		requiredVoltageText.setFontColor(Color.white);
		this.addComponent(requiredVoltageText);
		
		Rectangle textLocation3 = new Rectangle(30, 450, 275, 30);
		TextField currentCapacityText = new TextField(textLocation3, 0.95f,
				CurrentOutputLabel,
				TextDisplay.FormattingOption.FIT_TEXT);
		currentCapacityText.setFontColor(Color.darkGray);
		this.addComponent(currentCapacityText);
		
		Rectangle textLocation4 = new Rectangle(310, 450, 275, 30);
		TextField currentVoltageText = new TextField(textLocation4, 0.95f,
				CurrentVoltageLabel,
				TextDisplay.FormattingOption.FIT_TEXT);
		currentVoltageText.setFontColor(Color.darkGray);
		this.addComponent(currentVoltageText);
		
		Rectangle textLocation7 = new Rectangle(153, 450, 275, 30);
	    currentCapacity = new TextField(textLocation7, 0.95f,
	        0+CapacityUnit,
	        TextDisplay.FormattingOption.FIT_TEXT);
	    currentCapacity.setFontColor(Color.red);
	    this.addComponent(currentCapacity);
	    
	    Rectangle textLocation8 = new Rectangle(515, 450, 275, 30);
	    currentVoltage = new TextField(textLocation8, 0.95f,
	        0+VoltageUnit,
	        TextDisplay.FormattingOption.FIT_TEXT);
	    currentVoltage.setFontColor(Color.red);
	    this.addComponent(currentVoltage);

		
		for(int index = 0; index<initialBatteries.size(); index++)
		{
			InitialBattery addInitialBattery = initialBatteries.get(index);
			Rectangle textLocation5 = new Rectangle((115+(index*150)), addInitialBattery.getBounds().y+5, 80, 30);
			TextField battery1Capacity = new TextField(textLocation5, 0.95f,
					addInitialBattery.getCapacity()+CapacityUnit,
					TextDisplay.FormattingOption.FIT_TEXT);
			battery1Capacity.setFontColor(Color.white);
			this.addComponent(battery1Capacity);
			
			Rectangle textLocation6 = new Rectangle((115+(index*150)), addInitialBattery.getBounds().y+30, 80, 30);
			TextField battery1Voltage = new TextField(textLocation6, 0.95f,
					addInitialBattery.getVoltage()+VoltageUnit,
					TextDisplay.FormattingOption.FIT_TEXT);
			battery1Voltage.setFontColor(Color.white);
			this.addComponent(battery1Voltage);
		}
		instructionBox.setText(BatteryBankStartLabel);
	}
	
	public static void changeBatteryBankText()
	  {
	    batteryBankText.setText(BatteryBankLabel);
	  }
	
	public class HintsButtonListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			showNextHintText();
		}
	}
	
	public class DoneButtonListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			if(Battery.isGameOver())
			{
				hintBox.setText(CorrectAnswerCongratulation+" "+CompletingGameMessage+" "+CorrectAnswerMessage);
						
				PVExit.passEndGameMessage(CorrectAnswerCongratulation,
						CompletingGameMessage,
						CorrectAnswerMessage, Color.black);	
				CompletedGame = true;
				// TODO fix crash
				trackTime = false;
			}
			else
			{
				if(!CompletedGame)
				{
					doneButtonCounter++;
				}
				showNextHintText(IncorrectAnswerMessage);
			}
			
			if(doneButtonCounter >= maxChances)
			{
				hintBox.setText(GameAnswer);
				hintBox.setFontColor(Color.white);
				while(deductedScore < 6)
				{
					// TODO fix crash (initialize taskData)
					Game.getCurrentTask().getCurrentAttempt().addHints(1);
					deductedScore++;
				}
			}
		}
	}
	
	public class ContinueButtonListener extends ButtonListener
	{

		@Override
		protected void actionPerformed() {
			if(CompletedGame)
			{
				try {
					// TODO generates error, needs to be fixed
					//starDisplay.setScore(6-deductedScore);
					Game.updateExitText("Good Job!", "You have successfully completed the PV Array Sizing Game", new Image(END_BACKGROUND));
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TaskScreen task = (TaskScreen)Game.getCurrentGame().getState(Game.getStateID(TaskScreen.class));
			    if (task.currentImage < 4){
			     try {
			      task.setBackgroundImage(new Image(TASK_SCREEN_BACKGROUND));
			     } catch (SlickException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
			     }
			     task.currentImage = 4;
			    }
				reset();
				if (MainMenu.UserData.size() <16){
					MainMenu.UserData.add("Size the PV Array");
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
					//s = MainMenu.UserData.get(0) + "'s data(minigame, hints used, time spent): ";
					s += MainMenu.UserData.get(13);
					s += ", ";
					s += MainMenu.UserData.get(14);
					s += ", ";
					s += MainMenu.UserData.get(15);
					s += "; ";
				
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
	
	public void reset()
	{
		hintBox.setText("");
		batteryBankText.setText(BatteryBankLabel);
		currentHintText = 0;
		doneButtonCounter = 0;
		deductedScore = 0;
		Battery.reset();
		CompletedGame = false;
		resetButtons();
	}
	
	public void onEnter()
	 {
		// TODO fix crash
		trackTime = true;
	 }
	public void onExit()
	 {
		// TODO fix crash
		trackTime = false;
	 }
}
