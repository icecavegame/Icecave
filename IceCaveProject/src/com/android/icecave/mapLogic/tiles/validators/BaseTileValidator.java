package com.android.icecave.mapLogic.tiles.validators;

import com.android.icecave.mapLogic.tiles.ITile;

/**
 * Base implementation of the ITileValidator interface.
 * @author Tom
 *
 */
public abstract class BaseTileValidator implements ITileValidator {

	/**
	 * Check if the x and y location is adjacent to a given type on the board.
	 * 
	 * @param xLocation - x location to check.
	 * @param yLocation - y location to check.
	 * @param board     - The current tile board.
	 * @param tileClass - Tile class to check if adjacent to.
	 * @return true if adjacent to a tile from the specified type.
	 */
	public boolean isAdjacent(int       	   xLocation, 
				  			  int       	   yLocation, 
				  			  Class<?> 		   tileClass,
				  			  ITile[][] 	   board){
		
		return tileClass.isInstance(board[yLocation - 1][xLocation - 1]) &&
			   tileClass.isInstance(board[yLocation - 1][xLocation + 0]) &&  
			   tileClass.isInstance(board[yLocation - 1][xLocation + 1]) &&  
			   tileClass.isInstance(board[yLocation + 0][xLocation + 1]) &&  
			   tileClass.isInstance(board[yLocation + 1][xLocation + 1]) &&  
			   tileClass.isInstance(board[yLocation + 1][xLocation + 0]) &&  
			   tileClass.isInstance(board[yLocation + 1][xLocation - 1]) &&  
			   tileClass.isInstance(board[yLocation + 0][xLocation - 1]);  
	}
}
