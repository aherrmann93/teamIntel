package ie.ucd.nearmiss;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.example.games.basegameutils.BaseGameUtils;

/**
 * Activity that launches when the app is started.
 *
 * Allows the user to set the settings of the app and view the leaderboard
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /**
     * Reference to the Game View
     */
    public Game view;

    /**
     * Level the user has selected
     */
    private int level = 0;

    /**
     * Reference to the toolbar
     */
    Toolbar toolbar;

    // Variables for Google Services (from the Google API Docs: https://developers.google.com/games/services/training/signin):

    /**
     * Client used to interact with Google APIs
      */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Are we currently resolving a connection failure?
     */
    private boolean mResolvingConnectionFailure = false;

    /**
     * Has the user clicked the sign-in button?
     */
    private boolean mSignInClicked = false;

    /**
     * Automatically start the sign-in flow when the Activity starts
     */
    private boolean mAutoStartSignInFlow = true;

    /**
     * Request code used when launching leaderbaord
     */
    private static final int RC_UNUSED = 5001;
    /**
     * Request code used when launching Google Sign In service
     */
    private static final int RC_SIGN_IN = 9001;

    /**
     * Method which runs when the Activity is Created
     * @param savedInstanceState Previous instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create buttons from the XML View
        findViewById(R.id.playb).setOnClickListener(this);
        findViewById(R.id.levelb).setOnClickListener(this);
        findViewById(R.id.leaderb).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);


        // Create the Google API Client with access to Games
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }

    /**
     * Updates the leaderboard
     * @param scoreToPush Score to send to the leaderboard
     */
    public void updateLeaderboards(int scoreToPush) {
        if (isSignedIn()) {
            if (level == 0) {
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.easy_leaderboard),
                        scoreToPush);
            } else if (level == 1) {
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.medium_leaderboard),
                        scoreToPush);
            } else {
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.hard_leaderboard),
                        scoreToPush);
            }
        }
    }

    /**
     * Method that runs when a Button is clicked
     * @param v Current View
     */
    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;
        switch(clicked.getId()) {
            case R.id.playb:
                // Start the game
                setContentView(view = new Game(this, level, this));
                break;
            case R.id.levelb:
                // Toggle between levels
                toggleLevel();
                break;
            case R.id.leaderb:
                // Launch the leaderbaord
                launchLeaderboard();
                break;
            case R.id.sign_in_button:
                // Sign into Google Play Services
                beginSignIn();
                break;
            case R.id.sign_out_button:
                // Sign out of Google Play Services
                beginSignOut();
                break;
        }
    }

    /**
     * Launches the Leaderboard Activity provided by Google
     */
    private void launchLeaderboard() {
        // If signed in launch leaderboard
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            Toast.makeText(this, "You Need to be Signed In to Use This Feature!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Toggles between the three levels
     */
    private void toggleLevel() {
        Button levelButton = (Button) findViewById(R.id.levelb);
        if (level == 0) {
            level = 1;
            levelButton.setText("Level: Medium");
        }
        else if (level == 1) {
            level = 2;
            levelButton.setText("Level: Hard");
        }
        else {
            level = 0;
            levelButton.setText("Level: Easy");
        }
    }

    /**
     * Changes between Sign In/Sign Out Buttons on the View
     * @param on Display the Sign In Button (True/False)
     * @param displayName Name to display above the menu when welcoming the user
     */
    private void toggleSignInButton(Boolean on, String displayName) {
        TextView welcome;
        welcome = (TextView) findViewById(R.id.textView);

        Button signIn = (Button) findViewById(R.id.sign_in_button);
        Button signOut = (Button) findViewById(R.id.sign_out_button);
        if(!on) {
            // show sign-in button, hide the sign-out button
            signIn.setVisibility(View.GONE);
            signOut.setVisibility(View.VISIBLE);

            // Change welcome label
            welcome.setText("Hello, " + displayName);
        }
        else {
            signOut.setVisibility(View.GONE);
            signIn.setVisibility(View.VISIBLE);

            // Change welcome label
            welcome.setText("Welcome, Tap to Sign In:");
        }
    }

    /**
     * Begin the sign in process
     */
    private void beginSignIn() {
        System.out.println("Checking mGoogleApiClient");
        if(!mGoogleApiClient.isConnected()) {
            System.out.println("Not signed in, signing in");
            // start the sign-in flow
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
    }

    /**
     * Begin the sign out Process
     */
    private void beginSignOut() {
        // sign out.
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        toggleSignInButton(true, null);


    }

    // *************** Code provided by Google for their sign in process: ******************

    /**
     * Checks if the user is currently signed in to Google Play Games
     * @return Is the user currently signed in to Google Play Games?
     */
    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    /**
     * Runs when the Activity Starts
     */
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart(): connecting");
        mGoogleApiClient.connect();
    }

    /**
     * Runs when the Activity Ends
     */
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop(): disconnecting");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method that runs when the user has been connected to Google Play Games
     * @param bundle Bundle passed in by the Google API Client
     */
    @Override
    public void onConnected(Bundle bundle) {

        System.out.println("onConnected(): connected to Google APIs");

        // Set the greeting appropriately on main menu
        Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
        String displayName;
        if (p == null) {
            System.out.println("mGamesClient.getCurrentPlayer() is NULL!");
            displayName = "???";
        } else {
            displayName = p.getDisplayName();
        }
        // Show sign-out button on main menu
        toggleSignInButton(false, displayName);


    }

    /**
     * Mthod that runs when the conenction is suspended
     * @param cause Reason the connection was suspended
     */
    @Override
    public void onConnectionSuspended(int cause) {
        System.out.println("onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }

    /**
     * Method that runs when the connection fails
     * @param connectionResult Result from the connection
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("onConnectionFailed(): attempting to resolve");
        if (mResolvingConnectionFailure) {
            System.out.println("onConnectionFailed(): already resolving");
            return;
        }

        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;
            if (!BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient, connectionResult,
                  RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Sign-in failed, so show sign-in button on main menu
        toggleSignInButton(true, null);
    }

    // ***************** End of Google Code **********************



}
