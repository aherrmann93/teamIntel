package ie.ucd.nearmiss;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
    Class which models the Sky. The Sky is an extension of the Sprite class.
    The Update method updates the position of the sky.
    The Draw method draws the sky (it overrides the method in the Sprite class as it needs to draw another image of the sky as it moves off the screen
 */

public class Sky extends Sprite {

    public Sky(Bitmap sky_image, int w, int h) {
        setImage(sky_image);
        setWidth(w);
        setHeight(h);
    }

    // Update the position of the sky in the model
    public void update() {
        setX(getX()+getVecX());     // move x by the x vector

        // if the position of x is less than width of the image, reset it back to 0
        if(getX()<-getWidth()) {
            setX(0);
        }

    }
    // Override the method we created in Sprite class
    @Override
    public void draw(Canvas canvas) {

        // If the sky image goes off the screen draw another one next to it.
        if(getX()<0) {
            canvas.drawBitmap(getImage(), getX()+getWidth(),getY(),null);

        }
        canvas.drawBitmap(getImage(), getX(), getY(), null);
    }
}
