package adhoffman.storyteller.activities;

import adhoffman.storyteller.R;
import adhoffman.storyteller.content.ButtonContent;
import adhoffman.storyteller.content.ImageContent;
import adhoffman.storyteller.content.TextContent;
import adhoffman.storyteller.content.Visitable;
import adhoffman.storyteller.domain.Node;
import adhoffman.storyteller.domain.Story;
import adhoffman.storyteller.listadaptors.EditorNodeContentListAdapter;
import adhoffman.storyteller.listadaptors.NodeDialogAdapter;
import adhoffman.storyteller.listadaptors.NodeListAdapter;
import adhoffman.storyteller.utility.StorySaver;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class StoryEditorActivity extends Activity implements OnClickListener {

	private static final int CAMERA_REQUEST = 1888;
	private String storyTitleText;
	private boolean pictureIsForNewContent = true;
	private final String storyStartingDefaultNodeName = "Start";
	private Story story;
	private ArrayAdapter<Node> nodeListAdapter;
	private ArrayAdapter<Visitable> nodeContentListAdapter;
	private Node currentlySelectedNode;
	private Node currentlySelectedNodeInDialog;
	private Visitable currentlySelectedVisitable;
	private TextView textfieldForCurrentlySelectedNodeName;
	private StorySaver storySaver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_editor_main);

		setListenersToButtons();
		setTitleTextFromCreationScreen();
		createStoryObject();

		instantiateTextViewForDisplayingSelectedNode();
		addDefaultStartingNode();
		setDefaultStartingNodeToCurrentlySelectedNode();
		setDefaultStartingNodeAsCurrentlySelectedNodeInDialog();
		makeTextViewForCurrentNodeDisplayDefaultNodeName();

		populateNodeListViewWithNodes();
		registerClickOnNodeListView();

		populateNodeContentListViewWithContent();
		registerClickOnNodeContentListView();

		createStorySaverObject();

	}

	private void createStorySaverObject() {
		this.storySaver = new StorySaver(this.story, this);
	}

	private void setDefaultStartingNodeToCurrentlySelectedNode() {
		this.currentlySelectedNode = this.story
				.getNodeWithName(this.storyStartingDefaultNodeName);
	}

	private void setDefaultStartingNodeAsCurrentlySelectedNodeInDialog() {
		this.currentlySelectedNodeInDialog = this.story
				.getNodeWithName(this.storyStartingDefaultNodeName);

	}

	private void instantiateTextViewForDisplayingSelectedNode() {
		textfieldForCurrentlySelectedNodeName = (TextView) findViewById(R.id.name_of_selected_node);

	}

	private void addDefaultStartingNode() {
		story.addNodeWithName(this.storyStartingDefaultNodeName);
	}

	private void makeTextViewForCurrentNodeDisplayDefaultNodeName() {
		textfieldForCurrentlySelectedNodeName
				.setText(this.currentlySelectedNode.getNodeName());
	}

	private void populateNodeContentListViewWithContent() {
		this.nodeContentListAdapter = new EditorNodeContentListAdapter(this,
				this.currentlySelectedNode);
		ListView nodeContentList = (ListView) findViewById(R.id.node_content_list_view);
		nodeContentList.setAdapter(this.nodeContentListAdapter);

	}

	public void deleteContentAtIndext(int position) {
		currentlySelectedNode.deleteContentAtIndex(position);
	}

	private void populateNodeListViewWithNodes() {
		this.nodeListAdapter = new NodeListAdapter(this, this.story);
		ListView nodeList = (ListView) findViewById(R.id.node_list_view);
		nodeList.setAdapter(this.nodeListAdapter);
	}

	private void createStoryObject() {

		setStoryTitleText();
		this.story = new Story(storyTitleText);

	}

	private void setStoryTitleText() {
		Intent intent = getIntent();
		this.storyTitleText = intent.getStringExtra("story_name");

	}

	private void setListenersToButtons() {

		View newStoryNodeButton = findViewById(R.id.new_story_node_button);
		newStoryNodeButton.setOnClickListener(this);

		View addTextStoryNodeButton = findViewById(R.id.add_text_to_story_node_botton);
		addTextStoryNodeButton.setOnClickListener(this);

		View addPictureStoryNodeBotton = findViewById(R.id.add_picture_to_story_node_botton);
		addPictureStoryNodeBotton.setOnClickListener(this);

		View addButtonStoryNodeButton = findViewById(R.id.add_button_to_story_node_button);
		addButtonStoryNodeButton.setOnClickListener(this);

		View saveStoryButton = findViewById(R.id.save_story_button);
		saveStoryButton.setOnClickListener(this);

		View mainMenuButton = findViewById(R.id.back_to_main_menu_button);
		mainMenuButton.setOnClickListener(this);
	}

	private void registerClickOnNodeListView() {

		ListView list = (ListView) findViewById(R.id.node_list_view);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				currentlySelectedNode = story.getNodeList().get(position);
				textfieldForCurrentlySelectedNodeName
						.setText(currentlySelectedNode.getNodeName());

				populateNodeContentListViewWithContent();
				registerClickOnNodeContentListView();
			}
		});
	}

	private void registerClickOnNodeContentListView() {

		ListView list = (ListView) findViewById(R.id.node_content_list_view);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				currentlySelectedVisitable = currentlySelectedNode
						.getContentViewAtIndex(position);

				promptEditingDialogueDependingOnContentType();

			}

			private void promptEditingDialogueDependingOnContentType() {

				if (ifCurrentlySelectedVisitableIsTextContent()) {
					openEditingDialogForTextContent();
				} else if (ifCurrentlySelectedVisitableIsButtonContent()) {
					openEditingDialogForButtonContent("");
				} else if (ifCurrentlySelectedVisitableIsImageContent()) {
					openEditingDialogForImageContent();

				}

			}

			private boolean ifCurrentlySelectedVisitableIsImageContent() {
				return currentlySelectedVisitable.getClass().toString()
						.equals(ImageContent.class.toString());
			}

			private boolean ifCurrentlySelectedVisitableIsButtonContent() {
				return currentlySelectedVisitable.getClass().toString()
						.equals(ButtonContent.class.toString());
			}

			private boolean ifCurrentlySelectedVisitableIsTextContent() {
				return currentlySelectedVisitable.getClass().toString()
						.equals(TextContent.class.toString());
			}

		});
	}

	private void openEditingDialogForImageContent() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Retake this Picture?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				pictureIsForNewContent = false;
				startCameraIntent();

			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void openEditingDialogForButtonContent(final String buttonText) {

		final ButtonContent localButtonContent = (ButtonContent) currentlySelectedVisitable;
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter Button Text:");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		input.setText(localButtonContent.getButtonText());
		alert.setView(input);

		alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				localButtonContent.setButtonText(input.getText().toString());
				selectNodeButtonPointsToEditDialog(localButtonContent);
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void selectNodeButtonPointsToEditDialog(
			final ButtonContent buttonContent) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("This Button Points To:");
		NodeDialogAdapter adapter = new NodeDialogAdapter(this, story,
				StoryEditorActivity.this);
		ListView nodeList = new ListView(this);
		nodeList.setAdapter(adapter);

		registerClickOnToSelectWhichNodeButtonPointsTo(nodeList);

		alert.setView(nodeList);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				buttonContent
						.setNodeThisButtonPointsTo(currentlySelectedNodeInDialog);

				currentlySelectedVisitable = (Visitable) buttonContent;
				nodeContentListAdapter.notifyDataSetChanged();
			}
		});

		alert.setNegativeButton("Back", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				openEditingDialogForButtonContent(buttonContent.getButtonText());
			}
		});

		alert.show();

	}

	private void openEditingDialogForTextContent() {

		final TextContent localTextContent = (TextContent) currentlySelectedVisitable;

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Edit Text:");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		input.setText(localTextContent.getText());
		alert.setView(input);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				localTextContent.setText(input.getText().toString());

				currentlySelectedVisitable = (Visitable) localTextContent;
				nodeContentListAdapter.notifyDataSetChanged();
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void setTitleTextFromCreationScreen() {

		setStoryTitleText();
		TextView storyNameTextView = (TextView) findViewById(R.id.new_story_title_textview);

		if (!storyTitleText.equals("")) {

			storyNameTextView.setText(storyTitleText);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.new_story_node_button:
			addNewStoryNode();
			break;

		case R.id.add_text_to_story_node_botton:
			addTextContentToNode();
			break;

		case R.id.add_button_to_story_node_button:
			addButtonContentToNode("");
			break;

		case R.id.add_picture_to_story_node_botton:
			pictureIsForNewContent = true;
			startCameraIntent();
			break;

		case R.id.save_story_button:
			saveStory();
			break;

		case R.id.back_to_main_menu_button:
			returnToMainMenu();
			break;

		}

	}

	private void returnToMainMenu() {

		Intent returnToMainMenu = new Intent(this, MainActivity.class);
		startActivity(returnToMainMenu);
	}

	private void saveStory() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Save this story as:  " + story.getTitle() + "?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				storySaver.saveFileWithContent();
				confirmAlertDialogForSavedStory();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();
	}

	private void confirmAlertDialogForSavedStory() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Story has been saved as: " + story.getTitle());

		alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});

		alert.show();
	}

	private void startCameraIntent() {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);

	}

	private void addButtonContentToNode(String editTextString) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter Button Text:");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		input.setText(editTextString);
		alert.setView(input);

		alert.setPositiveButton("Next", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				selectNodeButtonPointsTo(input.getText().toString());
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void selectNodeButtonPointsTo(final String buttonText) {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter Button Text:");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		alert.setView(input);

		alert.setTitle("This Button Points To:");
		NodeDialogAdapter adapter = new NodeDialogAdapter(this, story,
				StoryEditorActivity.this);
		ListView nodeList = new ListView(this);
		nodeList.setAdapter(adapter);

		registerClickOnToSelectWhichNodeButtonPointsTo(nodeList);

		alert.setView(nodeList);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				addButtonContentToCurrentlySelectedNode(buttonText,
						currentlySelectedNodeInDialog);
			}
		});

		alert.setNegativeButton("Back", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				addButtonContentToNode(buttonText);
			}
		});

		alert.show();

	}

	private void registerClickOnToSelectWhichNodeButtonPointsTo(
			ListView listView) {

		ListView list = listView;
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				currentlySelectedNodeInDialog = story.getNodeList().get(
						position);

			}
		});

	}

	private void addTextContentToNode() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter Text:");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		alert.setView(input);

		alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				addTextContentToCurrentlySelectedNode(input.getText()
						.toString());
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void addNewStoryNode() {

		promptUserForNewNodeName();
		nodeListAdapter.notifyDataSetChanged();

	}

	private void setSelectedNodeToNodeJustAdded() {

		this.currentlySelectedNode = story.getNodeList().get(
				story.getNodeList().size() - 1);
		makeTextViewForCurrentNodeDisplayDefaultNodeName();

		populateNodeContentListViewWithContent();
		registerClickOnNodeContentListView();

	}

	private void promptUserForNewNodeName() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Enter Unique Node Name:");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		alert.setView(input);

		alert.setPositiveButton("Create!",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						if (nodeNameAlreadyInList()) {
							nodeNameAlreadyTakenAlert();
						} else {
							story.addNodeWithName(input.getText().toString());
							setSelectedNodeToNodeJustAdded();

						}
					}

					private boolean nodeNameAlreadyInList() {
						return story.nodeNameAlreadyInList(input.getText()
								.toString());
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});

		alert.show();

	}

	private void nodeNameAlreadyTakenAlert() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Node Name Already Taken! Please Enter Unique Node Name");

		alert.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});

		alert.show();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ImageContent localImageContent = (ImageContent) currentlySelectedVisitable;

		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");

			if (pictureIsForNewContent) {
				addImageContentToCurrentlySelectedNode(photo);
			} else if (!pictureIsForNewContent) {
				localImageContent.setImage(photo);
				currentlySelectedVisitable = (Visitable) localImageContent;
				nodeContentListAdapter.notifyDataSetChanged();
			}

		}

		pictureIsForNewContent = true;
	}

	private void addImageContentToCurrentlySelectedNode(Bitmap photo) {
		this.currentlySelectedNode.addImageContent(photo);
		this.nodeContentListAdapter.notifyDataSetChanged();
	}

	private void addTextContentToCurrentlySelectedNode(String string) {

		this.currentlySelectedNode.addTextContent(string);
		this.nodeContentListAdapter.notifyDataSetChanged();

	}

	private void addButtonContentToCurrentlySelectedNode(String buttonTitle,
			Node nodeButtonPointsTo) {
		this.currentlySelectedNode.addButtonContent(buttonTitle,
				nodeButtonPointsTo);
		this.nodeContentListAdapter.notifyDataSetChanged();
	}

}
