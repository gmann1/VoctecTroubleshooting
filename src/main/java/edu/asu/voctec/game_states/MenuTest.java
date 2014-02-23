package edu.asu.voctec.game_states;

import java.awt.Rectangle;
import java.util.Arrays;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import edu.asu.voctec.GUI.Selector;
import edu.asu.voctec.GUI.SelectorIcon;
import edu.asu.voctec.GUI.TextArea;
import edu.asu.voctec.GUI.TextDisplay;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.GameDefaults.ImagePaths.SelectorIcons;

public class MenuTest extends GUI
{
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException
	{
		Rectangle textLocation = new Rectangle(88, 25, 300, 50);
		TextField textField = new TextField(textLocation, 0.95f,
				"Cliped Text Field ... CLIP CLIP CLIP",
				TextDisplay.FormattingOption.CLIP_TEXT);
		
		textLocation.setLocation(88, 125);
		TextField textField2 = new TextField(textLocation, 0.95f,
				"Fit Text Field ... CLIP CLIP CLIP",
				TextDisplay.FormattingOption.FIT_TEXT);
		
		textLocation.setLocation(88, 225);
		TextField textField3 = new TextField(textLocation, 0.95f,
				"Vert Fit Text Field ... CLIP CLIP CLIP",
				TextDisplay.FormattingOption.FIT_TEXT_VERTICALLY);
		
		textLocation.setSize(300, 250);
		textLocation.setLocation(408, 25);
		TextArea textArea = new TextArea(textLocation, 0.95f,
				"Cliped Text Field ... CLIP CLIP CLIP ... Fit Text Field ... CLIP CLIP CLIP ... Vert Fit Text Field ... CLIP CLIP CLIP");
		
		textField.enableBorder();
		textField2.enableBorder();
		textField3.enableBorder();
		textArea.enableBorder();
		textArea.setFontSize(8f);
		textField.setFontSize(10f);
		textField.center();
		textField2.center();
		textField3.center();
		
		this.addComponent(textField);
		this.addComponent(textField2);
		this.addComponent(textField3);
		this.addComponent(textArea);
		
		Selector<SelectorIcon> selector = new Selector<>(100, 290, true);
		selector.add(new SelectorIcon(SelectorIcons.ENERGY_ASSESSMENT,
				"Energy Assessment", 0));
		selector.add(new SelectorIcon(SelectorIcons.CRITICAL_DESIGN_MONTH,
				"Determine the Critical Design Month", 1));
		selector.add(new SelectorIcon(SelectorIcons.BATTERY_SIZING,
				"Size the Battery", 2));
		this.addComponent(selector);
		
		System.out
				.println("Listeners: " + Arrays.toString(this.getListeners()));
	}
	
}
