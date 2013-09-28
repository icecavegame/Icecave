package com.android.icecave.guiLogic;

import android.graphics.Bitmap;
import android.graphics.Point;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDirection;
import com.android.icecave.guiLogic.tiles.BaseTileGUIWorker;

public class PlayerGUIManager extends BaseTileGUIWorker
{
	private Point mCurrentSprite;
	
	/**
	 * Create a new instance of the player GUI manager.
	 */
	public PlayerGUIManager () {
		mCurrentSprite = new Point();
	}
	
	/**
	 * Get the cropped bitmap of the player to display.
	 * @param row - Row in the board to display the player.
	 * @param col - Column in the board to display the player.
	 * @param direction 
	 * @param isMoving
	 * @return
	 */
	public Bitmap getPlayerImage (EDirection direction, 
							      boolean isMoving,
							      Bitmap theme,
							      ITileScale scaler) {
		// Select the current sprite of the player
		
		
		return makeTile(scaler, theme, Consts.DEFAULT_PLAYER_BMP_ROWS, Consts.DEFAULT_PLAYER_BMP_COLUMNS);
	}

	@Override
	public Point getTilePointInSprite()
	{
		return mCurrentSprite;
	}
}
