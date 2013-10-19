package ebs.ewt.client.widgets.grid;

import com.google.gwt.user.client.ui.Widget;

/**
 * Created by Dubov Aleksei
 * Date: Nov 2, 2008
 * Time: 9:14:32 PM
 * Company: EBS (c) 2008
 */

public interface EWTDataRender<T> {
	Widget renderWidget(T o);
	String renderData(T o);
}
