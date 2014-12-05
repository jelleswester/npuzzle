// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
		
		// create imageview and set image
		Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.referee);
		ImageView i = (ImageView) findViewById(R.id.imageViewEnd);
		i.setImageBitmap(picture);
	}
	
	public void imageSelection(View view) {
	    // activates when clicking on no button
			
			// reset check to zero (i.e. no open game left)
	    	Context mContext = getApplicationContext();
	        SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
	    	SharedPreferences.Editor sEdit = mPrefs.edit();
	    	sEdit.putInt("check", 0);
	    	sEdit.commit();
	    	
	    	// create intent ImageSelection and start activity
	    	Intent intentImageSelection = new Intent(this, ImageSelection.class);
	    	startActivity(intentImageSelection);
	    	finish();
	    }
}
