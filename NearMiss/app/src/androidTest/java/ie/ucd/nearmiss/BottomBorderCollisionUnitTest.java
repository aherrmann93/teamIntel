package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Checks that the game ends when the plane hits the bottom border
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class BottomBorderCollisionUnitTest {

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
    public int level = 3;

    /**
     * Set up the testing environment
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        // Create new game object
        game = new Game(appContext, level, mainActivity);
        // Create the objects that are created when the surface is created
        game.createInitObjects();
        // Start game
        game.playing = true;
        // Wait 5 seconds to give the plane enough time to fall to hit the border
        Thread.sleep(5000);
    }

    @Test
    public void checkBorderBottomCollision() {
        assertFalse("Playing goes to false after collision with border occurs",game.playing);
        assertTrue("Reset goes true after collision with border occurs",game.reset);
    }
}
