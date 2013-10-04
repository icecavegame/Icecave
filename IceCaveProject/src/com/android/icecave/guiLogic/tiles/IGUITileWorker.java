package com.android.icecave.guiLogic.tiles;

import android.graphics.Bitmap;
import com.android.icecave.guiLogic.ITileScale;
import com.android.icecave.mapLogic.tiles.ITile;

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
	                       ITile[] tiles);
	
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
	                       int themeCols);
}
