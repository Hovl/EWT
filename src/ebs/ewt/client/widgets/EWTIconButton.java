package ebs.ewt.client.widgets;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.widget.button.IconButton;

/**
 * Created by Dubov Aleksei
 * Date: Jul 10, 2008
 * Time: 7:10:43 PM
 * Company: EBS (c) 2008
 */

public class EWTIconButton<T> extends IconButton {
	public interface EWTIconButtonListener<T> {
		public void onEvent(T t);
	}

	private EWTIconButtonListener<T> buttonListener;

	public EWTIconButton(String style) {
		super(style);
	}

	public EWTIconButton(String style, String tooltip) {
		super(style);
		setToolTip(tooltip);
	}

	public EWTIconButton(String style, String tooltip, T t, EWTIconButtonListener<T> listener) {
		this(style, tooltip);

		buttonListener = listener;
		
		addSelectionListener(new EWTSelectionListener<T>(t) {
			@Override
			public void componentSelected(T t, IconButtonEvent ce) {
				buttonListener.onEvent(t);
			}
		});
	}

	public EWTIconButton(String style, String tooltip, EWTSelectionListener<T> listener) {
		this(style, tooltip);
		addSelectionListener(listener);
	}
}
