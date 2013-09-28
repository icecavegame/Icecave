package com.android.icecave.guiLogic;

import android.graphics.Matrix;

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
							      Bitmap theme,
							      ITileScale scaler) {
		Bitmap result;
		
		// Get the width and height of each player sprite
		int width = theme.getWidth() / Consts.DEFAULT_PLAYER_BMP_COLUMNS;
		int height = theme.getHeight() / Consts.DEFAULT_PLAYER_BMP_ROWS;
		
		// TODO Finish up here.. select the appropriate sprite for each movement
		result = Bitmap.createBitmap(theme,
				0,
				0,
				width,
				height);
		
		// Create matrix to change the scale of the bitmap to fit the screen
		Matrix matrix = new Matrix();
		matrix.postScale((float) scaler.getTileWidth() / width, (float) scaler.getTileHeight() / height);

		// Resize
		result = Bitmap.createBitmap(result, 0, 0, width, height, matrix, true);
				
		return result;
	}
}
