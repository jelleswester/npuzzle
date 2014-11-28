// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class ImageSelection extends ActionBarActivity implements AdapterView.OnItemClickListener {
	public final static String PICTURE = "nl.mprog.setup.Hello6303307.PICT";
	
	// list of picture names
	ListView list;
	String[] names = {
			"Bayern",
			"Barcelona",
			"Chelsea",
			"Arsenal",
			"Man City",
			"Man United",
			"Real Madrid",
			"PSG",
			"Liverpool",
			"Dortmund"
			};
	
	// list of pictures
	Integer[] imageId = {
	      R.drawable.puzzle_0,
	      R.drawable.puzzle_1,
	      R.drawable.puzzle_2,
	      R.drawable.puzzle_3,
	      R.drawable.puzzle_4,
	      R.drawable.puzzle_5,
	      R.drawable.puzzle_6,
	      R.drawable.puzzle_7,
	      R.drawable.puzzle_8,
	      R.drawable.puzzle_9
	  };	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        
        final Intent intentCountDown = new Intent(this, CountDown.class);
        
        CustomList adapter = new CustomList(ImageSelection.this, names, imageId);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Toast.makeText(ImageSelection.this, "You Clicked at " +names[+ position], Toast.LENGTH_SHORT).show();	
        		int picture = imageId[position];
        		intentCountDown.putExtra(PICTURE, picture);
        		startActivity(intentCountDown);
        	}
        	});
        }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}	
	}
