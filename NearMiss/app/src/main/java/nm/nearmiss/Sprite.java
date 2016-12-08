package nm.nearmiss;

import android.graphics.Rect;

/**
    Abstract class that defines common atributes for the game objects.
 */

public abstract class Sprite {

    private int locx; // location in x
    private int locy; // location in y
    private int vecx; // x vector
    private int vecy; // y vector
    private int width;    // has to be integers to use the rect type for getting the boundry
    private int height;

    public void setX(int x) {
        this.locx = x;
    }

    public void setY(int y) {
        this.locy = y;
    }

    public int getX() {
        return locx;
    }

    public int getY() {
        return locy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getBoundary() {
        return new Rect(locx,locy, locx+width, locy+height);
    }

}
