package edu.asu.voctec.information;

import java.util.ArrayList;

import edu.asu.voctec.GUI.SelectorIcon;
import edu.asu.voctec.utilities.CircularList;

public class SizingStepsData extends AttemptData
{
	private static final long serialVersionUID = 7276084073137860427L;
	
	private ArrayList<SelectorIcon> selectorDisplayContents;
	private CircularList<SelectorIcon> selectorContents;
	private ArrayList<String> currentHints;
	private boolean partOneComplete;
	
	public SizingStepsData(ArrayList<SelectorIcon> selectorDisplayContents,
			CircularList<SelectorIcon> selectorContents,
			ArrayList<String> currentHints, boolean stepsVerified)
	{
		super();
		this.selectorDisplayContents = selectorDisplayContents;
		this.selectorContents = selectorContents;
		this.currentHints = currentHints;
		this.partOneComplete = stepsVerified;
	}
	
	public ArrayList<SelectorIcon> getSelectorDisplayContents()
	{
		return selectorDisplayContents;
	}
	
	public void setSelectorDisplayContents(
			ArrayList<SelectorIcon> selectorDisplayContents)
	{
		this.selectorDisplayContents = selectorDisplayContents;
	}
	
	public CircularList<SelectorIcon> getSelectorContents()
	{
		return selectorContents;
	}
	
	public void setSelectorContents(CircularList<SelectorIcon> selectorContents)
	{
		this.selectorContents = selectorContents;
	}
	
	public ArrayList<String> getCurrentHints()
	{
		return currentHints;
	}
	
	public void setCurrentHints(ArrayList<String> currentHints)
	{
		this.currentHints = currentHints;
	}

	public boolean isPartOneComplete()
	{
		return partOneComplete;
	}

	public void setPartOneComplete(boolean stepsVerified)
	{
		this.partOneComplete = stepsVerified;
	}
	
}
