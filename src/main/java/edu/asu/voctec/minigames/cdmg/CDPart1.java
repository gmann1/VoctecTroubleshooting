package edu.asu.voctec.minigames.cdmg;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GUI.BasicComponent;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GUI.TransitionButtonListener;
import edu.asu.voctec.game_states.GameTemplate;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * 
 * @author Gabriel Mann
 * 
 */

public class CDPart1 extends GameTemplate {

	private static int index = 0;
	public static int hints = 0;
	private int hintCount = 0;
	private int score = 0;

	private boolean correctAnswer = false;

	private static final Color FONT_COLOR = Color.darkGray;
	private static final Color FONT_COLOR1 = Color.white;

	public static final String APRIL = "resources/default/img/minigames/criticalDesign/February.png";
	public static final String FEBRUARY = "resources/default/img/minigames/criticalDesign/April.png";
	public static final String DECEMBER = "resources/default/img/minigames/criticalDesign/June.png";
	public static final String OCTOBER = "resources/default/img/minigames/criticalDesign/October.png";
	public static final String SEPTEMBER = "resources/default/img/minigames/criticalDesign/April.png";
	public static final String JUNE = "resources/default/img/minigames/criticalDesign/December.png";
	public static final String SPRING = "resources/default/img/minigames/criticalDesign/Spring.png";
	public static final String SUMMER = "resources/default/img/minigames/criticalDesign/Summer.png";
	public static final String WINTER = "resources/default/img/minigames/criticalDesign/Winter.png";
	public static final String FALL = "resources/default/img/minigames/criticalDesign/Fall.png";
	public static final String BACKGROUND = "resources/default/img/minigames/criticalDesign/space.jpg";
	public static final String BOX = "resources/default/img/selector/display/EmptyBox.png";
	public static final String MOUSE_OVER_BOX = "resources/default/img/minigames/criticalDesign/MouseOverBox.png";
	public static final String CORRECT_BOX = "resources/default/img/minigames/criticalDesign/CorrectBox.png";
	public static final String SELECTED_BOX = "resources/default/img/minigames/criticalDesign/SelectedBox.png";
	public static final String INCORRECT_BOX = "resources/default/img/minigames/criticalDesign/IncorrectBox.png";

	public static final float SMALL_FONT_SIZE = 8f;
	public static final float MEDIUM_FONT_SIZE = 12f;
	public static final float LARGE_FONT_SIZE = 18f;

	public static final int READY_BUTTON_X = 600;

	private static boolean ap = false;
	private static boolean fe = false;
	private static boolean oc = false;
	private static boolean se = false;
	private static boolean ju = false;
	
	private boolean box1Used = false;
	private boolean box3Used = false;
	private boolean box4Used = false;

	static ArrayList<String> Earths = new ArrayList<>();
	
	static ArrayList<String> February = new ArrayList<>();
	static ArrayList<String> December = new ArrayList<>();
	static ArrayList<String> October = new ArrayList<>();
	static ArrayList<String> September = new ArrayList<>();
	static ArrayList<String> June = new ArrayList<>();
	static ArrayList<ArrayList<String>> months = new ArrayList<>();

	static TextField main1;
	static TextField main2;
	static TextField main3;
	static TextField right1;
	static TextField right2;
	static TextField right3;
	static TextField left1;
	static TextField left2;
	static TextField left3;

	BasicComponent box1;
	BasicComponent box2;
	BasicComponent box3;
	BasicComponent box4;
	BasicComponent box1Image;
	BasicComponent box2Image;
	BasicComponent box3Image;
	BasicComponent box4Image;

	static BasicComponent earths;
	ArrayList<String> monthlyHints = new ArrayList<>();
	ArrayList<String> genericHints = new ArrayList<>();
	private boolean box1Selected = false;
	private boolean box1Hover = false;
	private boolean box2Selected = false;
	private boolean box2Hover = false;
	private boolean box3Selected = false;
	private boolean box3Hover = false;
	private boolean box4Selected = false;
	private boolean box4Hover = false;
	private boolean hoverAny = false;
	private TextField box1Text1;
	private TextField box1Text2;
	private TextField box2Text1;
	private TextField box2Text2;
	private TextField box3Text1;
	private TextField box3Text2;
	private TextField box4Text1;
	private TextField box4Text2;
	private boolean nextState;
	private int lc;

	public class CDReadyListener extends ButtonListener {

		@Override
		protected void actionPerformed() {

		if (box1Selected){
			if (!box1Used){
			box1Used = true;
			Game.getCurrentTask().getCurrentAttempt().addHints(1);
			++hints;
			}
			try {
				box1.setCurrentImage(new Image(INCORRECT_BOX), true);
			} catch (SlickException e) {
				
				e.printStackTrace();
			}
			hintBox.setText(monthlyHints.get(0));
		}
		else if(box2Selected){
			
			correctAnswer = true;
			
			try {
				box2.setCurrentImage(new Image(CORRECT_BOX), true);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			readyButtonOff();
			continueButtonOn();
			hintBox.setText(monthlyHints.get(1));
		}
		else if(box3Selected){
			if (!box3Used){
				box3Used = true;
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				++hints;
				}
			try {
				box3.setCurrentImage(new Image(INCORRECT_BOX), true);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			hintBox.setText(monthlyHints.get(2));
		}
		else if(box4Selected){
			if (!box4Used){
				box4Used = true;
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				++hints;
				}
			try {
				box4.setCurrentImage(new Image(INCORRECT_BOX), true);
			} catch (SlickException e) {
			
				e.printStackTrace();
			}
			hintBox.setText(monthlyHints.get(3));
		}
		}

	}

	public class CDContinueListener extends ButtonListener {

		@Override
		protected void actionPerformed() {
			if (correctAnswer) {
				
				// TODO
				//task.setBackgroundImage(backgroundImage);
				Game.getCurrentGame().enterState(CDPart2.class);
			}

		}

	}

	public class CDHintListener extends ButtonListener {

		@Override
		protected void actionPerformed() {
			displayHint(hintCount);

		}

	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		int spacing = 0;
		this.backgroundImage = new Image(BACKGROUND);
		Rectangle boxBounds = new Rectangle();
		box1 = new BasicComponent(new Image(BOX), 50, 500);
		box1.rescale(1.2f);
		box2 = new BasicComponent(new Image(BOX), 50, 500);
		box2.rescale(1.2f);
		box3 = new BasicComponent(new Image(BOX), 50, 500);
		box3.rescale(1.2f);
		box4 = new BasicComponent(new Image(BOX), 50, 500);
		box4.rescale(1.2f);

		boxBounds = box1.getBounds();
		UtilFunctions.centerRectangle(control.getBounds(), boxBounds);
		box1.setBounds(boxBounds);
		spacing = (control.getBounds().width - 4 * box1.getBounds().width) / 7;


		box2.setX(box1.getX());
		box2.setY(box1.getY());
		box3.setX(box1.getX());
		box3.setY(box1.getY());
		box4.setX(box1.getX());
		box4.setY(box1.getY());

		box1.setX(2 * spacing);
		box2.setX(box1.getBounds().width + box1.getX() + spacing);
		box3.setX(box2.getBounds().width + box2.getX() + spacing);
		box4.setX(box3.getBounds().width + box3.getX() + spacing);
		
		box1Image = new BasicComponent(new Image(SPRING), box1.getX(), box1.getY());
		box1Image.rescale(box1.getBounds().width, box1.getBounds().height);
		box1Image.setX(box1.getX());
		box1Image.setY(box1.getY());
		box2Image = new BasicComponent(new Image(WINTER), box2.getX(), box2.getY());
		box2Image.rescale(box2.getBounds().width, box2.getBounds().height);
		box2Image.setX(box2.getX());
		box2Image.setY(box2.getY());
		box3Image = new BasicComponent(new Image(FALL), box3.getX(), box3.getY());
		box3Image.rescale(box3.getBounds().width, box3.getBounds().height);
		box3Image.setX(box3.getX());
		box3Image.setY(box3.getY());
		box4Image = new BasicComponent(new Image(SUMMER), box4.getX(), box4.getY());
		box4Image.rescale(box4.getBounds().width, box4.getBounds().height);
		box4Image.setX(box4.getX());
		box4Image.setY(box4.getY());

		instructionBox.setText("Select the season that includes the critical design month by clicking on one of the four seasons.");
		topText.setText("Rarotonga, Cook Islands. "
				+ "Latitude: 12.2 degrees S, Longitude: 159.8 degrees W");
		// add initial things to the arraylists
		Earths.add(APRIL);
		Earths.add(FEBRUARY);
		Earths.add(DECEMBER);
		Earths.add(OCTOBER);
		Earths.add(SEPTEMBER);
		Earths.add(JUNE);

	
		

	

		

		monthlyHints
				.add("The critical design month is not in the Spring season.");
		
		monthlyHints
				.add("Good Job! You have selected the season that contains the critical design month. Press continue when you are ready to move on.");
		monthlyHints
				.add("The critical design month is not in the Fall season");
		monthlyHints
				.add("The critical design month is not in the Summer season.");
	

		genericHints
				.add("The Earth has a tilt of 23.5 degrees. Because of this, different parts of the Earth are tilted closer to the Sun during different times of the year. This is why we have seasons.");
		genericHints
				.add("PSH or peak sun-hours is a measure of the amount of solar insolation being received. ");
		genericHints
				.add("The critical design month is usually the month with the lowest solar insolation.");

		// earth
		Image Earth = new Image(Earths.get(index));
		earths = new BasicComponent(Earth, sidePanel.getX() / 2
				- Earth.getWidth() / 2, 57);

		// whole selector

		Image readyButtonImage = new Image(ImagePaths.READY_BUTTON);
		Rectangle textBounds = UtilFunctions.getImageBounds(readyButtonImage);
		int readyButtonOffSet = container.getWidth() - READY_BUTTON_X
				- textBounds.width;

		box1Text1 = new TextField(new Rectangle(box1.getX()+2, box1.getY()+10, box1.getBounds().width-5, box1.getBounds().height/4), 0.95f,
				"Spring", TextDisplay.FormattingOption.FIT_TEXT);
		
		box1Text1.setFontColor(FONT_COLOR);
		box1Text1.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box1Text1.center();
		box1Text2 = new TextField(new Rectangle(box1.getX()+2, box1.getY()+box1.getBounds().height - 40, box1.getBounds().width-5, box1.getBounds().height/4), 0.95f,
				"7.02 PSH/Day", TextDisplay.FormattingOption.FIT_TEXT);
		
		box1Text2.setFontColor(FONT_COLOR);
		box1Text2.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box1Text2.center();
		
		box2Text1 = new TextField(new Rectangle(box2.getX()+2, box2.getY()+10, box2.getBounds().width-5, box2.getBounds().height/4), 0.95f,
				"Winter", TextDisplay.FormattingOption.FIT_TEXT);
		
		box2Text1.setFontColor(FONT_COLOR);
		box2Text1.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box2Text1.center();
		box2Text2 = new TextField(new Rectangle(box2.getX()+2, box2.getY()+box2.getBounds().height - 40, box2.getBounds().width-5, box2.getBounds().height/4), 0.95f,
				"5.24 PSH/Day", TextDisplay.FormattingOption.FIT_TEXT);
		
		box2Text2.setFontColor(FONT_COLOR);
		box2Text2.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box2Text2.center();
		
		
		box3Text1 = new TextField(new Rectangle(box3.getX()+2, box3.getY()+10, box3.getBounds().width-5, box3.getBounds().height/4), 0.95f,
				"Fall", TextDisplay.FormattingOption.FIT_TEXT);
		
		box3Text1.setFontColor(FONT_COLOR);
		box3Text1.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box3Text1.center();
		box3Text2 = new TextField(new Rectangle(box3.getX()+2, box1.getY()+box3.getBounds().height - 40, box3.getBounds().width-5, box3.getBounds().height/4), 0.95f,
				"5.96 PSH/Day", TextDisplay.FormattingOption.FIT_TEXT);
		
		box3Text2.setFontColor(FONT_COLOR);
		box3Text2.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box3Text2.center();
		
		box4Text1 = new TextField(new Rectangle(box4.getX()+2, box4.getY()+10, box4.getBounds().width-5, box4.getBounds().height/4), 0.95f,
				"Summer", TextDisplay.FormattingOption.FIT_TEXT);
		
		box4Text1.setFontColor(FONT_COLOR);
		box4Text1.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box4Text1.center();
		box4Text2 = new TextField(new Rectangle(box4.getX()+2, box4.getY()+box4.getBounds().height - 40, box4.getBounds().width-5, box4.getBounds().height/4), 0.95f,
				"6.64 PSH/Day", TextDisplay.FormattingOption.FIT_TEXT);
		
		box4Text2.setFontColor(FONT_COLOR);
		box4Text2.setFormatting(TextDisplay.FormattingOption.CLIP_TEXT);
		box4Text2.center();
		
		this.addComponent(box1);
		this.addComponent(box2);
		this.addComponent(box3);
		this.addComponent(box4);
		this.addComponent(box1Image);
		this.addComponent(box2Image);
		this.addComponent(box3Image);
		this.addComponent(box4Image);
		this.addComponent(box1Text1);
		this.addComponent(box1Text2);
		this.addComponent(box2Text1);
		this.addComponent(box2Text2);
		this.addComponent(box3Text1);
		this.addComponent(box3Text2);
		this.addComponent(box4Text1);
		this.addComponent(box4Text2);

		this.addComponent(earths);
		this.addComponent(topText);

		hintButton.addActionListener(new CDHintListener());
		readyButton.addActionListener(new CDReadyListener());
		continueButton.addActionListener(new CDContinueListener());
		backButton.addActionListener(new TransitionButtonListener(
				CDIntroScreen.class));

		System.out
				.println("Listeners: " + Arrays.toString(this.getListeners()));
	}

	private void userAnswer(int i) throws SlickException {
		if (i == 0) {
			hintBox.setText(monthlyHints.get(i));
			if (!ap && !correctAnswer) {
				++hints;
				//TODO
				//Game.getCurrentTask().getCurrentAttempt().addHints(hints.size());
				ap = true;
			}
			System.out.println("April Hint shown, total hints: " + hints);
		}
		if (i == 1) {
			hintBox.setText(monthlyHints.get(i));
			if (!fe && !correctAnswer) {
				++hints;
				fe = true;
			}
			System.out.println("February Hint shown, total hints: " + hints);
		}
		if (i == 2) {
			hintBox.setText(monthlyHints.get(i));
			continueButtonOn();
			readyButtonOff();
			correctAnswer = true;
			System.out.println("Correct answer gotten after " + hints
					+ " hints.");
		}
		if (i == 3) {
			hintBox.setText(monthlyHints.get(i));
			if (!oc && !correctAnswer) {
				++hints;
				oc = true;
			}
			System.out.println("October Hint shown, total hints: " + hints);
		}
		if (i == 4) {
			hintBox.setText(monthlyHints.get(i));
			if (!se && !correctAnswer) {
				++hints;
				se = true;
			}
			System.out.println("September Hint shown, total hints: " + hints);
		}
		if (i == 5) {
			hintBox.setText(monthlyHints.get(i));
			if (!ju && !correctAnswer) {
				++hints;
				ju = true;
			}
			System.out.println("June Hint shown, total hints: " + hints);
		}
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (!nextState){
			++lc;
			if (lc == 5){
			try {
		
				Game.getCurrentGame().addState(new CDPart2(), Game.getCurrentGame().getContainer());
		
			
			} catch (SlickException e) {
	
				e.printStackTrace();
			}
		nextState = true;
			}
	}
		if(correctAnswer){
			score = 6 - hints;
			if (score <0){
				score = 0;
			}
			if (sequenceStep != 4000){
			sequenceStep = initiateStars(score, sequenceStep);
			}
		}
		int MouseX = container.getInput().getMouseX();
		int MouseY = container.getInput().getMouseY();
		if (box1Hover || box2Hover || box3Hover || box4Hover){
			hoverAny = true;
		}
		else{
			hoverAny = false;
		}
		if ((MouseX >= box1.getX() && MouseX <= (box1.getX() + box1.getBounds().width)) // range
				&& (MouseY >= box1.getY() && MouseY <= (box1.getY() + box1
						.getBounds().height))) {
			if (!box1Selected) {
				if (!box1Hover) {
					box1.setCurrentImage(new Image(MOUSE_OVER_BOX), true);
					box1Hover = true;
					earths.setCurrentImage(new Image(APRIL), true);
				}
				if (container.getInput().isMouseButtonDown(
						Input.MOUSE_LEFT_BUTTON) && !correctAnswer) {
					box1.setCurrentImage(new Image(SELECTED_BOX), true);
				
					box2.setCurrentImage(new Image(BOX), true);
					
					box3.setCurrentImage(new Image(BOX), true);
					box4.setCurrentImage(new Image(BOX), true);
					box1Selected = true;
					box2Selected = false;
					box3Selected = false;
					box4Selected = false;

					box1Hover = false;
				}
			}
		} else {
			if (box1Hover) {
				box1.setCurrentImage(new Image(BOX), true);
				box1Hover = false;
			}
			if (!hoverAny){
				if (box2Selected) {
					earths.setCurrentImage(new Image(DECEMBER), true);
				}
				if (box3Selected) {
					earths.setCurrentImage(new Image(SEPTEMBER), true);
				}
				if (box4Selected) {
					earths.setCurrentImage(new Image(JUNE), true);
				}
			}
		}
		if (box1Hover || box2Hover || box3Hover || box4Hover){
			hoverAny = true;
		}
		else{
			hoverAny = false;
		}
		if ((MouseX >= box2.getX() && MouseX <= (box2.getX() + box2.getBounds().width)) // range
				&& (MouseY >= box2.getY() && MouseY <= (box2.getY() + box2
						.getBounds().height))) {
			if (!box2Selected && !correctAnswer) {
				if (!box2Hover) {
					box2.setCurrentImage(new Image(MOUSE_OVER_BOX), true);
					box2Hover = true;
					earths.setCurrentImage(new Image(DECEMBER), true);
				}
				if (container.getInput().isMouseButtonDown(
						Input.MOUSE_LEFT_BUTTON)) {
					box1.setCurrentImage(new Image(BOX), true);
					box2.setCurrentImage(new Image(SELECTED_BOX), true);
					box3.setCurrentImage(new Image(BOX), true);
					box4.setCurrentImage(new Image(BOX), true);
					box2Selected = true;
					box1Selected = false;
					box3Selected = false;
					box4Selected = false;
					box2Hover = false;
				}
			}
		} else {
			if (box2Hover) {
				box2.setCurrentImage(new Image(BOX), true);
				box2Hover = false;
			}
			if (!hoverAny ){
				if (box1Selected) {
					earths.setCurrentImage(new Image(APRIL), true);
				}
				if (box3Selected) {
					earths.setCurrentImage(new Image(SEPTEMBER), true);
				}
				if (box4Selected) {
					earths.setCurrentImage(new Image(JUNE), true);
				}
			}
		}
		if (box1Hover || box2Hover || box3Hover || box4Hover){
			hoverAny = true;
		}
		else{
			hoverAny = false;
		}
		if ((MouseX >= box3.getX() && MouseX <= (box3.getX() + box3.getBounds().width)) // range
				&& (MouseY >= box3.getY() && MouseY <= (box3.getY() + box3
						.getBounds().height))) {
			if (!box3Selected) {
				if (!box3Hover) {
					box3.setCurrentImage(new Image(MOUSE_OVER_BOX), true);
					box3Hover = true;
					earths.setCurrentImage(new Image(SEPTEMBER), true);
				}
				if (container.getInput().isMouseButtonDown(
						Input.MOUSE_LEFT_BUTTON) && !correctAnswer) {
					box1.setCurrentImage(new Image(BOX), true);
				
					box2.setCurrentImage(new Image(BOX), true);
					
					box3.setCurrentImage(new Image(SELECTED_BOX), true);
					box4.setCurrentImage(new Image(BOX), true);
					box3Selected = true;
					box1Selected = false;
					box2Selected = false;
					box4Selected = false;
					box3Hover = false;
				}
			}
		} else {
			if (box3Hover) {
				box3.setCurrentImage(new Image(BOX), true);
				box3Hover = false;
			}
			if (!hoverAny){
				if (box2Selected) {
					earths.setCurrentImage(new Image(DECEMBER), true);
				}
				if (box1Selected) {
					earths.setCurrentImage(new Image(APRIL), true);
				}
				if (box4Selected) {
					earths.setCurrentImage(new Image(JUNE), true);
				}
			}
		}
		if (box1Hover || box2Hover || box3Hover || box4Hover){
			hoverAny = true;
		}
		else{
			hoverAny = false;
		}
		if ((MouseX >= box4.getX() && MouseX <= (box4.getX() + box4.getBounds().width)) // range
				&& (MouseY >= box4.getY() && MouseY <= (box4.getY() + box4
						.getBounds().height))) {
			if (!box4Selected) {
				if (!box4Hover) {
					box4.setCurrentImage(new Image(MOUSE_OVER_BOX), true);
					box4Hover = true;
					earths.setCurrentImage(new Image(JUNE), true);
				}
				if (container.getInput().isMouseButtonDown(
						Input.MOUSE_LEFT_BUTTON) && !correctAnswer) {
					box1.setCurrentImage(new Image(BOX), true);
				
					box2.setCurrentImage(new Image(BOX), true);
				
					box3.setCurrentImage(new Image(BOX), true);
					box4.setCurrentImage(new Image(SELECTED_BOX), true);
					box4Selected = true;
					box2Selected = false;
					box3Selected = false;
					box1Selected = false;
					box4Hover = false;
				}
			}
		} else {
			if (box4Hover) {
				box4.setCurrentImage(new Image(BOX), true);
				box4Hover = false;
			}
			if (!hoverAny){
				if (box2Selected) {
					earths.setCurrentImage(new Image(DECEMBER), true);
				}
				if (box3Selected) {
					earths.setCurrentImage(new Image(SEPTEMBER), true);
				}
				if (box1Selected) {
					earths.setCurrentImage(new Image(APRIL), true);
				}
			}
		}
	}

	private void displayHint(int hCount) {
		if (hCount == 0) {
			hintBox.setText(genericHints.get(0));
			++hintCount;
			if (!correctAnswer) {
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				++hints;
			}
			System.out.println("Generic Hint1 shown, total hints: " + hints);
		}
		if (hCount == 1) {
			hintBox.setText(genericHints.get(1));
			++hintCount;
			if (!correctAnswer) {
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
				++hints;
			}
			System.out.println("Generic Hint2 shown, total hints: " + hints);
		}
		if (hCount == 2) {
			hintBox.setText(genericHints.get(2));
			++hintCount;
			if (!correctAnswer) {
				++hints;
				Game.getCurrentTask().getCurrentAttempt().addHints(1);
			}
			System.out.println("Generic Hint2 shown, total hints: " + hints);
		}
		if (hCount > 2) {

			if (hCount == 3) {
				hintBox.setText(genericHints.get(0));
			} else if (hCount == 4) {
				hintBox.setText(genericHints.get(1));
			} else {
				hintBox.setText(genericHints.get(2));
			}
			++hintCount;
			if (hintCount == 6) {
				hintCount = 3;
			}
			System.out
					.println("User requested more hints, none to give. total hints: "
							+ hints);

		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics graphics) throws SlickException {
		super.render(container, game, graphics);

	}

	public Dimension getDesignResolution() {
		// TODO Auto-generated method stub
		return null;
	}
	public void onEnter()
	 {
		
		trackTime = true;
	 }





}
