package edu.asu.voctec.utilities;

/**
 * Represents an object that can be finalized. In other words, the user can call
 * the makeFinal() method to ensure that no future changes are made to the
 * desired object. Once the makeFinal() method is called, the object will remain
 * immutable for the remainder of its existence. 
 * 
 * @author Moore, Zachary
 *
 */
public interface Finalizable
{
	public void makeFinal();
}
