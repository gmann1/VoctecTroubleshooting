package edu.asu.voctec.minigames.cdmg;

import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

public class CDPart3 extends GUI {
	
	private static final Color FONT_COLOR = Color.darkGray;
	
	public TextAreaX hintBox;
	public TextAreaX instructionBox;
	
	public BasicComponent sidePanel;
	public BasicComponent control;
	public BasicComponent readyBox;
	
	Button backButton;
	Button readyButton;
	Button hintButton;
	Button contButton;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.backgroundImage = new Image(ImagePaths.MainMenuBackground);
		intializeDefaults();
		
		
	}
	public void intializeDefaults() throws SlickException {
		// Initialize Side Panel
		
		sidePanel = new BasicComponent(ImagePaths.SIDE_PANEL, 592, 0);
		sidePanel.rescale(208, 433);
		sidePanel.setX(592);
		sidePanel.setY(0);
		
		//Initialize Ready Box
		
		readyBox = new BasicComponent(ImagePaths.READY_BOX, 592, 0);
		readyBox.rescale(208, 167);
		readyBox.setX(592);
		readyBox.setY(433);
		
		//Initialize Control
		control = new BasicComponent(ImagePaths.CONTROL_PANEL, 0, 167);
		control.rescale(592, 167);
		control.setX(0);
		control.setY(600-167);
		
		// Hint Box Initialization
		
		Rectangle hintBounds = new Rectangle(600, 208, 192, 192); 
		Rectangle relativeHintTextBounds = UtilFunctions.dialateRectangle(
				new Rectangle(0, 0, 188, 192), 0.92f);
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
		readyButton = new Button(readyButtonImage, sidePanel.getX() + sidePanel.getBounds().width/2 - UtilFunctions.getImageBounds(readyButtonImage).width/2, readyBox.getY() + 6, textBounds,null);
		

		// Hint Button
		Image hintButtonImage = (new Image(ImagePaths.HINT_BUTTON));
		textBounds = UtilFunctions.getImageBounds(hintButtonImage);
		hintButton = new Button(hintButtonImage, readyButton.getX() + 18,
				readyButton.getY() - textBounds.height - 5, textBounds,
				"HINT");
	
		hintButton.rescale(.8f, 1f);
		hintButton.setX(sidePanel.getX() + sidePanel.getBounds().width/2 - hintButton.getBounds().width/2);
		hintButton.setY(402);
		hintButton.getTextField().center();
		hintButton.setFontColor(FONT_COLOR);

		this.addComponent(sidePanel);
		this.addComponent(readyBox);
		this.addComponent(control);
		this.addComponent(hintBox);
		this.addComponent(instructionBox);
		this.addComponent(hintButton);
		this.addComponent(readyButton);
		this.addComponent(backButton);

	}

}