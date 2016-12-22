package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



/**
 * Check that the game objects are not moving when the user launches the game.
 *
 * This test checks the sky, plane and score to check that they have not moved from the default starting position.
 * It also checks that the FPS that the Controller is achieving is less than the cap of 30.
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GameStartUnitTest {

    public Context appContext;
    public MainActivity mainActivity;
    public Game game;


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

    @Test
    public void checkContext() throws Exception {
        assertEquals("ie.ucd.nearmiss", appContext.getPackageName());
    }

    @Test
    public void checkGameIsNotInPlay() throws Exception {
        assertFalse("Playing must be false", game.playing);
    }

    @Test
    public void checkSkyIsNotMoving() throws Exception {
        assertEquals("Check Sky has not moved", 0, game.sky.getX(), 0);
    }

    @Test
    public void checkPlaneIsNotMoving() throws Exception {
        assertEquals("Check Sky has not moved",game.plane.STARTPOSY,game.plane.getY(),0);
    }

    @Test
    public void checkScoreIsZero() throws Exception {
        assertEquals("Score must be zero at the start",0,game.plane.getScore(),0);
    }

    @Test
    public void checkFPS() throws Exception {
        assertTrue("FPS has to be capped at 30",game.controllerThread.averageFPS < 31);
    }

}
