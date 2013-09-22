package com.android.icecave.guiLogic.tiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.icecave.general.Consts;
import com.android.icecave.general.GeneralServiceProvider;
import com.android.icecave.guiLogic.TileImageView;
import com.android.icecave.mapLogic.tiles.BaseTile;
import com.android.icecave.mapLogic.tiles.ITile;

public class WallTileGUIWorker implements IGUITileWorker
{

	@Override
	public TileImageView makeTile(ITile tile, Context context)
	{
		// Create tile
		TileImageView result = new TileImageView(((BaseTile)tile).getLocation().x, 
												 ((BaseTile)tile).getLocation().x, 
												 context);
		
		// Get the selected tile theme
		Drawable d = GeneralServiceProvider.getInstance().getTheme();
		
		// Get the width and height of each tile
		int width = d.getBounds().width() / Consts.DEFAULT_TILES_BMP_COLUMNS;
		int height = d.getBounds().height() / Consts.DEFAULT_TILES_BMP_ROWS;
		
		// Set bounds for the theme bitmap to select the specific tile wanted
		d.setBounds(width * Consts.WALL_TILE_IN_SPRITE.x, 
				height * Consts.WALL_TILE_IN_SPRITE.y,
				(width * Consts.WALL_TILE_IN_SPRITE.x) + width,
				(height * Consts.WALL_TILE_IN_SPRITE.y) + height);
		
		// Set the image
		result.setImageDrawable(d);
		
		return result;
	}

}
