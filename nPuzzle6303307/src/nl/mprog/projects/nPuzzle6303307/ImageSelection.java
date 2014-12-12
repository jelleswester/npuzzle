// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ImageSelection extends ActionBarActivity {
	
	// create list of football club names
	ListView list;
	String[] names = {
			"Bayern München FC",
			"Barcelona FC",
			"Chelsea FC",
			"Arsenal FC",
			"Manchester City FC",
			"Manchester United FC",
			"Real Madrid CF",
			"Paris Saint-Germain FC",
			"Liverpool FC",
			"Borussia Dortmund"
			};
	
	// list of corresponding football club pictures
	Integer[] imageId = {
	      R.drawable.icon_0,
	      R.drawable.icon_1,
	      R.drawable.icon_2,
	      R.drawable.icon_3,
	      R.drawable.icon_4,
	      R.drawable.icon_5,
	      R.drawable.icon_6,
	      R.drawable.icon_7,
	      R.drawable.icon_8,
	      R.drawable.icon_9
	  };
	
	// create mediaplayer
	MediaPlayer mediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        
     	// create intent countdown and intent resumegame
        final Intent intentCountDown = new Intent(this, CountDown.class);
        final Intent intentResumeGame = new Intent(this, ResumeGame.class);
        
        // retrieve check from shared preferences
        Context mContext = getApplicationContext();
	    SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
	    int check = mPrefs.getInt("check", 0);
	    
	    // set and play intro music
	    mediaplayer = MediaPlayer.create(mContext, R.raw.intro);
        mediaplayer.start();
		
		// if already open game -> start ResumeGamea activity
		if (check == 1){
			startActivity(intentResumeGame);
			mediaplayer.stop();
			finish();
		}
		
        // create custom list
        CustomList adapter = new CustomList(ImageSelection.this, names, imageId);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
    	@Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		
    		// add the image id to shared preferences
    		int image_id = position;
    		Context mContext = getApplicationContext();
    	    SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
    	    SharedPreferences.Editor sEdit = mPrefs.edit();
    		sEdit.putInt("image_id", image_id);
        	sEdit.commit();
    		
    		// start countdown activity
    		startActivity(intentCountDown);
    		mediaplayer.stop();
    		finish();
    	}
    	});
        }
    
    @Override
    public void onBackPressed() {
        
    	// when back button pressed, stop music
    	mediaplayer.stop();
 
        super.onBackPressed();
    }
    
    public void onPause() {
    	super.onPause();
    	
    	// when activity is not in front screen, stop music
    	mediaplayer.stop();
    }
    
    public void onResume() {
    	super.onResume();
    	
    	// when activity comes back in front screen, start music
    	Context mContext = getApplicationContext();
    	mediaplayer = MediaPlayer.create(mContext, R.raw.intro);
    	mediaplayer.start();
    }
}
