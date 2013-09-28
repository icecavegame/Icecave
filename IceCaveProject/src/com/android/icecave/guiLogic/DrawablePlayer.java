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

		this.getHolder().addCallback(this);
		this.canvasThread = new CanvasThread(getHolder());
		this.setFocusable(true);
		this.setBackgroundColor(color.transparent);

		// Set player theme in manager
		mPGM = new PlayerGUIManager();
		mPlayerTheme = playerTheme;

		mHeight = mPlayerTheme.getHeight() / Consts.DEFAULT_PLAYER_BMP_ROWS;
		mWidth = mPlayerTheme.getWidth() / Consts.DEFAULT_PLAYER_BMP_COLUMNS;

		// Init image (draw on the player's current position)
		mPlayerPosition = new Point(Consts.DEFAULT_START_POS);
		mPlayerPositionOnScreen = new Point(mPlayerPosition.x * mWidth, mPlayerPosition.y * mHeight);
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
		this.setFocusable(true);
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
		// FIXME An alternative to drawing the color black. That is meant for refreshing image views, I guess
		// canvas.drawColor(color.transparent);
		mPlayerImage =
				mPGM.getPlayerImage(mPlayerPosition.x, mPlayerPosition.y, mDirection, true, mPlayerTheme);
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

			while (isRun)
			{
				c = null;
				try
				{
					c = this.surfaceHolder.lockCanvas(null);

					// If received canvas
					if (c != null)
					{
						synchronized (c)
						{
							DrawablePlayer.this.update();
							DrawablePlayer.this.draw(c);
						}

						next_game_tick += SKIP_TICKS;
						sleep_time = (int) (next_game_tick - GetTickCount());
						if (sleep_time >= 0)
						{
							try
							{
								Thread.sleep(sleep_time);
							} catch (InterruptedException e)
							{
								// Print to log
								e.printStackTrace();
							}
						} else
						{
							// Shit, we are running behind!
						}
					}
				} finally
				{
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
