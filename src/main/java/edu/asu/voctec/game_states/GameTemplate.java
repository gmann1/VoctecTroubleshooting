package edu.asu.voctec.game_states;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.StarDisplay;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

public class GameTemplate extends GUI {
	
	protected int sequenceStep;

	
	protected TextAreaX hintBox;
	protected TextAreaX instructionBox;
	protected TextAreaX topText;
	
	protected int readyButtonX = 0;
	
	
	protected BasicComponent sidePanel;
	protected BasicComponent control;
	protected BasicComponent flare;
	
	protected Button backButton;
	protected Button readyButton;
	protected Button hintButton;
	protected Button contButton;
	protected Button continueButton;
	protected StarDisplay sDisplay;


	private Button readyButtonOff;
	
	public static final String STAR1 = "resources/default/img/gameTemplate/Star1.png";
	public static final String STAR2 = "resources/default/img/gameTemplate/Star2.png";
	public static final String STAR3 = "resources/default/img/gameTemplate/Star3.png";
	public static final String STAR4 = "resources/default/img/gameTemplate/Star4.png";
	public static final String STAR5 = "resources/default/img/gameTemplate/Star5.png";
	public static final String STAR6 = "resources/default/img/gameTemplate/Star6.png";
	public static final String STAR7 = "resources/default/img/gameTemplate/Star7.png";
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		intializeDefaults();
		
		
	}
	public void intializeDefaults() throws SlickException {
		
	    
		
		//initialize top text
		
		Rectangle textLocation = new Rectangle(135, 15, 410, 150);
		topText = new TextAreaX(textLocation, new Rectangle(0, 0, 410, 150),
				null);
		topText.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		topText.setFontSize(Fonts.FONT_SIZE_LARGE);
		
		// Initialize Side Panel
		
		sidePanel = new BasicComponent(ImagePaths.SIDE_PANEL, 592, 0);
		sidePanel.rescale(208, 600);
		sidePanel.setX(592);
		sidePanel.setY(0);

		
		//Initialize Control
		control = new BasicComponent(ImagePaths.CONTROL_PANEL, 0, 167);
		control.rescale(592, 167);
		control.setX(0);
		control.setY(600-167);
		
		// Hint Box Initialization
		
		Rectangle hintBounds = new Rectangle(600, 208, 192, 192); 
		Rectangle relativeHintTextBounds = UtilFunctions.dialateRectangle(
				new Rectangle(0, 0, 192, 192), 0.92f);
		hintBox = new TextAreaX(hintBounds, relativeHintTextBounds,
				null);

		Image hintBoxBackground = new Image(
				ImagePaths.HINT_BOX_TEMPLATE);
		
		hintBox.setCurrentImage(hintBoxBackground, true);
		
		//Instruction Box Initialization
		hintBounds = new Rectangle(600, 8, 192, 192);
		instructionBox = new TextAreaX(hintBounds, relativeHintTextBounds,
				null);
		instructionBox.setCurrentImage(hintBoxBackground, true);

		// Format hint box and Intsturction Box
		hintBox.setFontSize(Fonts.FONT_SIZE_MEDIUM);
		hintBox.setFontColor(Fonts.FONT_COLOR);
		
		instructionBox.setFontSize(Fonts.FONT_SIZE_MEDIUM);
		instructionBox.setFontColor(Fonts.FONT_COLOR);

		// Back Button
		backButton = new Button(new Image(ImagePaths.BACK_BUTTON), 5, 5,
				new Rectangle(0, 0, 50, 25), "Back");
	
		backButton.setFontColor(Fonts.TRANSITION_FONT_COLOR);
		backButton.positionText(Position.RIGHT);
		// Ready Button
		Image readyButtonImage = new Image(ImagePaths.READY_BUTTON);
		Rectangle textBounds = UtilFunctions.getImageBounds(readyButtonImage);
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.80f);
		readyButton = new Button(readyButtonImage, sidePanel.getX() + sidePanel.getBounds().width/2 - UtilFunctions.getImageBounds(readyButtonImage).width/2, hintBox.getY() + hintBox.getBounds().height + 50, textBounds,"Ready");
		readyButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		readyButtonX = readyButton.getX();
		readyButtonOff = new Button(new Image(ImagePaths.CONTINUE_BUTTON_OFF), sidePanel.getX() + sidePanel.getBounds().width/2 - UtilFunctions.getImageBounds(readyButtonImage).width/2, hintBox.getY() + hintBox.getBounds().height + 50, textBounds,"Ready");
		readyButtonOff.setFontColor(Fonts.DISABLED_BUTTON_FONT_COLOR);
		readyButtonOff.setX(800);
		//Continue Button
		Image continueButtonImage = new Image(ImagePaths.CONTINUE_BUTTON_OFF);
		textBounds = UtilFunctions.getImageBounds(continueButtonImage);
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.80f);
		continueButton = new Button(continueButtonImage, sidePanel.getX() + sidePanel.getBounds().width/2 - UtilFunctions.getImageBounds(readyButtonImage).width/2, readyButton.getY() + 73, textBounds,"Continue");
		continueButton.setFontColor(Fonts.DISABLED_BUTTON_FONT_COLOR);
		// Hint Button
		Image hintButtonImage = (new Image(ImagePaths.HINT_BUTTON));
		textBounds = UtilFunctions.getImageBounds(hintButtonImage);
		textBounds = UtilFunctions.dialateRectangle(textBounds, 0.75f);
		textBounds.y = textBounds.y - 2;
		hintButton = new Button(hintButtonImage, readyButton.getX() + 18,
				readyButton.getY() - textBounds.height - 5, textBounds,
				"Click for Hint");
	
	
		hintButton.setX(sidePanel.getX() + sidePanel.getBounds().width/2 - hintButton.getBounds().width/2);
		hintButton.setY(401);
		hintButton.getTextField().center();
		hintButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		 
		this.addComponent(sidePanel);

		this.addComponent(control);
		this.addComponent(hintBox);
		this.addComponent(instructionBox);
		this.addComponent(hintButton);
		this.addComponent(readyButton);
		this.addComponent(backButton);
		this.addComponent(continueButton);
		this.addComponent(readyButtonOff);

	}
	
	
	
	public void continueButtonOn() {
		continueButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		try {
			continueButton.setCurrentImage(new Image(ImagePaths.CONTINUE_BUTTON_ON), true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void continueButtonOff() {
		continueButton.setFontColor(Fonts.DISABLED_BUTTON_FONT_COLOR);
		try {
			continueButton.setCurrentImage(new Image(ImagePaths.CONTINUE_BUTTON_OFF), true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readyButtonOff(){
		readyButtonOff.setX(readyButtonX);
		readyButton.setX(800);
	}
	public void readyButtonOn(){
		readyButton.setX(readyButtonX);
		readyButtonOff.setX(800);
	}
	
	public void resetButtons() {
		continueButtonOff();
		readyButtonOn();
		if (sDisplay != null){
		removeComponent(sDisplay);
		}
		sequenceStep = 0;
	}
	
	public int initiateStars(int starCount, int sequenceCount) throws SlickException{
		if (sDisplay != null){
			this.removeComponent(sDisplay);
			}
		if (sequenceCount < 50){
			sDisplay = new StarDisplay(starCount, 400, 400);
			sDisplay.rescale(.54f);
			sDisplay.setX(hintBox.getX());
			sDisplay.setY(hintBox.getBounds().height + hintBox.getY() -35 -sDisplay.getBounds().height/2);
			if(sequenceCount == 0){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR1), 400,400);
			
			}
			if(sequenceCount == 3){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR2), 400,400);
			
			}
			if(sequenceCount == 6){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR3), 400,400);
			
			}
			if(sequenceCount == 9){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR4), 400,400);
			
			}
			if(sequenceCount == 12){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR5), 400,400);
			
			}
			if(sequenceCount == 15){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR6), 400,400);
				
			}
			if(sequenceCount == 18){
				if(flare!=null){
					this.removeComponent(flare);
				}
				flare = new BasicComponent(new Image(STAR7), 400,400);
				
			}
			if(sequenceCount == 21){
				if(flare!=null){
					
					this.removeComponent(flare);
					
					
					
				}
			}
			if (flare!=null ){
				flare.rescale(sDisplay.getBounds().width, sDisplay.getBounds().height+8);
				flare.setX(sDisplay.getX());
				flare.setY(sDisplay.getY());
			}
			if(sequenceCount <21 && sequenceCount%3==0){
			this.addComponent(flare);
			}
			this.addComponent(sDisplay);
			
		}
		if (sequenceCount >= 50){
			
			double calculation;
			sDisplay = new StarDisplay(starCount, 400, 400);
			
			sDisplay.rescale(.5f - (float).008*(sequenceCount-55));
		
				sDisplay.setY(hintBox.getBounds().height + hintBox.getY() -35 -sDisplay.getBounds().height/2);
			
			calculation = hintBox.getX() + (sequenceCount - 50)*58/40;

			sDisplay.setX((int)calculation);
			this.addComponent(sDisplay);
			if (sequenceCount == 90){
				sequenceCount = 3999;
			}
		}
		++sequenceCount;
		return sequenceCount;
		
	}

}
