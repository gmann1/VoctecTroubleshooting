package edu.asu.voctec.minigames.cdmg;

import java.awt.Rectangle;

/**
 * 
 * @author Gabriel Mann
 *
 */

import org.newdawn.slick.SlickException;

import edu.asu.voctec.GUI.Selector;
import edu.asu.voctec.GUI.SelectorIcon;

public class CDSelector<T extends SelectorIcon> extends Selector<T>
{
	public class RightListener extends RightArrowListener
	{
		
	}
	
	public class LeftListener extends LeftArrowListener
	{
		
	}
	
	public CDSelector(int x, int y) throws SlickException
	{
		super(x, y, false);
		this.addActionListener(new RightListener());
		this.addActionListener(new LeftListener());
	}
	
	public Rectangle getMainBounds()
	{
		return getAbsoluteBounds(currentChoiceBackground);
	}
	
	public Rectangle getLeftBounds()
	{
		return getAbsoluteBounds(previousChoiceBackground);
	}
	
	public Rectangle getRightBounds()
	{
		return getAbsoluteBounds(nextChoiceBackground);
	}
	
}
