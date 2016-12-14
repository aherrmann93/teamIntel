package ie.ucd.nearmiss;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Thread which controls the updating of models and instructing the models to draw themselves.
 *
 * This class is from an Online Tutorial. Please see author.
 *
 * @author Paymon Wang-Lotfi
 * @see <a href="https://www.youtube.com/watch?v=-XOMJYZmfkw&list=PLWweaDaGRHjvQlpLV0yZDmRKVBdy6rSlg&index=2">Tutorial</a>
 */

public class ControllerThread extends Thread {

	/**
    * Frames per second cap.
    */
    private int FPS = 30;
    /**
    * Average frames per second.
    */
    private double averageFPS;
    /**
    * Abstract interface to the surface view.
    */
    private SurfaceHolder surfaceHolder;
    /**
    * Reference to the Surface View which the game is drawn on.
    */
    private Game view;
    /**
    * Is the game currently running?
    */
    private boolean running;
    /**
    * Canvas which the sprites are drawn on.
    */
    public static Canvas canvas;
	/**
    * Definition of a nanosecond
    */
    private static final int NANOSEC = 1000000000;
    
	/**
    * Constructor for the Game Controller.
    *
    * @param surfaceHolder Interface to the Surface View
    * @param view Reference to the Surface View 
    */
    public ControllerThread(SurfaceHolder surfaceHolder, Game view) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }

	/**
    * Main game engine which controls the timing of the updates and draws based on the maximum FPS defined.
    *
    * Override of the run method from the Thread class.
    */
    @Override
    public void run() {
    	/**
    	* Time at the start of the current iteration
    	*/
        long startTime;
        /**
    	* Time of this iteration (in milliseconds)
    	*/
        long timeMillis;
        /**
   	 	* Time to wait in order to keep the FPS capped (in milliseconds)
   	 	*/
        long waitTime;
        /**
   	 	* Total time spent in the iteration (in milliseconds)
    	*/
        long totalTime = 0;
        /**
    	* How many frames have occurred
    	*/
        int frameCount = 0;
        /**
    	* Target time per frame (in milliseconds)
    	*/
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            // Lock canvas while updating and drawing the views
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.view.update();     // Update the view
                    this.view.draw(canvas); // Draw the view
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                // Unlock the canvas
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Calculate the wait time in order to cap the frame rate at 30FPS
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime-timeMillis;

            try {
                sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime()-startTime;
            frameCount++;

            if(frameCount == FPS)
            {
                averageFPS = NANOSEC/(totalTime/frameCount);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }

        }
    }
    
	/**
	* Set the game loop running
	*
	* @param val Start the game? True/False
	*/
    public void setRunning(boolean val) {
        running = val;
    }
}
