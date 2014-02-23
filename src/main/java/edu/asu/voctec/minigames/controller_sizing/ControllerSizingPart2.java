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
import edu.asu.voctec.game_states.GameTemplate;
import edu.asu.voctec.minigames.cdmg.CDPart1;

public class ControllerSizingPart2 extends GameTemplate
{
	public static final String GameBackground = "resources/default/img/minigames/ControllerSizing/Step1Background.png";
	public static final String OriginalControllerPath = "resources/default/img/minigames/ControllerSizing/NormalController.png";
	public static final String ChosenControllerPath = "resources/default/img/minigames/ControllerSizing/ChosenController.png";
	public static final String IncorrectControllerPath = "resources/default/img/minigames/ControllerSizing/IncorrectController.png";
	public static final String CorrectControllerPath = "resources/default/img/minigames/ControllerSizing/CorrectController.png";
	
	public static final String instructionMessage = "Select the most cost-effective controller that best satisfy the minimum power current.";
	public static final String correctSolutionMessage = "This is the correct answer.\nPress the Continue Button to advance to the next step.";
	public static final String smallerControllerMessage = "This is not the correct choice.\nThis controller does not satisfy the minimum power current.";
	public static final String largerControllerMessage = "This is not the correct choice.\nThere is a more cost-effective controller.";
	
	private int[] controllersValues = {13,15,20};
	private int solution = 15;
	private double minimumPowerCurrent = 13.5;
	
	private List<BasicComponent> controllers = new ArrayList<BasicComponent>();
	private static BasicComponent chosenController;
	private Image OriginalControllerImage, ChosenControllerImage, IncorrectControllerImage, CorrectControllerImage, ContinueButtonImage;
	private boolean stepCompleted = false, largerControllerHintDisplayed = false, smallerControllerHintDisplayed = false;
	private int doneButtonCounter = 0;
	private boolean nextState;
	private int lc;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		super.init(container,game);
		
		OriginalControllerImage = new Image(OriginalControllerPath);
		ChosenControllerImage = new Image(ChosenControllerPath);
		IncorrectControllerImage = new Image(IncorrectControllerPath);
		CorrectControllerImage = new Image(CorrectControllerPath);
		
		
		
		controllers.add(new BasicComponent(OriginalControllerPath, 20, 180, 110, 100));
		controllers.add(new BasicComponent(OriginalControllerPath, 210, 180, 110, 100));
		controllers.add(new BasicComponent(OriginalControllerPath, 400, 180, 110, 100));
		
		for(BasicComponent controller :controllers)
		{
			controller.addActionListener(new ControllerListener());
			this.addComponent(controller);
		}
		
		initializeText();
		
		backButton.addActionListener(new TransitionButtonListener(ControllerSizingIntroScreen.class));
		readyButton.addActionListener(new DoneButtonListener());
		continueButton.addActionListener(new ContinueButtonListener());
		this.removeComponent(hintButton);
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
				
						Game.getCurrentGame().addState(new ControllerSizingPart3(), Game.getCurrentGame().getContainer());
				
					
					} catch (SlickException e) {
			
						e.printStackTrace();
					}
				nextState = true;
					}
			}
				
		
		if(stepCompleted){
			   if (sequenceStep != 4000){
			   sequenceStep = initiateStars(6-(3*doneButtonCounter), sequenceStep);
			   }
			  }
	}
	
	private void initializeText()
	{
		topText.setText("Minimum Power Current: "+minimumPowerCurrent+" A");
		this.addComponent(topText);
		
		for(int index = 0; index<controllers.size(); index++)
		{
			BasicComponent controller = controllers.get(index);
			
			Rectangle textLocation2 = new Rectangle( controller.getX()+20, controller.getY()+110, 60, 60);
			TextField controllerValue = new TextField(textLocation2, 0.95f,
					controllersValues[index]+" A",
					TextDisplay.FormattingOption.FIT_TEXT);
			controllerValue.setFontColor(Color.white);
			this.addComponent(controllerValue);
		}
		
		
				instructionBox.setText(instructionMessage);
	}
	
	public class ControllerListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			if(chosenController == associatedComponent)
			{
				chosenController.setCurrentImage(OriginalControllerImage, true);
				chosenController = null;
			}
			else
			{
				if(chosenController != null)
				{
					chosenController.setCurrentImage(OriginalControllerImage, true);
					chosenController = null;
				}
				chosenController = (BasicComponent) associatedComponent;
				chosenController.setCurrentImage(ChosenControllerImage, true);
			}
		}
	}
	
	public class DoneButtonListener extends ButtonListener
	{
		@Override
		protected void actionPerformed()
		{
			if(chosenController != null)
			{
				if(controllersValues[controllers.indexOf(chosenController)] == solution)
				{
					chosenController.setCurrentImage(CorrectControllerImage, true);
					hintBox.setText(correctSolutionMessage);
					hintBox.setFontColor(Color.white);
					stepCompleted = true;
					trackTime = false;
					continueButtonOn();
					readyButtonOff();
				}
				else
				{
					chosenController.setCurrentImage(IncorrectControllerImage, true);
					if(controllersValues[controllers.indexOf(chosenController)] > solution)
					{
						hintBox.setText(largerControllerMessage);
						hintBox.setFontColor(Color.white);
						if(!largerControllerHintDisplayed)
						{
							Game.getCurrentTask().getCurrentAttempt().addHints(1);
							largerControllerHintDisplayed = true;
						}
					}
					else
					{
						hintBox.setText(smallerControllerMessage);
						hintBox.setFontColor(Color.white);
						if(!smallerControllerHintDisplayed)
						{
							Game.getCurrentTask().getCurrentAttempt().addHints(1);
							smallerControllerHintDisplayed = true;
						}
					}
					doneButtonCounter++;
					System.out.println("Number of tries: "+doneButtonCounter);
				}
			}
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
				Game.getCurrentGame().enterState(ControllerSizingPart3.class);
			}
		}
	}
	
	public void reset()
	{
		hintBox.setText("");
		doneButtonCounter = 0;
		chosenController.setCurrentImage(OriginalControllerImage, true);
		chosenController = null;
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