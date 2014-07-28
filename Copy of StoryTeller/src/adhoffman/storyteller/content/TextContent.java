package adhoffman.storyteller.content;

import com.google.gson.annotations.Expose;

import android.view.View;

public class TextContent implements Visitable {

	@Expose
	private String type = "text";
	@Expose
	private String textContent;

	public TextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getText() {
		return this.textContent;
	}

	@Override
	public View accept(Visitor visitor, View view) {
		return visitor.visit(this, view);
	}

	public void setText(String string) {
		this.textContent = string;
	}

	public String getType() {
		return this.type;
	}

}
