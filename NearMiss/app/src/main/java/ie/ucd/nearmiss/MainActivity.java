package ie.ucd.nearmiss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ie.ucd.nearmiss.View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create buttons from the XML View
        Button play = (Button) findViewById(R.id.playb);
        play.setOnClickListener(this);

        Button levels = (Button) findViewById(R.id.levelb);
        levels.setOnClickListener(this);

        Button leaderboard = (Button) findViewById(R.id.leaderb);
        leaderboard.setOnClickListener(this);

        Button googleplaybutton = (Button) findViewById(R.id.googleplayb);
        googleplaybutton.setOnClickListener(this);
    }

    // On Click Methods
    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;
        switch(clicked.getId()) {
            case R.id.playb:
                setContentView(view = new ie.ucd.nearmiss.View(this));
                break;
            case R.id.levelb:
                break;
            case R.id.leaderb:
                break;
            case R.id.googleplayb:
                break;
        }

    }
}
