package adhoffman.storyteller.domain;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Story {

	@Expose
	private String title;
	@Expose
	private ArrayList<Node> storyNodeList;

	public Story(String storyTitle) {
		this.title = storyTitle;
		this.storyNodeList = new ArrayList<Node>();

	}

	public String getTitle() {
		return this.title;
	}

	public ArrayList<Node> getNodeList() {
		return this.storyNodeList;
	}

	public void addNodeWithName(String nodeName) {
		storyNodeList.add(new Node(nodeName));

	}

	public Node getNodeWithName(String nodeName) {

		String currentNodeName;

		for (int i = 0; i < storyNodeList.size(); i++) {

			currentNodeName = storyNodeList.get(i).getNodeName();

			if (nodeName.equals(currentNodeName)) {
				return storyNodeList.get(i);
			}

		}

		return null;

	}

	public Node getFirstNodeInList() {
		return this.storyNodeList.get(0);
	}

	public boolean nodeNameAlreadyInList(String string) {

		for (int i = 0; i < storyNodeList.size(); i++) {

			if (storyNodeList.get(i).getNodeName().equalsIgnoreCase(string)) {
				return true;
			}

		}

		return false;
	}

}
