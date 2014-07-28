package adhoffman.storyteller.content;

import android.view.View;

public interface Visitor {

	public View visit(TextContent textContent, View view);

	public View visit(ButtonContent buttonContent, View view);

	public View visit(ImageContent imageContent, View view);

}
