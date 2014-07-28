package adhoffman.storyteller.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import adhoffman.storyteller.activities.StoryPlayerActivity;
import adhoffman.storyteller.content.Visitable;
import adhoffman.storyteller.domain.Story;

import com.google.gson.GsonBuilder;

public class StoryReader {

	private StoryPlayerActivity storyPlayerActivity;
	private String fileName;
	private FileInputStream fileInputStream;
	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;
	private StringBuilder stringBuilder;
	private String line;

	public StoryReader(String fileName, StoryPlayerActivity storyPlayerActivity) {

		this.fileName = fileName;
		this.storyPlayerActivity = storyPlayerActivity;

		initializefileInputStream();
		this.inputStreamReader = new InputStreamReader(fileInputStream);
		this.bufferedReader = new BufferedReader(this.inputStreamReader);
		this.stringBuilder = new StringBuilder();
		this.line = "";

		loadStoryContentsToString();

	}

	private void loadStoryContentsToString() {
		try {
			while ((this.line = this.bufferedReader.readLine()) != null) {
				this.stringBuilder.append(this.line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initializefileInputStream() {
		try {
			this.fileInputStream = this.storyPlayerActivity
					.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Story getStoryObjectFromFile() {

		String json = this.stringBuilder.toString();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Visitable.class,
				new VisitableDeserializer());

		Story localStory = gsonBuilder.create().fromJson(json, Story.class);

		return localStory;

	}

}
