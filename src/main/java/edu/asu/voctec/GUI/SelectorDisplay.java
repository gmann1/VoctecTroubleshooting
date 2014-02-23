package edu.asu.voctec.GUI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.GameDefaults.ImagePaths;
import edu.asu.voctec.GameDefaults.Labels.Step0;
import edu.asu.voctec.game_states.GUI;
import edu.asu.voctec.minigames.step_selection.StepSelection;
import edu.asu.voctec.utilities.Position;
import edu.asu.voctec.utilities.UtilFunctions;

public class SelectorDisplay<T extends SelectorIcon> extends Component
{
	private static final long serialVersionUID = -6039485636899288406L;
	
	protected static Rectangle defaultBorderBounds;
	protected static Dimension smallArrowDimension;
	protected static Dimension largeArrowDimension;
	protected static Image defaultBorder;
	protected static Image highlightedBorder;
	protected static Image correctBorder;
	protected static Image incorrectBorder;
	
	protected static Image smallArrow;
	protected static Image largeArrow;
	
	protected Rectangle borderBounds;
	protected Button[] choiceBorders;
	protected ArrayList<T> elements;
	protected ArrayList<Component> aethsteticComponents;
	protected Selector<T> associatedSelector;
	protected int capacity;
	protected int x;
	protected int y;
	
	/**
	 * Listener for the choice borders of a SelectorDisplay. If any border is
	 * clicked while it is not empty, the element it currently contains will be
	 * sent to the associated Selector (if possible).
	 * 
	 * @author Moore, Zachary
	 * 
	 */
	public class ChoiceListener extends ButtonListener
	{
		private static final long serialVersionUID = -5724209354491062766L;
		
		@Override
		protected void actionPerformed()
		{
			// Determine which choiceBorder was clicked
			int index = Arrays.asList(choiceBorders).indexOf(
					(BasicComponent) this.associatedComponent);
			
			// Get the icon that is being held by the current choiceBorder
			T icon = elements.get(index);
			
			// Ignore the click if the selected choiceBorder is empty.
			if (icon != null)
			{
				// Return icon to selector
				sendToSelector(icon);
				
				// Ensure the box border is appropriate
				if (choiceBorders[index].baseImage != defaultBorder)
					choiceBorders[index].setCurrentImage(defaultBorder, true);
				
				// Free the space in this display
				elements.set(index, null);
				
				updateInstructions();
			}
		}
	}
	
	/**
	 * Indicates that an action cannot be completed because a selector display
	 * has reached its capacity.
	 * 
	 * @author Moore, Zachary
	 * 
	 */
	public static class DisplayIsFullException extends Exception
	{
		private static final long serialVersionUID = 5948362100760330981L;
		
		public DisplayIsFullException(String message)
		{
			super(message);
		}
	}
	
	/**
	 * Instantiate all static variables, and handle errors in instantiation.
	 */
	static
	{
		// Attempt to instantiate all static variables
		try
		{
			// Load & Instantiate DefaultBoder Image
			defaultBorder = new Image(ImagePaths.SelectorDisplayBorders.DEFAULT);
			
			// Declare default bounds and sizes
			defaultBorderBounds = UtilFunctions.getImageBounds(defaultBorder);
			smallArrowDimension = new Dimension(39, 39);
			largeArrowDimension = new Dimension(169, 109);
			
			// Load & Instantiate Border Images
			highlightedBorder = new Image(
					ImagePaths.SelectorDisplayBorders.HIGHLIGHTED)
					.getScaledCopy(defaultBorderBounds.width,
							defaultBorderBounds.height);
			correctBorder = new Image(ImagePaths.SelectorDisplayBorders.CORRECT)
					.getScaledCopy(defaultBorderBounds.width,
							defaultBorderBounds.height);
			incorrectBorder = new Image(
					ImagePaths.SelectorDisplayBorders.INCORRECT).getScaledCopy(
					defaultBorderBounds.width, defaultBorderBounds.height);
			
			// Instantiate Arrow Images (Aesthetic Components)
			smallArrow = new Image(
					ImagePaths.SelectorDisplayBorders.SMALL_ARROW)
					.getScaledCopy(smallArrowDimension.width,
							smallArrowDimension.height);
			largeArrow = new Image(
					ImagePaths.SelectorDisplayBorders.LARGE_ARROW)
					.getScaledCopy(largeArrowDimension.width,
							largeArrowDimension.height);
		}
		catch (SlickException e)
		{
			System.out.println("Selector Display: Static Block: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Base constructor. All other constructors will call this. Protected to
	 * force all instantiations to explicitly state if the default borders and
	 * actions should be used.
	 * 
	 * @param x
	 *            -location of this component
	 * @param y
	 *            -location of this component
	 * @param capacity
	 *            How many elements can this display hold.
	 * @see #SelectorDisplay(int, int, boolean)
	 */
	protected SelectorDisplay(int x, int y, int capacity)
	{
		super();
		this.x = x;
		this.y = y;
		this.capacity = capacity;
		this.borderBounds = defaultBorderBounds;
		elements = new ArrayList<>(capacity);
		aethsteticComponents = new ArrayList<>();
		
		for (int index = 0; index < capacity; index++)
			elements.add(null);
	}
	
	public SelectorDisplay(int x, int y, boolean useDefaults) throws SlickException
	{
		this(x, y, 5);
		
		if (useDefaults)
		{
			int spacing = 29; // Minimum space between choiceBorders
			
			/*
			 * Create the choice borders for this component, using the default
			 * image and format; relative to this component
			 */
			ArrayList<Button> borders = generateDefaultFormation(
					spacing, aethsteticComponents);
			
			// Instantiate choice borders with the values defined above.
			choiceBorders = borders.toArray(new Button[borders.size()]);
			
			// Setup each choiceBorder
			// Set screen-relative positions, associate with GUI, and listen for
			// mouse clicks
			setupChoiceBorders(true);
			
			// Position arrows
			setupAethsteticComponents(true);
		}
	}
	
	protected void updateInstructions()
	{
		if (getAssociatedGUI() instanceof StepSelection)
			((StepSelection) getAssociatedGUI()).updateInstructions();
	}
	
	protected void setupChoiceBorders(boolean positionComponents)
	{
		if (choiceBorders != null)
		{
			GUI associatedGUI = getAssociatedGUI();
			for (Component component : choiceBorders)
			{
				/*
				 * Associate this component's GUI with each component Note:
				 * because the component has not been associated with the GUI,
				 * the GUI will not render or scale the component, it will only
				 * track the listeners assigned to these components
				 */
				component.associate(associatedGUI);
				
				// Listen for clicks on all components
				component.addActionListener(new ChoiceListener());
				
				// Replace relative positioning with absolute positioning
				if (positionComponents)
					component.translate(x, y);
			}
		}
	}
	
	protected void setupAethsteticComponents(boolean positionComponents)
	{
		if (aethsteticComponents != null)
		{
			for (Component component : aethsteticComponents)
			{
				// Replace relative positioning with absolute positioning
				if (positionComponents)
					component.translate(x, y);
			}
		}
	}
	
	public void updateChoiceBorders()
	{
		// Determine if each element is in the proper position
		for (int index = 0; index < capacity; index++)
		{
			T element = elements.get(index);
			
			// Each element's ID should correspond to its index (if correct)
			if (element == null)
				this.choiceBorders[index].setCurrentImage(defaultBorder, true);
			else if (element.getId() == index)
				this.choiceBorders[index].setCurrentImage(correctBorder, true);
			else
				this.choiceBorders[index]
						.setCurrentImage(incorrectBorder, true);
		}
		
	}
	
	public void restoreChoiceBorders()
	{
		for (BasicComponent border : choiceBorders)
			border.setCurrentImage(defaultBorder, true);
	}
	
	public boolean verifyChoices(boolean updateBorders)
	{
		boolean correctChoices = true;
		
		for (int index = 0; index < capacity; index++)
		{
			T element = elements.get(index);
			if (element == null || element.getId() != index)
			{
				correctChoices = false;
				break;
			}
		}
		
		if (updateBorders)
			updateChoiceBorders();
		
		return correctChoices;
	}
	
	public ArrayList<String> deriveHints()
	{
		return deriveHints(false);
	}
	
	public ArrayList<String> deriveHints(boolean shuffle)
	{
		ArrayList<String> hints = new ArrayList<>();
		ArrayList<T> orderedElements = generateOrderedElementsArray();
		ArrayList<T> misplacedElements = determineMisplacedElements(elements);
		
		for (T misplacedElement : misplacedElements)
			hints.add(deriveHint(misplacedElement, orderedElements));
		
		if (shuffle)
			Collections.shuffle(hints);
		
		return hints;
	}
	
	public String deriveHint()
	{
		ArrayList<T> orderedElements = generateOrderedElementsArray();
		System.out.println("Ordered: "
				+ Arrays.toString(orderedElements.toArray()));
		Random random = new Random();
		int index = random.nextInt() % orderedElements.size();
		System.out.println("Index: " + index);
		T element = orderedElements.get(index);
		System.out.println("Element: " + element);
		int invalidIndex;
		do
		{
			invalidIndex = random.nextInt() % orderedElements.size();
		} while (invalidIndex == element.getId());
		
		invalidIndex = (invalidIndex < 0) ? -invalidIndex : invalidIndex;
		
		return deriveHint(element, orderedElements, invalidIndex);
	}
	
	protected String deriveHint(T element, ArrayList<T> orderedElements)
	{
		int actualIndex = this.elements.indexOf(element);
		return deriveHint(element, orderedElements, actualIndex);
	}
	
	protected String deriveHint(T element, ArrayList<T> orderedElements,
			int actualIndex)
	{
		if (element == null)
			throw new NullPointerException("Hint cannot be derived from null!");
		else if (orderedElements == null)
			throw new NullPointerException("Ordered Array Required");
		
		String hint;
		int properIndex = element.getId();
		
		if (actualIndex == properIndex)
		{
			hint = element.getName() + " " + Step0.Hints.CORRECT_STEP;
		}
		else
		{
			Random random = new Random();
			int rootIndex = random.nextInt() % orderedElements.size();
			
			// Choose an element other than the misplaced element
			while (rootIndex == properIndex)
				rootIndex = random.nextInt() % orderedElements.size();
			
			rootIndex = (rootIndex < 0) ? -rootIndex : rootIndex;
			T root = orderedElements.get(rootIndex);
			int order = properIndex - rootIndex;
			String keyWord;
			
			if (order < 0)
				keyWord = Step0.Hints.BEFORE.getTranslation();
			else
				keyWord = Step0.Hints.AFTER.getTranslation();
			
			hint = element.getName() + " "
					+ Step0.Hints.HINT_BODY.getTranslation() + " " + keyWord
					+ " " + root.getName() + ".";
		}
		
		return hint;
	}
	
	public static <E extends SelectorIcon> ArrayList<E> determineMisplacedElements(
			ArrayList<E> elements)
	{
		ArrayList<E> misplacedElements = new ArrayList<>();
		
		for (int index = 0; index < elements.size(); index++)
		{
			E element = elements.get(index);
			
			if (element != null && element.getId() != index)
				misplacedElements.add(element);
		}
		
		return misplacedElements;
	}
	
	protected ArrayList<T> generateOrderedElementsArray()
	{
		ArrayList<T> sortedElements = new ArrayList<>(capacity);
		UtilFunctions.populateNull(sortedElements, capacity);
		
		for (T element : elements)
		{
			// Add each element to the index specified by its ID
			if (element != null)
				sortedElements.set(element.getId(), element);
		}
		
		return sortedElements;
	}
	
	protected ArrayList<Button> generateDefaultFormation(int spacing,
			ArrayList<Component> extraComponentContainer) throws SlickException
	{
		ArrayList<Button> borders = new ArrayList<>(5);
		spacing += defaultBorder.getWidth();
		
		// Populate default borders - Place 5 borders side-by-side
		{
			Point relativeLocation = new Point(0, 0);
			for (int index = 0; index < 5; index++)
			{
				Button border = new Button(defaultBorder, relativeLocation.x,
						relativeLocation.y, 0.95f, Integer.toString(index + 1));
				border.positionText(Position.BOTTOM);
				borders.add(border);
				relativeLocation.translate(spacing, 0);
			}
		}
		/*
		 * // Populate default borders - Make a '5-domino' formation // Top left
		 * border Point relativeLocation = new Point(0, 0); borders.add(new
		 * BasicComponent(defaultBorder, relativeLocation));
		 * 
		 * // Top right border relativeLocation.translate(2 * spacing + 2 *
		 * defaultBorder.getWidth(), 0); borders.add(new
		 * BasicComponent(defaultBorder, relativeLocation));
		 * 
		 * // Middle Border relativeLocation.setLocation(spacing +
		 * defaultBorder.getWidth(), spacing + defaultBorder.getHeight());
		 * borders.add(new BasicComponent(defaultBorder, relativeLocation));
		 * 
		 * // Bottom left border relativeLocation.setLocation(0, 2 * spacing + 2
		 * * defaultBorder.getHeight()); borders.add(new
		 * BasicComponent(defaultBorder, relativeLocation));
		 * 
		 * // Bottom right border relativeLocation.translate(2 * spacing + 2 *
		 * defaultBorder.getWidth(), 0); borders.add(new
		 * BasicComponent(defaultBorder, relativeLocation));
		 * 
		 * // Small arrows // Step 2-3
		 * relativeLocation.setLocation(borders.get(2).getX() +
		 * borders.get(2).getBounds().width - 5, borders.get(2).getY() - spacing
		 * - 5); extraComponentContainer.add(new BasicComponent(smallArrow,
		 * relativeLocation)); // Step 3-4
		 * relativeLocation.setLocation(borders.get(3).getX() +
		 * borders.get(3).getBounds().width - 5, borders.get(3).getY() - spacing
		 * - 5); extraComponentContainer.add(new BasicComponent(smallArrow,
		 * relativeLocation));
		 * 
		 * // Large arrows // Step 1-2
		 * relativeLocation.setLocation(borders.get(0).getX() +
		 * borders.get(0).getBounds().width, borders.get(0).getY());
		 * extraComponentContainer.add(new BasicComponent(largeArrow,
		 * relativeLocation)); // Step 4-5
		 * relativeLocation.setLocation(borders.get(3).getX() +
		 * borders.get(3).getBounds().width, borders.get(3).getY());
		 * extraComponentContainer.add(new BasicComponent(largeArrow,
		 * relativeLocation));
		 */
		return borders;
	}
	
	public boolean accept(T element)
	{
		boolean accepted;
		
		// Get the first empty "slot" in this display
		int currentIndex = elements.indexOf(null);
		
		System.out.println("SelectorDisplay: capacity=" + capacity);
		System.out.println("SelectorDisplay: index=" + currentIndex);
		System.out.println("SelectorDisplay: element=" + element);
		
		// Reject the element if this display is full, or if the element is null
		if (element != null && !(currentIndex > capacity || currentIndex < 0))
		{
			BasicComponent container = choiceBorders[currentIndex];
			
			// Determine new bounds for selection
			Rectangle newBounds = new Rectangle(borderBounds);
			newBounds.setLocation(container.getX(), container.getY());
			
			// Resize and replace element
			element.setBounds(newBounds);
			
			elements.set(currentIndex, element);
			accepted = true;
			System.out.println("SelectorDisplay: Element Accepted.\n");
		}
		else
		{
			// Reject element
			accepted = false;
			System.out.println("SelectorDisplay: Element Rejected.");
		}
		
		updateInstructions();
		return accepted;
	}
	
	public void associate(Selector<T> associatedSelector)
	{
		this.associatedSelector = associatedSelector;
	}
	
	@Override
	public void associate(GUI associatedGUI)
	{
		super.associate(associatedGUI);
		for (Component component : choiceBorders)
			component.associate(associatedGUI);
	}
	
	public void link(Selector<T> associatedSelector)
	{
		associate(associatedSelector);
		associatedSelector.associate(this);
	}
	
	/**
	 * Attempts to send the provided data to the selector associated with this
	 * display. Returns true if the data was accepted by the selector.
	 * 
	 * @param data
	 *            Object to send to this display's associated selector.
	 * @return True if the data was accepted by the selector.
	 */
	public boolean sendToSelector(T data)
	{
		if (data != null)
			return associatedSelector.accept(data);
		else
			return false;
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		// Draw choice borders
		for (Component border : this.choiceBorders)
		{
			if (border != null)
				border.draw(graphics);
		}
		
		// Draw choice icons
		for (Component element : this.elements)
		{
			if (element != null)
				element.draw(graphics);
		}
		
		// Draw arrows and other aethstetic components
		for (Component component : this.aethsteticComponents)
		{
			if (component != null)
				component.draw(graphics);
		}
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
		// TODO calculate width and height based on components
		return new Rectangle(x, y, choiceBorders[4].getX()
				+ choiceBorders[4].getBounds().width - x,
				choiceBorders[0].getBounds().height);
	}
	
	@Override
	public void setX(int x)
	{
		// TODO replace with delta translations
		// Make choiceBorder positions relative to this component
		UtilFunctions.translateAll(-this.x, -y, aethsteticComponents);
		UtilFunctions.translateAll(-this.x, -y, choiceBorders);
		
		this.x = x;
		
		// Reset screen-relative positions for all choiceBorders
		UtilFunctions.translateAll(this.x, y, aethsteticComponents);
		UtilFunctions.translateAll(this.x, y, choiceBorders);
	}
	
	@Override
	public void setY(int y)
	{
		// TODO replace with delta translations
		// Make choiceBorder positions relative to this component
		UtilFunctions.translateAll(-x, -this.y, aethsteticComponents);
		UtilFunctions.translateAll(-x, -this.y, choiceBorders);
		
		this.y = y;
		
		// Reset screen-relative positions for all choiceBorders
		UtilFunctions.translateAll(x, this.y, aethsteticComponents);
		UtilFunctions.translateAll(x, this.y, choiceBorders);
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		boolean success = true;
		
		for (Component component : choiceBorders)
		{
			// Make position relative to this component
			component.translate(-x, -y);
			
			// Rescale the component
			success = success && component.rescale(width, height);
			
			// Reset screen-relative position
			component.translate(x, y);
		}
		
		for (Component component : aethsteticComponents)
		{
			// Make position relative to this component
			component.translate(-x, -y);
			
			// Rescale the component
			success = success && component.rescale(width, height);
			
			// Reset screen-relative position
			component.translate(x, y);
		}
		
		return success;
	}
	
	@Override
	public boolean rescale(float horizontalScale, float verticalScale)
	{
		this.x = (int) (x * horizontalScale);
		this.y = (int) (y * verticalScale);
		
		for (Component component : choiceBorders)
			component.rescale(horizontalScale, verticalScale);
		
		for (Component component : aethsteticComponents)
			component.rescale(horizontalScale, verticalScale);
		
		for (Component component : this.elements)
		{
			if (component != null)
				component.rescale(horizontalScale, verticalScale);
		}
		
		this.borderBounds = UtilFunctions.getScaledRectangle(borderBounds,
				horizontalScale, verticalScale);
		return true;
	}
	
	/**
	 * Returns true if this selector display has reached its capacity (i.e. has
	 * no empty spaces).
	 * 
	 * @return True if this display is currently full.
	 */
	public boolean isFull()
	{
		int firstNull = elements.indexOf(null);
		return (firstNull < 0 || firstNull > capacity);
	}
	
	public boolean isEmpty()
	{
		boolean empty = true;
		for (T element : elements)
		{
			if (element != null)
			{
				empty = false;
				break;
			}
		}
		return empty;
	}
	
	/**
	 * Determine the index of the first empty display border (i.e. where the
	 * next element will be placed when it is received).
	 * 
	 * @return The index of the first empty space.
	 * @throws DisplayIsFullException
	 *             Indicates that this display has reached its capacity, and all
	 *             display borders have been filled already.
	 */
	public int getCurrentIndex() throws DisplayIsFullException
	{
		int currentIndex = elements.indexOf(null);
		
		if (currentIndex < 0 || currentIndex > capacity)
			throw new DisplayIsFullException("case not handled");
		
		return currentIndex;
	}
	
	public ArrayList<T> getElements()
	{
		return elements;
	}
	
	public void setElements(ArrayList<T> elements)
	{
		this.elements = elements;
		
		while (elements.size() < capacity)
			elements.add(null);
	}
	
	public void empty()
	{
		elements.clear();
	}
}
