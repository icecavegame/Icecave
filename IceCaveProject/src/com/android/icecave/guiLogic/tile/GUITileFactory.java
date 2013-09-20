package com.android.icecave.guiLogic.tile;

import com.android.icecave.mapLogic.tiles.ITile;

import com.android.icecave.guiLogic.TileImageView;

import java.lang.reflect.Type;

import java.util.Dictionary;

public class GUITileFactory
{
	private Dictionary<Type, IGUITileWorker> mTileWorkers;
	
	public TileImageView getTiles(ITile tile) {
		return null;
	}
}
