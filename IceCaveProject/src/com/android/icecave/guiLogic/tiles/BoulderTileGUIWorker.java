package com.android.icecave.guiLogic.tiles;

import com.android.icecave.utils.Point;

import com.android.icecave.general.Consts;

public class BoulderTileGUIWorker extends BaseTileGUIWorker
{
	@Override
	public Point getTilePointInSprite()
	{
		return Consts.BOULDER_TILE_IN_SPRITE;
	}
}
