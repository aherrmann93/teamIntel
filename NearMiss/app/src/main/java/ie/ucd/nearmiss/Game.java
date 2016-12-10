package ie.ucd.nearmiss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
    THIS CLASS USES CODE FROM THE TUTORIAL MENTIONED IN CONTROLLERTHREAD.java
    It has been modified to suit the needs of our game.
 */

public class Game extends SurfaceView implements SurfaceHolder.Callback{

    private ControllerThread controllerThread;
    private Sky sky;
    private Plane plane;
    private boolean playing = false;

    public Game(Context menu) {
        super(menu); // Call the superclass SurfaceView's constructor
        getHolder().addCallback(this);

        // Start the Controller Thread to get the game going
        controllerThread = new ControllerThread(getHolder(), this);

        // Make this view the focused view
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while(retry) {
            try{
                controllerThread.setRunning(false);
                controllerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        sky = new Sky(BitmapFactory.decodeResource(getResources(),R.drawable.sky_wikipedia),2560,1349);
        plane = new Plane(BitmapFactory.decodeResource(getResources(),R.drawable.plane),225,82);
        sky.setVecX(-5); // start moving the sky by -5px/ms (I think those are the right units?)
        controllerThread.setRunning(true);
        controllerThread.start();
    }

    // Controls what happens when the user touches the screen
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if(!playing) {
                playing = true;
            }
            else {
                System.out.println("Going up");
                plane.goUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            plane.goUp(false);
            System.out.println("No longer going up");
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        if(playing) {
            sky.update();
            plane.update();

            if((plane.getY()<0)||(plane.getY()>getHeight())) {
                playing = false;
                Intent intent = new Intent().setClass(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        }


    }
    @Override
    public void draw(Canvas canvas) {
        // Need to scale the background to fit the phone that the user is using
        final double scaleFactorX = (double)getWidth()/sky.getWidth();
        final double scaleFactorY = (double)getHeight()/sky.getHeight();
        //System.out.println(getWidth() + " , " + getHeight() + " , " + scaleFactorX + " , " + scaleFactorY);

        // If the canvas exists, draw the objects
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale((float)scaleFactorX, (float)scaleFactorY);
            sky.draw(canvas);
            plane.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

}
