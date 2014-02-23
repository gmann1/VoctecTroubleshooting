package edu.asu.voctec.utilities;

import java.awt.Dimension;

public interface Resizable
{
	/**
	 * This is equivalent to a call to {@link #rescale(float, float)}, where the
	 * horizontal and vertical scales are equal.
	 * 
	 * @param scale
	 *            The multiplier by which to scale this object.
	 * @return Whether or not the scaling was successful.
	 * @see #rescale(float, float)
	 */
	public boolean rescale(float scale);
	
	/**
	 * Resize and Relocate the object using the specified scales. If either
	 * scale is invalid (i.e. <= 0), then this object will maintain its current
	 * size, and this method will return false to indicate that the scaling was
	 * not successful.
	 * 
	 * @param horizontalScale
	 *            The multiplier by which to scale the horizontal components of
	 *            this object (i.e. x-location and width)
	 * @param verticalScale
	 *            The multiplier by which to scale the vertical components of
	 *            this object (i.e y-location and height)
	 * @return Whether or not the scaling was successful.
	 */
	public boolean rescale(float horizontalScale, float verticalScale);
	
	/**
	 * Resize this object to the given width and height, then reposition the
	 * object proportional to the changes in width and height.
	 * 
	 * Protocol: Determine the appropriate scales to achieve the provided width
	 * and height for this object, then rescale (i.e. resize and relocate) the
	 * object using those scales.
	 * 
	 * @param width
	 *            The desired width of this object
	 * @param height
	 *            The desired height of this object
	 * @return Whether or not the resizing was successful.
	 */
	public boolean rescale(int width, int height);
	
	/**
	 * Resize this object, but do not reposition it (i.e. x and y values will be
	 * unaffected).
	 * 
	 * If the object's position is relevant, and it will not be repositioned
	 * manually, consider a call to {@link #rescale(int, int)} instead.
	 * 
	 * @param width
	 *            The desired width of this object
	 * @param height
	 *            The desired height of this object
	 * @return Whether or not the resizing was successful.
	 * @see #rescale(int, int)
	 */
	public boolean resize(int width, int height);
	
	/**
	 * Included for completeness.
	 * 
	 * @see #resize(int, int)
	 */
	public boolean resize(Dimension dimension);
}
