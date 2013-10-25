package com.tas.icecave.guiLogic;


import android.graphics.Bitmap;

import com.tas.icecave.gui.GameTheme;
import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecave.guiLogic.tiles.BaseTileGUIWorker;
import com.tas.icecaveLibrary.general.Consts;
import com.tas.icecaveLibrary.general.EDirection;
import com.tas.icecaveLibrary.utils.Point;

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
							      GameTheme gameTheme,
							      ITileScale scaler) {
		// Select the current sprite of the player
		// First, if not moving, select the middle (standing) sprite of the appropriate direction
		if (!isMoving) {
			mCurrentSprite = 
					new Point(Consts.PLAYER_STANDING, 
							  gameTheme.getPlayerDirectionRow(direction));
		} else {
			// Select the next movement sprite in a cycle, and keep the same direction
			mCurrentSprite = 
					new Point(((mCurrentSprite.x + 1) % Consts.PLAYER_MOVEMENTS_SUM), 
							   gameTheme.getPlayerDirectionRow(direction));
		}
		
		return makeTile(scaler, 
						gameTheme.getPlayerTheme(), 
						Consts.DEFAULT_PLAYER_BMP_ROWS, 
						Consts.DEFAULT_PLAYER_BMP_COLUMNS,
						mCurrentSprite);
	}

	@Override
	public Point getRandomTileImage(ThemeMap themeMap)
	{
		return mCurrentSprite;
	}
}
