package ie.ucd.nearmiss;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Obstacle extends Sprite{
    private int score;
    private int speed;
    private Random rand = new Random();

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
    public void update()
    {
        setX(getX()-speed);
    }

    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(getImage(),getX(),getY(),null);
        }catch(Exception e){}
    }


}