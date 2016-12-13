package ie.ucd.nearmiss;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
*   Class which models the Sky. The Sky is an extension of the Sprite class.
*/

public class Sky extends Sprite {
    
    /**
    * Constructor for the Sky object.
    *
    * @param sky_image Bitmap image of the sky
    * @param w Width of the sky image
    * @param h Height of the sky image
    */
    public Sky(Bitmap sky_image, int w, int h) {
        setImage(sky_image);
        setWidth(w);
        setHeight(h);
    }

    /**
    * Updates the position of the sky model
    */
    public void update() {
        setX(getX()+getVecX());     // move x by the x vector

        // if the position of x is less than width of the image, reset it back to 0
        if(getX()<-getWidth()) {
            setX(0);
        }

    }
    /**
    * Draws the sky object onto the canvas.
    *
    * This method overrides the method described in the sprite class, as we require that the sky is redrawn next to itself as it moves off the screen.
    * 
    * @param canvas Canvas on which to draw the sky on.
    */
    @Override
    public void draw(Canvas canvas) {

        // If the sky image goes off the screen draw another one next to it.
        if(getX()<0) {
            canvas.drawBitmap(getImage(), getX()+getWidth(),getY(),null);

        }
        canvas.drawBitmap(getImage(), getX(), getY(), null);
    }
}
