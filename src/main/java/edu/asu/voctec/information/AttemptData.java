package edu.asu.voctec.information;

import java.io.Serializable;

/**
 * Contains information related to a single attempt made by a user at a
 * mini-game.
 * 
 * @author Moore, Zachary
 * 
 */
public class AttemptData implements Comparable<AttemptData>, Serializable
{
	private static final long serialVersionUID = 6482578605941748609L;
	public static final int MAX_STARS = 6;
	public static final int MIN_STARS = 1;
	
	/** The number of unique hints the user has received, during this attempt */
	protected int numberOfUniqueHints;
	
	/** Time spent playing during this attempt (in milliseconds) */
	protected long timeSpent;
	
	/**
	 * How far the user has progressed in the associated task, relative to this
	 * attempt
	 */
	protected int percentCompletion;
	
	/**
	 * Arbitrary score, defined by each minigame. This will be normalized before
	 * it is displayed to the user.
	 */
	protected int score;
	
	/**
	 * Arbitrary score component, defined by each minigame. This will be used to
	 * normalize the 'score' field before it is displayed to the user.
	 */
	protected int maximumPossibleScore;
	
	/**
	 * No-arg constructor; initializes all values to their defaults (maxScore =
	 * 100). 
	 */
	public AttemptData()
	{
		this.numberOfUniqueHints = 0;
		this.timeSpent = 0;
		this.percentCompletion = 0;
		this.score = 0;
		this.maximumPossibleScore = 100;
	}
	
	/**
	 * Performs a comparison between two AttemptData objects based on the
	 * normalized score of each object. Returns a value <0 if this object's
	 * score is less than the other object; returns a value >0 if this object's
	 * score is greater than the other object; returns 0 if the normalized
	 * scores are equivalent.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AttemptData otherAttemptData)
	{
		return this.score - otherAttemptData.score;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getMaximumPossibleScore()
	{
		return maximumPossibleScore;
	}
	
	public void setMaximumPossibleScore(int maximumPossibleScore)
	{
		this.maximumPossibleScore = maximumPossibleScore;
	}
	
	public int getNumberOfUniqueHints()
	{
		return numberOfUniqueHints;
	}
	
	public long getTimeSpent()
	{
		return timeSpent;
	}
	
	public long addTime(long time)
	{
		this.timeSpent += time;
		
		return timeSpent;
	}
	
	public int addHints(int hints)
	{
		this.numberOfUniqueHints += hints;
		
		return numberOfUniqueHints;
	}
	
	public boolean isComplete()
	{
		return (percentCompletion >= 100);
	}
	
	public int getPercentCompletion()
	{
		return percentCompletion;
	}
	
	public void setPercentCompletion(int percentCompletion)
	{
		this.percentCompletion = percentCompletion;
	}
	
	public int calculateStarScore()
	{
		// Deduct one half-star for each hint used
		int numberOfStars = MAX_STARS - numberOfUniqueHints;
		
		return (numberOfStars <= MIN_STARS) ? MIN_STARS : numberOfStars;
	}
	
}
