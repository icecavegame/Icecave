package com.android.icecave.mapLogic;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import com.android.icecave.utils.Point;

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

/**
 * Class to hold all the logic of the game.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class IceCaveGame extends CollisionManager implements IIceCaveGameStatus, Serializable
{
	/**
	 * The overall moves for the current game.
	 */
	private int mOverallMoves;
	
	/**
	 * The number of moves for the current stage. 
	 */
	private int mCurrentStageMoves;
	
	/**
	 * The last direction moved.
	 */
	private EDirection mLastDirectionMoved;
	
	/**
	 * The current stage.
	 */
	private IceCaveStage mStage;
	
	/**
	 * The current player location.
	 */
	private Point mPlayerLocation;
	
	/**
	 * Is the player moving.
	 */
	private transient boolean mPlayerMoving;
	
	/**
	 * Number of boulders for the current game.
	 */
	private int mBoulderNum;
	
	/**
	 * The X Board size for the current game.
	 */
	private int mBoardSizeX;
	
	/**
	 * The X Board size for the current game.
	 */
	private int mBoardSizeY;
	
	/**
	 * The Difficulty for the current game.
	 */
	private EDifficulty mDifficulty;
	
	/**
	 * Indicates weather or not the game has ended.
	 */
	private transient boolean mIsStageEnded;

	/**
	 * Create a new instance of the IceCaveGame object.
	 * 
	 * @param boulderNum
	 *            - Number of boulders to place on board.
	 * @param boardSizeX
	 *            - Board width (in tiles).
	 * @param boardSizeY
	 *            - Board height (in tiles).
	 * @param difficulty
	 *            - Game difficulty.
	 */
	public IceCaveGame(int boulderNum, int boardSizeX, int boardSizeY, EDifficulty difficulty)
	{
		mBoulderNum = boulderNum;
		mBoardSizeX = boardSizeX;
		mBoardSizeY = boardSizeY;
		mDifficulty = difficulty;
		mIsStageEnded = false;
		mStage = new IceCaveStage();
		mPlayerLocation = new Point();

		// Create invokers.
		IFunction<Void> stopPlayer = new IFunction<Void>()
		{
			@Override
			public Void invoke(Point collisionPoint)
			{
				mPlayerMoving = false;
				collisionPoint.offset(
						mLastDirectionMoved.getOpositeDirection().getDirection().x,
						mLastDirectionMoved.getOpositeDirection().getDirection().y);
				if(!collisionPoint.equals(mPlayerLocation.x, mPlayerLocation.y)){
					mOverallMoves++;
					mCurrentStageMoves++;
					mPlayerLocation = collisionPoint;
				}
				
				return null;
			}
		};

		IFunction<Void> endStage = new IFunction<Void>()
		{

			@Override
			public Void invoke(Point collisionPoint)
			{
				mPlayerMoving = false;
				mIsStageEnded = true;
				
				mPlayerLocation = collisionPoint;
				
				mCurrentStageMoves++;
				mOverallMoves++;
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

	/**
	 * Start a new stage.
	 * @throws IOException 
	 * @throws StreamCorruptedException 
	 * @throws ClassNotFoundException 
	 */
	public void newStage(String mapFilePath) throws StreamCorruptedException, IOException, ClassNotFoundException
	{
		mIsStageEnded = false;
		mLastDirectionMoved = null;
		
		// Read the map board.
		IceCaveBoard mapBoard = readMapBoard(mapFilePath);
		
		mPlayerLocation = new Point(mapBoard.getStartPoint());
		mCurrentStageMoves = 0;
		
		// Start the new stage.
		mStage.buildBoard(mapBoard);
	}

	/**
	 * Read the map board from a file.
	 * 
	 * @param mapFilePath - File to read the map board from.
	 * @return
	 * @throws FileNotFoundException
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws OptionalDataException
	 * @throws ClassNotFoundException
	 */
	private IceCaveBoard readMapBoard(String mapFilePath)
			throws FileNotFoundException, StreamCorruptedException,
			IOException, OptionalDataException, ClassNotFoundException
	{
		IceCaveBoard mapBoard;
		ObjectInputStream objectInputStream = null;
		
		try
		{
			FileInputStream fileInputStream = new FileInputStream(mapFilePath);
			objectInputStream = new ObjectInputStream(fileInputStream);
			mapBoard = (IceCaveBoard) objectInputStream.readObject();
		}
		finally
		{
			// Close the stream.
			if(objectInputStream != null)
			{
				objectInputStream.close();
			}
		}
		
		return mapBoard;
	}
	
	/**
	 * Start a new stage.
	 * 
	 * @param playerStart
	 *            - The starting position of the player.
	 * @param wallWidth
	 *            - Width of the walls in tiles.
	 */
	public void newStage(Point playerStart, int wallWidth)
	{
		mIsStageEnded = false;
		mLastDirectionMoved = null;
		mPlayerLocation = new Point(playerStart);
		mStage.buildBoard(mDifficulty,
				mBoardSizeY,
				mBoardSizeX,
				wallWidth,
				new Point(playerStart),
				mBoulderNum,
				EDirection.RIGHT);
		mCurrentStageMoves = 0;
	}

	/**
	 * Get the number of moves that were done in the current stage.
	 * 
	 * @return Number of moves in current stage.
	 */
	public int getStageMoves()
	{
		return mStage.getMoves();
	}

	/**
	 * Move the player on the board.
	 * 
	 * @param direction
	 *            - Direction to move the player in.
	 * @return Point - New location of the player.
	 */
	public IIceCaveGameStatus movePlayer(EDirection direction)
	{
		// Check if the requested direction is the last direction moved.
		if (!direction.equals(mLastDirectionMoved))
		{
			// Set last move
			mLastDirectionMoved = direction;
			
			// Start moving.
			mPlayerMoving = true;

			Point nextPlayerPoint = mStage.movePlayerOneTile(mPlayerLocation, direction);

			// While we are moving.
			while (mPlayerMoving)
			{
				nextPlayerPoint = mStage.movePlayerOneTile(nextPlayerPoint, direction);
			}
		}

		return this;
	}

	/**
	 * Return overall moves taken in game.
	 * 
	 * @return Overall moves.
	 */
	public int getOverallMoves()
	{
		return mOverallMoves;
	}
	
	/**
	 * Return number of moves taken in the current stage.
	 * 
	 * @return Steps in current stage.
	 */
	public int getCurrentStageTakenMoves()
	{
		return mCurrentStageMoves;
	}

	/**
	 * Get the current board of the stage.
	 * 
	 * @return Board of the stage.
	 */
	public ITile[][] getBoard()
	{
		return mStage.getBoard();
	}

	@Override
	public void handleCollision(ICollisionable collisionable)
	{
		if (mCollisionInvokers.containsKey(collisionable.getClass()))
		{
			mCollisionInvokers.get(collisionable.getClass()).
								   onCollision(collisionable.getLocation());
		}
	}

	@Override
	public Point getPlayerPoint()
	{
		return mPlayerLocation;
	}

	@Override
	public boolean getIsStageEnded()
	{
		return mIsStageEnded;
	}

	/**
	 * Resets the player location.
	 * @param startLoc - Starting position of the player to reset to.
	 */
	public void resetPlayer(Point startLoc)
	{
		mPlayerLocation = new Point(startLoc);
		mLastDirectionMoved = null;
	}
}
