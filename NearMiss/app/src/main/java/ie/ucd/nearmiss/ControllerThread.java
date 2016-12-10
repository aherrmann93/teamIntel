package ie.ucd.nearmiss;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
    THIS CLASS IS BASED ON THE FOLLOWING TUTORIAL: https://www.youtube.com/watch?v=-XOMJYZmfkw&list=PLWweaDaGRHjvQlpLV0yZDmRKVBdy6rSlg&index=2
 */

public class ControllerThread extends Thread {

    private int FPS = 30;   //capping the frames per second to be 30
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private View view;
    private boolean running;
    public static Canvas canvas;

    private static final int NANOSEC = 1000000000;

    public ControllerThread(SurfaceHolder surfaceHolder, View view) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.view = view;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
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

    public void setRunning(boolean val) {
        running = val;
    }
}
