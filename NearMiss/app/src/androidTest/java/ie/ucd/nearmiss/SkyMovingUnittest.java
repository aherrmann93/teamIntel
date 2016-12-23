package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Checks that the sky has moved and is moving at the specified rate (-5px/ms)
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class SkyMovingUnitTest {

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
    public void checkSkyHasMoving() throws Exception {
        assertNotEquals("Check Sky has moved", 0, game.sky.getX(), 0);
    }

    /**
     * Check sky is moving at -5px/ms
     * @throws Exception
     */
    @Test
    public void checkSkyIsMovingatMinus5() throws Exception {
        assertEquals("Check sky is moving at -5px/ms",-5,game.sky.getVecX(),0);
    }
}
