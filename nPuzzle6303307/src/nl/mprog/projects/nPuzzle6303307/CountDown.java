package nl.mprog.projects.nPuzzle6303307;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class CountDown extends ActionBarActivity {
	public final static String PICTURE = "nl.mprog.setup.Hello6303307.PICT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_down);
		
		// get the chosen image
		Intent intent = getIntent();
		final int picture = intent.getIntExtra(ImageSelection.PICTURE,0);
		Bitmap image = BitmapFactory.decodeResource(getResources(),picture);
		image = Bitmap.createScaledBitmap(image, 500, 350, true);
		
		// show the chosen image
		ImageView imageView = (ImageView)findViewById(R.id.imageview1);
		imageView.setImageBitmap(image);
		
		final Intent intentGamePlay = new Intent(this, GamePlay.class);
		final Handler handler = new Handler();
		final TextView textView = (TextView) findViewById(R.id.countdown);
		final java.util.concurrent.atomic.AtomicInteger n = new AtomicInteger(3);
		final Runnable counter = new Runnable() {
			
			@Override
		    public void run() {
				textView.setText(Integer.toString(n.get()));
				if(n.getAndDecrement() >= 1 )
					handler.postDelayed(this, 1000);
		        else {
		        	textView.setVisibility(textView.GONE);
        
		// start the game
        intentGamePlay.putExtra(PICTURE, picture);
        startActivity(intentGamePlay);
        finish();
		                }
		            }
		        };
		handler.postDelayed(counter, 1000);
		
		
	}
}
