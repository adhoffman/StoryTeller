package adhoffman.storyteller.listadaptors;

import java.util.ArrayList;

import adhoffman.storyteller.R;
import adhoffman.storyteller.activities.MainActivity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileDialogAdapter extends ArrayAdapter<String> {

	private String localFileAsString;
	private MainActivity localMainActivity;
	private ArrayList<String> localFileList;

	public FileDialogAdapter(Context context,
			ArrayList<String> listOfSavedFiles, MainActivity mainActivity) {
		super(context, R.layout.node_item_list_for_dialog, listOfSavedFiles);

		localMainActivity = mainActivity;
		localFileList = listOfSavedFiles;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;

		if (itemView == null) {
			itemView = this.localMainActivity.getLayoutInflater().inflate(
					R.layout.node_item_list_for_dialog, parent, false);

		}

		localFileAsString = localFileList.get(position);
		TextView textView = (TextView) itemView
				.findViewById(R.id.item_node_list_view_text_for_dialogue);

		textView.setText(localFileAsString);

		return itemView;
	}

}
