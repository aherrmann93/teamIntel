package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Checks that the plane is going down the screen when the user is not touching the screen.
 */

public class GamePlayingDownUnitTest {

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
        // Wait for the game to update a few times
        Thread.sleep(100);
    }

    /**
     * Checks that the sky is moving
     * @throws Exception
     */
    @Test
    public void checkSkyIsMoving() throws Exception {
        assertNotEquals("Check Sky has moved", 0, game.sky.getX(), 0);
    }

    /**
     * Checks that the plane has moved from the starting position and is going down the screen
     * @throws Exception
     */
    @Test
    public void checkPlaneIsMovingDown() throws Exception {
        assertNotEquals("Check Plane has moved",game.plane.STARTPOSY,game.plane.getY(),0);
        assertTrue("Check that the plane is going down",game.plane.getY() > game.plane.STARTPOSY); // greater than the starting position = going down
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
