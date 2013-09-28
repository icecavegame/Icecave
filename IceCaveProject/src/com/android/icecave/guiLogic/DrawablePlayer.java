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

public class DrawablePlayer extends SurfaceView implements Callback
{
	private CanvasThread canvasThread;
	private Bitmap mPlayerImage;
	private PlayerGUIManager mPGM;
	private Point mPlayerPosition;
	private Point mPlayerPositionOnScreen;
	private Point mPlayerNewPosition;
	private EDirection mDirection;
	private Bitmap mPlayerTheme;
	private int mHeight;
	private int mWidth;
	private GameActivity mContext;
	private GUIScreenManager mScreenManager;

	private boolean mSurfaceCreated = false;

	public DrawablePlayer(Context context)
	{
		super(context);
	}

	public DrawablePlayer(Context context, AttributeSet attSet)
	{
		super(context, attSet);
	}

	public DrawablePlayer(Context context, Bitmap playerTheme)
	{
		super(context);

		mContext = (GameActivity) context;
		this.getHolder().addCallback(this);
		this.canvasThread = new CanvasThread(getHolder());
		this.setBackgroundColor(color.transparent);

		// Set player theme in manager
		mPGM = new PlayerGUIManager();
		mPlayerTheme = playerTheme;

		mHeight = mPlayerTheme.getHeight() / Consts.DEFAULT_PLAYER_BMP_ROWS;
		mWidth = mPlayerTheme.getWidth() / Consts.DEFAULT_PLAYER_BMP_COLUMNS;

		// Init image (draw on the player's current position)
		mPlayerPosition = new Point(Consts.DEFAULT_START_POS);
		mPlayerPositionOnScreen = new Point(mPlayerPosition.x * mWidth, mPlayerPosition.y * mHeight);

		// Set tile rows and columns into scale manager to fit the character into the tiles
		mScreenManager =
				new GUIScreenManager(	mContext.getTilesView().getBoardX(),
										mContext.getTilesView().getBoardY(),
										mContext.getWidth(),
										mContext.getHeight());
	}

	public void initializePlayer()
	{
		// Initialize new position as player position and draw
		mPlayerNewPosition = new Point(mPlayerPosition);
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
		canvasThread.setRunning(false);
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
		mPlayerNewPosition = newPosition;
		mDirection = direction;
		canvasThread.setRunning(true);

		// Run on a new thread or on the UI thread
		canvasThread.start();
	}

	public void movePlayer(EDirection direction, Point newPosition)
	{
		// Moving player to the new position in the new direction
		mPlayerNewPosition = newPosition;
		mDirection = direction;
		canvasThread = new CanvasThread(getHolder());
		canvasThread.setRunning(true);

		// Run on a new thread or on the UI thread
		canvasThread.start();
	}

	public void update()
	{
		// If reached new position, stop
		if (mPlayerPosition.x == mPlayerNewPosition.x && mPlayerPosition.y == mPlayerNewPosition.y)
		{
			canvasThread.setRunning(false);
		} else
		{
			// Move to direction
			mPlayerPosition.x += mDirection.getDirection().x;
			mPlayerPosition.y += mDirection.getDirection().y;

			// Move pixelwise
			// TODO Currently jumping by blocks, should change this somehow to make it smooth
			mPlayerPositionOnScreen = new Point(mPlayerPosition.x * mWidth, mPlayerPosition.y * mHeight);
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Draw background after every move. It's a must
		mContext.getTilesView().draw(canvas);

		// Get the next player image
		mPlayerImage =
				mPGM.getPlayerImage(mDirection,
									true,
									mPlayerTheme,
									mScreenManager);
		// TODO Set position
		canvas.drawBitmap(mPlayerImage, mPlayerPositionOnScreen.x, mPlayerPositionOnScreen.y, null);
	}

	private class CanvasThread extends Thread
	{
		private SurfaceHolder surfaceHolder;
		private boolean isRun = false;
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

		public void setRunning(boolean run)
		{
			this.isRun = run;

			// If running is set to true, reset first tick and next tick
			first_tick = System.currentTimeMillis();
			next_game_tick = GetTickCount();
		}

		@Override
		public void run()
		{
			Canvas c;

			// Run while flag is true (also do NOT attempt to run while surface is not yet created)
			while (isRun && mSurfaceCreated)
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
		}
	}
}
