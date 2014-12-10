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

public class ResumeGame extends ActionBarActivity {	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resume_game);
		
		// create imageview and connect to xml file
		ImageView imageView = (ImageView)findViewById(R.id.imageview_resume);
		
		// retrieve picture from drawables and set to imageview
		Bitmap picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.continue_playing);
		imageView.setImageBitmap(picture);
	}
	
	// activates when clicking on yes button
	public void gamePlay(View view) {
    
		// create intent gameplay and start activity
    	Intent intentGamePlay = new Intent(this, GamePlay.class);
    	startActivity(intentGamePlay);
    	finish();
    }
	
	
	// activates when clicking on no button
	public void imageSelection(View view) {
    
		// reset check to zero (i.e. no open game left)
    	Context mContext = getApplicationContext();
        SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
    	SharedPreferences.Editor sEdit = mPrefs.edit();
    	sEdit.putInt("check", 0);
    	sEdit.commit();
    	
    	// create intent imageselection and start activity
    	Intent intentImageSelection = new Intent(this, ImageSelection.class);
    	startActivity(intentImageSelection);
    	finish();
    }
}
