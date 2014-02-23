package edu.asu.voctec.minigames.cdmg;

import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.java.games.input.Mouse;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.Component;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.ExitScreen;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.game_states.GameTemplate;
import edu.asu.voctec.game_states.MainMenu;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.minigames.battery_sizing.BatteryIntro;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * 
 * @author Gabriel Mann
 * 
 */

public class CDPart2 extends GameTemplate {

	private boolean correctAnswer = false;

	public static final float SMALL_FONT_SIZE = 8f;
	public static final float MEDIUM_FONT_SIZE = 12f;
	public static final float LARGE_FONT_SIZE = 18f;

	public static final Color FONT_COLOR = Color.black;
	private static final Color FONT_COLOR1 = Color.white;
	private int score = 6;
	private int hintCount = 0;

	public static final String BACKGROUND = "resources/default/img/minigames/criticalDesign/Part2back.png";
	public static final String END_BACKGROUND = "resources/default/img/scoreScreenBackgrounds/ScoreBackgroundTask2.png";
	public static final String CRITICAL_MONTH_IMAGE = "resources/default/img/minigames/criticalDesign/criticalMonth1.png";
	public static final String PANEL = "resources/default/img/minigames/criticalDesign/Panel.png";
	public static final String POLE = "resources/default/img/minigames/criticalDesign/Pole.png";
	public static final String ROTATE_ARROW = "resources/default/img/minigames/criticalDesign/rotateArrow.png";
	public static final String UP_ARROW_OFF = "resources/default/img/minigames/criticalDesign/upArrowOff.png";
	public static final String UP_ARROW_ON = "resources/default/img/minigames/criticalDesign/upArrowOn.png";
	public static final String DOWN_ARROW_OFF = "resources/default/img/minigames/criticalDesign/downArrowOff.png";
	public static final String DOWN_ARROW_ON = "resources/default/img/minigames/criticalDesign/downArrowOn.png";
	public static final String COMPASS = "resources/default/img/minigames/criticalDesign/Compass.png";
	public static final String NEEDLE = "resources/default/img/minigames/criticalDesign/Needle.png";
	public static final String TASK_SCREEN_BACKGROUND = "resources/default/img/taskScreenBackgrounds/background2.png";
	


	
	public TextField angle;
	public String s;
	public Image panel1;

	private boolean south = true;
	private boolean arrowChange = false;
	BasicComponent upArrow;
	BasicComponent downArrow;

	ArrayList<String> genericHints = new ArrayList<>();

	private Button rotateArrow;

	private Image pole1;

	private BasicComponent pole;

	private BasicComponent panel;

	private boolean left = false;

	private BasicComponent poleLeft;

	private BasicComponent panelLeft;

	private Image panel2;

	private Image needle1;

	private BasicComponent needle;

	private BasicComponent criticalMonth;

	private boolean nextState;

	private int lc;

	


	
	public class RotateListener extends ButtonListener {

		@Override
		protected void actionPerformed() {
			
			rotate();
			south = !south;
		}

	}
	public class CDReadyListener extends ButtonListener {

		@Override
		protected void actionPerformed() {
			
			try {
				winConditional();
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	public class CDContinueListener extends ButtonListener {

		@Override
		protected void actionPerformed() {
			if (correctAnswer){
				
				TaskScreen task = (TaskScreen)Game.getCurrentGame().getState(Game.getStateID(TaskScreen.class));
				if (task.currentImage < 2){
					try {
						task.setBackgroundImage(new Image(TASK_SCREEN_BACKGROUND));
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					task.currentImage = 2;
				}
				try {
					Game.updateExitText("Good Job!", "You have successfully completed both parts of the Critical Design Month game", new Image(END_BACKGROUND));
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (MainMenu.UserData.size() <10){
					MainMenu.UserData.add("Find the Critical Design Month");
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
					s += MainMenu.UserData.get(7);
					s += ", ";
					s += MainMenu.UserData.get(8);
					s += ", ";
					s += MainMenu.UserData.get(9);
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

	public class CDHintListener extends ButtonListener {

		@Override
		protected void actionPerformed() {
			displayHint(hintCount, false);

		}

	}
//comment
//add a comment
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		instructionBox.setText("Determine the tilt and orientation. Click the arrows to adjust tilt and orientation of the solar panel for the critical design month.");
		this.backgroundImage = new Image(BACKGROUND);
		topText.setText("Rarotonga, Cook Islands. "
				+ "Latitude: 12.2 degrees S, Longitude: 159.8 degrees W");
		BasicComponent compass = new BasicComponent(new Image(COMPASS), 205 + 248 -35, 516 - 59); 
		needle1 = new Image(NEEDLE);
		needle1.setCenterOfRotation(4, 22);
		needle1.rotate(180f);
		needle = new BasicComponent(needle1, 232 + 248 -35, 545 - 59);
		
		
		
		criticalMonth = new BasicComponent(new Image(
				CRITICAL_MONTH_IMAGE), hintBox.getX(), hintBox.getY());
		criticalMonth.rescale(instructionBox.getBounds().width, instructionBox.getBounds().height);
		criticalMonth.setX(hintBox.getX());
		criticalMonth.setY(hintBox.getY());
	
	
		
		
		pole1 = new Image(POLE);
		pole = new BasicComponent(pole1, 300, 300);
		pole.rescale(.6f);
		pole.rescale(.75f, 1.25f);
		pole.setX(289-65);
		pole.setY(277);
		
		pole1 = pole1.getFlippedCopy(true, false);
		poleLeft = new BasicComponent(pole1, 300,300);
		poleLeft.rescale(.6f);
		poleLeft.rescale(.75f, 1.25f);
		poleLeft.setX(800);
		poleLeft.setY(600);
		

		panel1 = new Image(PANEL);

		
		panel1 = panel1.getScaledCopy(211, 50);
		panel = new BasicComponent(panel1, 50, 50);
	
		panel1.setCenterOfRotation(59, 290-254);
		panel.setX(257-75);
		panel.setY(254);
		
		panel2 = panel1.getFlippedCopy(true, false);
		panelLeft = new BasicComponent(panel2, 50, 50);
	
		panel2.setCenterOfRotation(286-136, 290-254);
		panelLeft.setX(800);
		panelLeft.setY(600);
		
		TextField orientation = new TextField(new Rectangle(322-35, 462, 500, 500), 0.95f,
				"Orientation:                    ", TextDisplay.FormattingOption.CLIP_TEXT);
		orientation.setFontSize(LARGE_FONT_SIZE);
		orientation.setFontColor(FONT_COLOR);
		

		s = String.format("%d", (int) (panel1.getRotation()));
		angle = new TextField(new Rectangle(322-35, 526, 500, 500), 0.95f,
				"Angle:           " + s + "°", TextDisplay.FormattingOption.CLIP_TEXT);
		angle.setFontSize(LARGE_FONT_SIZE);
		angle.setFontColor(FONT_COLOR);
		
		//arrows
		
		rotateArrow = new Button(new Image(ROTATE_ARROW), 158-100+10, 486, new Rectangle(0,0,50,50), null);
		rotateArrow.addActionListener(new RotateListener());
		
 Image arrow = new Image(UP_ARROW_OFF);
		
		upArrow = new BasicComponent(arrow, 300, 300);
	
		upArrow.setX(rotateArrow.getX() + rotateArrow.getBounds().width + 5);
		upArrow.setY(476);
		
		
		Image arrow1 = new Image(DOWN_ARROW_OFF);
		
		downArrow = new BasicComponent(arrow1, 300, 300);
		
		downArrow.setX(rotateArrow.getX() - 5 - downArrow.getBounds().width);
		downArrow.setY(476);
	
		// BasicComponent dot = new BasicComponent(new
		// Image("resources/default/img/minigames/criticalDesign/dot.png"),
		// panel.getX() + 11,panel.getY() + 30);

		genericHints
				.add("In the Southern Hemisphere, point the PV array toward true North and in the Northern Hemisphere towards true South. ");
		genericHints
				.add("Best tilt angle for all year is latitude.");
		genericHints
		.add("Best tilt angle for winter conditions is latitude plus 15 degrees. ");
genericHints
		.add("Best tilt angle for summer conditions is latitude minus 15 degrees.");
	

		this.addComponent(criticalMonth);
		this.addComponent(pole);
		this.addComponent(panel);
		this.addComponent(angle);
		this.addComponent(upArrow);
		this.addComponent(downArrow);
		this.addComponent(rotateArrow);

		this.addComponent(poleLeft);
		this.addComponent(panelLeft);
		this.addComponent(compass);
		this.addComponent(needle);
		this.addComponent(topText);
		this.addComponent(orientation);
		
		hintButton.addActionListener(new CDHintListener());
		readyButton.addActionListener(new CDReadyListener());
		continueButton.addActionListener(new CDContinueListener());
		backButton.addActionListener(new TransitionButtonListener(
				CDPart1.class));

		
		/*
		BasicComponent dot = new BasicComponent(new Image("resources/default/img/minigames/criticalDesign/sunBeam.png"), 100,100);
		dot.rescale(.3f);
		dot.setX(152);
		dot.setY(382);
		
		this.addComponent(dot);
		*/

	}

	private void displayHint(int hCount, boolean readyClick) {
		if (hCount != 4 || readyClick){
			criticalMonth.setX(800);
		}
		if (hCount == 0) {
			if (readyClick){
			hintBox.setText("Sorry that is incorrect. " + genericHints.get(0));
			}
			else
			{
				hintBox.setText(genericHints.get(0));
			}
			++hintCount;
			if (!correctAnswer) {
				score -= 1;
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				++CDPart1.hints;
			}
			System.out.println("Generic Hint1 shown, total hints: "
					+ CDPart1.hints);
		}
		if (hCount == 1) {
			if (readyClick){
				hintBox.setText("Sorry that is incorrect. " + genericHints.get(1));
				}
				else
				{
					hintBox.setText(genericHints.get(1));
				}
			hintCount = 4;
			if (!correctAnswer) {
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				score -=1;
				++CDPart1.hints;
			}
			System.out.println("Generic Hint2 shown, total hints: "
					+ CDPart1.hints);
		}
		if (hCount > 1) {
			Game.getCurrentTask().getCurrentAttempt().addHints(1);
			score -= 1;
			if (hCount == 2) {
				if (readyClick){
					hintBox.setText("Sorry that is incorrect. " + genericHints.get(2));
					}
					else
					{
						hintBox.setText(genericHints.get(0));
					}
			} else if (hCount == 3) {
				if (readyClick){
					hintBox.setText("Sorry that is incorrect. " + genericHints.get(3));
					}
					else
					{
						hintBox.setText(genericHints.get(1));
					}
			}
			else{
				if (!readyClick){
				criticalMonth.setX(hintBox.getX());
				hintBox.setText("");
				}
				
			}
			++hintCount;
			if (hintCount == 5){
				hintCount = 0;
			}
			System.out
					.println("User requested more hints, none to give. total hints: "
							+ CDPart1.hints);

		}

	}

	private void winConditional() throws SlickException {
		if ((panel1.getRotation() > (12. - 5) && !south)
				&& (panel1.getRotation() < (12.2 + 5))) {
			if ((int) ((panel1.getRotation()) * 10) == 122) {
				criticalMonth.setX(800);
				hintBox.setText("Great Job! You got the correct answer exactly. Press continue when you are ready to move on.");
			} else {
				criticalMonth.setX(800);
				hintBox.setText("Good job! However, the precise answer is 12.2 degrees (latitude). Press continue when you are ready to move on.");
			}
			continueButtonOn();
			readyButtonOff();
			correctAnswer = true;
		} else {
			displayHint(hintCount, true);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (!nextState){
			++lc;
			if (lc == 5){
			try {
		
				Game.getCurrentGame().addState(new BatteryIntro(), Game.getCurrentGame().getContainer());
		
			
			} catch (SlickException e) {
	
				e.printStackTrace();
			}
		nextState = true;
			}
	}
		if (correctAnswer){
			if (score < 0){
				score = 0;
			}
			if (sequenceStep != 4000){
				sequenceStep = initiateStars(score, sequenceStep);
				}
		}
		
			
		
		// container.getInput();
		
		int MouseX = container.getInput().getMouseX();
		int MouseY = container.getInput().getMouseY();
		if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			
			if (( MouseX >= upArrow.getX() && MouseX <= (upArrow.getX() + upArrow.getBounds().width) )   // check if X is within range
				   && ( MouseY >= upArrow.getY() && MouseY <= (upArrow.getY() + upArrow.getBounds().height)) ){
				arrowChange = true;
				upArrow.setCurrentImage(new Image(UP_ARROW_ON), true);
				if (panel1.getRotation()  > 0) {
					if (panel1.getRotation()  == 90f) {
						panel1.rotate(-.2f);
						
					}  else {
						panel1.rotate(-.2f);
						
					}

				}
				if (panel1.getRotation() <=0){
					panel1.setRotation(0f);
					
				}
				s = String.format("Angle:           %.1f°", (panel1.getRotation() ));
				angle.setText(s);
				panel2.setRotation(-panel1.getRotation());
			}
			else if(( MouseX >= downArrow.getX() && MouseX <= (downArrow.getX() + downArrow.getBounds().width) )   // check if X is within range
				   && ( MouseY >= downArrow.getY() && MouseY <= (downArrow.getY() + downArrow.getBounds().height)) ){
				arrowChange = true;
				downArrow.setCurrentImage(new Image(DOWN_ARROW_ON), true);
				if (panel1.getRotation()< 90) {
					if (panel1.getRotation() == 0f) {
						panel1.rotate(.2f);

					}  else {
						panel1.rotate(.2f);
					}
				}
				if (panel1.getRotation() >=90){
					panel1.setRotation(90f);
				}
				s = String.format("Angle:           %.1f°", (panel1.getRotation() ));
				angle.setText(s);
				panel2.setRotation(-panel1.getRotation());
			}
			
			else{
				if(arrowChange){
					arrowChange = false;
					upArrow.setCurrentImage(new Image(UP_ARROW_OFF), true);
					downArrow.setCurrentImage(new Image(DOWN_ARROW_OFF), true);
				}
			}
			
		}
		else{
			if(arrowChange){
				arrowChange = false;
				upArrow.setCurrentImage(new Image(UP_ARROW_OFF), true);
				downArrow.setCurrentImage(new Image(DOWN_ARROW_OFF), true);
			}
		}
		
		
		

	}
	
	public void rotate(){
		if (!left){
			left = true;
			needle1.rotate(180f);
			needle.setY(521 - 59);
			needle.setX(231 + 248-35);
			panelLeft.setX(134-65);
			panelLeft.setY(254);
			panel.setX(800);
			panel.setY(600);
			poleLeft.setX(pole.getX()-10);
			poleLeft.setY(pole.getY());
			pole.setX(800);
			pole.setY(600);
			
		}else{
			left = false;
			needle1.rotate(180f);
			needle.setY(539 - 59);
			needle.setX(232 + 248-35);
			panel.setX(257-65);
			panel.setY(254);
		
			panelLeft.setX(800);
			panelLeft.setY(600);
			pole.setX(289-65);
			pole.setY(277);
			poleLeft.setX(800);
			poleLeft.setY(600);
		}
		
	}
	public void onEnter()
	 {
		
		trackTime = true;
	 }
	

}
