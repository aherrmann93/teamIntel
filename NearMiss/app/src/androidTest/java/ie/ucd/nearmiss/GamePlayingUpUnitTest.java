package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Checks that the plane goes up the screen when the user is touching the screen.
 *
 */

public class GamePlayingUpUnitTest {

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
        // Start game
        game.playing = true;
        // Set plane going up
        game.plane.goUp(true);
        // Wait for the game to update a few times
        Thread.sleep(100);
    }

    @Test
    public void checkSkyIsMoving() throws Exception {
        assertNotEquals("Check Sky has moved", 0, game.sky.getX(), 0);
    }

    @Test
    public void checkPlaneIsMovingDown() throws Exception {
        assertNotEquals("Check Plane has moved",game.plane.STARTPOSY,game.plane.getY(),0);
        assertTrue("Check that the plane is going down",game.plane.getY() < game.plane.STARTPOSY); // positive vector = going down
    }

    @Test
    public void checkScoreIsNotZero() throws Exception {
        assertNotEquals("Score must not be zero",0,game.plane.getScore(),0);
    }
}
