package edu.asu.voctec.game_states;

import java.awt.Dimension;

import org.newdawn.slick.state.BasicGameState;

import edu.asu.voctec.GameDefaults;
import edu.asu.voctec.utilities.Resizable;
import edu.asu.voctec.utilities.Translatable;

public abstract class ModifiedGameState extends BasicGameState implements
		Resizable, Translatable, GameDefaults
{
	private static int currentID = 0;
	private int ID;
	
	public abstract Dimension getDesignResolution();
	
	public abstract void onEnter();
	
	public abstract void onExit();
	
	public ModifiedGameState()
	{
		super();
		
		// Initialize ID. This ensures that all IDs for GUI objects are unique.
		this.ID = currentID;
		currentID++;
	}
	
	/**
	 * This method serves as a secondary initiation. Where the
	 * {@link #init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)}
	 * function is called before the game starts, the
	 * {@link #initiateDeffered()} method is called while the introduction
	 * animation plays, or on the instruction screen for the game (depending on
	 * the circumstances of the child class).
	 */
	public abstract void initiateDeffered();
	
	@Override
	public final int getID()
	{
		return ID;
	}
	
	@Override
	public void updateTranslation()
	{
		// TODO Auto-generated method stub
		// TODO REMOVE
	}
	
	@Override
	public boolean rescale(float scale)
	{
		// TODO Auto-generated method stub
		// TODO REMOVE
		return false;
	}
	
	@Override
	public boolean rescale(float horizontalScale, float verticalScale)
	{
		// TODO Auto-generated method stub
		// TODO REMOVE
		return false;
	}
	
	@Override
	public boolean rescale(int width, int height)
	{
		// TODO Auto-generated method stub
		// TODO REMOVE
		return false;
	}
	
	@Override
	public boolean resize(int width, int height)
	{
		// TODO Auto-generated method stub
		// TODO REMOVE
		return false;
	}
	
	@Override
	public boolean resize(Dimension dimension)
	{
		// TODO Auto-generated method stub
		// TODO REMOVE
		return false;
	}
	
}
