// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		// get the chosen image
		Intent intent = getIntent();
		int picture = intent.getIntExtra(ImageSelection.PICTURE,0);
		
		// show the chosen image
		ImageView imageView = (ImageView)findViewById(R.id.imageview2);
		imageView.setImageResource(picture);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		String message = "";
		int id = item.getItemId();
		if (id == R.id.restart_button) {
			message = "Restart";
		}
		else if (id == R.id.easy_button) {
			message = "Easy";
		}
		else if (id == R.id.medium_button) {
			message = "Medium";
		}
		else if (id == R.id.hard_button) {
			message = "Hard!";
		}
		
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();
		
		return super.onOptionsItemSelected(item);
	}
}
