package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;

/**
 * Created by Dubov Aleksei
 * Date: Dec 26, 2008
 * Time: 5:57:47 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTSelectionListener<T> extends SelectionListener<IconButtonEvent> {
	private T t = null;

	protected EWTSelectionListener() {
	}

	protected EWTSelectionListener(T t) {
		this.t = t;
	}

	public void componentSelected(IconButtonEvent ce) {
		componentSelected(t, ce);
	}

	public abstract void componentSelected(T t, IconButtonEvent ce);
}
