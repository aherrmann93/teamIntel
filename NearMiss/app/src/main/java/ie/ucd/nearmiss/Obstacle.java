package ie.ucd.nearmiss;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

/**
 * Obstacles Class which Creates and Gives a Speed to an Obstacle
 */


public class Obstacle extends Sprite{
    /**
     * Score Variable
     */
    private int score;
    /**
     * Speed of Obstacle
     */
    private int speed;
    /**
     * Random Object for Varying Speed
     */
    private Random rand = new Random();
    /**
     * Constructor for the Obstacle View.
     *
     * @param obs_image The Image to use as an Obstacle
     * @param x The x co-ordinate of the obstacle
     * @param y The y co-ordinate of the obstacle
     * @param w The width in pixels of the obstacle
     * @param h The height in pixels of the obstacle
     * @param s The Score
     */
    public Obstacle(Bitmap obs_image, int x, int y, int w, int h, int s)
    {
        setImage(obs_image);
        setWidth(w);
        setHeight(h);
        setX(x);
        setY(y);

        score = s;
        setImage(obs_image);
        speed = 7 + (int) (rand.nextDouble()*score/30);

        //cap missile speed
        if(speed>40)speed = 40;


    }

    /**
     * Moves the x location of the Obstacle according to speed
     *
     */
    public void update()
    {
        setX(getX()-speed);
    }

    /**
     * Refreshes the View for the Obstacle
     *
     */
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(getImage(),getX(),getY(),null);
        }catch(Exception e){}
    }


}