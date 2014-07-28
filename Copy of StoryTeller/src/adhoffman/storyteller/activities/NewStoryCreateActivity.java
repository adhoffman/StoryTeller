package adhoffman.storyteller.activities;

import adhoffman.storyteller.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NewStoryCreateActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_story_create_screen);

		View createStoryButton = findViewById(R.id.create_story_button);
		createStoryButton.setOnClickListener(this);

		View cancelStoryCreationButton = findViewById(R.id.cancel_story_creation_button);
		cancelStoryCreationButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		final EditText storyNameTextField = (EditText) findViewById(R.id.story_input_field);

		switch (v.getId()) {

		case R.id.create_story_button:
			startCreateStoryActivity(storyNameTextField);
			break;

		case R.id.cancel_story_creation_button:
			returnToHomeScreenActivity();
			break;

		}

	}

	private void returnToHomeScreenActivity() {
		Intent returnToHomeScreenActivity = new Intent(this, MainActivity.class);
		startActivity(returnToHomeScreenActivity);
	}

	private void startCreateStoryActivity(EditText storyNameTextField) {

		Intent startStoryEditor = new Intent(this, StoryEditorActivity.class);
		startStoryEditor.putExtra("story_name", storyNameTextField.getText()
				.toString());
		startActivity(startStoryEditor);
	}

}
