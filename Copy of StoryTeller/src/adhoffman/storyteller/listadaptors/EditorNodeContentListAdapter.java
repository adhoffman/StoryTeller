package adhoffman.storyteller.listadaptors;

import adhoffman.storyteller.R;
import adhoffman.storyteller.activities.StoryEditorActivity;
import adhoffman.storyteller.content.ButtonContent;
import adhoffman.storyteller.content.ContentViewVisitor;
import adhoffman.storyteller.content.ImageContent;
import adhoffman.storyteller.content.TextContent;
import adhoffman.storyteller.content.Visitable;
import adhoffman.storyteller.domain.Node;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditorNodeContentListAdapter extends ArrayAdapter<Visitable> {

	private StoryEditorActivity storyEditorActivity;

	private Visitable localVisitable;
	private Node localNode;
	private ContentViewVisitor contentViewVisitor;

	private TextView textView;
	private Button button;
	private ImageView imageView;
	private Button deleteButton;

	public EditorNodeContentListAdapter(
			StoryEditorActivity storyEditorActivity, Node node) {
		super(storyEditorActivity, R.layout.node_content_item_list, node
				.getContentList());

		this.contentViewVisitor = new ContentViewVisitor();
		this.localNode = node;
		this.storyEditorActivity = storyEditorActivity;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;

		if (itemView == null) {
			itemView = this.storyEditorActivity.getLayoutInflater().inflate(
					R.layout.node_content_item_list, parent, false);

		}

		this.localVisitable = this.localNode.getContentList().get(position);
		this.textView = (TextView) itemView
				.findViewById(R.id.item_node_content_list_text_content);
		this.button = (Button) itemView
				.findViewById(R.id.item_node_content_list_button_content);
		this.imageView = (ImageView) itemView
				.findViewById(R.id.item_node_content_list_image_content);
		this.deleteButton = (Button) itemView
				.findViewById(R.id.item_node_content_list_deleteButton);

		setListenerToDeleteButton(deleteButton, position);

		if (ifVisitorIsATextContentObject()) {
			setTextViewViaContentViewVisitor(itemView);
			setOnlyTextContentVisiable();

		} else if (ifVisitorIsAButtonContentObject()) {
			setButtonViaContentViewVisitor(itemView);
			setTextViewOfNodeButtonPointsToViaContentViewVisitor(itemView);
			setOnlyButtonContentVisiable();

		} else if (ifVisitorIsAImageContentObject()) {
			setImageViewViaContentViewVisitor(itemView);
			setOnlyImageContentVisiable();

		}

		return itemView;
	}

	private void setListenerToDeleteButton(Button deleteButton,
			final int position) {

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						getContext());

				alert.setTitle("Delete this Content?");

				alert.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								storyEditorActivity
										.deleteContentAtIndext(position);
								notifyDataSetChanged();
							}

						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						});

				alert.show();

			}

		});

	}

	private void setTextViewOfNodeButtonPointsToViaContentViewVisitor(
			View itemView) {
		String label = "This Points to:  ";
		ButtonContent buttonContent = (ButtonContent) this.localVisitable;
		textView.setText(label + buttonContent.getNodeNameThisButtonPointsTo());
	}

	private void setImageViewViaContentViewVisitor(View itemView) {
		imageView = (ImageView) this.localVisitable
				.accept(contentViewVisitor,
						itemView.findViewById(R.id.item_node_content_list_image_content));

	}

	private void setButtonViaContentViewVisitor(View itemView) {
		button = (Button) this.localVisitable
				.accept(contentViewVisitor,
						itemView.findViewById(R.id.item_node_content_list_button_content));

	}

	private void setTextViewViaContentViewVisitor(View itemView) {
		textView = (TextView) this.localVisitable
				.accept(contentViewVisitor, itemView
						.findViewById(R.id.item_node_content_list_text_content));

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
		button.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
	}

	private void setOnlyButtonContentVisiable() {
		textView.setVisibility(View.VISIBLE);
		button.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.GONE);

	}

	private void setOnlyTextContentVisiable() {
		textView.setVisibility(View.VISIBLE);
		button.setVisibility(View.GONE);
		imageView.setVisibility(View.GONE);
	}

}