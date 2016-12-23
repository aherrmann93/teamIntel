package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

/**
 * This test checks that the sky moves at the specified rate of -5px/ms across the screen.
 * This test is set up similarly to the GamePlayingDownUnitTest and checks that the sky has moved
 * from the starting position and that its vector is set to the specified -5px/ms.
 * The test checks multiple times (After 0.1, 1 and 2 seconds) to make sure the sky is continuously moving.
 *
 * This test verifies that the update method in the ControllerThread and Game classes are updating the Sky model correctly.
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
     * Sky position at Test 1
     */
    public int skyPos1;
    /**
     * Sky position at Test 2
     */
    public int skyPos2;

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
     * Check sky is moving at -5px/ms
     * @throws Exception
     */
    @Test
    public void checkSkyIsMovingatMinus5() throws Exception {
        assertEquals("Check sky is moving at -5px/ms",-5,game.sky.getVecX(),0);
    }

    /**
     * Check that the sky has moved
     * @throws Exception
     */
    @Test
    public void checkSkyHasMoved() throws Exception {
        assertNotEquals("Check Sky has moved", 0, skyPos1=game.sky.getX(), 0);
    }

    /**
     * Check that the sky has moved further
     * @throws Exception
     */
    @Test
    public void checkSkyHasMoved1sec() throws Exception {
        Thread.sleep(1000); // wait 1 second
        assertTrue("Check Sky has moved after 1 second", (skyPos2=game.sky.getX())<skyPos1);
    }

    /**
     * Check that the sky has moved further again!
     * @throws Exception
     */
    @Test
    public void checkSkyHasMoved2sec() throws Exception {
        Thread.sleep(2000); // wait 2 seconds
        assertTrue("Check Sky has moved after 2 seconds", game.sky.getX()<skyPos2);
    }
}
