package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test checks that when the user does touch the screen, the plane moves up the screen. This test is set up similarly to GamePlayingDownUnitTest except the setUp method is called for the duration of the 100ms. The expected behaviour is that the plane moves up from the starting position and that the score increments.
 *
 * This test verifies that the update method in the ControllerThread and Game classes are updating the Plane model correctly.
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class GamePlayingUpUnitTest {

    /**
     * Application Context
     */
    public Context appContext;
    /**
     * Reference to the main activity
     */
    public MainActivity mainActivity;
    /**
     * Reference to the game
     */
    public Game game;
    /**
     * Level to pass into the game constructor
     */
    public int level = 1;

    /**
     * Set up the testing environment
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        // Create new game object
        game = new Game(appContext,level,mainActivity);
        // Create the objects that are created when the surface is created
        game.createInitObjects();
        // Start game
        game.playing = true;
        // Set plane going up
        game.plane.goUp(true);
        // Wait for the game to update a few times
        Thread.sleep(100);
    }

    /**
     * Check that the plane has moved from its starting position and that it is moving up the screen
     * @throws Exception
     */
    @Test
    public void checkPlaneIsMovingUp() throws Exception {
        assertTrue("Check that the plane is going down",game.plane.getY() < game.plane.STARTPOSY); // less than the starting position = going up
    }

    /**
     * Checks that the score is incrementing
     * @throws Exception
     */
    @Test
    public void checkScoreIsNotZero() throws Exception {
        assertNotEquals("Score must not be zero",0,game.plane.getScore(),0);
    }
}
