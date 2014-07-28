package adhoffman.storyteller.content;

import android.view.View;

public interface Visitable {

	public View accept(Visitor visitor, View view);
}
