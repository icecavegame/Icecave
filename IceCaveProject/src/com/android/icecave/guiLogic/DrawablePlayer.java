package com.android.icecave.guiLogic;

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
	private Point mPlayerNewPosition;
	private EDirection mDirection;

	public DrawablePlayer(Context context)
	{
		super(context);
	}

	public DrawablePlayer(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		this.getHolder().addCallback(this);
		this.canvasThread = new CanvasThread(getHolder());
		this.setFocusable(true);

		// Set player theme in manager
		mPGM = new PlayerGUIManager();

		// Init image (draw on the player's current position)
		mPlayerPosition = new Point(Consts.DEFAULT_START_POS);
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
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// FIXME An alternative to drawing the color black. That is refreshing image views, I guess
		// canvas.drawColor(color.black);
		mPlayerImage = mPGM.getPlayerImage(mPlayerPosition.x, mPlayerPosition.y, mDirection, true);
		// TODO Set position
		canvas.drawBitmap(mPlayerImage, 0, 0, null);
	}

	private class CanvasThread extends Thread
	{
		private SurfaceHolder surfaceHolder;
		private boolean isRun = false;

		public CanvasThread(SurfaceHolder holder)
		{
			this.surfaceHolder = holder;
		}

		public void setRunning(boolean run)
		{
			this.isRun = run;
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
					synchronized (this.surfaceHolder)
					{
						DrawablePlayer.this.update();
						DrawablePlayer.this.draw(c);
					}
				} finally
				{
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
