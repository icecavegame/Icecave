package com.android.icecave.guiLogic.tiles;

import com.android.icecave.guiLogic.GUIScreenManager;

import android.content.Context;
import com.android.icecave.guiLogic.TileImageView;
import com.android.icecave.mapLogic.tiles.ITile;

public interface IGUITileWorker
{
	public TileImageView makeTile(ITile tile, Context context, GUIScreenManager screenManager);
}
