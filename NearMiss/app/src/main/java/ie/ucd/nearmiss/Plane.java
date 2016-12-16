package ie.ucd.nearmiss;

import android.graphics.Bitmap;

/**
*   Class which models the Plane. The Sky is an extension of the Sprite class.
*/

public class Plane extends Sprite {
    
    /**
    * Initial position of the plane in the x plane
    */
    private static final int STARTPOSX = 100;
    /**
    * Initial position of the plane in the y plane
    */
    private static final int STARTPOSY = 50;
    /**
    * Is the plane going up or down?
    */
    private boolean up;
    /**
     * Variable for calculating playtime during session
     */
    private long startTime;
    /**
     * Part of player score as they play the level
     */
    private int score;
    
    /**
    * Constructor for the Plane object.
    *
    * @param plane_image Bitmap image of the plane
    * @param w Width of the plane image
    * @param h Height of the plane image
    */
    public Plane (Bitmap plane_image, int w, int h) {
        setImage(plane_image);
        setWidth(w);
        setHeight(h);
        setX(STARTPOSX);
        setY(STARTPOSY);
    }

    /**
    * Sets whther the plane is moving up or down
    *
    * @param up Is the plane going up (true/false)
    */
    public void goUp (boolean up) {
        this.up = up;
    }

    /**
    * Updates the position of the plane model
    */
    public void update () {

        long elapsed = (System.nanoTime()-startTime)/1000000;

        if (elapsed>0) {
            score++;
            startTime = System.nanoTime();
        }
        if(up) {
            setAccY(getAccY()-1.1);
            setVecY((int)(getAccY()));
        }
        else {
            setAccY(getAccY()+1.1);
            setVecY((int)(getAccY()));
        }
        // Cap the speed at +10 and -10
        if(getVecY()>10) {
            setVecY(10);
        }
        if(getVecY()<-10) {
            setVecY(-10);
        }

        // Update the position of the plane on the Y axis
        setY(getY() + getVecY()*2);
        System.out.println(getY());

        setVecY(0);

    }

    //Method to return player score
    public int planescore() {
        return score;
    }
}
