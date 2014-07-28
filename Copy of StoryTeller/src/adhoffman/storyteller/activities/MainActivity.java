package adhoffman.storyteller.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import adhoffman.storyteller.R;
import adhoffman.storyteller.listadaptors.FileDialogAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

	private File localDir;
	private ArrayList<String> listOfSavedFiles;
	private String currentlySelectedFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setFileDirectory();
		setArrayListOfSavedFiles();
		setDefaultCurrentlySelectedFileName();

		setListenersToButtons();

	}

	private void setDefaultCurrentlySelectedFileName() {

		if (!listOfSavedFiles.isEmpty())
			this.currentlySelectedFileName = listOfSavedFiles.get(0);
	}

	private void setArrayListOfSavedFiles() {
		String[] localArray = this.localDir.list();

		this.listOfSavedFiles = new ArrayList<String>(Arrays.asList(localArray));
	}

	private void setFileDirectory() {
		this.localDir = this.getFilesDir();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.new_story_button:
			startNewStoryTasks();
			break;

		case R.id.load_story_button:
			selectStoryToLoad();
			break;

		}

	}

	private void selectStoryToLoad() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		FileDialogAdapter adapter = new FileDialogAdapter(this,
				this.listOfSavedFiles, MainActivity.this);
		ListView fileList = new ListView(this);
		fileList.setAdapter(adapter);

		registerClickOnToSelectWhichNodeButtonPointsTo(fileList);

		alert.setView(fileList);

		alert.setPositiveButton("Play!", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				startPlayingCurrentlySelectedStoryFile(currentlySelectedFileName);

			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void registerClickOnToSelectWhichNodeButtonPointsTo(
			ListView fileList) {

		ListView list = fileList;
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				currentlySelectedFileName = listOfSavedFiles.get(position);

			}
		});
	}

	private void setListenersToButtons() {
		View newStoryButton = findViewById(R.id.new_story_button);
		newStoryButton.setOnClickListener(this);

		View loadStoryButton = findViewById(R.id.load_story_button);
		loadStoryButton.setOnClickListener(this);
	}

	private void startNewStoryTasks() {
		Intent startNewStoryScreenIntent = new Intent(this,
				NewStoryCreateActivity.class);
		startActivity(startNewStoryScreenIntent);

	}

	private void startPlayingCurrentlySelectedStoryFile(
			String currentlySelectedFileName) {

		Intent startStoryPlayer = new Intent(this, StoryPlayerActivity.class);
		startStoryPlayer.putExtra("load_story_with_title",
				currentlySelectedFileName);
		startActivity(startStoryPlayer);

	}

}
