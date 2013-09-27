package com.android.icecave.guiLogic;

import android.graphics.Bitmap;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDirection;

public class PlayerGUIManager
{
	/**
	 * Create a new instance of the player GUI manager.
	 */
	public PlayerGUIManager () {
		
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
							      boolean isMoving,
							      Bitmap theme) {
		Bitmap result;
		
		// Get the width and height of each player sprite
		int width = theme.getWidth() / Consts.DEFAULT_PLAYER_BMP_COLUMNS;
		int height = theme.getHeight() / Consts.DEFAULT_PLAYER_BMP_ROWS;
		
		// TODO Finish up here..
		result = Bitmap.createBitmap(theme,
				0,
				0,
				width,
				height);
				
		return result;
	}
}
