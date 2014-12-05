// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ImageSelection extends ActionBarActivity implements AdapterView.OnItemClickListener {
	public final static String PICTURE = "nl.mprog.setup.Hello6303307.PICT";
	
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        
     	// create intent countDown and intent ResumeGame
        final Intent intentCountDown = new Intent(this, CountDown.class);
        final Intent intentResumeGame = new Intent(this, ResumeGame.class);
        
        // retrieve check from savings
        Context mContext = getApplicationContext();
	    SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
		int check = mPrefs.getInt("check", 0);
		
		// if already open game -> start ResumeGamea activity
		if (check == 1){
			startActivity(intentResumeGame);
			finish();
		}
		
        // create custom list
        CustomList adapter = new CustomList(ImageSelection.this, names, imageId);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	// when clicking on one item in the list:
        		
        		// add the image id to intentCountDown
        		int image_id = position;
        		intentCountDown.putExtra(PICTURE, image_id);
        		
        		// start Countdown activity
        		startActivity(intentCountDown);
        		finish();
        	}
        	});
        }
    

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}	
	}
