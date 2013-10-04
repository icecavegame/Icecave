package com.android.icecave.gui;

import android.graphics.Bitmap;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDirection;
import com.android.icecave.guiLogic.theme.ThemeMap;

/**
 * Class to represent the over all game theme.
 * 
 * @author Tom
 * 
 */
public class GameTheme
{
	private Bitmap mTilesTheme;
	private Bitmap mPlayerTheme;
	private ThemeMap mMap;

	/**
	 * Create a new instance of the GameTheme object.
	 * 
	 * @param tilesTheme
	 *            - The current tiles theme.
	 * @param playerTheme
	 *            - The current player theme.
	 */
	public GameTheme(Bitmap tilesTheme, Bitmap playerTheme)
	{
		mTilesTheme = tilesTheme;
		mPlayerTheme = playerTheme;
		mMap = new ThemeMap();
	}

	/**
	 * Get the tiles theme of the game.
	 * 
	 * @return
	 */
	public Bitmap getTilesTheme()
	{
		return mTilesTheme;
	}

	/**
	 * Get the player theme of the game.
	 * 
	 * @return
	 */
	public Bitmap getPlayerTheme()
	{
		return mPlayerTheme;
	}
	
	/**
	 * Get the map theme of the tiles.
	 * 
	 * @return
	 */
	public ThemeMap getThemeMap()
	{
		return mMap;
	}

	/***
	 * 
	 * @param direction
	 *            Direction of where the player is moving towards
	 * @return Index of the row of the tiles with the given direction
	 */
	public int getPlayerDirectionRow(EDirection direction)
	{		
		int result;
		
		switch (EDirection.values()[direction.ordinal()])
		{
		case DOWN:
			result = Consts.PLAYER_DOWN_ROW;
			break;
		case LEFT:
			result = Consts.PLAYER_LEFT_ROW;
			break;
		case RIGHT:
			result = Consts.PLAYER_RIGHT_ROW;
			break;
		case UP:
			result = Consts.PLAYER_UP_ROW;
			break;
		default:
			result = Consts.PLAYER_ERROR_ROW;
			break;
		}
		
		return result;
	}
}
