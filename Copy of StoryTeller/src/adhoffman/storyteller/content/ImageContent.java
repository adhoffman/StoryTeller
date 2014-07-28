package adhoffman.storyteller.content;

import adhoffman.storyteller.utility.BitmapConverter;
import android.graphics.Bitmap;
import android.view.View;

import com.google.gson.annotations.Expose;

public class ImageContent implements Visitable {

	@Expose
	private String type = "image";
	@Expose
	private String photoAsString;
	private Bitmap photo;

	public ImageContent(Bitmap photo) {
		this.photo = photo;

		convertBitmapToStringFormat();
	}

	private void convertBitmapToStringFormat() {
		this.photoAsString = BitmapConverter.convertBitmapToString(this.photo);
	}

	public Bitmap getPhoto() {
		return this.photo;
	}

	@Override
	public View accept(Visitor visitor, View view) {
		return visitor.visit(this, view);
	}

	public void setImage(Bitmap newPhoto) {
		this.photo = newPhoto;

	}

	public String getType() {
		return this.type;
	}

	public String getPhotoAsString() {
		return this.photoAsString;
	}

}
