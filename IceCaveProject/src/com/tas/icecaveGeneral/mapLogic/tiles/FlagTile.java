package com.tas.icecaveGeneral.mapLogic.tiles;

import com.tas.icecaveGeneral.utils.Point;

/**
 * The flag tile in the game.
 * The tile that ends the game.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class FlagTile extends BaseTile
{
	/**
	 * Create a new instance of the FlagTile object.
	 * @param x - X position for the tiles.
	 * @param y - Y position for the tile.
	 */
	public FlagTile(int x, int y)
	{
		super(x, y);
	}
	
	/**
	 * Create a new instance of the FlagTile object.
	 * @param location - Location for the flag tile.
	 */
	public FlagTile(Point location)
	{
		super(location);
	}

	@Override
	public ITile clone()
	{
		return new FlagTile(mLocation);
	}
}
