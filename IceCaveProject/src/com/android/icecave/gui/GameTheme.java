package com.android.icecave.gui;

import android.graphics.Bitmap;

/**
 * Class to represent the over all game theme.
 * @author Tom
 *
 */
public class GameTheme
{
	private Bitmap mTilesTheme;
	private Bitmap mPlayerTheme;

	/**
	 * Create a new instance of the GameTheme object.
	 * @param tilesTheme - The current tiles theme.
	 * @param playerTheme - The current player theme.
	 */
	public GameTheme(Bitmap tilesTheme, Bitmap playerTheme) {
		mTilesTheme = tilesTheme;
		mPlayerTheme = playerTheme;
	}
	
	/**
	 * Get the tiles theme of the game.
	 * @return
	 */
	public Bitmap getTilesTheme() {
		return mTilesTheme;
	}
	
	/**
	 * Get the player theme of the game.
	 * @return
	 */
	public Bitmap getPlayerTheme() {
		return mPlayerTheme;
	}

}
