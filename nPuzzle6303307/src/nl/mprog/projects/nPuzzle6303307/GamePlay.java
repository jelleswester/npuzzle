// Name:			Jelles Wester
// Studentnumber:	6303307	and 10004531
// Email:			jelleswester@gmail.com

package nl.mprog.projects.nPuzzle6303307;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity {
	public final static String PICTURE = "nl.mprog.setup.Hello6303307.PICT";
	public final static String LEVEL = "nl.mprog.setup.Hello6303307.LEV";
	
	// declare Bitmap ArrayList
	ArrayList<Bitmap> tiles = new ArrayList<Bitmap>();
	
	// declare default level (i.e. board width/height)
	int cur_level = 4;
	
	// declare default location of blank tile
	int blank_location = 15;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		// retrieve the chosen image
		Intent intent = getIntent();
		final int picture = intent.getIntExtra(ImageSelection.PICTURE,0);
		
		// create and scale Bitmap image
		Bitmap image = BitmapFactory.decodeResource(getResources(),picture);
		image = Bitmap.createScaledBitmap(image, 512, 512, true);
		
		// obtain width and height of Bitmap
		int image_height = image.getHeight();
		int image_width = image.getWidth();
		
		// if not first game, update the current level
		if (intent.getIntExtra(GamePlay.LEVEL,0) != 0){
			cur_level = intent.getIntExtra(GamePlay.LEVEL,0);
		}
		
		// declare board_size
		final int board_size = cur_level;
		
		// update the blank location
		blank_location = (board_size * board_size) - 1;
		
		// declare width and height of one tile
		int tile_width = image_width / board_size;
		int tile_height = image_height / board_size;
		
		// declare start location to crop Bitmap
		int x = image_width - tile_width;
		int y = image_height - tile_height;
		
		// crop Bitmap and store tiles in tiles ArrayList
		for (int i = 0, n = board_size; i < n; i++) {
			for (int j = 0, m = board_size; j < m; j++){
				
				// if last tile is reached, create blank tile
				if ((j != 0) | (i != 0)){
					Bitmap tile = Bitmap.createBitmap(image, x, y, tile_width, tile_height);
					tiles.add(tile);
				}
				
				x -= tile_width;
			}
			x = image_width - tile_width;
			y -= tile_height;
		}
		
		// add the blank tile to the ArrayList
		Bitmap blank = BitmapFactory.decodeResource(getResources(),R.drawable.tile_white);
		blank = Bitmap.createScaledBitmap(blank, tile_width, tile_height, true);
		tiles.add(blank);
		
		// when the board has an odd number of tiles, swap the last two tiles
		if (board_size % 2 == 0){
			Bitmap temp = tiles.get((board_size * board_size) - 2);
			Bitmap temp1 = tiles.get((board_size * board_size) - 3);
			tiles.set((board_size * board_size) - 2, temp1);
			tiles.set((board_size * board_size) - 3, temp);
		}
				
		// create gridview to plot the tiles
		GridView gridView = (GridView) findViewById(R.id.gridview);
		
		// use adapter to get the list of images
		final ImageAdapter mAdapter = new ImageAdapter(this);
		gridView.setAdapter(new ImageAdapter(this));
		gridView.setNumColumns(board_size);
		gridView.invalidateViews();
		
		// when clicking on one image
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				
				// when tile is next to blank -> swap tiles
				if ((position - 1) == blank_location){
					Bitmap temp = tiles.get(position);
					Bitmap temp1 = tiles.get(position - 1);
					tiles.set(position, temp1);
					tiles.set(position - 1, temp);
					blank_location = position;
					mAdapter.notifyDataSetChanged();
				}
				else if (position + 1 == blank_location){
					Bitmap temp = tiles.get(position);
					Bitmap temp1 = tiles.get(position + 1);
					tiles.set(position, temp1);
					tiles.set(position + 1, temp);
					blank_location = position;
					mAdapter.notifyDataSetChanged();
				}
				else if (position - board_size == blank_location){
					Bitmap temp = tiles.get(position);
					Bitmap temp1 = tiles.get(position - board_size);
					tiles.set(position, temp1);
					tiles.set(position - board_size, temp);
					blank_location = position;
					mAdapter.notifyDataSetChanged();
				}
				else if (position + board_size == blank_location){
					Bitmap temp = tiles.get(position);
					Bitmap temp1 = tiles.get(position + board_size);
					tiles.set(position, temp1);
					tiles.set(position + board_size, temp);
					blank_location = position;
					mAdapter.notifyDataSetChanged();
				}
				
				// if not -> toast 'not possible'
				else {
					Toast.makeText(getBaseContext(), "not possible", Toast.LENGTH_SHORT).show();
				}
				
				// TODO: did i won?
			}
			});
		}
		
		// create an image adapter
		public class ImageAdapter extends BaseAdapter {
			private Context context;
			public ImageAdapter(Context c) {
				context = c; 
				} 
			//---returns the number of images---
			public int getCount() {
				return tiles.size(); 
				}
			//---returns the ID of an item--- 
			public Object getItem(int position) { 
				return position; 
				} 
			//---returns the ID of an item---
			public long getItemId(int position) {
				return position; 
				}
			//---returns an ImageView view---
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView imageView; 
				if (convertView == null) {
					imageView = new ImageView(context);
					imageView.setLayoutParams(new GridView.LayoutParams(80, 80));
					imageView.setScaleType( ImageView.ScaleType.CENTER_CROP);
					imageView.setPadding(0, 0, 0, 0);
					} 
				else {
					imageView = (ImageView) convertView;
					} 
				imageView.setImageBitmap(tiles.get(position));
				return imageView; 
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// get the current image
		Intent intent = getIntent();
		int picture = intent.getIntExtra(ImageSelection.PICTURE,0);
		
		// attach picture to intent GamePlay
		Intent intentGamePlay = new Intent(this, GamePlay.class);
		intentGamePlay.putExtra(PICTURE, picture);
		
		// get the current level
		int lvl = cur_level;
		
		// check menu item selected and attach corresponding level to intent
		int id = item.getItemId();
		if (id == R.id.restart_button) {
			intentGamePlay.putExtra(LEVEL, lvl);
		}
		else if (id == R.id.easy_button) {
			intentGamePlay.putExtra(LEVEL, 3);
		}
		else if (id == R.id.medium_button) {
			intentGamePlay.putExtra(LEVEL, 4);
		}
		else if (id == R.id.hard_button) {
			intentGamePlay.putExtra(LEVEL, 5);
		}
		
		// end current GamePlay and open new using intentGamePlay
		finish();
		startActivity(intentGamePlay);
		return super.onOptionsItemSelected(item);
	}
}
