package adhoffman.storyteller.listadaptors;

import adhoffman.storyteller.R;
import adhoffman.storyteller.activities.StoryPlayerActivity;
import adhoffman.storyteller.content.ButtonContent;
import adhoffman.storyteller.content.ContentViewVisitor;
import adhoffman.storyteller.content.ImageContent;
import adhoffman.storyteller.content.TextContent;
import adhoffman.storyteller.content.Visitable;
import adhoffman.storyteller.domain.Node;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerNodeContentListAdapter extends ArrayAdapter<Visitable> {

	private StoryPlayerActivity storyPlayerActivity;

	private Visitable localVisitable;
	private Node localNode;
	private ContentViewVisitor contentViewVisitor;

	private TextView textView;
	private Button buttonView;
	private ImageView imageView;

	public PlayerNodeContentListAdapter(
			StoryPlayerActivity storyPlayerActivity, Node currentlySelectedNode) {
		super(storyPlayerActivity, R.layout.node_content_item_list_for_player,
				currentlySelectedNode.getContentList());

		this.storyPlayerActivity = storyPlayerActivity;
		this.localNode = currentlySelectedNode;
		this.contentViewVisitor = new ContentViewVisitor();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;

		if (itemView == null) {
			itemView = this.storyPlayerActivity.getLayoutInflater().inflate(
					R.layout.node_content_item_list_for_player, parent, false);

		}

		this.localVisitable = this.localNode.getContentList().get(position);
		this.textView = (TextView) itemView
				.findViewById(R.id.player_node_content_list_text_content);
		this.buttonView = (Button) itemView
				.findViewById(R.id.player_node_content_list_button_content);
		this.imageView = (ImageView) itemView
				.findViewById(R.id.player_node_content_list_image_content);

		setListenersToButtonView(buttonView, position);

		if (ifVisitorIsATextContentObject()) {
			setTextViewViaContentViewVisitor(itemView);
			setOnlyTextContentVisiable();

		} else if (ifVisitorIsAButtonContentObject()) {
			setButtonViaContentViewVisitor(itemView);
			setOnlyButtonContentVisiable();

		} else if (ifVisitorIsAImageContentObject()) {
			setImageViewViaContentViewVisitor(itemView);
			setOnlyImageContentVisiable();

		}

		return itemView;
	}

	private void setListenersToButtonView(Button localButtonView,
			final int position) {

		localButtonView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ButtonContent content = (ButtonContent) localNode
						.getContentViewAtIndex(position);
				storyPlayerActivity.setCurrentlySelectedNode(content
						.getNodeNameThisButtonPointsTo());
			}

		});

	}

	private void setImageViewViaContentViewVisitor(View itemView) {
		imageView = (ImageView) this.localVisitable
				.accept(contentViewVisitor,
						itemView.findViewById(R.id.player_node_content_list_image_content));

	}

	private void setButtonViaContentViewVisitor(View itemView) {
		buttonView = (Button) this.localVisitable
				.accept(contentViewVisitor,
						itemView.findViewById(R.id.player_node_content_list_button_content));

	}

	private void setTextViewViaContentViewVisitor(View itemView) {
		textView = (TextView) this.localVisitable
				.accept(contentViewVisitor,
						itemView.findViewById(R.id.player_node_content_list_text_content));

	}

	private boolean ifVisitorIsAImageContentObject() {
		return (localVisitable.getClass().toString().equals(ImageContent.class
				.toString()));
	}

	private boolean ifVisitorIsAButtonContentObject() {
		return localVisitable.getClass().toString()
				.equals(ButtonContent.class.toString());
	}

	private boolean ifVisitorIsATextContentObject() {
		return localVisitable.getClass().toString()
				.equals(TextContent.class.toString());
	}

	private void setOnlyImageContentVisiable() {
		textView.setVisibility(View.GONE);
		buttonView.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
	}

	private void setOnlyButtonContentVisiable() {
		textView.setVisibility(View.GONE);
		buttonView.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.GONE);

	}

	private void setOnlyTextContentVisiable() {
		textView.setVisibility(View.VISIBLE);
		buttonView.setVisibility(View.GONE);
		imageView.setVisibility(View.GONE);
	}
}