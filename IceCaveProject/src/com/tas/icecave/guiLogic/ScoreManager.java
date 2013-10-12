package com.tas.icecave.guiLogic;

import com.android.icecave.R;

public class ScoreManager
{
	private int mOverAllMinMoves;

	public ScoreManager()
	{
		mOverAllMinMoves = 0;
	}
	
	public int getOverAllMinMoves() {
		return mOverAllMinMoves;
	}

	public void updateOverAllMinMoves(int newStageMinMoves)
	{
		mOverAllMinMoves += newStageMinMoves;
	}

	/**
	 * Receive the color of the percent data
	 * 
	 * @param percent
	 *            Percent of how close the player is to the minimum required moves
	 * @return Color according to accuracy
	 */
	public int getColorByTotalMoves(int totalPlayerMoves, int totalMinMoves)
	{
		final int PERCENT_HIGH = 80;
		final int PERCENT_MED = 60;
		int colorId = R.color.light_blue_icy;

		double percent = getPercent(totalPlayerMoves, totalMinMoves);

		if (percent >= PERCENT_HIGH)
		{
			colorId = R.color.light_blue_icy;
		} else if (percent >= PERCENT_MED)
		{
			colorId = R.color.android_purple_dark;
		} else
		{
			colorId = R.color.red;
		}

		return colorId;
	}

	/**
	 * 
	 * @param totalPlayerMoves
	 * @param totalMinMoves
	 * @return Percent of how close the player is to the minimum required moves
	 */
	public String getTotalMovesPercent(int totalPlayerMoves, int totalMinMoves)
	{
		
		double percent = getPercent(totalPlayerMoves, totalMinMoves);

		// Show number with 2 digits after decimal point and a "%" at the end
		String result =
				Double.toString(percent).substring(0, Double.toString(percent).indexOf(".") + 2) + "%";

		return result;
	}
	
	private double getPercent(int totalPlayerMoves, int totalMinMoves) {
		// Get percent of total moves in player moves. If player moves is less than total min put 100%
		return (totalPlayerMoves > totalMinMoves) ? ((totalMinMoves * 100.0) / totalPlayerMoves) : 100.0;
	}

	/**
	 * Color the player moves of the current stage
	 * 
	 * @param playerMoves
	 * @param minMoves
	 * @return White if the player is below or equal to the minimum moves, orange if more
	 */
	public int getColorOverMinMoves(int playerMoves, int minMoves)
	{
		return (playerMoves <= minMoves) ? R.color.white : R.color.orange;
	}
}
