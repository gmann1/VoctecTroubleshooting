package edu.asu.voctec.utilities;

import java.awt.Dimension;
import java.awt.Rectangle;

public enum AspectRatio
{
	x16_9(16, 9), x4_3(4, 3);
	
	/**
	 * If ASPECT_RATIO_MARGIN > 0, then each AspectRatio can be used to
	 * represent dimensions that are *close* to the desired AspectRatio, using
	 * this constant as a MOE. For instance, if ASPECT_RATIO_MARGIN is 5, then
	 * an aspect ratio of 4:3 can be used to represent the dimension 800x600, as
	 * well as 805x605 and 795x600, etc. This constant should generally be 0,
	 * unless imprecise image resizing is being done outside of this class.
	 */
	public static final int ASPECT_RATIO_MARGIN = 0;
	
	public final int width;
	public final int height;
	
	private AspectRatio(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	// TODO abstract to apply to all ratios
	/**
	 * Finds a maximized window of the desired aspect ratio within the given
	 * baseScreen. For instance, if given a 1280x720 screen, and an aspect ratio
	 * of 4:3, this method will return a 960x720 window, centered in the
	 * baseScreen (new Rectangle(160, 0, 960, 720)).
	 * 
	 * At the moment, only 16:9 to 4:3 subsections are supported.
	 * 
	 * @param baseScreen
	 * @param subSectionAspectRatio
	 * @return
	 */
	public static Rectangle getSubSection(ScreenResolution baseScreen,
			AspectRatio subSectionAspectRatio)
			throws ResolutionNotSupportedException
	{
		// Ensure aspect ratios are supported
		if (!(baseScreen.getAspectRatio().equals(AspectRatio.x16_9))
				|| !(subSectionAspectRatio.equals(x4_3)))
		{
			throw new ResolutionNotSupportedException("getSubSection only "
					+ "supports 16:9 to 4:3. (Given: "
					+ baseScreen.getAspectRatio().toString() + " to "
					+ subSectionAspectRatio.toString());
		}
		else
		// Specified operation is supported
		{
			// TODO abstract to apply to all ratios
			// Get 4:3 window centered in 16:9 window
			// Determine scale of subSection
			// Because 3 maps to 9 sooner (3 steps) than 4 maps to 16
			// (4 steps), use height as a basis for scale.
			int scale = baseScreen.height / subSectionAspectRatio.height;
			// Determine width and height of subSection based on the calculated
			// scale.
			int width = subSectionAspectRatio.width * scale;
			int height = subSectionAspectRatio.height * scale;
			// Determine topleft corner of subSection in order to center the
			// subSection.
			int x = (baseScreen.width - width) / 2;
			int y = 0; // Because subSection height = baseScreen height, it will
						// be centered with y=0;
			
			return new Rectangle(x, y, width, height);
		}
	}
	
	public static AspectRatio getAspectRatio(int width, int height)
			throws ResolutionNotSupportedException
	{
		AspectRatio matchingAspectRatio = null;
		
		// TODO optimize search
		for (AspectRatio aspectRatio : AspectRatio.values())
		{
			int scale;
			
			// Ensure width corresponds to aspect ratio
			if ((width % aspectRatio.width) <= ASPECT_RATIO_MARGIN)
			{
				// TODO account for case in which height = ratio - MARGIN and
				// TODO_CONT width = ratio + MARGIN
				// Determine the constant that is modified by the aspect ratio
				// to obtain the desired resolution
				scale = width / aspectRatio.width;
				
				// Ensure height corresponds to aspect ratio and scale
				if ((height % aspectRatio.height) <= ASPECT_RATIO_MARGIN
						&& (height / aspectRatio.height) == scale)
				{
					// Found matching aspect ratio; stop searching
					matchingAspectRatio = aspectRatio;
					break;
				}
			}
		}
		
		// If a matchingAspectRatio was not found, this resolution is not
		// supported
		if (matchingAspectRatio == null)
			throw new ResolutionNotSupportedException(width, height);
		else
			return matchingAspectRatio;
		
	}
	
	public static AspectRatio getAspectRatio(Dimension dimension)
			throws ResolutionNotSupportedException
	{
		return getAspectRatio(dimension.width, dimension.height);
	}
	
	// TODO move to exceptions package
	public static class ResolutionNotSupportedException extends Exception
	{
		private static final long serialVersionUID = 1548486820456150649L;
		
		public ResolutionNotSupportedException(int width, int height)
		{
			super("This resolution is not supported: " + width + "x" + height);
		}
		
		public ResolutionNotSupportedException(String message)
		{
			super(message);
		}
	}
	
	@Override
	public String toString()
	{
		// WxH e.g. 16x9 or 4x3
		return "" + this.width + "x" + this.height;
	}
}
