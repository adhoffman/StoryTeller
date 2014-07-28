package adhoffman.storyteller.listadaptors;

import adhoffman.storyteller.R;
import adhoffman.storyteller.activities.StoryEditorActivity;
import adhoffman.storyteller.domain.Node;
import adhoffman.storyteller.domain.Story;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NodeListAdapter extends ArrayAdapter<Node> {

	private Story story;
	private StoryEditorActivity activity;

	public NodeListAdapter(StoryEditorActivity storyEditorActivity, Story story) {
		super(storyEditorActivity, R.layout.node_item_list, story.getNodeList());

		this.story = story;
		this.activity = storyEditorActivity;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;

		if (itemView == null) {

			itemView = activity.getLayoutInflater().inflate(
					R.layout.node_item_list, parent, false);

		}

		Node currentNode = story.getNodeList().get(position);
		TextView textView = (TextView) itemView
				.findViewById(R.id.item_node_list_view_text);

		textView.setText(currentNode.getNodeName());

		return itemView;
	}

}
