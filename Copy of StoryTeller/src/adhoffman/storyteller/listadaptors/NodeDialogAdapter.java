package adhoffman.storyteller.listadaptors;

import adhoffman.storyteller.R;
import adhoffman.storyteller.activities.StoryEditorActivity;
import adhoffman.storyteller.domain.Node;
import adhoffman.storyteller.domain.Story;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NodeDialogAdapter extends ArrayAdapter<Node> {

	private Node localNode;
	private Story story;
	private StoryEditorActivity storyEditorActivity;

	public NodeDialogAdapter(Context context, Story story,
			StoryEditorActivity storyEditorActivity) {
		super(context, R.layout.node_item_list_for_dialog, story.getNodeList());

		this.story = story;
		this.storyEditorActivity = storyEditorActivity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;

		if (itemView == null) {
			itemView = this.storyEditorActivity.getLayoutInflater().inflate(
					R.layout.node_item_list_for_dialog, parent, false);

		}

		localNode = story.getNodeList().get(position);
		TextView textView = (TextView) itemView
				.findViewById(R.id.item_node_list_view_text_for_dialogue);

		textView.setText(localNode.getNodeName());

		return itemView;
	}

}
