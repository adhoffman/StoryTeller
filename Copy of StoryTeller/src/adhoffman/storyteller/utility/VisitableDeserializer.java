package adhoffman.storyteller.utility;

import java.lang.reflect.Type;

import adhoffman.storyteller.content.ButtonContent;
import adhoffman.storyteller.content.ImageContent;
import adhoffman.storyteller.content.TextContent;
import adhoffman.storyteller.content.Visitable;
import android.graphics.Bitmap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class VisitableDeserializer implements JsonDeserializer<Visitable> {

	private String textContentType = "text";
	private String buttonContentType = "button";
	private String imageContentType = "image";
	private JsonElement localJsonElement;

	@Override
	public Visitable deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext deserializationContent)
			throws JsonParseException {
		localJsonElement = jsonElement;

		if (ifJsonElementIsTextContent()) {

			String textContent = localJsonElement.getAsJsonObject()
					.get("textContent").getAsString();
			return new TextContent(textContent);
		} else if (ifJsonElementIsButtonContent()) {

			String buttonText = localJsonElement.getAsJsonObject()
					.get("buttonText").getAsString();
			String nodeNameThisButtonPointsTo = localJsonElement
					.getAsJsonObject().get("nodeNameThisButtonPointsTo")
					.getAsString();

			return new ButtonContent(buttonText, nodeNameThisButtonPointsTo);

		} else if (ifJsonElementIsImageContent()) {

			String photoAsString = localJsonElement.getAsJsonObject()
					.get("photoAsString").getAsString();

			Bitmap photo = BitmapConverter.convertStringToBitmap(photoAsString);

			return new ImageContent(photo);
		} else {
			return null;
		}
	}

	private boolean ifJsonElementIsImageContent() {
		return localJsonElement.getAsJsonObject().get("type").getAsString()
				.equalsIgnoreCase(imageContentType);
	}

	private boolean ifJsonElementIsButtonContent() {
		return localJsonElement.getAsJsonObject().get("type").getAsString()
				.equalsIgnoreCase(buttonContentType);
	}

	private boolean ifJsonElementIsTextContent() {
		return localJsonElement.getAsJsonObject().get("type").getAsString()
				.equalsIgnoreCase(textContentType);
	}
}
