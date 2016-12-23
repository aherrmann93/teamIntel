package ie.ucd.nearmiss;


import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

/**
 * This unit test checks that as the game time progresses the score gets larger and larger.
 * This is tested by checking that the score is higher than the previous score at specific
 * time stamps (100ms, 200ms, 300ms and 400ms). This test is checking the scoring logic in the Game and Plane classes.
 * The test also checks if the add score method works correctly for adding bonus points.
 *
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class CheckScoreIncrementsUnitTest {

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
     * Score at time 200ms
     */
    public int score1;
    /**
     * Score at time 300ms
     */
    public int score2;
    /**
     * Score at time 400ms
     */
    public int score3;

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
     * Checks that the score is not zero
     * @throws Exception
     */
    @Test
    public void checkScoreIsNotZero() throws Exception {
        assertNotEquals("Score must not be zero",0,score1=game.plane.getScore(),0);
    }

    /**
     * Check that the score is incrementing at 200ms
     */
    @Test
    public void checkScore200ms() throws Exception{
        Thread.sleep(200);
        assertTrue("Score has increased after 200ma",(score2=game.plane.getScore())>score1);
    }

    /**
     * Check that the score is incrementing at 300ms
     */
    @Test
    public void checkScore300ms() throws Exception{
        Thread.sleep(300);
        assertTrue("Score has increased after 300ms",(score3=game.plane.getScore())>score2);
    }

    /**
     * Check that the score is incrementing at 400ms
     */
    @Test
    public void checkScore400ms() throws Exception{
        Thread.sleep(400);
        assertTrue("Score has increased after 400ms",game.plane.getScore()>score3);
    }

    /**
     * Check that manually adding points to the score (for bonus points) works as expected
     * @throws Exception
     */
    @Test
    public void checkAddScore() throws Exception {
        int expected = game.plane.getScore()+500;
        game.plane.addScore(500);
        assertEquals("Points after adding 500",expected,game.plane.getScore());

    }

}
