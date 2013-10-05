package com.tas.icecave.guiLogic.theme;

import java.util.HashMap;

import com.tas.icecaveLibrary.mapLogic.tiles.BoulderTile;
import com.tas.icecaveLibrary.mapLogic.tiles.EmptyTile;
import com.tas.icecaveLibrary.mapLogic.tiles.FlagTile;
import com.tas.icecaveLibrary.mapLogic.tiles.WallTile;

/**
 * Holder for object themes.
 * @author Tom
 *
 */
public class ThemeMap implements IShuffelableTheme
{
	/**
	 * Themes wrapped.
	 */
	HashMap<Class<?>, IObjectTheme> mThemes;

	/**
	 * Create a new instance of the theme map.
	 */
	public ThemeMap()
	{
		mThemes = new HashMap<Class<?>, IObjectTheme>();
		mThemes.put(BoulderTile.class, new BoulderTheme());
		mThemes.put(EmptyTile.class, new EmptyTheme());
		mThemes.put(FlagTile.class, new FlagTheme());
		mThemes.put(WallTile.class, new WallTheme());
	}

	/**
	 * Get the object theme.
	 * @param themeType - Tile type to get theme for.
	 * @return Tile object theme.
	 */
	public IObjectTheme getTheme(Class<?> themeType){
		if(mThemes.containsKey(themeType)){
			return mThemes.get(themeType);
		}
		
		return null;
	}

	@Override
	public void shuffle()
	{
		// Go through the themes.
		for (IObjectTheme objectTheme : mThemes.values()){
			// Check if is shuffle-able.
			if(objectTheme instanceof IShuffelableTheme){
				// Shuffle.
				((IShuffelableTheme) objectTheme).shuffle();
			}
		}
	}
}
