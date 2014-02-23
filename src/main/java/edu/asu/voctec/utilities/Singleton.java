package edu.asu.voctec.utilities;

public interface Singleton
{
	/**
	 * Exception thrown when an attempt is made to create multiple instances of
	 * a singleton class.
	 * 
	 * @author Zach Moore
	 */
	public static class DuplicateInstantiationException extends Exception
	{
		private static final long	serialVersionUID	= 1L;
		
		/**
		 * Constructor.
		 * 
		 * @see DuplicateInstantiationException#DuplicateInstantiationException(String)
		 */
		public DuplicateInstantiationException()
		{
			this("attempt made to instantiate Singleton class more than once");
		}
		
		/**
		 * Constructor.
		 * 
		 * @see Exception#Exception(String)
		 */
		public DuplicateInstantiationException(String message)
		{
			super(message);
		}
		
		/**
		 * Constructor.
		 * 
		 * @see Exception#Exception(String, Throwable)
		 */
		public DuplicateInstantiationException(String message,
				Throwable throwable)
		{
			super(message, throwable);
		}
	}
}
