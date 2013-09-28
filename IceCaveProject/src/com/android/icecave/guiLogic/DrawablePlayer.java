package com.android.icecave.guiLogic;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDirection;
import com.android.icecave.gui.GameActivity;
import com.android.icecave.gui.GameTheme;
import com.android.icecave.utils.AutoObservable;

public class DrawablePlayer extends SurfaceView implements Callback
{
	private CanvasThread canvasThread;
	private Bitmap mPlayerImage;
	private PlayerGUIManager mPGM;
	private Point mPlayerPosition;
	private Point mPlayerPositionOnScreen;
	private Point mPlayerNewPositionOnScreen;
	private EDirection mDirection;
	private GameTheme mGameTheme;
	private int mHeight;
	private int mWidth;
	private GameActivity mContext;
	private GUIScreenManager mScreenManager;
	private boolean mSurfaceCreated = false;
	private boolean mIsMoving;
	final float mSpeed = 5;
	public AutoObservable mFinishAnimation;

	public DrawablePlayer(Context context)
	{
		super(context);
	}

	public DrawablePlayer(Context context, AttributeSet attSet)
	{
		super(context, attSet);
	}

	public DrawablePlayer(Context context, GameTheme gameTheme)
	{
		super(context);

		// Initialize data
		mContext = (GameActivity) context;
		this.getHolder().addCallback(this);
		this.canvasThread = new CanvasThread(getHolder());
		this.setBackgroundColor(color.transparent);
		mFinishAnimation = new AutoObservable();
		
		// Add game activity as an observer
		mFinishAnimation.addObserver(mContext);

		// Set player theme in manager
		mPGM = new PlayerGUIManager();
		mGameTheme = gameTheme;

		// Set tile rows and columns into scale manager to fit the character into the tiles
		mScreenManager =
				new GUIScreenManager(	mContext.getTilesView().getBoardX(),
										mContext.getTilesView().getBoardY(),
										mContext.getWidth(),
										mContext.getHeight());

		// Get the first player image
		mIsMoving = false;
		mPlayerImage = mPGM.getPlayerImage(EDirection.DOWN, mIsMoving, mGameTheme, mScreenManager);

		// Get height and width of player image
		mHeight = mPlayerImage.getHeight();
		mWidth = mPlayerImage.getWidth();

		// Init image (draw on the player's current position)
		mPlayerPosition = new Point(Consts.DEFAULT_START_POS);
		mPlayerPositionOnScreen = new Point(mPlayerPosition.x * mWidth, mPlayerPosition.y * mHeight);
	}

	public void initializePlayer()
	{
		// Initialize new position as player position and draw
		mPlayerNewPositionOnScreen = new Point(mPlayerPositionOnScreen);
		startDrawImage(EDirection.DOWN, mPlayerPosition);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0)
	{
		mSurfaceCreated = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0)
	{
		boolean retry = true;
		mIsMoving = false;
		
		while (retry)
		{
			try
			{
				canvasThread.join();
				retry = false;
			} catch (InterruptedException e)
			{
				// TODO Print to log
				e.printStackTrace();
			}
		}
	}

	public void startDrawImage(EDirection direction, Point newPosition)
	{
		mPlayerNewPositionOnScreen = new Point(newPosition.x * mWidth, newPosition.y * mHeight);
		mDirection = direction;
		mIsMoving = true;

		// Run on a new thread or on the UI thread
		canvasThread.start();
	}

	public void movePlayer(EDirection direction, Point newPosition)
	{
		// Moving player to the new position in the new direction
		canvasThread = new CanvasThread(getHolder());
		startDrawImage(direction, newPosition);
	}

	public void update()
	{
		// If reached new position, stop
		if (mPlayerPositionOnScreen.x == mPlayerNewPositionOnScreen.x && mPlayerPositionOnScreen.y == mPlayerNewPositionOnScreen.y)
		{
			// Stop moving and stop thread
			mIsMoving = false;
		} else
		{
			// Move to direction by speed
			mPlayerPositionOnScreen.x += mDirection.getDirection().x * mSpeed;
			mPlayerPositionOnScreen.y += mDirection.getDirection().y * mSpeed;
			
			// Check that move does not exceed new position
			if (mPlayerPositionOnScreen.x > mPlayerNewPositionOnScreen.x) {
				mPlayerPositionOnScreen.x = mPlayerNewPositionOnScreen.x;
			}
			
			if (mPlayerPositionOnScreen.y > mPlayerNewPositionOnScreen.y) {
				mPlayerPositionOnScreen.y = mPlayerNewPositionOnScreen.y;
			}
			
			mIsMoving = true;
		}
		
		// Get the next player image
		mPlayerImage = mPGM.getPlayerImage(mDirection, mIsMoving, mGameTheme, mScreenManager);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Draw background after every move. It's a must
		mContext.getTilesView().draw(canvas);

		canvas.drawBitmap(mPlayerImage, mPlayerPositionOnScreen.x, mPlayerPositionOnScreen.y, null);
		
		if (mIsMoving) {
			update();
			postInvalidate();
		}
	}

	private class CanvasThread extends Thread
	{
		private SurfaceHolder surfaceHolder;
		final int FRAMES_PER_SECOND = 25;
		final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
		long first_tick;
		long next_game_tick;

		// returns the current number of milliseconds
		// that have elapsed since the system was started
		public long GetTickCount()
		{
			return first_tick - System.currentTimeMillis();
		}

		int sleep_time = 0;

		public CanvasThread(SurfaceHolder holder)
		{
			this.surfaceHolder = holder;
		}

		@Override
		public void run()
		{
			Canvas c;
			
			// Reset first tick and next tick
			first_tick = System.currentTimeMillis();
			next_game_tick = GetTickCount();

			// Run while flag is true (also do NOT attempt to run while surface is not yet created)
			while (mIsMoving && mSurfaceCreated)
			{
				c = null;
				try
				{
					c = this.surfaceHolder.lockCanvas(null);

					synchronized (surfaceHolder)
					{
						DrawablePlayer.this.update();
						DrawablePlayer.this.draw(c);
					}

					// Count FPS. Put thread to sleep if going too fast
					next_game_tick += SKIP_TICKS;
					sleep_time = (int) (next_game_tick - GetTickCount());
					if (sleep_time >= 0)
					{
						try
						{
							Thread.sleep(sleep_time);
						} catch (InterruptedException e)
						{
							// TODO Print to log
							e.printStackTrace();
						}
					} else
					{
						// Shit, we are running behind!
					}
				} finally
				{
					if (c != null)
					{
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
			
			// Notify observers that animation has finished
			mFinishAnimation.notifyObservers();
		}
	}
}
