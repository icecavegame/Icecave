package com.tas.icecave.gui;

import com.tas.icecave.R;

import com.tas.icecave.error.ExceptionHandler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.ads.AdView;
import com.tas.icecave.general.MusicService;
import com.tas.icecave.general.sharedPreferences.SharedPreferencesFactory;
import com.tas.icecave.guiLogic.DrawablePlayer;
import com.tas.icecave.guiLogic.GUIBoardManager;
import com.tas.icecave.guiLogic.ScoreManager;
import com.tas.icecave.guiLogic.TilesView;
import com.tas.icecaveLibrary.general.Consts;
import com.tas.icecaveLibrary.general.EDifficulty;
import com.tas.icecaveLibrary.general.EDirection;
import com.tas.icecaveLibrary.mapLogic.IIceCaveGameStatus;
import com.tas.icecaveLibrary.utils.UpdateDataBundle;
import java.util.Observable;
import java.util.Observer;

public class GameActivity extends Activity implements ISwipeDetector, Observer
{
	private GUIBoardManager mGBM;
	private DrawablePlayer mPlayer;
	private GameTheme mGameTheme;
	private TilesView mTilesView;
	private RelativeLayout mActivityLayout, mExtras;
	private LoadingScreen mLoadingScreen;
	private boolean mIsFlagReached, mIsInitialized;
	private TextView mPlayerMoves, mMinimumMoves, mMovesPercent, mLevelSelected;
	private ImageView mResetButton;
	private AdView mAd;
	private ScoreManager mScoreManager;
	private CheckBox mMuteMusic;

	private final String GUI_BOARD_MANAGER_TAG = "guiBoardManager";

	// Music data
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection mScon;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set an exception handler for this activity first of all
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.tiles_layout);

		mActivityLayout = ((RelativeLayout) findViewById(R.id.game_layout));
		mLoadingScreen = (LoadingScreen) findViewById(R.id.loading_screen);
		mPlayerMoves = (TextView) findViewById(R.id.player_moves);
		mMinimumMoves = (TextView) findViewById(R.id.minimum_moves);
		mMovesPercent = (TextView) findViewById(R.id.moves_percent);
		mResetButton = (ImageView) findViewById(R.id.reset_button);
		mExtras = (RelativeLayout) findViewById(R.id.extras);
		mAd = (AdView) findViewById(R.id.advertisment_game_activity_bottom);

		mMuteMusic = (CheckBox) findViewById(R.id.mute_music);
		mLevelSelected = (TextView) findViewById(R.id.level_selected);

		// Set styles
		Typeface iceAge = Typeface.createFromAsset(getAssets(), Consts.STYLE_SNOW_TOP);
		mMuteMusic.setTypeface(iceAge);
		mMinimumMoves.setTypeface(iceAge);
		mPlayerMoves.setTypeface(iceAge);
		mLevelSelected.setTypeface(iceAge);

		// Set initialized to false, as the activity is just now being created
		mIsInitialized = false;

		// Hide the Status Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Load GUI Board Manager if exists
		if (savedInstanceState != null)
		{
			mGBM = (GUIBoardManager) savedInstanceState.getSerializable(GUI_BOARD_MANAGER_TAG);
		}

		mGameTheme =
				new GameTheme(	BitmapFactory.decodeResource(getResources(),
										(Integer) (SharedPreferencesFactory.getInstance().get(Consts.THEME_SELECT_TAG))),
								BitmapFactory.decodeResource(getResources(),
										(Integer) (SharedPreferencesFactory.getInstance().get(Consts.PLAYER_SELECT_TAG))));

		mScoreManager = new ScoreManager();

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

				// Reset move count
				// mGBM.resetMoves(); Not for now

				// Update move counter text
				updateMovesData();
			}
		});

		mScon = new ServiceConnection()
		{

			public void onServiceConnected(ComponentName name, IBinder binder)
			{
				mServ = ((MusicService.ServiceBinder) binder).getService();

				// Set music mode for first time
				initMusic();

				// Check according to saved data
				mMuteMusic.setChecked((Boolean) SharedPreferencesFactory.getInstance()
						.get(Consts.MUSIC_MUTE_FLAG));
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

		mMuteMusic.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// Save selection
				SharedPreferencesFactory.getInstance().set(Consts.MUSIC_MUTE_FLAG, isChecked);

				// Play/pause music
				initMusic();
			}
		});
	}

	public boolean isInitialized()
	{
		return mIsInitialized;
	}

	public GameTheme getGameTheme()
	{
		return mGameTheme;
	}

	public int getHeight()
	{
		return mActivityLayout.getBottom();
	}

	public int getWidth()
	{
		return mActivityLayout.getWidth();
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

			if(iceCaveGameStatus.getBoardChanged()){
				mGBM.updateBoard(mGameTheme);
			}
			
			// Make movement animation
			mPlayer.movePlayer(direction, 
						       iceCaveGameStatus.getPlayerPoint());
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
			mGBM = new GUIBoardManager(this);

			// Initialize flags (only the first time the activity is created)
			mIsFlagReached = false;

			// Initialize the game board & shit
			mGBM.startNewGame((Integer) getIntent().getExtras().get(Consts.SELECT_BOARD_SIZE_SIZE),
					EDifficulty.values()[(Integer) getIntent().getExtras().get(Consts.LEVEL_SELECT_TAG)]);

			// Set views references for the loading screen
			mLoadingScreen.setViews();
			
			// Set level index for the first time..
			updateLevelIndex(mGBM.getBundleIndex());

			// Create the first stage
			mLoadingScreen.preLoad(mGBM);

			super.onWindowFocusChanged(hasFocus);
		}
	}

	// Create the tiles view and add it to the layout
	private void createLayouts()
	{
		// Show hidden views
		showViews();

		mTilesView = new TilesView(this, mGBM.getTiles());

		// Position the board below the player moves text and add the view
		mTilesView.setLayoutParams(new RelativeLayout.LayoutParams(	RelativeLayout.LayoutParams.WRAP_CONTENT,
																	RelativeLayout.LayoutParams.WRAP_CONTENT));

		// Set the board a little below the texts at the top
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

	// Shows views after the layout has been created. This is to avoid having a "blink" of random text before load
	private void showViews()
	{
		mResetButton.setVisibility(View.VISIBLE);
		mMuteMusic.setVisibility(View.VISIBLE);
		mLevelSelected.setVisibility(View.VISIBLE);
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
				updateMovesData();

				// Run a new game if flag is reached
				if (mIsFlagReached)
				{
					// Special condition flag updates!!
					flagUpdate();

					// Reset reached flag
					mIsFlagReached = false;

					// Save the new stage index if stage was loaded from file.. and update the level index text
					updateLevelIndex(mGBM.saveStageIndex());

					// Disable the ad while loading screen is active
					disableButtons();

					// Show loading screen in the meantime
					mLoadingScreen.preLoad(mGBM);
				}
			} else if (updateBundle.getNotificationId() == Consts.LOADING_LEVEL_FINISHED_UPDATE) // Level creation complete
			{
				// Must run on UI thread
				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						// If game is not yet initialized
						if (!mIsInitialized)
						{

							// Create board and character
							createLayouts();

							// Set initialized to true, as the activity has finished initializing
							mIsInitialized = true;
						}

						// Hide loading screen
						mLoadingScreen.postLoad(mGBM);

						// Enable the ad
						enableButtons();

						// Set new GUI level board in case it was restarted (if loaded from file)
						mTilesView.setNewBoard(mGBM.getTiles());

						// Reset move texts
						setMinimumMoves();

						// Increase score manager counter
						mScoreManager.updateOverAllMinMoves(mGBM.getMinimalMovesForStage());

						updateMovesData();

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

	// Update the level selected name + the index of the current stage
	private void updateLevelIndex(int levelIndex)
	{
		// Show level name + level index. Show infinity if no index +1 for readability 
		mLevelSelected.setText(EDifficulty.values()[(Integer) getIntent().getExtras()
				.get(Consts.LEVEL_SELECT_TAG)].name() + ": " + ((levelIndex != -1) ? levelIndex  + 1 : getString(R.string.infinity)));
	}

	private void flagUpdate()
	{
		// If any difficulty level was solved, unlock hard difficulty
		SharedPreferencesFactory.getInstance().set(Consts.LOCK_HARD_DIFFICULTY, false);
	}

	private void updateMovesData()
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
				mPlayerMoves.setTextColor(getResources().getColor(mScoreManager.getColorOverMinMoves(mGBM.getMovesCarriedOutThisStage(),
						mGBM.getMinimalMovesForStage())));

				// Update percent data and color
				mMovesPercent.setText(mScoreManager.getTotalMovesPercent(mGBM.getOverAllMovesForGame(),
						mScoreManager.getOverAllMinMoves()));
				mMovesPercent.setTextColor(getResources().getColor(mScoreManager.getColorByTotalMoves(mGBM.getOverAllMovesForGame(),
						mScoreManager.getOverAllMinMoves())));
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

		// Display various views
		mMinimumMoves.bringToFront();
		mPlayerMoves.bringToFront();
		mExtras.bringToFront();
		mResetButton.bringToFront();
		mAd.bringToFront();
	}

	private void initMusic()
	{
		// Initialize music (or pause it) according to saved selection
		if ((Boolean) SharedPreferencesFactory.getInstance().get(Consts.MUSIC_MUTE_FLAG))
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

	private void enableButtons()
	{
		mAd.setClickable(true);
		mResetButton.setEnabled(true);
	}

	private void disableButtons()
	{
		mAd.setClickable(false);
		mResetButton.setEnabled(false);
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
}
