package ie.ucd.nearmiss;

import android.graphics.Bitmap;

/**
 * Created by adam on 10/12/2016.
 */

public class Plane extends Sprite {

    private static final int STARTPOSX = 100;
    private static final int STARTPOSY = 50;
    private boolean up;

    public Plane (Bitmap plane_image, int w, int h) {
        setImage(plane_image);
        setWidth(w);
        setHeight(h);
        setX(STARTPOSX);
        setY(STARTPOSY);
    }


    public void goUp (boolean up) {
        this.up = up;
    }

    public void update () {
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
}
