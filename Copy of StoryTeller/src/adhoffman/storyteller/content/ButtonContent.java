package adhoffman.storyteller.content;

import com.google.gson.annotations.Expose;

import adhoffman.storyteller.domain.Node;
import android.view.View;

public class ButtonContent implements Visitable {

	@Expose
	private String buttonText;
	@Expose
	private String type = "button";
	@Expose
	private String nodeNameThisButtonPointsTo;

	public ButtonContent(String buttonText, String nodeButtonPointsTo) {
		this.buttonText = buttonText;
		this.nodeNameThisButtonPointsTo = nodeButtonPointsTo;
	}

	public String getNodeNameThisButtonPointsTo() {
		return this.nodeNameThisButtonPointsTo;
	}

	public String getButtonText() {
		return this.buttonText;
	}

	@Override
	public View accept(Visitor visitor, View view) {
		return visitor.visit(this, view);
	}

	public void setButtonText(String string) {
		this.buttonText = string;

	}

	public void setNodeThisButtonPointsTo(Node currentlySelectedNodeInDialog) {
		this.nodeNameThisButtonPointsTo = currentlySelectedNodeInDialog
				.getNodeName();
	}

	public String getType() {
		return this.type;
	}
}
