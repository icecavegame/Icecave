package com.android.icecave.guiLogic.tiles;

import android.graphics.Point;

import com.android.icecave.general.Consts;

public class WallTileGUIWorker extends BaseTileGUIWorker
{

	@Override
	public Point getTilePointInSprite()
	{
		return Consts.WALL_TILE_IN_SPRITE;
	}

}
