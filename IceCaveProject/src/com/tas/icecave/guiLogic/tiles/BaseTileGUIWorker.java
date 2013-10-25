package com.tas.icecave.guiLogic.tiles;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.tas.icecave.guiLogic.ITileScale;
import com.tas.icecaveLibrary.mapLogic.tiles.ITile;
import com.tas.icecaveLibrary.utils.Point;

public abstract class BaseTileGUIWorker implements IGUITileWorker
{
	@Override
	public Bitmap makeTile(ITileScale scaler,
			Bitmap theme,
			int themeRows,
			int themeCols,
			ITile[] tiles,
			Point tilePositionInTheme)
	{
		int width = theme.getWidth() / themeCols;
		int height = theme.getHeight() / themeRows;

		// Crop theme bitmap to select the specific tile wanted
		Bitmap croppedTile =
				Bitmap.createBitmap(theme,
									width * tilePositionInTheme.x,
									height * tilePositionInTheme.y,
									width, 
									height);

		// Create matrix to change the scale of the bitmap to fit the screen
		Matrix matrix = new Matrix();
		matrix.postScale((float) scaler.getTileWidth() / width, 
						 (float) scaler.getTileHeight() / height);

		// Resize
		croppedTile = Bitmap.createBitmap(croppedTile, 0, 0, width, height, matrix, false);

		return croppedTile;
	}

	@Override
	public Bitmap makeTile(ITileScale scaler,
			Bitmap theme,
			int themeRows,
			int themeCols,
			Point tilePositionInTheme)
	{
		// Get the width and height of each tile
		int width = theme.getWidth() / themeCols;
		int height = theme.getHeight() / themeRows;

		// Crop theme bitmap to select the specific tile wanted
		Bitmap croppedTile =
				Bitmap.createBitmap(theme, width * tilePositionInTheme.x, height * tilePositionInTheme.y, width, height);

		// Create matrix to change the scale of the bitmap to fit the screen
		Matrix matrix = new Matrix();
		matrix.postScale((float) scaler.getTileWidth() / width, (float) scaler.getTileHeight() / height);

		// Resize
		croppedTile = Bitmap.createBitmap(croppedTile, 0, 0, width, height, matrix, false);

		return croppedTile;
	}
}
