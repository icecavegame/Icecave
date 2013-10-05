package com.android.icecave.mapLogic;

import java.io.Serializable;

import com.android.icecave.general.EDirection;
import com.android.icecave.mapLogic.tiles.ITile;
import com.android.icecave.utils.IBoard;
import com.android.icecave.utils.Point;

@SuppressWarnings("serial")
public class IceCaveBoard implements IBoard<ITile>, Serializable
{
	/**
	 * The map board.
	 */
	private ITile[][] mBoard;
	
	/**
	 * The player starting location.
	 */
	private Point mPlayerStart;
	
	/**
	 * The starting move for the player.
	 */
	private EDirection mStartingMove;

	/**
	 * Min moves to solve the stage.
	 */
	private int mMoves;
	
	/**
	 * Get the starting direction to move the player in.
	 * @return Starting direction to move the player in.
	 */
	public EDirection getStartingMove(){
		return mStartingMove;
	}
	
	@Override
	public Point getStartPoint(){
		return mPlayerStart;
	}
	
	/**
	 * Create a new instance of the ice cave board.
	 * 
	 * @param board - Board to hold as IceCaveBoard.
	 * @param startPoint - The starting location of the player.
	 * @param startingMove - The starting move of the player.
	 * @param moves - Minimum moves to solve the stage in.
	 */
	public IceCaveBoard(ITile[][] board, 
	                    Point startPoint,
	                    EDirection startingMove,
	                    int moves)
	{
		mStartingMove = startingMove;
		mBoard = board.clone();
		mPlayerStart = new Point(startPoint);
		mMoves = moves;
	}
	
	@Override
	public ITile[][] getBoard()
	{
		return mBoard;
	}

	/**
	 * Get the minimum number of moves needed to solve the stage.
	 * @return Minimum number of moves needed to solve the stage.
	 */
	public int getMinMoves()
	{
		return mMoves;
	}

}
