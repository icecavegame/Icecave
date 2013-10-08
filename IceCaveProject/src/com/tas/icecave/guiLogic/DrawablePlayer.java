package com.tas.icecave.guiLogic;


import android.content.Context;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import com.tas.icecave.gui.GameActivity;
import com.tas.icecave.gui.GameTheme;
import com.tas.icecaveLibrary.general.Consts;
import com.tas.icecaveLibrary.general.EDirection;
import com.tas.icecaveLibrary.utils.AutoObservable;
import com.tas.icecaveLibrary.utils.Point;
import com.tas.icecaveLibrary.utils.UpdateDataBundle;

public class DrawablePlayer extends ImageView
{
	private PlayerGUIManager mPGM;
	private GameTheme mGameTheme;
	private GameActivity mContext;
	private GUIScreenManager mScreenManager;
	private boolean mIsAnimationRunning;
	private AutoObservable mFinishAnimation;
	private Point mPlayerPosition;

	public DrawablePlayer(Context context)
	{
		super(context);
	}

	public DrawablePlayer(Context context, GameTheme gameTheme)
	{
		super(context);

		// Initialize data
		mContext = (GameActivity) context;
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
										mContext.getWidth());

		// Get height and width of player image
		setImageBitmap(mPGM.getPlayerImage(EDirection.DOWN, false, mGameTheme, mScreenManager));
	}

	public void initializePlayer()
	{
		// Stop animation in the middle
		animate().cancel();

		// Initialize animation running as false
		mIsAnimationRunning = false;

		// Init image (draw on the player's current position)
		setImageBitmap(mPGM.getPlayerImage(EDirection.DOWN, false, mGameTheme, mScreenManager));

		// Initialize new position as player position and draw
		mPlayerPosition = new Point(Consts.DEFAULT_START_POS);

		// Init player position (if image is set)
		if (getWidth() != 0 && getHeight() != 0)
		{
			initPlayerPosition();
		}
	}

	public void movePlayer(EDirection direction, Point newPosition)
	{
		// Move player to the new position in the new direction
		update(getPositionOnScreen(newPosition),
				direction,
				Math.abs((newPosition.x - mPlayerPosition.x) + (newPosition.y - mPlayerPosition.y)));
		mIsAnimationRunning = true;
		animate().start();

		// Set new position as current player position
		mPlayerPosition = newPosition;
	}

	private Point getPositionOnScreen(Point logicPosition)
	{
		// Get the position of the character on screen. Take the translation of the tiles view into consideration
		return new Point((logicPosition.x * getWidth()) + (int)mContext.getTilesView().getTranslationX(), (logicPosition.y * getHeight()) + (int)mContext.getTilesView().getTranslationY());
	}

	public void update(Point playerNewPositionOnScreen, final EDirection direction, int distance)
	{
		// Update the player image to a movement stance
		setImageBitmap(mPGM.getPlayerImage(direction, true, mGameTheme, mScreenManager));

		// End action
		Runnable endAction = new Runnable()
		{
			public void run()
			{
				// Set image back to standing position
				setImageBitmap(mPGM.getPlayerImage(direction, false, mGameTheme, mScreenManager));

				// Notify observers that action has ended
				mFinishAnimation.notifyObservers(new UpdateDataBundle(Consts.PLAYER_FINISH_MOVE_UPDATE, null));

				// Set animation running back to false
				mIsAnimationRunning = false;
			}
		};

		// Set duration of action by the board size
		final int HUNDREDTH_SECOND = 10;

		// TODO This is a pretty wild guess. Try to come up with a better idea
		final long DURATION_PER_TILE =
				(((mContext.getTilesView().getBoardX() + mContext.getTilesView().getBoardY()) / 5) + 2) *
						HUNDREDTH_SECOND;

		// Create animation flow for moving a character
		ViewPropertyAnimator animator = animate();
		animator.setDuration(DURATION_PER_TILE * distance);
		animator.translationX(playerNewPositionOnScreen.x);
		animator.translationY(playerNewPositionOnScreen.y);
		animator.withEndAction(endAction);
	}

	public void initPlayerPosition()
	{
		// Set initial position. Take the translation of the tiles view into consideration
		setTranslationX((mPlayerPosition.x * getWidth()) + mContext.getTilesView().getTranslationX());
		setTranslationY((mPlayerPosition.y * getHeight()) + mContext.getTilesView().getTranslationY());
	}

	public boolean isAnimationRunning()
	{
		return mIsAnimationRunning;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		// Initialize position of view if height and width are no longer 0 (an image has been set)
		// IMPORTANT!!! If for some reason in the future the size of the player should change ingame, this is a bug
		if (w != 0 && h != 0)
		{
			initPlayerPosition();
		}
	}
}
