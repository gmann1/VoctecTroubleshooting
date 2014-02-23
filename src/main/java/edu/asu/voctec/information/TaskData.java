package edu.asu.voctec.information;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.Game;
import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.GameDefaults.ImagePaths;
import edu.asu.voctec.GameDefaults.Labels;
import edu.asu.voctec.GameDefaults.TaskScreenDefaults;
import edu.asu.voctec.GUI.Button;
import edu.asu.voctec.GUI.ButtonListener;
import edu.asu.voctec.GUI.Component;
import edu.asu.voctec.GUI.ProgressBar;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.game_states.TaskScreen;
import edu.asu.voctec.utilities.UtilFunctions;

public class TaskData
{
	public static final Image DEFAULT_IMAGE = UtilFunctions
			.createImage(ImagePaths.TaskScreen.STEP_FIVE);
	
	public static MultiTaskListener activeListener;
	
	protected transient Image buttonImageInaccessible;
	protected transient Image buttonImageComplete;
	protected transient Image buttonImageAccessible;
	
	protected String buttonImageInaccessiblePath;
	protected String buttonImageCompletePath;
	protected String buttonImageAccessiblePath;
	
	protected transient TaskScreen associatedHub;
	protected Class<?> associatedTask;
	protected ArrayList<AttemptData> listOfAttempts;
	protected boolean complete;
	protected boolean accessible;
	protected Button taskIcon;
	
	protected ArrayList<Component> informationComponents;
	protected TextAreaX inaccessibleText;
	protected Button comboButton;
	protected ProgressBar progressBar;
	protected TextField percentageLabel;
	
	public class MultiTaskListener extends ButtonListener
	{
		private static final long serialVersionUID = -2608946596703830325L;
		
		protected boolean displayingComponents;
		
		public MultiTaskListener()
		{
			this.displayingComponents = false;
		}
		
		@Override
		protected void actionPerformed()
		{
			System.out.println("MultiTask Fired:\n\tDisplaying: "
					+ displayingComponents + "\n\tactiveListener: "
					+ activeListener + "\n\tTask Screen ActiveListener: "
					+ TaskScreen.activeListener);
			if (displayingComponents)
			{
				stopDisplaying();
			}
			else
			{
				updateInformation();
				
				if (activeListener != null)
					activeListener.stopDisplaying();
				if (TaskScreen.activeListener != null)
					TaskScreen.activeListener.stopDisplaying();
				
				activeListener = this;
				
				if (accessible)
					associatedHub.queueAddComponents(informationComponents);
				else
					associatedHub.queueAddComponents(inaccessibleText);
				
				displayingComponents = true;
			}
		}
		
		public void stopDisplaying()
		{
			System.out.println("\tStop Displaying:\n\t\tDisplaying: "
					+ displayingComponents + "\n\t\tactiveListener: "
					+ activeListener + "\n\t\tTask Screen ActiveListener: "
					+ TaskScreen.activeListener);
			if (displayingComponents)
			{
				associatedHub.queueRemoveComponents(informationComponents);
				associatedHub.queueRemoveComponents(inaccessibleText);
			}
			
			displayingComponents = false;
			activeListener = null;
		}
		
	}
	
	public class ReplayContinueComboListener extends ButtonListener
	{
		private static final long serialVersionUID = -8964591612455364733L;
		
		@Override
		protected void actionPerformed()
		{
			ScenarioData scenario = Game.getCurrentScenario();
			TaskData[] tasks = scenario.getTasks();
			TaskData associatedTaskData = null;
			
			// TODO verify
			// TODO: If null pointer exception occurs on transfer, check here
			for (TaskData task : tasks)
			{
				if(associatedTask == task.getAssociatedTask())
				{
					associatedTaskData = task;
					break;
				}
			}
			
			Game.setCurrentTask(associatedTaskData);
			AttemptData currentAttempt = getCurrentAttempt();
			
			if (currentAttempt == null || currentAttempt.isComplete())
				listOfAttempts.add(null);
			
			Game.getCurrentGame().enterState(associatedTask);
		}
		
	}
	
	public TaskData()
	{
		this.associatedHub = null;
		this.associatedTask = null;
		this.taskIcon = null;
		
		this.listOfAttempts = new ArrayList<>();
		this.complete = false;
		this.accessible = false;
		this.informationComponents = new ArrayList<>();
		
		listOfAttempts.add(null);
	}
	
	public TaskData(Class<?> associatedTask, TaskScreen associatedHub,
			Rectangle relativeTextBounds, String name) throws SlickException
	{
		this();
		this.associatedHub = associatedHub;
		this.associatedTask = associatedTask;
		boolean writeOnButton = TaskScreenDefaults.WRITE_TO_BUTTONS;
		this.taskIcon = new Button(DEFAULT_IMAGE, 0, 0, relativeTextBounds,
				writeOnButton ? name : null);
		
		populateInformationComponents(name, informationComponents);
		taskIcon.addActionListener(new MultiTaskListener());
	}
	
	public TaskData(Class<?> associatedTaskScreen, TaskScreen taskScreen,
			float textBounds, String name) throws SlickException
	{
		this(associatedTaskScreen, taskScreen, UtilFunctions.dialateRectangle(
				DEFAULT_IMAGE, textBounds), name);
	}
	
	public void populateInformationComponents(String name,
			ArrayList<Component> informationComponents) throws SlickException
	{
		informationComponents.clear();
		// Inaccessible Text Area
		Rectangle relativeBounds = new Rectangle(0, 0, 400, 300);
		inaccessibleText = new TextAreaX(relativeBounds, 0.92f,
				Labels.TaskScreen.INACCESSIBLE_TEXT.getTranslation());
		inaccessibleText.setFontSize(Fonts.FONT_SIZE_LARGE);
		inaccessibleText.setFontColor(Color.white);
		
		// Name Label
		relativeBounds = new Rectangle(0, 0, 400, 100);
		TextField nameLabel = new TextField(relativeBounds, 0.92f, name,
				TextDisplay.FormattingOption.FIT_TEXT);
		nameLabel.center();
		nameLabel.setFontColor(Color.white);
		informationComponents.add(nameLabel);
		
		// Progress Bar
		relativeBounds = new Rectangle(0, 100, 400, 100);
		progressBar = new ProgressBar(relativeBounds);
		progressBar.setImages(ImagePaths.TaskScreen.PROGRESS_BAR_FULL,
				ImagePaths.TaskScreen.PROGRESS_BAR_EMPTY,
				ImagePaths.TaskScreen.PROGRESS_BAR_BORDER);
		informationComponents.add(progressBar);
		
		// Ready/Replay Button
		Image replayButtonImage = new Image(ImagePaths.Buttons.BASE);
		Rectangle imageBounds = UtilFunctions.getImageBounds(replayButtonImage);
		int x = 400 - imageBounds.width;
		imageBounds = UtilFunctions.dialateRectangle(imageBounds, 0.80f);
		comboButton = new Button(replayButtonImage, x, 220, imageBounds,
				"Start");
		comboButton.addActionListener(new ReplayContinueComboListener());
		comboButton.setFontColor(Fonts.BUTTON_FONT_COLOR);
		informationComponents.add(comboButton);
		
		// Percentage Label
		Rectangle textFieldBounds = new Rectangle(0, 220, x, imageBounds.height);
		percentageLabel = new TextField(textFieldBounds, 0.80f, "0%",
				TextDisplay.FormattingOption.FIT_TEXT);
		percentageLabel.setFontColor(Fonts.FONT_COLOR);
		percentageLabel.center();
		informationComponents.add(percentageLabel);
		
		// TODO Add Star Score
	}
	
	private void setImages(Image inaccessible, Image complete, Image accessible)
	{
		this.buttonImageAccessible = accessible;
		this.buttonImageComplete = complete;
		this.buttonImageInaccessible = inaccessible;
		
		updateImage();
	}
	
	public void setImages(String inaccessible, String complete,
			String accessible) throws SlickException
	{
		this.buttonImageAccessiblePath = accessible;
		this.buttonImageCompletePath = complete;
		this.buttonImageInaccessiblePath = inaccessible;
		instantiateImages();
	}
	
	public void instantiateImages() throws SlickException
	{
		this.setImages(new Image(buttonImageInaccessiblePath), new Image(
				buttonImageCompletePath), new Image(buttonImageAccessiblePath));
	}
	
	protected void updateImage()
	{
		if (complete)
			this.taskIcon.setCurrentImage(buttonImageComplete, true);
		else if (accessible)
			this.taskIcon.setCurrentImage(buttonImageAccessible, true);
		else
			this.taskIcon.setCurrentImage(buttonImageInaccessible, true);
	}
	
	public void setComplete(boolean complete)
	{
		boolean update = (this.complete != complete);
		
		this.complete = complete;
		
		if (update)
			updateImage();
	}
	
	public void setAccessible(boolean accessible)
	{
		boolean update = (this.accessible != accessible);
		
		this.accessible = accessible;
		
		if (update)
			updateImage();
	}
	
	public Button getTaskIcon()
	{
		return taskIcon;
	}
	
	public AttemptData getCurrentAttempt()
	{
		// Account for uninstantiated list
		if (listOfAttempts == null)
		{
			listOfAttempts = new ArrayList<>();
			listOfAttempts.add(null);
		}
		
		return listOfAttempts.get(listOfAttempts.size() - 1);
	}
	
	public void setCurrentAttempt(AttemptData attemptData)
	{
		listOfAttempts.set(listOfAttempts.size() - 1, attemptData);
	}
	
	public void addAttempt(AttemptData attemptData)
	{
		listOfAttempts.add(attemptData);
	}
	
	public ArrayList<AttemptData> getListOfAttempts()
	{
		return listOfAttempts;
	}
	
	public boolean isComplete()
	{
		for (AttemptData attempt : listOfAttempts)
		{
			if (attempt != null && attempt.isComplete())
			{
				complete = true;
				break;
			}
		}
		
		return complete;
	}
	
	public boolean isAccessible()
	{
		return accessible;
	}
	
	public ArrayList<Component> getInformationComponents()
	{
		return informationComponents;
	}
	
	public TextArea getInaccessibleText()
	{
		return inaccessibleText;
	}
	
	public void reload()
	{
		try
		{
			instantiateImages();
			associatedHub = (TaskScreen) Game.getGameState(TaskScreen.class);
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateInformation()
	{
		if (this.progressBar != null && getCurrentAttempt() != null)
		{
			int percent = getCurrentAttempt().getPercentCompletion();
			progressBar.setPercentComplete(percent);
			percentageLabel.setText(percent + "%");
			
			String buttonText;
			if (getCurrentAttempt().isComplete())
				buttonText = "Retry";
			else
				buttonText = "Continue";
			
			comboButton.getTextField().setText(buttonText);
		}
		else
		{
			comboButton.getTextField().setText("Begin");
		}
	}

	public Class<?> getAssociatedTask()
	{
		return associatedTask;
	}
	
	
	
}
