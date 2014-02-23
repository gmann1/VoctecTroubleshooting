package edu.asu.voctec.minigames.controller_sizing;

import java.awt.Rectangle;
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
import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.GameDefaults.ImagePaths;
import edu.asu.voctec.game_states.GameTemplate;
import edu.asu.voctec.minigames.cdmg.CDPart1;

public class ControllerSizingPart3 extends GameTemplate{

	public static final String GraySquare = "resources/default/img/minigames/BatterySizing/BatteryBank.png";
	public static final String PVArraysPath = "resources/default/img/minigames/ControllerSizing/PVArrays.png";
	public static final String BatteriesPath = "resources/default/img/minigames/ControllerSizing/Batteries.png";
	public static final String ChargeControllerPath = "resources/default/img/minigames/ControllerSizing/ChargeController.png";
	public static final String LoadsPath = "resources/default/img/minigames/ControllerSizing/Loads.png";
	public static final String DisconnectSwitchPath = "resources/default/img/minigames/ControllerSizing/DisconnectSwitch.png";
	public static final String Trash = "resources/default/img/minigames/BatterySizing/GarbageBin.png";
	
	public static final String TransparentObjects = "resources/default/img/minigames/ControllerSizing/TransparentObject.png";
	private static Image TransparentObjectImage;
	
	private static final String[] hintsTextArray = {"You only need 3 disconnect switches for the Installation.",
													"You only need one of each of the other system parts in order to complete the Installation."};

	
	public static final String PVArraysLabel = "PV Arrays";
	public static final String BatteriesLabel = "Batteries";
	public static final String ChargeControllerLabel = "Charge Controller";
	public static final String LoadsLabel = "Loads";
	public static final String DisconnectSwitchLabel = "Disconnect Switch";
	public static final String Instructions = "Drag any of the system parts to the gray area in order to start installing the system";
	public static final String ConstantText = "System Installation Step";
	public static final String CompletingGameMessage = "Well Done. Now you know how the system parts are connected through the controller. Press continue when you are ready to move on.";
	public static final String GameAnswer = "You need to connect a pv array, a battery, and the loads to the charge controller using the disconnect switch";
	
	public static List<PartControl> objectsArray = new ArrayList<PartControl>();
	private List<InitialPart> initialBatteries = new ArrayList<InitialPart>();
	private boolean stepCompleted = false, firstRoundOfHints = true;
	private boolean nextState;
	private int lc;
	private static BasicComponent installationArea;
	private static int currentHintText = 0;
	public static int totalNumberOfHintsUsed = 0, doneButtonCounter = 0;
	private static final int maxChances = 3;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		super.init(container,game);
		
		installationArea = new BasicComponent(new Image(GraySquare),20,70);
		this.addComponent(installationArea);
		
		topText.setText(ConstantText);
		this.addComponent(topText);

		TransparentObjectImage = new Image(TransparentObjects);
		BasicComponent transparentObject = new BasicComponent(TransparentObjectImage, Part.xBatteryOffset, Part.yBatteryOffset);
		Part.addToTransparentBatteriesArray(transparentObject);
		this.addComponent(transparentObject);
		
		initialBatteries.add(new InitialPart(PVArraysLabel, 20, 460, new Image(PVArraysPath), 20, 460, this));
		initialBatteries.add(new InitialPart(BatteriesLabel, 120, 460, new Image(BatteriesPath), 120, 460, this));
		initialBatteries.add(new InitialPart(ChargeControllerLabel, 220, 460, new Image(ChargeControllerPath), 220, 460, this));
		initialBatteries.add(new InitialPart(LoadsLabel, 320, 460, new Image(LoadsPath), 320, 460, this));
		initialBatteries.add(new InitialPart(DisconnectSwitchLabel, 420, 460, new Image(DisconnectSwitchPath), 420, 460, this));
		
		for(InitialPart addInitialBattery :initialBatteries)
		{
			addObject(addInitialBattery);
		}
		
		initializeText();
		
		BasicComponent GarbageBin = new BasicComponent(new Image(Trash),540,480);
		this.addComponent(GarbageBin);
		
		backButton.addActionListener(new TransitionButtonListener(ControllerSizingPart2.class));
		readyButton.addActionListener(new DoneButtonListener());
		hintButton.addActionListener(new HintsButtonListener());
		continueButton.addActionListener(new ContinueButtonListener());
	}
	
	public void addObject(PartControl object)
	{
		objectsArray.add(object);
		this.addComponent(object);
	}
	
	public void removeObject(PartControl object)
	{
		objectsArray.remove(object);
		this.removeComponent(object);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException
	{
		super.update(container,game,delta);
		
			//Game.getCurrentGame();
				super.update(container, game, delta);
				if (!nextState){
					++lc;
					if (lc == 5){
					try {
				
						Game.getCurrentGame().addState(new ControllerSizingPart1(), Game.getCurrentGame().getContainer());
				
					
					} catch (SlickException e) {
			
						e.printStackTrace();
					}
				nextState = true;
					}
			}
				
		
		for(int index =0; index<objectsArray.size(); index++)
		{
			PartControl invokedObject = objectsArray.get(index);
			invokedObject.update();
		}
		
		if(stepCompleted){
			   if (sequenceStep != 4000){
			   sequenceStep = initiateStars(6-(2*totalNumberOfHintsUsed), sequenceStep);
			   }
			  }
	}
	
	public static Image getTransparentBatteryImage() {
		return TransparentObjectImage;
	}
	
	public static Rectangle getBatteryBankAreaBounds()
	{
		return installationArea.getBounds();
	}
	
	private void initializeText()
	{
		for(int index = 0; index<initialBatteries.size(); index++)
		{
			InitialPart addInitialBattery = initialBatteries.get(index);
			Rectangle textLocation5 = new Rectangle(addInitialBattery.getBounds().x-120, addInitialBattery.getBounds().y+65, 300, 20);
			TextField battery1Capacity = new TextField(textLocation5, 0.95f,
					addInitialBattery.getLabel(),
					TextDisplay.FormattingOption.FIT_TEXT);
			battery1Capacity.setFontColor(Color.white);
			battery1Capacity.center();
			this.addComponent(battery1Capacity);
		}
		instructionBox.setText(Instructions);
	}
	
	private void showNextHintText()
	{
		hintBox.setText(hintsTextArray[currentHintText]);
		if(firstRoundOfHints && !stepCompleted)
		{
			totalNumberOfHintsUsed++;
			Game.getCurrentTask().getCurrentAttempt().addHints(1);
		}
		if(currentHintText == (hintsTextArray.length-1))
		{
			currentHintText = 0;
			firstRoundOfHints = false;
		}
		else
			currentHintText++;
	}
	
	public boolean gameOver()
	{
		ArrayList<Part> testedBatteryArray;
        Part testedPart, testedPart1, testedPart2, testedPart3;
		if(Part.batteryArray.size() == 5)
		{
			testedBatteryArray = Part.batteryArray.get(0);
			testedPart1 = testedBatteryArray.get(0);
			if(testedBatteryArray.size() == 1)
			{
				testedBatteryArray = Part.batteryArray.get(1);
				testedPart = testedBatteryArray.get(0);
				if(testedBatteryArray.size() == 1 && testedPart.getLabel().equalsIgnoreCase(DisconnectSwitchLabel))
				{
					testedBatteryArray = Part.batteryArray.get(2);
					if(testedBatteryArray.size()>= 1)
					{
						if(testedBatteryArray.size()>= 2)
						{
							if(testedBatteryArray.size()>= 3)
							{
								testedPart3 = testedBatteryArray.get(0);
								testedPart = testedBatteryArray.get(1);
								testedPart2 = testedBatteryArray.get(2);
								if(testedBatteryArray.size() == 3 && testedPart.getLabel().equalsIgnoreCase(DisconnectSwitchLabel) && testedPart3.getLabel().equalsIgnoreCase(ChargeControllerLabel))
								{
									testedBatteryArray = Part.batteryArray.get(3);
									testedPart = testedBatteryArray.get(0);
									if(testedBatteryArray.size() == 1 && testedPart.getLabel().equalsIgnoreCase(DisconnectSwitchLabel))
									{
										testedBatteryArray = Part.batteryArray.get(4);
										testedPart3 = testedBatteryArray.get(0);
										if(testedBatteryArray.size() == 1)
										{
											if(correctParts(testedPart1,testedPart2,testedPart3))
												return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean correctParts(Part testedPart1, Part testedPart2, Part testedPart3)
	{
		if(acceptablePart(testedPart1) && acceptablePart(testedPart2) && acceptablePart(testedPart3))
		{
			if(differentParts(testedPart1, testedPart2))
			{
				if(differentParts(testedPart1, testedPart3) && differentParts(testedPart2, testedPart3))
					return true;
			}
		}
		return false;
	}
	
	private boolean acceptablePart(Part testedPart)
	{
		return(testedPart.getLabel().equalsIgnoreCase(PVArraysLabel)
				|| testedPart.getLabel().equalsIgnoreCase(LoadsLabel)
				|| testedPart.getLabel().equalsIgnoreCase(BatteriesLabel));
	}
	
	private boolean differentParts(Part testedPart1, Part testedPart2)
	{
		return(!(testedPart1.getLabel().equalsIgnoreCase(testedPart2.getLabel())));
	}
	
	public class DoneButtonListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			if(gameOver())
			{
				stepCompleted = true;
				trackTime = false;
				hintBox.setText(CompletingGameMessage);
				continueButtonOn();
				readyButtonOff();
			}
			else
			{
				doneButtonCounter++;
				showNextHintText();
			}
			
			if(doneButtonCounter >= maxChances)
			{
				hintBox.setText(GameAnswer);
				totalNumberOfHintsUsed++;
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
			}
			
		}
	}
	
	public class HintsButtonListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			showNextHintText();
		}
	}
	
	public class ContinueButtonListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			if(stepCompleted)
			{
				reset();
				Game.getCurrentGame().enterState(ControllerSizingPart1.class);
			}
		}
	}
	
	public void reset()
	{
		hintBox.setText("");
		currentHintText = 0;
		doneButtonCounter = 0;
		Part.reset();
		stepCompleted = false;
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
