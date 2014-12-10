// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class CountDown extends ActionBarActivity {
	
	// declare bitmap picture
	Bitmap picture = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count_down);
		
		// retrieve image id from shared preferences
		Context mContext = getApplicationContext();
	    SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
	    int image_id = mPrefs.getInt("image_id", 0);
		
		// get image using image_id
		getImage(image_id);
				
		// create imageview and connect to xml file
		ImageView imageView = (ImageView)findViewById(R.id.imageview1);
		
		// set image to imageview
		imageView.setImageBitmap(picture);
		
		// create intent GamePlay 
		final Intent intentGamePlay = new Intent(this, GamePlay.class);
        		
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
	
private void getImage(int image_id){
		
		// get image_id
		int image = image_id;
		
		// using image id, store corresponding image in bitmap picture
		if (image == 0){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_0);
		}
		else if (image == 1){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_1);
		}
		else if (image == 2){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_2);
		}
		else if (image == 3){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_3);
		}
		else if (image == 4){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_4);
		}
		else if (image == 5){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_5);
		}
		else if (image == 6){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_6);
		}
		else if (image == 7){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_7);
		}
		else if (image == 8){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_8);
		}
		else if (image == 9){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_9);
		}
	}
}
