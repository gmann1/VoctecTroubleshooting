package edu.asu.voctec.GUI;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.GameDefaults;
import edu.asu.voctec.GameDefaults.ImagePaths;
import edu.asu.voctec.GameDefaults.SelectorDefaults;
import edu.asu.voctec.utilities.CircularList;
import edu.asu.voctec.utilities.PositionedObject;
import edu.asu.voctec.utilities.UtilFunctions;

public class Selector<T extends SelectorIcon> extends Component
{
	private static final long serialVersionUID = -852158435078638038L;
	
	public static final String emptyText = "All Components Selected";
	protected SelectorDisplay<T> associatedDisplay;
	protected CircularList<T> elements = new CircularList<>();
	protected boolean orientLeft;
	protected boolean displayLabel;
	
	// TODO simplify
	protected BasicComponent currentChoiceBackground;
	protected BasicComponent previousChoiceBackground;
	protected BasicComponent nextChoiceBackground;
	protected BasicComponent rightArrow;
	protected BasicComponent leftArrow;
	protected TextField choiceLabel;
	protected ArrayList<Component> components = new ArrayList<>();
	protected int x;
	protected int y;
	
	protected boolean updated;
	
	public class CurrentChoiceListener extends ButtonListener
	{
		private static final long serialVersionUID = -2357533399392148024L;
		
		@Override
		protected void actionPerformed()
		{
			System.out.println("Icon Selected.");
			
			// Define the clicked element
			T element = elements.getCurrentElement();
			
			// Send the current selector to the associated display
			if (sendToDisplay(element))
			{
				// If the display accepted the element
				// Remove the element from this selector
				elements.pop();
			}
			
			updateChoiceLabel();
		}
		
		@Override
		protected boolean verify(Input input)
		{
			
			Rectangle absoluteBounds = getAbsoluteBounds(currentChoiceBackground);
			return verify(input, absoluteBounds);
		}
	}
	
	public class RightArrowListener extends ButtonListener
	{
		private static final long serialVersionUID = -693279160743754769L;
		Component associatedComponent;
		
		public RightArrowListener(Component associatedComponent)
		{
			this.associatedComponent = associatedComponent;
		}
		
		public RightArrowListener()
		{
			this.associatedComponent = rightArrow;
		}
		
		@Override
		protected void actionPerformed()
		{
			System.out.println("Cycle Right.");
			cycleRight();
		}
		
		@Override
		protected boolean verify(Input input)
		{
			Rectangle absoluteBounds = getAbsoluteBounds(associatedComponent);
			return verify(input, absoluteBounds);
		}
	}
	
	public class LeftArrowListener extends ButtonListener
	{
		private static final long serialVersionUID = -6842480090480810360L;
		Component associatedComponent;
		
		public LeftArrowListener(Component associatedComponent)
		{
			this.associatedComponent = associatedComponent;
		}
		
		public LeftArrowListener()
		{
			this.associatedComponent = leftArrow;
		}
		
		@Override
		protected void actionPerformed()
		{
			System.out.println("Cycle Left.");
			cycleLeft();
		}
		
		@Override
		protected boolean verify(Input input)
		{
			Rectangle absoluteBounds = getAbsoluteBounds(associatedComponent);
			return verify(input, absoluteBounds);
		}
	}
	
	public Selector() throws SlickException
	{
		this(true);
	}
	
	/**
	 * Constructs the default selector object. The size, images, and position of
	 * the new object will be determined by values in GameDefaults
	 * 
	 * @throws SlickException
	 *             Indicates that the images were unable to load.
	 * @see GameDefaults.SelectorDefaults
	 */
	public Selector(boolean useDeafultActions) throws SlickException
	{
		updated = true;
		
		currentChoiceBackground = new BasicComponent(ImagePaths.SELECTOR_LARGE,
				SelectorDefaults.PRIMARY_SELECTION_LOCATION);
		
		previousChoiceBackground = new BasicComponent(
				ImagePaths.SELECTOR_SMALL,
				SelectorDefaults.SECONDARY_SELECTION_LOCATION_LEFT);
		
		nextChoiceBackground = new BasicComponent(ImagePaths.SELECTOR_SMALL,
				SelectorDefaults.SECONDARY_SELECTION_LOCATION_RIGHT);
		
		rightArrow = new BasicComponent(new Image(ImagePaths.ARROW_RIGHT),
				SelectorDefaults.ARROW_LOCATION_RIGHT);
		
		leftArrow = new BasicComponent(new Image(ImagePaths.ARROW_LEFT),
				SelectorDefaults.ARROW_LOCATION_LEFT);
		
		// Add all components to array
		components.add(currentChoiceBackground);
		components.add(previousChoiceBackground);
		components.add(nextChoiceBackground);
		components.add(rightArrow);
		components.add(leftArrow);
		
		// Setup choice label
		choiceLabel = new TextField(SelectorDefaults.ICON_LABEL_BOUNDS, 0.99f,
				emptyText, TextDisplay.FormattingOption.CLIP_TEXT);
		choiceLabel.setFontSize(SelectorDefaults.LABEL_FONT_SIZE);
		
		// Define relative bounds for this selector
		int width = this.rightArrow.getX() + this.rightArrow.getBounds().width
				- this.leftArrow.getX();
		int height = this.currentChoiceBackground.getBounds().height;
		Rectangle relativeBounds = new Rectangle(0, 0, width, height);
		
		// Create centered bounds for choiceLabel
		Rectangle choiceLabelBounds = new Rectangle(choiceLabel.getBounds());
		UtilFunctions.centerRectangleHorizontally(relativeBounds,
				choiceLabelBounds);
		choiceLabel.setBounds(choiceLabelBounds);
		
		// Format choiceLabel
		choiceLabel.center();
		choiceLabel.setFontColor(Color.white);
		components.add(choiceLabel);
		
		if (useDeafultActions)
		{
			// Listen for clicks to the left and right arrows
			this.addActionListener(new RightArrowListener(rightArrow));
			this.addActionListener(new RightArrowListener(nextChoiceBackground));
			this.addActionListener(new LeftArrowListener(leftArrow));
			this.addActionListener(new LeftArrowListener(
					previousChoiceBackground));
			
			// Listen for clicks to the primary selection
			this.addActionListener(new CurrentChoiceListener());
		}
		
		System.out.println("Listener count: " + this.listeners.size());
	}
	
	public Selector(int x, int y, boolean useDeafultActions)
			throws SlickException
	{
		this(useDeafultActions);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		// Draw all components
		for (Component component : components)
		{
			if (component != choiceLabel)
				drawRelatively(graphics, component);
		}
		
		// Draw choice icons
		if (elements.size() >= 1)
			drawElement(graphics, elements.getCurrentElement(),
					currentChoiceBackground);
		if (elements.size() >= 2)
		{
			if (orientLeft || elements.size() >= 3)
				drawElement(graphics, elements.getPreviousElement(),
						previousChoiceBackground);
			if (!orientLeft || elements.size() >= 3)
				drawElement(graphics, elements.getNextElement(),
						nextChoiceBackground);
		}
		
		if (displayLabel && this.choiceLabel != null)
			drawRelatively(graphics, choiceLabel);
	}
	
	public boolean addComponent(Component component)
	{
		return this.components.add(component);
	}
	
	public boolean removeComponent(Component component)
	{
		return this.components.remove(component);
	}
	
	public void updateChoiceLabel()
	{
		if (displayLabel && choiceLabel != null)
		{
			T currentIcon = elements.getCurrentElement();
			
			if (currentIcon == null)
				choiceLabel.setText(emptyText);
			else
				choiceLabel.setText(currentIcon.getName());
		}
	}
	
	protected void drawElement(Graphics graphics, SelectorIcon icon,
			BasicComponent container)
	{
		Image scaledImage = icon.getCurrentImage().getScaledCopy(
				container.getBounds().width, container.getBounds().height);
		drawRelatively(graphics, scaledImage, container.getX(),
				container.getY());
	}
	
	public void cycleLeft()
	{
		updated = true;
		if (orientLeft || elements.size() >= 3)
			elements.previous();
		this.orientLeft = false;
		
		updateChoiceLabel();
	}
	
	public void cycleRight()
	{
		updated = true;
		if (!orientLeft || elements.size() >= 3)
			elements.next();
		this.orientLeft = true;
		
		updateChoiceLabel();
	}
	
	public Rectangle getAbsoluteBounds(PositionedObject<Image> object)
	{
		return new Rectangle(object.x + x, object.y + y,
				object.data.getWidth(), object.data.getHeight());
	}
	
	public Rectangle getAbsoluteBounds(Component component)
	{
		return UtilFunctions.getTranslatedRectangle(component.getBounds(),
				new Point(x, y));
	}
	
	public Rectangle getAbsoluteBounds(Rectangle relativeRectangle)
	{
		return UtilFunctions.getTranslatedRectangle(relativeRectangle,
				new Point(x, y));
	}
	
	public void drawRectangleRelatively(Graphics graphics, Rectangle rectangle,
			Point relativePosition, boolean filled)
	{
		if (filled)
			graphics.fillRect(rectangle.x + relativePosition.x, rectangle.y
					+ relativePosition.y, rectangle.width, rectangle.height);
		
		graphics.drawRect(rectangle.x + relativePosition.x, rectangle.y
				+ relativePosition.y, rectangle.width, rectangle.height);
	}
	
	public void drawRectangleRelatively(Graphics graphics, Rectangle rectangle,
			Point relativePosition)
	{
		drawRectangleRelatively(graphics, rectangle, relativePosition, false);
	}
	
	public void drawRelatively(Graphics graphics, PositionedObject<Image> image)
	{
		graphics.drawImage(image.data, x + image.x, y + image.y);
	}
	
	public void drawRelatively(Graphics graphics, Component component)
	{
		
		component.translate(x, y);
		component.draw(graphics);
		component.translate(-x, -y);
	}
	
	public void drawRelatively(Graphics graphics, Image image, int relativeX,
			int relativeY)
	{
		graphics.drawImage(image, x + relativeX, y + relativeY);
	}
	
	public boolean add(T selection)
	{
		return elements.add(selection);
	}
	
	public boolean accept(T selection)
	{
		boolean accepted;
		// Accept should always return true
		System.out.println("Selector: size=" + elements.size());
		System.out.println("\tSelector: previous="
				+ elements.getPreviousElement());
		System.out.println("\tSelector: current="
				+ elements.getCurrentElement());
		System.out.println("\tSelector: next=" + elements.getNextElement());
		System.out.println("Selector: element=" + selection);
		
		System.out.println("Selector: adding...");
		accepted = elements.addCurrent(selection);
		System.out.println("\tSelector: previous="
				+ elements.getPreviousElement());
		System.out.println("\tSelector: current="
				+ elements.getCurrentElement());
		System.out.println("\tSelector: next=" + elements.getNextElement());
		
		// Ensure the current label is accurate
		updateChoiceLabel();
		
		if (accepted)
			System.out.println("Selector: Element Accepted.\n");
		else
			System.out.println("Selector: Element Rejected.\n");
		return accepted;
	}
	
	public void associate(SelectorDisplay<T> associatedDisplay)
	{
		this.associatedDisplay = associatedDisplay;
	}
	
	public boolean sendToDisplay(T data)
	{
		if (associatedDisplay != null && data != null)
			return associatedDisplay.accept(data);
		else
			return false;
	}
	
	@Override
	public int getX()
	{
		return x;
	}
	
	@Override
	public int getY()
	{
		return y;
	}
	
	@Override
	public Rectangle getBounds()
	{
		// TODO calculate max and min X/Y components
		int x = this.x;
		int y = this.y;
		int width = this.rightArrow.getX() + this.rightArrow.getBounds().width
				- this.leftArrow.getX();
		int height = this.currentChoiceBackground.getY()
				+ this.currentChoiceBackground.getBounds().height;
		
		return new Rectangle(x, y, width, height);
	}
	
	@Override
	public boolean rescale(float horizontalScale, float verticalScale)
	{
		this.x = (int) (x * horizontalScale);
		this.y = (int) (y * verticalScale);
		
		boolean success = true;
		
		for (Component component : components)
		{
			success = success
					&& component.rescale(horizontalScale, verticalScale);
		}
		
		return success;
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		int originalX = this.x;
		int originalY = this.y;
		
		boolean success = this.rescale(width, height);
		
		this.x = originalX;
		this.y = originalY;
		
		return success;
	}
	
	@Override
	public void setX(int x)
	{
		this.x = x;
	}
	
	@Override
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void shuffle()
	{
		this.elements.shuffle();
	}
	
	public void displayLabel(boolean displayLabel)
	{
		this.displayLabel = displayLabel;
		updateChoiceLabel();
	}
	
	public void displayLabel()
	{
		displayLabel(true);
	}
	
	public CircularList<T> getElements()
	{
		return elements;
	}
	
	public void setElements(CircularList<T> elements)
	{
		this.elements = elements;
	}
	
	public void empty()
	{
		elements = new CircularList<>();
	}
	
	public TextField getChoiceLabel()
	{
		return choiceLabel;
	}
	
}
