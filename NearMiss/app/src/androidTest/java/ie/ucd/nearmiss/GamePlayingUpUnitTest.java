package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Checks that the plane goes up the screen when the user is touching the screen.
 *
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
     * Check that the sky is moving
     * @throws Exception
     */
    @Test
    public void checkSkyIsMoving() throws Exception {
        assertNotEquals("Check Sky has moved", 0, game.sky.getX(), 0);
    }

    /**
     * Check that the plane has moved from its starting position and that it is moving up the screen
     * @throws Exception
     */
    @Test
    public void checkPlaneIsMovingUp() throws Exception {
        assertNotEquals("Check Plane has moved",game.plane.STARTPOSY,game.plane.getY(),0);
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
