package adhoffman.storyteller.utility;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitmapConverter {

	final static int COMPRESSION_QUALITY = 100;

	public static String convertBitmapToString(Bitmap photo) {

		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
				byteArrayBitmapStream);
		byte[] byteArray = byteArrayBitmapStream.toByteArray();
		String photoAsString = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return photoAsString;
	}

	public static Bitmap convertStringToBitmap(String photoAsString) {

		byte[] decodedString = Base64.decode(photoAsString, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);

		return decodedByte;
	}

}
