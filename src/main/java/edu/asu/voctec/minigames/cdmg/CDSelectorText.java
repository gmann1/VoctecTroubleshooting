package edu.asu.voctec.minigames.cdmg;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.asu.voctec.GUI.SelectorIcon;
import edu.asu.voctec.GUI.TextField;
import edu.asu.voctec.utilities.UtilFunctions;

/**
 * 
 * @author Gabriel Mann
 *	NOTE: CLASS NOT USED!!
 */

public class CDSelectorText extends SelectorIcon
{
	public static final String TRANSPARENT_IMAGE_PATH = "resources/default/img/minigames/criticalDesign/transparent.png";
	ArrayList<TextField> textfields = new ArrayList<>();
	
	public CDSelectorText(TextField textfield1, TextField textfield2,
			TextField textfield3, int width, int height) throws SlickException
	{
		super(new Image(TRANSPARENT_IMAGE_PATH).getScaledCopy(width, height),
				0, 0, "textfield", 0);
		int delta = textfield1.getBounds().height;
		
		textfields.add(textfield1);
		textfields.add(textfield2);
		textfields.add(textfield3);
		Rectangle centerBounds = new Rectangle(textfield2.getBounds());
		Rectangle bounds = new Rectangle(0, 0, width, height);
		UtilFunctions.centerRectangle(bounds, centerBounds);
		
		// assume all textfields are same size
		
		textfield1.setBounds(centerBounds);
		textfield2.setBounds(centerBounds);
		textfield3.setBounds(centerBounds);
		
		textfield1.translate(0, -delta);
		textfield3.translate(0, delta);
		
	}
	
	@Override
	public void draw(Graphics graphics)
	{
		System.out.println("CDSelectorText: drawing...");
		super.draw(graphics);
		for (TextField tf : textfields)
		{
			
			if (tf != null)
			{
				tf.draw(graphics);
				System.out.println("\tCDSelectorText: textField drawn.");
			}
		}
	}
	
	@Override
	public boolean setBounds(Rectangle bounds)
	{
		super.setBounds(bounds);
		for (TextField textField : textfields)
		{
			textField.resize(bounds.width, bounds.height / 4);
		}
		Rectangle centerBounds = new Rectangle(textfields.get(1).getBounds());
		
		UtilFunctions.centerRectangle(bounds, centerBounds);
		
		// assume all textfields are same size
		
		textfields.get(0).setBounds(centerBounds);
		textfields.get(1).setBounds(centerBounds);
		textfields.get(2).setBounds(centerBounds);
		
		textfields.get(0).translate(0, -textfields.get(0).getBounds().height);
		textfields.get(2).translate(0, textfields.get(0).getBounds().height);
		
		return true;
	}
	
	@Override
	public boolean resize(Dimension dimension)
	{
		
		return setBounds(new Rectangle(this.getX(), this.getY(),
				dimension.width, dimension.height));
	}
	
}
