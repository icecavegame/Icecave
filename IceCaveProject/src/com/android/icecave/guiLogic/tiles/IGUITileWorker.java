package com.android.icecave.guiLogic.tiles;

import android.content.Context;
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
	 * @return
	 */
	public Bitmap makeTile(ITile tile,
	                              Context activityWindow,
	                              ITileScale scale,
	                              Bitmap theme);
}
