// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		
		// retrieve image id from ImageSelection activity
		Intent intent = getIntent();
		final int image_id = intent.getIntExtra(ImageSelection.PICTURE,0);
		
		// create bitmap picture and store the image corresponding to its id
		Bitmap picture = null;
		if (image_id == 0){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_0);
		}
		else if (image_id == 1){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_1);
		}
		else if (image_id == 2){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_2);
		}
		else if (image_id == 3){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_3);
		}
		else if (image_id == 4){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_4);
		}
		else if (image_id == 5){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_5);
		}
		else if (image_id == 6){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_6);
		}
		else if (image_id == 7){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_7);
		}
		else if (image_id == 8){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_8);
		}
		else if (image_id == 9){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_9);
		}
				
		// connect to imageview by its id
		ImageView imageView = (ImageView)findViewById(R.id.imageview1);
		
		// set image to imageview
		imageView.setImageBitmap(picture);
		
		// create intent GamePlay
		final Intent intentGamePlay = new Intent(this, GamePlay.class);
		
		// add image id to intentGamePlay
        intentGamePlay.putExtra(PICTURE, image_id);
        		
		// create a textview, handler, atomic integer and runnable
		final Handler handler = new Handler();
		final TextView textView = (TextView) findViewById(R.id.countdown);
		final java.util.concurrent.atomic.AtomicInteger n = new AtomicInteger(3);
		final Runnable counter = new Runnable() {
			
			@Override
		    public void run() {
			// count down from three until one
				
				textView.setText(Integer.toString(n.get()));
				if(n.getAndDecrement() >= 1 )
					handler.postDelayed(this, 1000);
		        else {
		        	textView.setVisibility(textView.GONE);
		        	
			        // start GamePlay activity
			        startActivity(intentGamePlay);
			        finish();
		                }
		            }
		        };
		handler.postDelayed(counter, 1000);
		
		
	}
}
