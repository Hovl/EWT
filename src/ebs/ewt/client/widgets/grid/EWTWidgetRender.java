package ebs.ewt.client.widgets.grid;

/**
 * Created by Dubov Aleksei
 * Date: Nov 2, 2008
 * Time: 11:15:37 PM
 * Company: EBS (c) 2008
 */

public abstract class EWTWidgetRender<T> implements EWTDataRender<T> {
	public String renderData(T o) {
		return "";
	}
}
