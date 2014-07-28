package adhoffman.storyteller.utility;

import java.io.FileOutputStream;

import adhoffman.storyteller.activities.StoryEditorActivity;
import adhoffman.storyteller.domain.Story;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StorySaver {

	private FileOutputStream outputStream;
	private StoryEditorActivity storyEditorActivity;
	private String fileName;
	private Story story;

	public StorySaver(Story story, StoryEditorActivity storyEditorActivity) {

		this.story = story;
		this.storyEditorActivity = storyEditorActivity;
		this.fileName = story.getTitle();
	}

	public void saveFileWithContent() {

		String string = createStoryJsonStringFromGson();

		try {
			outputStream = storyEditorActivity.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createStoryJsonStringFromGson() {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		return gson.toJson(this.story);
	}

}
