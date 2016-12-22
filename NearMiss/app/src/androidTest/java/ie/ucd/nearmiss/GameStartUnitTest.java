package ie.ucd.nearmiss;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.MotionEvent;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GameStartUnitTest {

    public Context appContext;
    public MainActivity mainActivity;
    public Game game;
    public View view;

    public MotionEvent motionDownEvent;
    public MotionEvent motionUpEvent;

    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis() + 100;

    public int level = 1;

    @Before
    public void setUp() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        // Create new game object
        game = new Game(appContext,level,mainActivity);
        // Create the objects that are created when the surface is created
        game.createInitObjects();

        // Define a motion events
        motionDownEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_DOWN,
                50,
                50,
                1
        );
        motionUpEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                50,
                50,
                1
        );
    }

    @Test
    public void checkContext() throws Exception {
        assertEquals("ie.ucd.nearmiss", appContext.getPackageName());
    }

    @Test
    public void checkNothingMoving() throws Exception {

        // Check playing is false
        assertFalse("Playing must be false",game.playing);
        // Check Sky is not moving
        assertEquals("Check Sky has not moved",0,game.sky.getX(),0);
        // Check Plane is not moving
        assertEquals("Check Sky has not moved",game.plane.STARTPOSY,game.plane.getY(),0);
    }

//    @Test
//    public void checkStart() throws Exception {
//        view.dispatchTouchEvent(motionEvent);
//    }
}
