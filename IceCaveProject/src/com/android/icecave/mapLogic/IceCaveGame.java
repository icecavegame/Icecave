package com.android.icecave.mapLogic;

import android.graphics.Point;

import com.android.icecave.general.EDifficulty;
import com.android.icecave.general.EDirection;
import com.android.icecave.general.IFunction;
import com.android.icecave.mapLogic.collision.BaseCollisionInvoker;
import com.android.icecave.mapLogic.collision.CollisionManager;
import com.android.icecave.mapLogic.collision.ICollisionable;
import com.android.icecave.mapLogic.tiles.BoulderTile;
import com.android.icecave.mapLogic.tiles.FlagTile;
import com.android.icecave.mapLogic.tiles.ITile;
import com.android.icecave.mapLogic.tiles.WallTile;

public class IceCaveGame extends CollisionManager
{
	private int mOverallMoves;
	private EDirection mLastDirectionMoved;
	private IceCaveStage mStage;
	private Point mPlayerLocation;
	private boolean mPlayerMoving;
	private int mBoulderNum;
	private int mBoardSizeX;
	private int mBoardSizeY;
	private EDifficulty mDifficulty;
	
	public IceCaveGame(int boulderNum, int boardSizeX, int boardSizeY, EDifficulty difficulty) {
		mBoulderNum = boulderNum;
		mBoardSizeX = boardSizeX;
		mBoardSizeY = boardSizeY;
		mDifficulty = difficulty;
		
		mStage = new IceCaveStage();
		mPlayerLocation = new Point();
		
		// Create invokers.
		IFunction<Void> stopPlayer = new IFunction<Void>() {
			
			@Override
			public Void invoke() {
				mPlayerMoving = false;
				return null;
			}
		};
		
		IFunction<Void> endStage = new IFunction<Void>() {
			
			@Override
			public Void invoke() {
				mPlayerMoving = false;

				// TODO: Add report to the GUI logic on end stage.
				return null;
			}
		};
		
		// Add invokers.
		mCollisionInvokers.put(BoulderTile.class, new BaseCollisionInvoker<Void>(stopPlayer));
		mCollisionInvokers.put(WallTile.class, new BaseCollisionInvoker<Void>(stopPlayer));
		mCollisionInvokers.put(FlagTile.class, new BaseCollisionInvoker<Void>(endStage));
		
		MapLogicServiceProvider.getInstance().registerCollisionManager(this);
	}
	
	public int getStageMoves() {
		return mStage.getMoves();
	}
	
	/**
	 * Move the player on the board.
	 * @param direction - Direction to move the player in.
	 * @return Point - New location of the player.
	 */
	public Point movePlayer(EDirection direction) {
		// Check if the requested direction is the last direction moved.
		if(!direction.equals(mLastDirectionMoved))
		{
			// Start moving.
			mPlayerMoving = true;
			
			// While we are moving.
			while (mPlayerMoving){
				mStage.movePlayerOneTile(mPlayerLocation, direction);
			}
		}
		
		return mPlayerLocation;
	}
	
	public int getOverallMoves() {
		return mOverallMoves;
	}
	
	public ITile[][] getBoard() {
		return mStage.getBoard();
	}

	@Override
	protected void handleCollision(ICollisionable collisionable)
	{
		mCollisionInvokers.get(collisionable).onCollision();
	}
}
