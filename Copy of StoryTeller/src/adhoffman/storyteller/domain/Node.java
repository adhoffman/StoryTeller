package adhoffman.storyteller.domain;

import java.util.ArrayList;

import adhoffman.storyteller.content.ButtonContent;
import adhoffman.storyteller.content.ImageContent;
import adhoffman.storyteller.content.TextContent;
import adhoffman.storyteller.content.Visitable;
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;

public class Node {

	@Expose
	private String nodeName;
	@Expose
	private ArrayList<Visitable> nodeContentList;

	public Node(String nodeName) {
		this.nodeName = nodeName;
		this.nodeContentList = new ArrayList<Visitable>();

	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void clearContent() {
		nodeContentList.clear();
	}

	public Visitable getContentViewAtIndex(int index) {
		return nodeContentList.get(index);
	}

	public boolean contentListIsEmpty() {
		return nodeContentList.isEmpty();
	}

	public ArrayList<Visitable> getContentList() {
		return this.nodeContentList;
	}

	public void addTextContent(String string) {
		this.nodeContentList.add(new TextContent(string));
	}

	public void addButtonContent(String buttonTitle, Node nodeButtonPointsTo) {

		this.nodeContentList.add(new ButtonContent(buttonTitle,
				nodeButtonPointsTo.getNodeName()));
	}

	public void addImageContent(Bitmap photo) {
		this.nodeContentList.add(new ImageContent(photo));
	}

	public void deleteContentAtIndex(int position) {
		this.nodeContentList.remove(position);
	}

	public int getCurrentListSize() {
		return this.nodeContentList.size();
	}

}
