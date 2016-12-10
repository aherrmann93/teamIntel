package ie.ucd.nearmiss;

import android.graphics.Bitmap;

/**
    Each game object will have the following attributes
 */

public abstract class Sprite {
    private int locx;       // location in x
    private int locy;       // location in y
    private int vecx;       // x vector
    private int vecy;       // y vector
    private int width;      // width of the object
    private int height;     // height of the object
    private Bitmap image;   // image of the object

    // Getters and Setters for each of the above

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

    public void setVecX(int x) {
        this.vecx = x;
    }

    public void setVecY(int y) {
        this.vecy = y;
    }

    public int getVecX() {
        return vecx;
    }

    public int getVecY() {
        return vecy;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
