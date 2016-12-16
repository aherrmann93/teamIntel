package ie.ucd.nearmiss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint; //add paint for scoreboard
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;
import android.graphics.Rect;

/**
 * Game View which Handles the Interaction Between the Game Controller and the Models for Each Sprite
 */

public class Game extends SurfaceView implements SurfaceHolder.Callback{

	/**
	* Width of Sky backdrop
	*/
    public static final int WIDTH = 2560;
    /**
	* Height of Sky backdrop
	*/
    public static final int HEIGHT = 1349;
    /**
	* Speed the sky moves in (px/ms)
	*/
    public static final int MOVESPEED = -5;
    /**
	* Game Controller class
	*/
    private ControllerThread controllerThread;
    /**
	* Sky object 
	*/
    private Sky sky;
    /**
	* Plane object
	*/
    private Plane plane;
    /**
	* Is the game currently being played?
	*/
    private boolean playing = false;
    /**
     * Has the player lost in the game?
     */
    private boolean reset = false;
    /**
	* User selected level
	*/
    private int level;
    
    private long obstacleStartTime;
    private long obstacleElapsed;
    private ArrayList<Obstacle> obstacles;
    private Random rand = new Random();
    private int levelspeed;
    private int playerscore = 0;
    private int obstaclespassed = 0;
    
	/**
    * Constructor for the Game View.
    *
    * @param menu Reference back to the menu screen (Main Activity)
    * @param level The user selected level
    */
    public Game(Context menu, int level) {
        super(menu); // Call the superclass SurfaceView's constructor
        getHolder().addCallback(this);
        this.level = level;

        levelspeed = 200+(level*100);

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
        plane = new Plane(BitmapFactory.decodeResource(getResources(),R.drawable.plane),400,200);
        sky.setVecX(-5); // start moving the sky by -5px/ms (I think those are the right units?)
        obstacles = new ArrayList<Obstacle>();
        obstacleStartTime = System.nanoTime();


        controllerThread.setRunning(true);
        controllerThread.start();
    }

    /** 
    * Controls what happens when the user touches the screen
    *
    * @param event The motion event which occurred
    */
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
        if (playing) {
            sky.update();
            plane.update();
            playerscore = plane.planescore()+obstaclespassed; //get player score
            long obstacleElapsed = (System.nanoTime() - obstacleStartTime) / 1000000;
            if (obstacleElapsed > (4000-(level*1000))) {
                obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle1), WIDTH + 10,100+((int)(rand.nextDouble()*(HEIGHT-100))), 200, 300, levelspeed));
                obstacleStartTime = System.nanoTime();
            }


            //loop through every missile and check collision and remove
            for (int i = 0; i < obstacles.size(); i++) {
                //update missile
                obstacles.get(i).update();

                if (collision(obstacles.get(i), plane)) {
                    obstacles.remove(i);
                    playing = false;
                    reset = true; //player has failed level
                    Intent intent = new Intent().setClass(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                    break;
                }
                //remove missile if it is way off the screen
                if (obstacles.get(i).getX() < -100) {
                    obstacles.remove(i);
                    obstaclespassed += 500; //Points for passing an obstacle
                    break;
                }
            }


            if ((plane.getY() < 0) || (plane.getY() > getHeight())) {
                playing = false;
                reset = true; //player has failed level
                // Go back to the main menu
                Intent intent = new Intent().setClass(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        }
    }


    public boolean collision(Sprite a, Sprite b)
    {
        if(Rect.intersects(a.getBoundary(),b.getBoundary()))
        {
            return true;
        }
        return false;
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

            for(Obstacle m: obstacles)
            {
                m.draw(canvas);
            }

            canvas.restoreToCount(savedState);
        }

        drawText(canvas);
    }

    //Method to draw game instructions and player score
    public void drawText(Canvas canvas) {
        if (playing) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("CURRENT SCORE: " + (playerscore), 150, 950, paint);
        }

        if(!playing && !reset) {
            Paint paint1 = new Paint();
            paint1.setTextSize(60);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
            canvas.drawText("TAP TO BEGIN",400,400,paint1);

            paint1.setTextSize(40);
            canvas.drawText("PRESS AND HOLD TO GO UP",400,440,paint1);
        }

    }

}
