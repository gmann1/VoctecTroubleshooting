package voctec_sizing;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import edu.asu.voctec.utilities.CircularList;

public class CircularListTest
{
	@Test
	public void testStoredElements()
	{
		try
		{
			CircularList<String> stringList = new CircularList<>();
			Assert.assertEquals("current element of a new list should be null.",
					null, stringList.getCurrentElement());
			Assert.assertEquals("last element of a new list should be null.",
					null, stringList.getFirstElement());
			Assert.assertEquals("first element of a new list should be null.",
					null, stringList.getLastElement());
			
			stringList.add("first");
			stringList.add("second");
			stringList.add("third");
			if (stringList.getCurrentElement() == null)
				fail("current element is null, but list is not empty");
			else if(!stringList.getCurrentElement().equals("first"))
				fail("current element is not as expected");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("exception thrown");
		}
	}
	
	@Test
	public void testLinkedElements()
	{
		try
		{
			CircularList<String> stringList = new CircularList<>();
			String element1 = "first";
			String element2 = "second";
			String element3 = "third";
			
			stringList.add(element1);
			stringList.add(element2);
			stringList.add(element3);
			
			Assert.assertEquals("middle element not as expected", element2, 
					stringList.next());
			
			stringList.next();
			
			if (stringList.getCurrentElement() == null)
				fail("current element is null, but list is not empty");
			else if(!stringList.getCurrentElement().equals(element3))
				fail("current element is not as expected");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("exception thrown");
		}
	}
	
	@Test
	public void testCircularLoop()
	{
		try
		{
			CircularList<String> stringList = new CircularList<>();
			String element1 = "first";
			String element2 = "second";
			String element3 = "third";
			
			stringList.add(element1);
			stringList.add(element2);
			stringList.add(element3);
			
			Assert.assertEquals("middle element not as expected", element2, 
					stringList.next());
			Assert.assertEquals("third element not as expected", element3, 
					stringList.next());
			Assert.assertEquals("list does not loop", element1, 
					stringList.next());
			
			stringList.next();
			
			if (stringList.getCurrentElement() == null)
				fail("current element is null, but list is not empty");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("exception thrown");
		}
	}
	
	@Test
	public void testAddFirst()
	{
		try
		{
			CircularList<String> stringList = new CircularList<>();
			String element1 = "first";
			String element2 = "second";
			String element3 = "third";
			
			stringList.addFirst(element1);
			stringList.addFirst(element2);
			stringList.addFirst(element3);
			
			Assert.assertEquals("current element not as expected", element1, 
					stringList.getCurrentElement());
			Assert.assertEquals("last element not as expected", element1, 
					stringList.getLastElement());
			Assert.assertEquals("element order not as expected", element3, 
					stringList.next());
			
			stringList.next();
			
			if (stringList.getCurrentElement() == null)
				fail("current element is null, but list is not empty");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("exception thrown");
		}
	}
	
}
