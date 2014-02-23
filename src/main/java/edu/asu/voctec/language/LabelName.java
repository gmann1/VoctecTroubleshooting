package edu.asu.voctec.language;

import java.util.HashMap;

import edu.asu.voctec.Game;

public class LabelName
{
	private static HashMap<String, LabelName> labelsByXMLListing = new HashMap<>();
	public static final LabelName startButton = new LabelName("startButton",
			"Start");
	public static final LabelName optionsButton = new LabelName(
			"optionsButton", "Options");
	public static final LabelName replayButton = new LabelName("replayButton",
			"Replay");
	public static final LabelName returnButton = new LabelName("returnButton",
			"Return");
	
	public final String xmlListing;
	public final String defaultTranslation;
	
	public LabelName(String xmlListing, String defaultTranslation)
	{
		this.xmlListing = xmlListing.toLowerCase();
		this.defaultTranslation = defaultTranslation;
		labelsByXMLListing.put(xmlListing.toLowerCase(), this);
	}
	
	private static HashMap<String, LabelName> getLabelsByXMLListing()
	{
		return labelsByXMLListing;
	}
	
	public static LabelName getLabelNameByXMLListing(String xmlID)
	{
		return getLabelsByXMLListing().get(xmlID.toLowerCase());
	}
	
	public String getTranslation()
	{
		String translation;
		Dictionary currentLanguage = Game.getCurrentLanguage();
		
		if (currentLanguage == null || currentLanguage.get(this) == null)
			translation = this.defaultTranslation;
		else
			translation = currentLanguage.get(this);
		
		return translation;
	}
}
