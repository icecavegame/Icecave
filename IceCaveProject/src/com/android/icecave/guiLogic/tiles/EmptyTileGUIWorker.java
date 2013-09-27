package com.android.icecave.guiLogic.tiles;

import android.graphics.Point;

import com.android.icecave.general.Consts;

public class EmptyTileGUIWorker extends BaseTileGUIWorker
{

	@Override
	public Point getTilePointInSprite()
	{
		return Consts.EMPTY_TILE_IN_SPRITE;
	}

}
