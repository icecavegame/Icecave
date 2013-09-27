package com.android.icecave.guiLogic.tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import com.android.icecave.general.Consts;
import com.android.icecave.guiLogic.ITileScale;
import com.android.icecave.mapLogic.tiles.ITile;

public abstract class BaseTileGUIWorker implements IGUITileWorker
{

	public abstract Point getTilePointInSprite();

	@Override
	public Bitmap makeTile(ITile tile, Context activityWindow, ITileScale scaler, Bitmap theme)
	{
		// Get the width and height of each tile
		int width = theme.getWidth() / Consts.DEFAULT_TILES_BMP_COLUMNS;
		int height = theme.getHeight() / Consts.DEFAULT_TILES_BMP_ROWS;

		// Crop theme bitmap to select the specific tile wanted
		Bitmap croppedTile =
				Bitmap.createBitmap(theme, width * getTilePointInSprite().x, height *
						getTilePointInSprite().y, width, height);

		// Create matrix to change the scale of the bitmap to fit the screen
		Matrix matrix = new Matrix();
		matrix.postScale((float) scaler.getTileWidth() / width, (float) scaler.getTileHeight() / height);

		// Resize
		croppedTile = Bitmap.createBitmap(croppedTile, 0, 0, width, height, matrix, true);

		return croppedTile;
	}

}
