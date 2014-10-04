package neamah.chris.fitbeat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

//package neamah.chris.fitbeat;
//
//import java.io.File; 
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import android.app.Activity;
//import android.app.ActionBar;
//import android.app.Fragment;
//import android.app.ListActivity;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.os.Build;
//
//
//
//public class MainActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
//}
//
public class MainActivity extends ListActivity  {

// Songs list
public ArrayList<HashMap<String,String>> songsList = new ArrayList<HashMap<String, String>>();
ListView musiclist;
 Cursor mCursor;
 int songTitle;
 int count;
 int songPath;

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	ArrayList<HashMap<String, String>> tempSong = new ArrayList<HashMap<String, String>>();


	SongsManager plm = new SongsManager();


    // get all songs from sdcard
    this.songsList = plm.getPlayList(this);

    // looping through playlist
    for (int i = 0; i < songsList.size(); i++) {
        // creating new HashMap
        HashMap<String, String> song = songsList.get(i);

        // adding HashList to ArrayList
        tempSong.add(song);

    }

    // Adding menuItems to ListView
   ListAdapter adapter = new SimpleAdapter(this, tempSong,
                     android.R.layout.simple_list_item_1, new String[] {      "songTitle", "songPath" }, new int[] {
                     android.R.id.text1});

              setListAdapter(adapter);


    // selecting single ListView item
    ListView lv = getListView();

    // listening to single listitem click
    lv.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {

            // getting listitem index
            int songIndex = position;
            // Starting new intent
            Intent in = new Intent(getApplicationContext(),
                   MainActivity.class);
            Log.d("TAG","onItemClick");
            // Sending songIndex to PlayerActivity
            in.putExtra("songPath", songIndex);
            setResult(100, in);
            // Closing PlayListView
            finish();
        }

    });
	}
	public class SongsManager {
		private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		
		
		public ArrayList<HashMap<String, String>> getPlayList(Context c) {
		
		
		
		
		    final Cursor mCursor = c.getContentResolver().query(
		            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		            new String[] { MediaColumns.TITLE, MediaColumns.DATA, AudioColumns.ALBUM }, null, null,
		            "LOWER(" + MediaColumns.TITLE + ") ASC");
		
		    String songTitle = "";
		    String songPath = "";
		
		
		
		
		
		    /* run through all the columns we got back and save the data we need into the arraylist for our listview*/
		    if (mCursor.moveToFirst()) {
		        do {
		
		
		            songTitle = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaColumns.TITLE));
		
		            songPath = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaColumns.DATA));
		            HashMap<String, String> song = new HashMap<String, String>();
		
		            song.put("songTitle", songTitle);
		            song.put("songPath", songPath);
		
		
		            songsList.add(song);
		
		        } while (mCursor.moveToNext());
		
		    }   
		
		    mCursor.close(); //cursor has been consumed so close it
		    return songsList;
		}	
	}
}