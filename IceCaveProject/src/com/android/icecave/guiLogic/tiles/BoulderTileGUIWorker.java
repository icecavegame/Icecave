package com.android.icecave.guiLogic.tiles;

import android.graphics.Matrix;

import com.android.icecave.guiLogic.GUIScreenManager;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.icecave.general.Consts;
import com.android.icecave.general.GeneralServiceProvider;
import com.android.icecave.guiLogic.TileImageView;
import com.android.icecave.mapLogic.tiles.BaseTile;
import com.android.icecave.mapLogic.tiles.ITile;

public class BoulderTileGUIWorker implements IGUITileWorker
{

	@Override
	public TileImageView makeTile(ITile tile, Context context, GUIScreenManager screenManager)
	{
		// Create tile
		TileImageView result =
				new TileImageView(	((BaseTile) tile).getLocation().x,
									((BaseTile) tile).getLocation().y,
									context);

		// Get the selected tile theme
		Bitmap theme = GeneralServiceProvider.getInstance().getTheme();

		// Get the width and height of each tile
		int width = theme.getWidth() / Consts.DEFAULT_TILES_BMP_COLUMNS;
		int height = theme.getHeight() / Consts.DEFAULT_TILES_BMP_ROWS;

		// Crop theme bitmap to select the specific tile wanted
		Bitmap croppedTile =
				Bitmap.createBitmap(theme,
									width * Consts.BOULDER_TILE_IN_SPRITE.x,
									height * Consts.BOULDER_TILE_IN_SPRITE.y,
									width,
									height);
		
		// Create matrix to change the scale of the bitmap to fit the screen
		Matrix matrix = new Matrix();
		matrix.postScale((float) screenManager.getModifiedTileWidth() / width, (float) screenManager.getModifiedTileHeight() / height);

		// Resize
		croppedTile = Bitmap.createBitmap(croppedTile, 0, 0, width, height, matrix, true);

		// Set the image
		result.setImageBitmap(croppedTile);

		return result;
	}

}
