package ie.ucd.nearmiss;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Abstract lass which describes movable objects in the game.
 *
 * Each game object will extend this class. It contains attributes that all game objects will require.
 */

public abstract class Sprite {
    /**
    * Location of Sprite in x plane
    */
    private int locx;
    /**
    * Location of Sprite in y plane
    */
    private int locy;   
    /**
    * Vector of Sprite in x direction
    */
    private int vecx;    
    /**
    * Vector of Sprite in y direction
    */
    private int vecy;     
    /**
    * Acceleration of Sprite in x direction
    */
    private double accx;     
    /**
    * Acceleration of Sprite in y direction
    */
    private double accy;
    /**
    * Width of Sprite
    */
    private int width;    
    /**
    * Height of Sprite
    */
    private int height;  
    /**
    * Bitmap image of the Sprite
    */
    private Bitmap image;

    
    /**
    * Set Location of Sprite in x Plane
    * @param x Location of Sprite in x Plane
    */
    public void setX(int x) {
        this.locx = x;
    }
    /**
    * Set Location of Sprite in y Plane
    * @param y Location of Sprite in y Plane
    */
    public void setY(int y) {
        this.locy = y;
    }
    /**
    * Get Location of Sprite in x Plane
    * @return Location of Sprite in x Plane
    */
    public int getX() {
        return locx;
    }
    /**
    * Get Location of Sprite in y Plane
    * @return Location of Sprite in y Plane
    */
    public int getY() {
        return locy;
    }
    /**
    * Set Acceleration of Sprite in x Direction
    * @param accx Acceleration of Sprite in x Direction
    */
    public void setAccX(double accx) {
        this.accx = accx;
    }
    /**
    * Set Acceleration of Sprite in y Direction
    * @param accy Acceleration of Sprite in y Direction
    */
    public void setAccY(double accy) {
        this.accy = accy;
    }
    /**
    * Get Acceleration of Sprite in x Direction
    * @return Acceleration of Sprite in x Direction
    */
    public double getAccX() {
        return accx;
    }
    /**
    * Get Acceleration of Sprite in y Direction
    * @return Acceleration of Sprite in y Direction
    */
    public double getAccY() {
        return accy;
    }
    /**
    * Set Vector of Sprite in x Direction
    * @param x Vector of Sprite in x Direction
    */
    public void setVecX(int x) {
        this.vecx = x;
    }
    /**
    * Set Vector of Sprite in y Direction
    * @param y Vector of Sprite in y Direction
    */
    public void setVecY(int y) {
        this.vecy = y;
    }
    /**
    * Get Vector of Sprite in x Direction
    * @return Vector of Sprite in x Direction
    */
    public int getVecX() {
        return vecx;
    }
    /**
    * Get Vector of Sprite in y Direction
    * @return Vector of Sprite in y Direction
    */
    public int getVecY() {
        return vecy;
    }
    /**
    * Set Width of Sprite
    * @param width Width of Sprite
    */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
    * Set Height of Sprite
    * @param height Height of Sprite
    */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
    * Get Width of Sprite
    * @return Width of Sprite
    */
    public int getWidth() {
        return width;
    }
    /**
    * Get Height of Sprite
    * @return Height of Sprite
    */
    public int getHeight() {
        return height;
    }
    /**
    * Set Bitmap Image of Sprite
    * @param image Bitmap Image of Sprite
    */
    public void setImage(Bitmap image) {
        this.image = image;
    }
    /**
    * Get Bitmap Image of Sprite
    * @return Bitmap Image of Sprite
    */  
    public Bitmap getImage() {
        return image;
    }

    /**
    * Get the rectangle around the object. Used to set the boundry for collisions
    * @return Rectangle around the Sprite
    */
    public Rect getBoundary() {
        return new Rect(locx,locy, locx+width, locy+height);
    }

    /**
     * Method to check collision
     * @param a Object
     * @return If a collision occured
     */
    public boolean collision(Sprite a)
    {
        if(Rect.intersects(a.getBoundary(),this.getBoundary()))
        {
            return true;
        }
        return false;
    }
    // 
    /**
    * Draw the sprite on the canvas
    * @param canvas Canvas to draw the Bitmap of the Sprite on
    */
    public void draw(Canvas canvas) {

        canvas.drawBitmap(getImage(), getX(), getY(), null);
    }
}
