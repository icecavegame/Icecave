/**
 * 
 */
package com.tas.icecaveGeneral.mapLogic.tiles.validators;

import com.tas.icecaveGeneral.mapLogic.tiles.EmptyTile;
import com.tas.icecaveGeneral.mapLogic.tiles.ITile;

/**
 * @author Tom
 * Implements the tile validator for a boulder.
 */
public class BoulderTileValidator extends BaseTileValidator{

	@Override
	public boolean isValid(int 		 xLocation, 
						   int 		 yLocation, 
						   int 		 xPlayerLocation,
						   int 	     yPlayerLocation, 
						   ITile[][] board) {

		return(
			// Check that the location is not the player.
			!((xLocation == xPlayerLocation) && (yLocation == yPlayerLocation)) &&			
			// Check that the location is an empty tile.
			EmptyTile.class.isInstance(board[yLocation][xLocation]) 
			// &&
			// Check that it is not adjacent to any boulder.
//			(!isAdjacent(xLocation, yLocation, BoulderTile.class,board)) &&
//			(!isAdjacent(xLocation, yLocation, FlagTile.class, board))
			);
	}
}
