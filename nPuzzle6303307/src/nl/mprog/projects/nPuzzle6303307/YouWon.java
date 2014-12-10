// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class YouWon extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_won);
		
		// retrieve number of steps from GamePlay activity
		Intent intent = getIntent();
		int steps = intent.getIntExtra(GamePlay.STEPS, 0);
		
		// create textview and set number of steps
		TextView t = (TextView) findViewById(R.id.numStepsView);
		t.setText("You won in " + steps + " steps!");
		
		// create imageview and set scaled image
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.referee);
		int height = (int)(picture.getHeight() * 0.5);
		int width = (int)(picture.getWidth() * 0.5);
		Bitmap image = Bitmap.createScaledBitmap(picture, width, height, true);
		ImageView i = (ImageView) findViewById(R.id.imageViewEnd);
		i.setImageBitmap(image);
		
		// reset check to zero (i.e. no open game left)
    	Context mContext = getApplicationContext();
        SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
    	SharedPreferences.Editor sEdit = mPrefs.edit();
    	sEdit.putInt("check", 0);
    	sEdit.commit();
    	
    	// set and play winning tune
	    MediaPlayer mediaplayer = MediaPlayer.create(mContext, R.raw.win);
        mediaplayer.start();
	}
	
	// activates when clicking on new game button
	public void imageSelection(View view) {
	    	
	    	// create intent imageselection and start activity
	    	Intent intentImageSelection = new Intent(this, ImageSelection.class);
	    	startActivity(intentImageSelection);
	    	finish();
	    }
}
