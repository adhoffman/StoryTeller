package adhoffman.storyteller.content;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentViewVisitor implements Visitor {

	@Override
	public View visit(TextContent textContent, View view) {
		TextView textView = (TextView) view;
		textView.setText(textContent.getText());

		return textView;
	}

	@Override
	public View visit(ButtonContent buttonContent, View view) {
		Button button = (Button) view;
		button.setText(buttonContent.getButtonText());

		return button;
	}

	@Override
	public View visit(ImageContent imageContent, View view) {
		ImageView imageView = (ImageView) view;
		imageView.setImageBitmap(imageContent.getPhoto());

		return imageView;
	}

}
