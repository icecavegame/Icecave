package com.android.icecave.guiLogic.tile;

import com.android.icecave.guiLogic.TileImageView;
import com.android.icecave.mapLogic.tiles.ITile;

public interface IGUITileWorker
{
	public TileImageView makeTile(ITile tile);
}
