package edu.asu.voctec.game_states;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.Game;
import edu.asu.voctec.GameDefaults;
import edu.asu.voctec.GUI.ActionListener;
import edu.asu.voctec.GUI.Component;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * GameState class for handling common GUI components (e.g. text displays,
 * buttons, etc) and user input/events. The abstract GUI handles events,
 * components, and screen updates (graphics). It can also track the time a user
 * spends on a minigame automatically, by setting the {@link #trackTime} field
 * to true.
 * 
 * Any child class of GUI should add components using the
 * {@link #addComponent(Component)} method. Child classes can override the
 * {@link #onEnter()} and {@link #onExit()} methods to more easily handle
 * Initialization and load/save logic.
 * 
 * Furthermore, as a precaution against ConcurrentModificationExceptions,
 * children of this class, as well as all other classes that wish to add
 * components to a GUI should consider using the
 * {@link #queueAddComponents(Component...)} and
 * {@link #queueRemoveComponents(Component...)} methods when attempting to add
 * or remove components mid-game. This will force the code to wait until access
 * permissions to the appropriate lists are open, before attempting to access
 * them.
 * 
 * @author Moore, Zachary
 * 
 */
public abstract class GUI extends ModifiedGameState implements GameDefaults
{
	/**
	 * Used to prevent ConcurrentModificationExceptions. All items in this queue
	 * will be processed near the end of the current frame, once permission to
	 * the component list is available.
	 */
	private final ArrayList<Component> additionQueue = new ArrayList<>();
	
	/**
	 * Used to prevent ConcurrentModificationExceptions. All items in this queue
	 * will be processed near the end of the current frame, once permission to
	 * the component list is available.
	 */
	private final ArrayList<Component> removalQueue = new ArrayList<>();
	
	/** Image to display behind all components and screen contents */
	protected Image backgroundImage;
	
	/** List of components that are handled by this GUI */
	protected final ArrayList<Component> components = new ArrayList<>();
	
	/** Used to track and handle EventListeners */
	protected final ArrayList<ActionListener> listeners = new ArrayList<>();
	
	/**
	 * Whether or not to update the currentTime field of the current AttemptData
	 * while this GUI is active. Setting this to true will automatically trigger
	 * the tracking of time. While this boolean is false, time will not be
	 * tracked.
	 */
	protected boolean trackTime;
	
	/** Whether or not this GUI has been fully initialized */
	protected boolean loaded;
	
	@Override
	public void onEnter()
	{
		if (!loaded)
			initiateDeffered();
		// TODO Remove?
	}
	
	@Override
	public void onExit()
	{
		// This method was added in an effort to reduce the amount of code
		// required in child classes, but still allow for extensive
		// customization.
		// TODO Remove?
	}
	
	@Override
	public void initiateDeffered()
	{
		// This method was added in an effort to reduce the amount of code
		// required in child classes, but still allow for extensive
		// customization.
		// TODO Remove?
	}
	
	@Override
	public Dimension getDesignResolution()
	{
		// TODO Remove, and force each child class to determine it's design
		// resolution?
		return new Dimension(800, 600);
	}
	
	/**
	 * Draw the background, and all associated components to the screen.
	 * 
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics graphics) throws SlickException
	{
		// Draw background
		if (this.backgroundImage != null)
			graphics.drawImage(this.backgroundImage, 0, 0);
		
		// TODO implement change tracking
		// TODO draw only components that have been changed
		// Draw all components
		for (Component component : components)
		{
			component.draw(graphics);
		}
	}
	
	/**
	 * Listen for events, and track the time spent (if {@link #trackTime} is
	 * true).
	 * 
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException
	{
		// Current user input
		Input input = container.getInput();
		
		// Listen for events
		listen(input);
		
		// Update current task (time spent)
		if (trackTime)
			Game.getCurrentTask().getCurrentAttempt().addTime(delta);
	}
	
	/**
	 * Positions all components associated with this GUI such that they are
	 * stacked vertically, in the center of this GUI. That is, form a single
	 * column of components, where each component is some distance above or
	 * below the next/previous element.
	 * 
	 * @param spaceBetweenComponents
	 *            Void space between each component.
	 */
	public void centerComponentsStacked(int spaceBetweenComponents)
	{
		// Define the bounds of this GUI
		Rectangle guiBounds = new Rectangle(new Point(0, 0),
				Game.getCurrentScreenDimension());
		UtilFunctions.centerComponentsStacked(guiBounds,
				spaceBetweenComponents,
				components.toArray(new Component[components.size()]));
	}
	
	/**
	 * Listen for, and handle all events. After all events have been processed,
	 * the {@link #additionQueue} and {@link #removalQueue} will be processed.
	 * 
	 * @param input
	 *            User input.
	 * @see #processQueue()
	 */
	private final void listen(Input input)
	{
		ActionListener.update(input);
		
		for (ActionListener listener : listeners)
		{
			listener.check(input);
		}
		
		processQueue();
	}
	
	/**
	 * Add all components in {@link #additionQueue} to {@link #components} and
	 * remove all components in {@link #removalQueue} from {@link #components}.
	 * 
	 * This method is called only outside of iteration loops, and at a time when
	 * the access permissions to {@link #components} are free. This eliminates
	 * potential ConcurrentModificationExceptions.
	 */
	private final void processQueue()
	{
		this.removeComponents(removalQueue);
		this.addComponents(additionQueue);
		
		this.additionQueue.clear();
		this.removalQueue.clear();
	}
	
	/**
	 * Queue components to be removed from this GUI at the end of this frame,
	 * once access permissions to the {@link #components} list become available.
	 * This will prevent ConcurrentModificationExceptions. As such, any
	 * {@link edu.asu.voctec.GUI.ActionListener} and any other code that may
	 * remove components mid-game should use this method instead of
	 * {@link #removeComponents(Component...)}.
	 * 
	 * @param informationComponents
	 *            Components to be removed when access permissions become
	 *            available.
	 */
	public final void queueRemoveComponents(Component... informationComponents)
	{
		queueRemoveComponents(Arrays.asList(informationComponents));
	}
	
	/**
	 * @see #queueRemoveComponents(Component...)
	 */
	public final void queueRemoveComponents(
			List<Component> informationComponents)
	{
		removalQueue.addAll(informationComponents);
	}
	
	/**
	 * Queue components to be added to this GUI at the end of this frame, once
	 * access permissions to the {@link #components} list become available. This
	 * will prevent ConcurrentModificationExceptions. As such, any
	 * {@link edu.asu.voctec.GUI.ActionListener} and any other code that may add
	 * components mid-game should use this method instead of
	 * {@link #addComponents(Component...)}.
	 * 
	 * @param informationComponents
	 *            Components to be added when access permissions become
	 *            available.
	 */
	public final void queueAddComponents(Component... informationComponents)
	{
		queueAddComponents(Arrays.asList(informationComponents));
	}
	
	/**
	 * @see GUI#queueAddComponents(Component...)
	 */
	public final void queueAddComponents(List<Component> informationComponents)
	{
		additionQueue.addAll(informationComponents);
	}
	
	/**
	 * Add a component to this GUI, so that it will be tracked and updated as
	 * long as this screen is active.
	 * 
	 * If the status of the access permissions of {@link #components} is unknown
	 * (e.g. mid-game), or if the permissions are known to be unavailable, then
	 * {@link #queueAddComponents(Component...)} should be used in place of this
	 * method.
	 * 
	 * @param component
	 *            The component to add to this GUI.
	 */
	public void addComponent(Component component)
	{
		// Add component
		component.associate(this);
		this.components.add(component);
	}
	
	/**
	 * Add multiple components.
	 * 
	 * @see GUI#addComponent(Component)
	 */
	public void addComponents(Component... components)
	{
		for (Component component : components)
			addComponent(component);
	}
	
	/**
	 * Add multiple components.
	 * 
	 * @see GUI#addComponent(Component)
	 */
	public void addComponents(ArrayList<Component> components)
	{
		for (Component component : components)
			addComponent(component);
	}
	
	/**
	 * Remove a component from this GUI, so that it will no longer be tracked by
	 * this GUI.
	 * 
	 * If the status of the access permissions of {@link #components} is unknown
	 * (e.g. mid-game), or if the permissions are known to be unavailable, then
	 * {@link #queueRemoveComponents(Component...)} should be used in place of
	 * this method.
	 * 
	 * @param component
	 *            The component to remove from this GUI.
	 */
	public void removeComponent(Component component)
	{
		// Add component
		this.components.remove(component);
		
		// Stop listening for all listeners associated with the given component.
		this.listeners.removeAll(Arrays.asList(component.getListeners()));
	}
	
	/**
	 * Remove multiple components.
	 * 
	 * @see GUI#removeComponent(Component)
	 */
	public void removeComponents(Component... components)
	{
		for (Component component : components)
			removeComponent(component);
	}
	
	/**
	 * Remove multiple components.
	 * 
	 * @see GUI#removeComponent(Component)
	 */
	public void removeComponents(ArrayList<Component> components)
	{
		for (Component component : components)
			removeComponent(component);
	}
	
	public Component[] getComponents()
	{
		return components.toArray(new Component[components.size()]);
	}
	
	public ActionListener[] getListeners()
	{
		return listeners.toArray(new ActionListener[listeners.size()]);
	}
	
	public ArrayList<ActionListener> getListenerList()
	{
		return listeners;
	}
	
	public void setBackgroundImage(Image backgroundImage)
	{
		this.backgroundImage = backgroundImage;
	}
	
	public Image getBackgroundImage()
	{
		return backgroundImage;
	}
	
}
