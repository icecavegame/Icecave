package com.android.icecave.guiLogic.tiles;

import android.graphics.Bitmap;
import com.android.icecave.guiLogic.ITileScale;

public interface IGUITileWorker
{
	/**
	 * Create a tiles.
	 * @param tile - The logic tile.
	 * @param context
	 * @param scale - Scaler for the tile size.
	 * @return
	 */
	public Bitmap makeTile(ITileScale scale,
	                       Bitmap theme,
	                       int themeRows,
	                       int themeCols);
}
