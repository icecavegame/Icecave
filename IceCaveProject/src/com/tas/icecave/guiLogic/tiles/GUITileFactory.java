package com.tas.icecave.guiLogic.tiles;

import java.util.HashMap;

import android.graphics.Bitmap;

import com.tas.icecave.gui.GameTheme;
import com.tas.icecave.guiLogic.ITileScale;
import com.tas.icecaveLibrary.general.Consts;
import com.tas.icecaveLibrary.mapLogic.tiles.BoulderTile;
import com.tas.icecaveLibrary.mapLogic.tiles.BreakableBoulderTile;
import com.tas.icecaveLibrary.mapLogic.tiles.EmptyTile;
import com.tas.icecaveLibrary.mapLogic.tiles.FlagTile;
import com.tas.icecaveLibrary.mapLogic.tiles.ITile;
import com.tas.icecaveLibrary.mapLogic.tiles.WallTile;

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
		
		mTileWorkers.put(BreakableBoulderTile.class, new BreakableBoulderTileGUIWorker());
		mTileWorkers.put(BoulderTile.class, new BoulderTileGUIWorker());
		mTileWorkers.put(EmptyTile.class, new EmptyTileGUIWorker());
		mTileWorkers.put(WallTile.class, new WallTileGUIWorker());
		mTileWorkers.put(FlagTile.class, new FlagTileGUIWorker());
	}
	
	/**
	 * Get image view for a tile.
	 * @param tiles - To get image of.
	 * @return Image for a tile.
	 */
	public Bitmap getTiles(ITile[] tiles, ITileScale scaler, GameTheme gameTheme) {
		// Check if exists.
		if(!mTileWorkers.containsKey(tiles[0].getClass())){
			return null;
		}
		
		return mTileWorkers.get(tiles[0].getClass()).makeTile(scaler,
														  	  gameTheme.getTilesTheme(),
														  	  Consts.DEFAULT_TILES_BMP_ROWS,
														  	  Consts.DEFAULT_TILES_BMP_COLUMNS,
														  	  tiles,
														  	  gameTheme.getThemeMap());
	}
	
	/**
	 * Get image view for a tile.
	 * @param tiles - To get image of.
	 * @return Image for a tile.
	 */
	public Bitmap getTiles(ITile tile, ITileScale scaler, GameTheme gameTheme) {
		// Check if exists.
		if(!mTileWorkers.containsKey(tile.getClass())){
			return null;
		}
		
		ITile[] tiles = {tile};
		
		return mTileWorkers.get(tile.getClass()).makeTile(scaler,
				gameTheme.getTilesTheme(),
				Consts.DEFAULT_TILES_BMP_ROWS,
				Consts.DEFAULT_TILES_BMP_COLUMNS,
				tiles,
				gameTheme.getThemeMap());
	}
}
