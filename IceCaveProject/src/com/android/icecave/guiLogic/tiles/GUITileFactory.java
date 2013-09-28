package com.android.icecave.guiLogic.tiles;

import com.android.icecave.general.Consts;

import android.graphics.Bitmap;
import com.android.icecave.guiLogic.ITileScale;
import com.android.icecave.mapLogic.tiles.BoulderTile;
import com.android.icecave.mapLogic.tiles.EmptyTile;
import com.android.icecave.mapLogic.tiles.FlagTile;
import com.android.icecave.mapLogic.tiles.ITile;
import com.android.icecave.mapLogic.tiles.WallTile;
import java.util.HashMap;

/**
 * Class holding all the GUITile workers.
 * @author Tom
 *
 */
public class GUITileFactory
{
	/**
	 * Hashmap for all the workers.
	 */
	private HashMap<Class<?>, IGUITileWorker> mTileWorkers;
	
	/**
	 * Create a new instance of the GUITileFactory.
	 */
	public GUITileFactory(){
		mTileWorkers = new HashMap<Class<?>, IGUITileWorker>();
		
		mTileWorkers.put(BoulderTile.class, new BoulderTileGUIWorker());
		mTileWorkers.put(EmptyTile.class, new EmptyTileGUIWorker());
		mTileWorkers.put(WallTile.class, new WallTileGUIWorker());
		mTileWorkers.put(FlagTile.class, new FlagTileGUIWorker());
	}
	
	/**
	 * Get image view for a tile.
	 * @param tile - To get image of.
	 * @return Image for a tile.
	 */
	public Bitmap getTiles(ITile tile, ITileScale scaler, Bitmap gameTheme) {
		// Check if exists.
		if(!mTileWorkers.containsKey(tile.getClass())){
			return null;
		}
		
		return mTileWorkers.get(tile.getClass()).makeTile(scaler,
														  gameTheme,
														  Consts.DEFAULT_TILES_BMP_ROWS,
														  Consts.DEFAULT_TILES_BMP_COLUMNS);
	}
}
