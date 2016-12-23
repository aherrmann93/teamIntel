package ie.ucd.nearmiss;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * This test checks that what the plane and obstacle intersect, a collision is detected.
 * This test also checks that obstacles are created correctly.
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ObstacleCollisionUnitTest {

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
        game.obstacleStartTime = 0; // Set start time to zero, this will force the generation of an obstacle
        game.generateObstacles();
        Thread.sleep(100);
        // Get the position of the obstacle and move the plane to where it is.
        game.plane.setX(game.obstacles.get(0).getX());
        game.plane.setY(game.obstacles.get(0).getY());
        // Let the game update
        Thread.sleep(100);

    }

    @Test
    public void checkObstacleGenerated() throws Exception {
        assertTrue("Obstacle Created?", 0 < game.obstacles.size());
    }

    @Test public void checkCollision() throws Exception {
        assertTrue("Collision must occur", game.plane.collision(game.obstacles.get(0)));
    }

}
