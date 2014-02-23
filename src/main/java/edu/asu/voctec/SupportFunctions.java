package edu.asu.voctec;

import edu.asu.voctec.GameDefaults.Fonts;
import edu.asu.voctec.GameDefaults.IntroductionScreenDefaults;
import edu.asu.voctec.GUI.TextAreaX;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.information.AttemptData;
import edu.asu.voctec.information.TaskData;

public class SupportFunctions
{
	public static TextField generateWelcomeLabel(String text)
	{
		TextField welcomeLabel = new TextField(
				IntroductionScreenDefaults.TITLE_BOUNDS, 0.95f, text,
				TextDisplay.FormattingOption.CLIP_TEXT);
		welcomeLabel.setFontSize(Fonts.FONT_SIZE_EXTRA_LARGE);
		welcomeLabel.setFontColor(Fonts.FONT_COLOR);
		welcomeLabel.center();
		
		return welcomeLabel;
	}
	
	public static TextAreaX generateIntroductionDisplay(String text)
	{
		TextAreaX introductionText = new TextAreaX(
				IntroductionScreenDefaults.BODY_TEXT_BOUNDS, 0.95f, text);
		introductionText.setFontSize(Fonts.FONT_SIZE_MEDIUM);
		introductionText.setFontSize(Fonts.FONT_SIZE_LARGE);
		introductionText.setFontColor(Fonts.FONT_COLOR);
		
		return introductionText;
	}
	
	public static void ensureAttemptData()
	{
		TaskData currentTask = Game.getCurrentTask();
		AttemptData currentAttempt = currentTask.getCurrentAttempt();
		if (currentAttempt == null)
			currentTask.setCurrentAttempt(new AttemptData());
	}
}
