package com.tas.icecave.guiLogic.tiles;

import android.graphics.Bitmap;

import com.tas.icecave.guiLogic.ITileScale;
import com.tas.icecave.guiLogic.theme.ThemeMap;
import com.tas.icecaveGeneral.mapLogic.tiles.ITile;

public interface IGUITileWorker
{
	/**
	 * Create a tiles.
	 * @param tile - The logic tile.
	 * @param context
	 * @param scale - Scaler for the tile size.
	 * @param tiles - The tiles to find an image for.
	 * @return Bitmap image for the tiles.
	 */
	public Bitmap makeTile(ITileScale scale,
	                       Bitmap theme,
	                       int themeRows,
	                       int themeCols,
	                       ITile[] tiles,
	                       ThemeMap map);
	
	/**
	 * Create a tiles.
	 * @param tile - The logic tile.
	 * @param context
	 * @param scale - Scaler for the tile size.
	 * @return Bitmap image for the tiles.
	 */
	public Bitmap makeTile(ITileScale scale,
	                       Bitmap theme,
	                       int themeRows,
	                       int themeCols,
	                       ThemeMap map);
}
