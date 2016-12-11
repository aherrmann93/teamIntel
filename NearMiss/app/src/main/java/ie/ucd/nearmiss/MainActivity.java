package ie.ucd.nearmiss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.example.games.basegameutils.BaseGameUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public Game view;
    TextView welcome;

    /** Variables for Google Services (from the Google API Docs: https://developers.google.com/games/services/training/signin)**/

    private boolean mAutoStartSignInflow = true;

    // Client used to interact with Google APIs
    private GoogleApiClient mGoogleApiClient;

    // Are we currently resolving a connection failure?
    private boolean mResolvingConnectionFailure = false;

    // Has the user clicked the sign-in button?
    private boolean mSignInClicked = false;

    // Automatically start the sign-in flow when the Activity starts
    private boolean mAutoStartSignInFlow = true;

    // request codes we use when invoking an external activity
    private static final int RC_RESOLVE = 5000;
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart(): connecting");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop(): disconnecting");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("onConnected(): connected to Google APIs");
        // Show sign-out button on main menu
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);

        // Show "you are signed in" message on win screen, with no sign in button.
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);

        // Set the greeting appropriately on main menu
        Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
        String displayName;
        if (p == null) {
            System.out.println("mGamesClient.getCurrentPlayer() is NULL!");
            displayName = "???";
        } else {
            displayName = p.getDisplayName();
        }
        welcome.setText("Hello, " + displayName);

    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }

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
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }

    /** End of Google Code **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create buttons from the XML View
        findViewById(R.id.playb).setOnClickListener(this);
        findViewById(R.id.levelb).setOnClickListener(this);
        findViewById(R.id.leaderb).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        // Create TextView reference
        welcome = (TextView) findViewById(R.id.textView);

        // Create the Google API Client with access to Games
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }

    // On Click Methods
    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;
        switch(clicked.getId()) {
            case R.id.playb:
                setContentView(view = new Game(this));
                break;
            case R.id.levelb:
                break;
            case R.id.leaderb:
                if (isSignedIn()) {
                    startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                            RC_UNUSED);
                } else {

                }
                break;
            case R.id.sign_in_button:
                System.out.println("Checking mGoogleApiClient");
                if(!mGoogleApiClient.isConnected()) {
                    System.out.println("Not signed in, signing in");
                    // start the sign-in flow
                    mSignInClicked = true;
                    mGoogleApiClient.connect();
                }

                break;
            case R.id.sign_out_button:
                // sign out.
                mSignInClicked = false;
                Games.signOut(mGoogleApiClient);
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.disconnect();
                }

                // show sign-in button, hide the sign-out button
                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_button).setVisibility(View.GONE);

                // Change welcome label
                welcome.setText("Welcome, Tap to Sign In:");
                break;

        }

    }

}
