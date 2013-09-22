package com.android.icecave.guiLogic.tiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.icecave.general.GeneralServiceProvider;
import com.android.icecave.guiLogic.TileImageView;
import com.android.icecave.mapLogic.tiles.ITile;

public class BoulderTileGUIWorker implements IGUITileWorker
{

	@Override
	public TileImageView makeTile(ITile tile, Context context)
	{
		//TileImageView result = new TileImageView(tile.row, tile.col, context);
		Drawable d = GeneralServiceProvider.getInstance().getTheme();
		
		return null;
	}

}
