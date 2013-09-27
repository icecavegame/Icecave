package com.android.icecave.guiLogic;

import android.graphics.Bitmap;
import com.android.icecave.general.EDirection;

public class PlayerGUIManager
{
	/**
	 * Create a new instance of the player GUI manager.
	 * @param playerTileSetFile - Path file for the player tile set.
	 */
	public PlayerGUIManager (Bitmap playerTileSetFile) {
		
	}
	
	/**
	 * Get the cropped bitmap of the player to display.
	 * @param row - Row in the board to display the player.
	 * @param col - Column in the board to display the player.
	 * @param direction 
	 * @param isMoving
	 * @return
	 */
	public Bitmap getPlayerImage (int row,
								  int col,
							 	  EDirection direction, 
							      boolean isMoving) {
		return null;
	}
}
