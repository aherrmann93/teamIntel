package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



/**
 * This test checks that when the game is started by the user, the sky and plane have been created and initially nothing is moving (since the app waits for the user to tap the screen before the objects move). This test also checks that the score has been set to 0 before the game begins. We also test that the frames per second is being capped correctly at max 30 FPS.
 *
 * This test verifies that the ControllerThread starts correctly and that the Sky and Plane classes have been constructed correctly.
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GameStartUnitTest {

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

    @Before
    public void setUp() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        // Create new game object
        game = new Game(appContext,level,mainActivity);
        // Create the objects that are created when the surface is created
        game.createInitObjects();
    }

    /**
     * Check the application context
     * @throws Exception
     */
    @Test
    public void checkContext() throws Exception {
        assertEquals("ie.ucd.nearmiss", appContext.getPackageName());
    }

    /**
     * Check the game is not in play mode
     * @throws Exception
     */
    @Test
    public void checkGameIsNotInPlay() throws Exception {
        assertFalse("Playing must be false", game.playing);
    }

    /**
     * Check that the sky is not moving
     * @throws Exception
     */
    @Test
    public void checkSkyIsNotMoving() throws Exception {
        assertEquals("Check Sky has not moved", 0, game.sky.getX(), 0);
    }

    /**
     * Check that the Plane is not moving
     * @throws Exception
     */
    @Test
    public void checkPlaneIsNotMoving() throws Exception {
        assertEquals("Check Plane has not moved",game.plane.STARTPOSY,game.plane.getY(),0);
    }

    /**
     * Check that the score is zero
     *
     * @throws Exception
     */
    @Test
    public void checkScoreIsZero() throws Exception {
        assertEquals("Score must be zero at the start",0,game.plane.getScore(),0);
    }

    /**
     * Check that the average frames per second being generated from the controller thread is less than 30FPS
     * @throws Exception
     */
    @Test
    public void checkFPS() throws Exception {
        assertTrue("FPS has to be capped at 30",game.controllerThread.averageFPS < 31);
    }

}
