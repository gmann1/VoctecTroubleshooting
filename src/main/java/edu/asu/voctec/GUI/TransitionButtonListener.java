package edu.asu.voctec.GUI;

import edu.asu.voctec.Game;

public class TransitionButtonListener extends ButtonListener
{
	private static final long serialVersionUID = -3113125282264208671L;
	
	private Class<?> transitionScreen;
	
	public TransitionButtonListener(Class<?> transitionScreen)
	{
		this.transitionScreen = transitionScreen;
	}
	
	@Override
	protected void actionPerformed()
	{
		Game.getCurrentGame().enterState(transitionScreen);
	}
	
}
