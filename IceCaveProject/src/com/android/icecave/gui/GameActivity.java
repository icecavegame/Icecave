package com.android.icecave.gui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.icecave.R;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDifficulty;
import com.android.icecave.general.EDirection;
import com.android.icecave.general.MusicService;
import com.android.icecave.guiLogic.DrawablePlayer;
import com.android.icecave.guiLogic.GUIBoardManager;
import com.android.icecave.guiLogic.TilesView;
import com.android.icecave.mapLogic.IIceCaveGameStatus;
import com.android.icecave.utils.UpdateDataBundle;
import java.util.Observable;
import java.util.Observer;

public class GameActivity extends Activity implements ISwipeDetector, Observer, ILoadable
{
	private GUIBoardManager mGBM;
	private DrawablePlayer mPlayer;
	private GameTheme mGameTheme;
	private TilesView mTilesView;
	private RelativeLayout mActivityLayout;
	private LoadingScreen mLoadingScreen;
	private boolean mIsFlagReached, mIsInitialized;
	private TextView mPlayerMoves, mMinimumMoves;
	private ImageView mResetButton;

	private final String GUI_BOARD_MANAGER_TAG = "guiBoardManager";
	private final long HIDE_SHOW_TIME = 300;

	// Music data
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection mScon;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.tiles_layout);

		mActivityLayout = ((RelativeLayout) findViewById(R.id.game_layout));
		mLoadingScreen = (LoadingScreen) findViewById(R.id.loading_screen);
		mPlayerMoves = (TextView) findViewById(R.id.player_moves);
		mMinimumMoves = (TextView) findViewById(R.id.minimum_moves);
		mResetButton = (ImageView) findViewById(R.id.reset_button);

		// Set initialized to false, as the activity is just now being created
		mIsInitialized = false;

		// Hide the Status Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		SharedPreferences mShared = getSharedPreferences(Consts.PREFS_FILE_TAG, 0);

		// Load GUI Board Manager if exists
		if (savedInstanceState != null)
		{
			mGBM = (GUIBoardManager) savedInstanceState.getSerializable(GUI_BOARD_MANAGER_TAG);
		}

		mGameTheme =
				new GameTheme(	BitmapFactory.decodeResource(getResources(),
										(mShared.getInt(Consts.THEME_SELECT_TAG, Consts.DEFAULT_TILES))),
								BitmapFactory.decodeResource(getResources(),
										(mShared.getInt(Consts.PLAYER_SELECT_TAG, Consts.DEFAULT_PLAYER))));

		// Set reset button effect
		mResetButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Reset player position on logic level
				mGBM.resetPlayer(Consts.DEFAULT_START_POS);

				// Re-initialize player on UI level
				mPlayer.initializePlayer();
			}
		});

		mScon = new ServiceConnection()
		{

			public void onServiceConnected(ComponentName name, IBinder binder)
			{
				mServ = ((MusicService.ServiceBinder) binder).getService();

				// Set music mode for first time
				initMusic();
			}

			public void onServiceDisconnected(ComponentName name)
			{
				mServ = null;
			}
		};

		// Bind music service to activity
		doBindService();
		Intent music = new Intent();
		music.setClass(this, MusicService.class);
		startService(music);
	}

	public int getFixedHeight()
	{
		return mActivityLayout.getBottom() - mPlayerMoves.getBottom();
	}

	public int getFixedWidth()
	{
		return mActivityLayout.getWidth() - mPlayerMoves.getLeft();
	}

	public TilesView getTilesView()
	{
		return mTilesView;
	}

	@Override
	public void bottom2top(View v)
	{
		commitSwipe(EDirection.UP);
	}

	@Override
	public void left2right(View v)
	{
		commitSwipe(EDirection.RIGHT);
	}

	@Override
	public void right2left(View v)
	{
		commitSwipe(EDirection.LEFT);
	}

	@Override
	public void top2bottom(View v)
	{
		commitSwipe(EDirection.DOWN);
	}

	/**
	 * 
	 */
	private void commitSwipe(EDirection direction)
	{
		// Commit a swipe only if animation is not running
		if (!mPlayer.isAnimationRunning())
		{
			// Get status
			IIceCaveGameStatus iceCaveGameStatus = mGBM.movePlayer(direction);

			// Set game ended value
			mIsFlagReached = iceCaveGameStatus.getIsStageEnded();

			// Make movement animation
			mPlayer.movePlayer(direction, iceCaveGameStatus.getPlayerPoint());
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		// Create new stage here to make sure layout is made and active and
		// visible
		if (mGBM == null && !isFinishing())
		{
			// Create once
			mGBM = new GUIBoardManager();

			// Initialize flags (only the first time the activity is created)
			mIsFlagReached = false;

			// Initialize the game board & shit
			mGBM.startNewGame((Integer) getIntent().getExtras().get(Consts.SELECT_BOARD_SIZE_SIZE),
					this,
					EDifficulty.values()[(Integer) getIntent().getExtras().get(Consts.LEVEL_SELECT_TAG)]);

			// Create the first stage
			mLoadingScreen.preLoad(this);

			super.onWindowFocusChanged(hasFocus);
		}
	}

	// Create the tiles view and add it to the layout
	private void createLayouts()
	{
		// Set up reset button image
		mResetButton.setImageResource(R.drawable.reset_button);
		
		mTilesView = new TilesView(this, mGBM.getTiles());
		mTilesView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
																FrameLayout.LayoutParams.WRAP_CONTENT));
		// Position the board just below the text fields
		mTilesView.setTranslationY(mPlayerMoves.getBottom());
		mActivityLayout.addView(mTilesView);

		// Create new player view
		mPlayer = new DrawablePlayer(this, mGameTheme);
		mPlayer.setLayoutParams(new FrameLayout.LayoutParams(	FrameLayout.LayoutParams.WRAP_CONTENT,
																FrameLayout.LayoutParams.WRAP_CONTENT));
		mActivityLayout.addView(mPlayer);

		// Register swipe events to the layout
		mTilesView.setOnTouchListener(new ActivitySwipeDetector(this));
	}

	@Override
	public void update(Observable observable, Object data)
	{
		// Check update flag
		if (data != null)
		{
			UpdateDataBundle updateBundle = (UpdateDataBundle) data;

			// Player movement animation complete
			if (updateBundle.getNotificationId() == Consts.PLAYER_FINISH_MOVE_UPDATE)
			{
				// Update counter
				setPlayerMoves();

				// Run a new game if flag is reached
				if (mIsFlagReached)
				{
					// Reset reached flag
					mIsFlagReached = false;

					// Show loading screen in the meantime
					mLoadingScreen.preLoad(this);
				}
			} else if (updateBundle.getNotificationId() == Consts.LOADING_LEVEL_FINISHED_UPDATE) // Level creation complete
			{
				// If game is not yet initialized
				if (!mIsInitialized)
				{
					// Must run on UI thread
					runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							// Create board and character
							createLayouts();
							
							// Set initialized to true, as the activity has finished initializing
							mIsInitialized = true;
						}
					});
				}
				
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						// Hide loading screen
						mLoadingScreen.postLoad(GameActivity.this);

						// Reset move texts
						setMinimumMoves();
						setPlayerMoves();
						
						// Re-initialize player (must do this on the UI thread)
						mPlayer.initializePlayer();
						
						// Refresh map (must be on UI thread because the view itself is created there)
						mTilesView.invalidate();
						
						// Show views
						drawForeground();
					}
				});
			}
		}
	}

	private void setPlayerMoves()
	{
		// Must run this update on UI thread because it may be called from the update() function
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				// Set player moves value
				mPlayerMoves.setText(getString(R.string.player_moves_text) + " " +
						Integer.toString(mGBM.getMovesCarriedOutThisStage()));

				// Color text differently if player exceeded the minimum moves
				if (mGBM.getMovesCarriedOutThisStage() > mGBM.getMinimalMovesForStage())
				{
					mPlayerMoves.setTextColor(getResources().getColor(R.color.orange));
				} else if (mGBM.getMovesCarriedOutThisStage() == 0)
				{
					// Color text white if player moves reset
					mPlayerMoves.setTextColor(getResources().getColor(R.color.white));
				}
			}
		});
	}

	private void setMinimumMoves()
	{
		// Must run this update on UI thread because it may be called from the update() function
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				// Set minimum moves value
				mMinimumMoves.setText(getString(R.string.minimum_moves_text) + " " +
						Integer.toString(mGBM.getMinimalMovesForStage()));
			}
		});
	}

	// Draws all views on top of background
	public void drawForeground()
	{
		// Display player
		mPlayer.bringToFront();

		// Display text fields
		mMinimumMoves.bringToFront();
		mPlayerMoves.bringToFront();
		mResetButton.bringToFront();
	}

	private void initMusic()
	{
		// Initialize music (or pause it) according to saved selection
		if (getSharedPreferences(Consts.PREFS_FILE_TAG, 0).getBoolean(Consts.MUSIC_MUTE_FLAG, false))
		{
			mServ.pauseMusic();
		} else
		{
			mServ.startMusic();
		}
	}

	private void doBindService()
	{
		bindService(new Intent(this, MusicService.class), mScon, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	private void doUnbindService()
	{
		// Unbind activity from service
		if (mIsBound)
		{
			unbindService(mScon);
			mIsBound = false;
		}
	}

	@Override
	protected void onDestroy()
	{
		doUnbindService();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// Save GUI Board Manager instance
		outState.putSerializable(GUI_BOARD_MANAGER_TAG, mGBM);

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume()
	{
		// Resume/pause music
		if (mServ != null)
		{
			initMusic();
		}

		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// Stop music
		if (mServ != null)
		{
			mServ.pauseMusic();
		}

		super.onPause();
	}

	@Override
	public long getAnimationDuration()
	{
		return HIDE_SHOW_TIME;
	}

	@Override
	public GUIBoardManager getGuiBoardManager()
	{
		return mGBM;
	}

	@Override
	public GameTheme getGameTheme()
	{
		return mGameTheme;
	}

	@Override
	public boolean isInitialLoading()
	{
		// If initialized, not initial loading.
		return !mIsInitialized;
	}
}
