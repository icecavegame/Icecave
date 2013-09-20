package com.android.icecave.general;

public enum EDifficulty
{
	Easy,
	Medium,
	Hard;
	
	int mMinMoves;
	
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
