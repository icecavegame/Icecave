package com.android.icecave.general;

/**
 * Enum representing the difficulty of the game.
 * @author Tom
 *
 */
public enum EDifficulty
{
	Easy(4),
	Medium(17),
	Hard(35),
	VeryHard(48);
	
	/**
	 * Min number of moves for the difficulty.
	 */
	int mMinMoves;
	
	/**
	 * Create a new instance of an EDifficulty object.
	 * @param minSteps - Min steps to take to solve the stage in the difficulty.
	 */
	private EDifficulty(int minSteps){
		mMinMoves = minSteps;
	}
	
	/**
	 * Get the min number of moves for this difficulty level.
	 * @return Min number of moves for this difficulty level.
	 */
	public int getMinMoves()
	{
		return mMinMoves;
	}
	
	/**
	 * Get the max number of moves for this difficulty level.
	 * @return Max number of moves for this difficulty level.
	 */
	public int getMaxMoves()
	{
		int nMaxMoves = Integer.MAX_VALUE;
		
		switch (this)
		{
			case Easy:
			{
				nMaxMoves = EDifficulty.Medium.getMinMoves();
				break;
			}
			case Medium:
			{
				nMaxMoves = EDifficulty.Hard.getMinMoves();
				break;
			}
			default:
				break;
		}
		
		return nMaxMoves;
	}
}
