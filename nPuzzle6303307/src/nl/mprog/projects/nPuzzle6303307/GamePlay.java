// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GamePlay extends ActionBarActivity {
	public final static String STEPS = "nl.mprog.setup.Hello6303307.STEPS";
	public class completeTile{
		
		// one tile contains a bitmap and a number
		int number;
		Bitmap bitmap;
	}
	
	// create gridview, imageview and imageadapter
	GridView gridView;
	ImageView imageView;
	ImageAdapter mAdapter1;
	
	// create an arraylist of complete tiles
	ArrayList<completeTile> tiles = new ArrayList<completeTile>();
	
	// declare current level and blank location
	int cur_level;
	int blank_location;
	
	// declare image_id and bitmap picture
	int image_id;
	Bitmap picture = null;
	
	// declare number of steps (in order to track)
	int num_steps;
	
	// declare display width and height
	DisplayMetrics displaymetrics;
	int display_height;
	int display_width;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
	
		// open shared preferences and editor
		Context mContext = getApplicationContext();
	    SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
	    SharedPreferences.Editor sEdit = mPrefs.edit();
		
	    // retrieve check from shared preferences
	    int check = mPrefs.getInt("check", 0);
	    
	    // retrieve image_id from shared preferences
	    image_id = mPrefs.getInt("image_id", 0);
		
		// check whether there is a saved game
		if (check == 1) {
			
			// retrieve image id, current level & blank location from savings
			cur_level = mPrefs.getInt("cur_level", 0);
			blank_location = mPrefs.getInt("blank_location", 0);
			
		}
		
		// if not -> new game
		else {
			
			// check if the new game is opened from the menu
			int menu_check = mPrefs.getInt("menu_check", 0);
			if (menu_check == 1){
				
				// retrieve image id, current level and blank location from shared preferences
				cur_level = mPrefs.getInt("menu_level", 0);
				blank_location = (cur_level * cur_level) - 1;
				
				// reset current level and menu check in savings
				sEdit.putInt("cur_level", cur_level);
				sEdit.putInt("menu_check", 0);
				sEdit.commit();
						 
			}
			
			// else set to default settings
			else {
				
				// set current level and blank_location to default
				cur_level = 4;
				blank_location = (cur_level * cur_level) - 1;
			}
		}
		
		// get image using image_id
		getImage(image_id);
		
		// create initial gameboard
		createBoard(picture, cur_level);
		
		// connect gridview to xml file
		gridView = (GridView) findViewById(R.id.gridview);
		
		// use adapter in order to add list of images to gridview
		mAdapter1 = new ImageAdapter(this);
		gridView.setAdapter(mAdapter1);
		gridView.setNumColumns(cur_level);
		gridView.invalidateViews();
		
		// use displaymetrics to obtain screen width and height
		displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		display_height = displaymetrics.heightPixels;
		display_width = displaymetrics.widthPixels;
		
		// create intent youwon
		final Intent intentYouWon = new Intent(this, YouWon.class);
		
		// when clicking on one image in the gridview
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				
				// move tile if possible
				moveTile(position);
				
				// check whether puzzle is solved
				if (puzzleSolved() == true){
					
					// add number of steps to intent youwon
					intentYouWon.putExtra(STEPS, num_steps);

					// start youwon activity
					startActivity(intentYouWon);
					finish();
				}
			}
			});
		}
		
		// create an image adapter
		public class ImageAdapter extends BaseAdapter {
			private Context context;
			public ImageAdapter(Context c) {
				context = c; 
				} 
			// returns the number of images
			public int getCount() {
				return tiles.size(); 
				}
			// returns the ID of an item
			public Object getItem(int position) { 
				return position; 
				} 
			// returns the ID of an item
			public long getItemId(int position) {
				return position; 
				}
			// returns an ImageView view
			public View getView(int position, View convertView, ViewGroup parent) { 
				if (convertView == null) {
					imageView = new ImageView(context);
					
					// use display width and current level to adapt image size to display screen
					int width = display_width / cur_level;
					imageView.setLayoutParams(new GridView.LayoutParams(width, width));
					imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					imageView.setPadding(1, 1, 1, 1);
					} 
				else {
					imageView = (ImageView) convertView;
					} 
				
				// retrieve bitmap from complete tile
				completeTile m = tiles.get(position);
				Bitmap bm = m.bitmap;
				
				// set bitmap to imageview
				imageView.setImageBitmap(bm);
				return imageView; 
				}
	}
	
	// returns true if puzzle is solved, else false	
	private Boolean puzzleSolved()
    {
		
		// declare current number
		int current;
		
		// declare previous number and set high
		int previous = 50;
		
		// iterate through tiles arraylist
		for (int i = 0, n = tiles.size(); i < n; i++){
			
			// get the number of complete tile i
			completeTile temp_tile = tiles.get(i);
			current = temp_tile.number;
			
			// if current number is larger than previous -> return false
			if (current > previous){
				return false;
			}
			
			// update previous
			previous = current;
		}
    	
		// if all numbers in order of large to small -> return true
		return true;
    }
	
	private void createBoard(Bitmap image, int current_level){
		
		// declare board_size
		final int board_size = current_level;
		
		// obtain width and height of Bitmap
		int image_height = image.getHeight();
		int image_width = image.getWidth();
		
		// declare width and height of one tile
		int tile_width = image_width / board_size;
		int tile_height = image_height / board_size;
		
		// declare start location to crop Bitmap
		int x = image_width - tile_width;
		int y = image_height - tile_height;
		
		// declare counter to count the tiles
		int tile_counter = 0;
		
		// crop Bitmap and store tiles in tiles arraylist
		for (int i = 0, n = board_size; i < n; i++) {
			for (int j = 0, m = board_size; j < m; j++){
				
				// check whether not blank tile
				if ((j != 0) | (i != 0)){
					completeTile t = new completeTile();
					t.number = tile_counter;
					Bitmap tile = Bitmap.createBitmap(picture, x, y, tile_width, tile_height);
					t.bitmap = tile;
					tiles.add(t);
				}
				
				// update x and counter
				x -= tile_width;
				tile_counter += 1;
			}
			
			// update x and y
			x = image_width - tile_width;
			y -= tile_height;
		}
		
		// create and add blank tile to the arraylist
		completeTile t = new completeTile();
		t.number = 0;
		Bitmap blank = BitmapFactory.decodeResource(getResources(),R.drawable.tile_png);
		blank = Bitmap.createScaledBitmap(blank, tile_width, tile_height, true);
		t.bitmap = blank;
		tiles.add(t);
		
		// when the board has an odd number of tiles, swap the last two tiles
		if (board_size % 2 == 0){
			completeTile temp = tiles.get((board_size * board_size) - 2);
			completeTile temp1 = tiles.get((board_size * board_size) - 3);
			tiles.set((board_size * board_size) - 2, temp1);
			tiles.set((board_size * board_size) - 3, temp);
		}
	}
	
	private void getImage(int image_id){
		
		// get image_id
		int image = image_id;
		
		// using image id, store corresponding image in bitmap picture
		if (image == 0){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_0);
		}
		else if (image == 1){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_1);
		}
		else if (image == 2){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_2);
		}
		else if (image == 3){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_3);
		}
		else if (image == 4){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_4);
		}
		else if (image == 5){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_5);
		}
		else if (image == 6){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_6);
		}
		else if (image == 7){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_7);
		}
		else if (image == 8){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_8);
		}
		else if (image == 9){
			picture = BitmapFactory.decodeResource(this.getResources(), R.drawable.puzzle_9);
		}
	}
	
	private void moveTile(int position){
		
		// check whether blank tile is to the left
		if ((position - 1 == blank_location) & ((position)%cur_level != 0)){
			
			// if so, swap tiles in arraylist
			completeTile temp = tiles.get(position);
			completeTile temp1 = tiles.get(position - 1);
			tiles.set(position, temp1);
			tiles.set(position - 1, temp);
			
			// update blank_location
			blank_location = position;
			
			// add one to total steps
			num_steps += 1;
			
			// let adapter know arraylist has changed
			mAdapter1.notifyDataSetChanged();
		}
		
		// check whether blank tile is to the right
		else if ((position + 1 == blank_location) & ((position + 1)%cur_level != 0)){
			
			// if so, swap tiles in arraylist
			completeTile temp = tiles.get(position);
			completeTile temp1 = tiles.get(position + 1);
			tiles.set(position, temp1);
			tiles.set(position + 1, temp);
			
			// update blank_location
			blank_location = position;
			
			// add one to total steps
			num_steps += 1;
			
			// let adapter know arraylist has changed
			mAdapter1.notifyDataSetChanged();
		}
		
		// check whether blank tile is above
		else if (position - cur_level == blank_location){
			
			// if so, swap tiles in arraylist
			completeTile temp = tiles.get(position);
			completeTile temp1 = tiles.get(position - cur_level);
			tiles.set(position, temp1);
			tiles.set(position - cur_level, temp);
			
			// update blank_location
			blank_location = position;
			
			// add one to total steps
			num_steps += 1;
			
			// let adapter know arraylist has changed
			mAdapter1.notifyDataSetChanged();
		}
		
		// check whether blank tile is below
		else if (position + cur_level == blank_location){
			
			// if so, swap tiles in arraylist
			completeTile temp = tiles.get(position);
			completeTile temp1 = tiles.get(position + cur_level);
			tiles.set(position, temp1);
			tiles.set(position + cur_level, temp);
			
			// update blank_location
			blank_location = position;
			
			// add one to total steps
			num_steps += 1;
			
			// let adapter know arraylist has changed
			mAdapter1.notifyDataSetChanged();
		}
	}
	
	public void onPause() {
    	super.onPause();
    	
    	// open shared preferences and editor
    	Context mContext = getApplicationContext();
        SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
    	SharedPreferences.Editor sEdit = mPrefs.edit();
    	
    	// add check, image id, current level and blank location to shared preferences
    	int check = 1;
    	sEdit.putInt("check", check);
    	sEdit.putInt("cur_level", cur_level);
    	sEdit.putInt("blank_location", blank_location);
    	sEdit.putInt("num_steps", num_steps);
  
    	// iterate through the arraylist of tiles
    	for(int i = 0; i < tiles.size(); i++)
    	{
    	    // store the tile numbers in saving order
    		completeTile t = tiles.get(i);
    	    int n = t.number;
    	    sEdit.putInt("num" + i, n); 
    	}
    	
    	// check for menu_check
    	int menu_check = mPrefs.getInt("menu_check", 0);
    	if (menu_check == 1){
    		check = 0;
    		sEdit.putInt("check", check);
    	}
    	
    	// commit savings
    	sEdit.commit();  
    }
	
	public void onResume() {
		super.onResume(); 
		
		// open shared preferences
		Context mContext = getApplicationContext();
	    SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
        
	    // retrieve check from shared preferences
        int check = mPrefs.getInt("check", 0);
        
        // check whether there is a game saved
        if (check == 1){ 
        	
        	// retrieve the current level & blank location
        	cur_level = mPrefs.getInt("cur_level", 0);
        	blank_location = mPrefs.getInt("blank_location", 0);
        	num_steps = mPrefs.getInt("num_steps", 0);
            
            // create arraylist containing numbers in right order
            ArrayList<Integer> orderlist = new ArrayList<Integer>();
               
            // add the numbers to the orderlist
            for(int j = 0; j < tiles.size(); j++){
            	orderlist.add(mPrefs.getInt("num" + j, 0));
            }
            
            // iterate throught tiles arraylist
            for (int k = 0; k < tiles.size(); k++){

            	// check whether the number of tile list and orderlist match
            	completeTile t = tiles.get(k);
            	int saved_num = orderlist.get(k);
            	if (t.number != saved_num){
            		
            		// if they do not match, search for the correct tile
            		for (int l = 0; l < tiles.size(); l++){
            			
            			completeTile t1 = tiles.get(l);
            			if (t1.number == saved_num){
            				
            				// swap tiles in tiles arraylist
            				completeTile temp1 = tiles.get(l);
            				completeTile temp2 = tiles.get(k);
            				tiles.set(k, temp1);
            				tiles.set(l, temp2);
            			}
            		}
            	}
            }
            
            // notify gridview and adapter that tiles arraylist changed
            mAdapter1.notifyDataSetChanged();
            gridView.invalidateViews();
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
				
		// open shared preferences and editor
		Context mContext = getApplicationContext();
        SharedPreferences mPrefs = mContext.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE);
    	SharedPreferences.Editor sEdit = mPrefs.edit();
    	
    	// update check in shared preferences
    	sEdit.putInt("check", 0);
		
		// declare new_game_check
		boolean new_game_check = false;
		
		// check menu item selected
		int id = item.getItemId();
		
		// restart button
		if (id == R.id.restart_button) {
			
			// update current level, blank location and menu check in savings
			sEdit.putInt("menu_level", cur_level);
			sEdit.putInt("menu_blank_location", blank_location);
			sEdit.putInt("menu_check", 1);
		}
		
		// easy button
		else if (id == R.id.easy_button) {
			
			// update current level, blank location and menu check in savings
			sEdit.putInt("menu_level", 3);
			sEdit.putInt("menu_blank_location", 8);
			sEdit.putInt("menu_check", 1);	
		}
		
		// medium button
		else if (id == R.id.medium_button) {
			
			// update current level, blank location and menu check in savings
			sEdit.putInt("menu_level", 4);
			sEdit.putInt("menu_blank_location", 15);
			sEdit.putInt("menu_check", 1);
		}
		
		// hard button
		else if (id == R.id.hard_button) {
			
			// update current level, blank location and menu check in savings
			sEdit.putInt("menu_level", 5);
			sEdit.putInt("menu_blank_location", 24);
			sEdit.putInt("menu_check", 1);
		}
		
		// new game button
		else if (id == R.id.new_game_button){
		
	    	// update new_game_check
			new_game_check = true;
	    }
		
		// commit savings
		sEdit.commit();
		
		// if new game is selected
		if (new_game_check == true) {
			
			// create intent quitgame and start activity
	    	Intent intentImageSelection = new Intent(this, QuitGame.class);
	    	startActivity(intentImageSelection);
	    	finish();
		}
		
		// else start gameplay again
		else {
			
			// create new intent gameplay and start activity
			Intent intentGamePlay = new Intent(this, GamePlay.class);
			startActivity(intentGamePlay);
			finish();
			
		}
		return super.onOptionsItemSelected(item);
	}
}
