package adhoffman.storyteller.activities;

import adhoffman.storyteller.R;
import adhoffman.storyteller.content.Visitable;
import adhoffman.storyteller.domain.Node;
import adhoffman.storyteller.domain.Story;
import adhoffman.storyteller.listadaptors.PlayerNodeContentListAdapter;
import adhoffman.storyteller.utility.StoryReader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StoryPlayerActivity extends Activity {

	private ArrayAdapter<Visitable> nodeContentListAdapter;
	private String storyFileName;
	private Story story;
	private Node currentlySelectedNode;
	private StoryReader storyReader;

	private TextView storyTitleTextView;
	private TextView currentNodeSelectedTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_player);

		setStoryFileName();
		createStoryReader();
		createStoryFromStoryReader();
		setTextViewToStoryTitle();

		setDefaultCurrentlySelectedNode();
		setTextViewForDefaultCurrentlySelectedNode();

		populateNodeContentListForDefaultSelectedNode();

	}

	private void populateNodeContentListForDefaultSelectedNode() {
		this.nodeContentListAdapter = new PlayerNodeContentListAdapter(this,
				this.currentlySelectedNode);
		ListView nodeContentList = (ListView) findViewById(R.id.node_content_list_view_for_player);
		nodeContentList.setAdapter(nodeContentListAdapter);
	}

	private void setTextViewForDefaultCurrentlySelectedNode() {
		this.currentNodeSelectedTextView = (TextView) findViewById(R.id.name_of_selected_node_for_story_player);
		this.currentNodeSelectedTextView.setText(this.currentlySelectedNode
				.getNodeName());
	}

	private void setDefaultCurrentlySelectedNode() {
		this.currentlySelectedNode = story.getFirstNodeInList();
	}

	private void setTextViewToStoryTitle() {
		this.storyTitleTextView = (TextView) findViewById(R.id.story_title_textview_for_play_story);
		this.storyTitleTextView.setText(this.story.getTitle());
	}

	private void createStoryFromStoryReader() {
		this.story = this.storyReader.getStoryObjectFromFile();
	}

	private void createStoryReader() {
		this.storyReader = new StoryReader(this.storyFileName, this);
	}

	private void setStoryFileName() {
		Intent intent = getIntent();
		this.storyFileName = intent.getStringExtra("load_story_with_title");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setCurrentlySelectedNode(String nodeNameThisButtonPointsTo) {

		if (this.story.getNodeWithName(nodeNameThisButtonPointsTo) != null) {
			this.currentlySelectedNode = this.story
					.getNodeWithName(nodeNameThisButtonPointsTo);
			populateNodeContentListForDefaultSelectedNode();
			updateTextViewForCurrentlySelectedNode();

		}

	}

	private void updateTextViewForCurrentlySelectedNode() {
		this.currentNodeSelectedTextView.setText(this.currentlySelectedNode
				.getNodeName());
	}

}
